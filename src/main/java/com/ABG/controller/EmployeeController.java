package com.ABG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ABG.Repository.UserRepository;
import com.ABG.model.Employee;
import com.ABG.model.User;
import com.ABG.service.EmployeeImportService;
import com.ABG.DTO.EmployeeDTO;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeImportService employeeImportService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int per_page,
            @RequestParam(required = false) String search) {
        
        try {
            Page<Employee> employeePage = employeeImportService.getEmployees(page, per_page, search);
            
            // Convert to DTOs
            List<EmployeeDTO> employeeDTOs = employeePage.getContent()
                .stream()
                .map(EmployeeDTO::from)
                .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("employees", employeeDTOs);
            response.put("meta", Map.of(
                "current_page", employeePage.getNumber() + 1,
                "per_page", employeePage.getSize(),
                "total_entries", employeePage.getTotalElements(),
                "total_pages", employeePage.getTotalPages()
            ));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Failed to fetch employees: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/import")
    public ResponseEntity<?> importEmployees(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "currentUserId", required = false) Long currentUserId,
            @RequestParam(value = "projectId", required = false) Long projectId,
            @RequestParam(value = "operatorId", required = false) Long operatorId) {

        System.out.println("DEBUG: currentUserId = " + currentUserId);
        System.out.println("DEBUG: projectId = " + projectId);
        System.out.println("DEBUG: operatorId = " + operatorId);
        System.out.println("DEBUG: file = " + (file != null ? file.getOriginalFilename() : "null"));
        
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "No file uploaded"));
        }
        
        try {
            // Validate operator if provided
            if (operatorId != null) {
                User operator = userRepository.findByIdAndTags_Name(operatorId, "operator")
                    .orElseThrow(() -> new RuntimeException("Invalid operator selected"));
            }
            
            // Save file temporarily
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String uploadDir = "temp/";
            Path dirPath = Paths.get(uploadDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            
            String filePath = uploadDir + "employee_import_" + timestamp + ".xlsx";
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes());
            
            // Start background job
            employeeImportService.processEmployeeImport(filePath, currentUserId, projectId, operatorId);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "File uploaded successfully. Import process started."
            ));
        } catch (IOException e) {
            return ResponseEntity.unprocessableEntity().body(Map.of(
                "error", "Failed to process file: " + e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}