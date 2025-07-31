package com.ABG.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ABG.Repository.EmployeeProjectRepository;
import com.ABG.Repository.EmployeeRepository;
import com.ABG.Repository.EmployeeUserRepository;
import com.ABG.Repository.ProjectDepartmentRepository;
import com.ABG.Repository.ProjectRepository;
import com.ABG.Repository.UserRepository;
import com.ABG.model.Branch;
import com.ABG.model.Company;
import com.ABG.model.Employee;
import com.ABG.model.EmployeeProject;
import com.ABG.model.Project;
import com.ABG.model.ProjectDepartment;
import com.ABG.model.User;



import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class EmployeeImportService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeImportService.class);
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProjectDepartmentRepository projectDepartmentRepository;
    
    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;
    
//    @Autowired
//    private EmployeeUserRepository employeeUserRepository;
   
    @Autowired
    private SalaryComponentService salaryComponentService;
    
    @Async
    @Transactional
    public void processEmployeeImport(String filePath, Long currentUserId, Long projectId, Long operatorId)
    {
        long startTime = System.currentTimeMillis();
        int importedCount = 0;
        int skippedCount = 0;
        List<Map<String, Object>> skippedUsers = new ArrayList<>();
        
        try {
            User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
            Company company = currentUser.getCompany();
            Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
            Branch branch = currentUser.getBranch() != null ? 
                currentUser.getBranch() : project.getBranch();
         
         // Validate operator if provided
            User operator = null;
            if (operatorId != null) {
                operator = userRepository.findByIdAndTags_Name(operatorId, "operator")
                    .orElseThrow(() -> {
                        String errorMsg = "Invalid operator ID: " + operatorId;
                        logger.error(errorMsg);

                        // Convert operatorId to String to match createSkippedUserEntry method signature
                        Map<String, String> operatorMap = Map.of("operator_id", String.valueOf(operatorId));

                        skippedUsers.add(createSkippedUserEntry(
                            0, 
                            "invalid_operator", 
                            operatorMap, 
                            errorMsg, 
                            null
                        ));

                        return new RuntimeException(errorMsg);
                    });
            }

            
            
            // Ensure default salary components exist
            salaryComponentService.createDefaultSalaryComponents(company);
            
            // Process Excel file
            try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) 
            {
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                
                // Get headers from row 2 (0-based index 1)
                Row headerRow = sheet.getRow(1);
                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(cell.getStringCellValue().trim());
                }
                
                // Process data rows starting from row 3 (0-based index 2)
                for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null || isRowEmpty(row)) {
                        continue;
                    }
                    
                    try {
                        Map<String, String> employeeData = extractRowData(row, headers);
                        if (employeeData.get("NAME OF EMPLOYEE") == null || 
                            employeeData.get("NAME OF EMPLOYEE").trim().isEmpty()) {
                            skippedCount++;
                            skippedUsers.add(createSkippedUserEntry(i+1, "missing_name", 
                                employeeData, "Missing employee name", null));
                            continue;
                        }
                        
                        Map<String, Object> validationResult = validateEmployeeData(employeeData);
                        if (!(boolean) validationResult.get("valid")) {
                            skippedCount++;
                            skippedUsers.add(createSkippedUserEntry(i+1, 
                                (String) validationResult.get("reason"), 
                                employeeData, 
                                (String) validationResult.get("message"), 
                                (List<String>) validationResult.get("errors")));
                            continue;
                        }
                        
                        Employee employee = processEmployeeRecord(
                            employeeData, company, branch, project);
                        
                        if (employee != null) {
                            importedCount++;
                            // Add CTC salary components (future implementation)
                            salaryComponentService.processEmployeeSalaryComponents(
                                employee, employeeData, company, project);
                        }
                    } catch (Exception e) {
                        skippedCount++;
                        skippedUsers.add(createSkippedUserEntry(i+1, "processing_error", 
                            null, "Error processing row: " + e.getMessage(), null));
                        logger.error("Error processing row {}: {}", i+1, e.getMessage());
                    }
                }
            }
            
            // Create employee-operator assignments if operator exists
            if (operator != null) {
                createEmployeeOperatorAssignments(operator);
            }
            
        } catch (Exception e) {
            logger.error("Error processing employee import: {}", e.getMessage(), e);
        } finally {
            try {
                Files.deleteIfExists(Paths.get(filePath));
            } catch (IOException e) {
                logger.warn("Could not delete temp file: {}", filePath, e);
            }
            
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Import completed. Imported: {}, Skipped: {}, Duration: {}ms", 
                importedCount, skippedCount, duration);
        }
    }
    
    private Map<String, String> extractRowData(Row row, List<String> headers) {
        Map<String, String> data = new HashMap<>();
        for (int j = 0; j < headers.size(); j++) {
            Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String value = "";
            
            switch (cell.getCellType()) {
                case STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        value = cell.getDateCellValue().toString();
                    } else {
                        // Remove .0 from integer values
                        double numValue = cell.getNumericCellValue();
                        if (numValue == (int) numValue) {
                            value = String.valueOf((int) numValue);
                        } else {
                            value = String.valueOf(numValue);
                        }
                    }
                    break;
                case BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                default:
                    value = "";
            }
            
            data.put(headers.get(j), value);
        }
        return data;
    }
    
    private boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
    
    private Map<String, Object> validateEmployeeData(Map<String, String> employeeData) {
        List<String> missingFields = new ArrayList<>();
           // Required fields - adjust as needed
        if (isEmpty(employeeData.get("NAME OF EMPLOYEE"))) {
            missingFields.add("employee_name");
        }
        if (isEmpty(employeeData.get("EMPLOYEE ID"))) {
            missingFields.add("employee_code");
        }
        if (isEmpty(employeeData.get("ADHAR NUMBER"))) {
            missingFields.add("aadhar_number");
        }
        if (isEmpty(employeeData.get("DATE OF JOINING"))) {
            missingFields.add("date_of_joining");
        }
            
        if (!missingFields.isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("valid", false);
            result.put("reason", "missing_required_fields");
            result.put("data", employeeData);
            result.put("message", "Missing required fields: " + String.join(", ", missingFields));
            result.put("errors", missingFields);
            return result;
        }
        
        return Map.of("valid", true);
    }
    
    private Employee processEmployeeRecord(Map<String, String> employeeData, 
            Company company, Branch branch, Project project) {
        
        String employeeCode = employeeData.get("EMPLOYEE ID");
        Employee employee = employeeRepository.findByEmployeeCodeAndCompanyId(
            employeeCode, company.getId())
            .orElse(new Employee());
        
        // Formatters
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        
        // Set employee attributes
        employee.setFullName(employeeData.get("NAME OF EMPLOYEE"));
        employee.setFathersName(employeeData.get("FATHER NAME"));
        employee.setHusbandsName(employeeData.get("HUSBAND NAME"));

        employee.setDoj(toLocalDate(parseDate(employeeData.get("DATE OF JOINING"))));
        employee.setDob(toLocalDate(parseDate(employeeData.get("DATE OF BIRTH"))));
        
        employee.setGender(employeeData.get("GENDER"));
        employee.setHighestQualification(employeeData.get("QUALIFICATION"));
//        employee.setYearOfPassout(parseDate(employeeData.get("QUALIFICATIONS")));
        
        Date passoutDate = parseDate(employeeData.get("QUALIFICATIONS"));
        if (passoutDate != null) {
            employee.setYearOfPassout(yearFormat.format(passoutDate));
        }
        
        employee.setOrgDetails(employeeData.get("PREVIOUS ORGANIZATION DETAILS"));
        employee.setYearOfExperience(employeeData.get("YEAR OF EXPERIENCE"));
        employee.setMobileNumber(employeeData.get("MOBILE NO"));
        employee.setCompanyEmail(employeeData.get("MAIL ID"));
        employee.setAadharNumber(employeeData.get("ADHAR NUMBER"));
        employee.setPan(employeeData.get("PAN NUMBER"));
        employee.setDrivingLicenseNumber(employeeData.get("DRIVING LICENCE"));
//        employee.setPoliceVerificationAndValidity(
//            parseDate(employeeData.get("POLICE VERIFICATION AND VALIDITY DATE")));
        

        Date policeDate = parseDate(employeeData.get("POLICE VERIFICATION AND VALIDITY DATE"));
        if (policeDate != null) {
            employee.setPoliceVerificationAndValidity(dateFormat.format(policeDate));
        }
        employee.setBankName(employeeData.get("BANK NAME"));
        employee.setAccountNumber(employeeData.get("BANK ACCOUNT NUMBER"));
        employee.setIfscCode(employeeData.get("IFSC CODE"));
        employee.setUanNumber(employeeData.get("UAN NO"));
        employee.setEsicNumber(employeeData.get("ESIC NUMBER"));
        employee.setLocation(employeeData.get("LOCATION"));
        employee.setEmployeeCode(employeeCode);
        employee.setDesignation(employeeData.get("DESIGNATION/POSITION"));
        employee.setResignedAt(toLocalDate(parseDate(employeeData.get("DATE OF EXIT"))));
        employee.setState(employeeData.get("STATE"));
        employee.setCity(employeeData.get("CITY"));
        employee.setAddress(employeeData.get("ADDRESS OF EMPLOYEE"));
        employee.setCompany(company);
        employee.setBranch(branch);
        
        try {
            employee = employeeRepository.save(employee);
            
            // Add to project and department
            ProjectDepartment department = processDepartment(
                employeeData, project);
            
            if (department != null) {
                addEmployeeToProject(employee, project, department);
            }
            
            return employee;
        } catch (Exception e) {
            logger.error("Error saving employee {}: {}", 
                employeeData.get("NAME OF EMPLOYEE"), e.getMessage());
            return null;
        }
    }
    
    private Date parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        List<String> dateFormats = Arrays.asList("dd/MM/yyyy", "yyyy-MM-dd", "dd.MM.yyyy");

        for (String format : dateFormats) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf.setLenient(false);
                return sdf.parse(dateString);
            } catch (ParseException e) {
                logger.warn("Could not parse date with format ({}): {}", format, dateString);
            }
        }

        logger.warn("Could not parse date with any known format: {}", dateString);
        return null;
    }

    private ProjectDepartment processDepartment(
            Map<String, String> employeeData, Project project) {
        
        String departmentName = employeeData.get("DEPARTMENT NAME");
        String subDepartmentName = employeeData.get("SUB DEPARTMENT NAME");
        
        if (departmentName == null || departmentName.trim().isEmpty() ||
            subDepartmentName == null || subDepartmentName.trim().isEmpty()) {
            logger.warn("Missing department information");
            return null;
        }
        
        try {
            // Find or create parent department
            ProjectDepartment department = projectDepartmentRepository
                .findByDepartmentNameAndProjectId(departmentName, project.getId())
                .orElseGet(() -> {
                    ProjectDepartment newDept = new ProjectDepartment();
                    newDept.setDepartmentName(departmentName);
                    newDept.setProject(project);
                    return projectDepartmentRepository.save(newDept);
                });
            
            // Find or create sub-department
            return projectDepartmentRepository
                .findByDepartmentNameAndProjectIdAndParentDepartmentId(
                    subDepartmentName, project.getId(), department.getId())
                .orElseGet(() -> {
                    ProjectDepartment newSubDept = new ProjectDepartment();
                    newSubDept.setDepartmentName(subDepartmentName);
                    newSubDept.setProject(project);
                    newSubDept.setParentDepartment(department);
                    return projectDepartmentRepository.save(newSubDept);
                });
        } catch (Exception e) {
            logger.error("Error processing department: {}", e.getMessage());
            return null;
        }
    }
    
    private void addEmployeeToProject(Employee employee, 
            Project project, ProjectDepartment department) {
        
        if (!employeeProjectRepository.existsByEmployeeIdAndProjectIdAndProjectDepartmentId(
            employee.getId(), project.getId(), department.getId())) {
            
            EmployeeProject employeeProject = new EmployeeProject();
            employeeProject.setEmployee(employee);
            employeeProject.setProject(project);
            employeeProject.setProjectDepartment(department);
            
            try {
                employeeProjectRepository.save(employeeProject);
            } catch (Exception e) {
                logger.error("Error adding employee to project: {}", e.getMessage());
            }
        }
    }
    
    private void createEmployeeOperatorAssignments(User operator) {
        // Implementation for assigning employees to operator
        // Similar to Rails version
    }
    
    private Map<String, Object> createSkippedUserEntry(int rowNumber, String reason, 
            Map<String, String> data, String message, List<String> errors) {
        
        Map<String, Object> entry = new HashMap<>();
        entry.put("row_number", rowNumber);
        entry.put("reason", reason);
        entry.put("data", data);
        entry.put("message", message);
        entry.put("validation_errors", errors);
        return entry;
    }
    
    private LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    private boolean isEmpty(String value) 
        {
            return value == null || value.trim().isEmpty();
        }
    
    public Page<Employee> getEmployees(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (search != null && !search.isEmpty()) {
            return employeeRepository.searchEmployees(search, pageable);
        }
        return employeeRepository.findAll(pageable);
    }

    
}