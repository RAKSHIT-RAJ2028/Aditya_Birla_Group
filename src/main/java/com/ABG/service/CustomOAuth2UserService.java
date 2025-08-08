package com.ABG.service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ABG.Repository.UserRepository;
import com.ABG.model.Branch;
import com.ABG.model.Company;
import com.ABG.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    
    // Try to use BranchRepository and CompanyRepository in future
    @PersistenceContext
    private EntityManager entityManager;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        String email = extractEmail(attributes, provider);
        String name = extractName(attributes, provider);
        
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;
        
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!provider.equals(user.getProvider())) {
                user.setProvider(provider + "," + user.getProvider());
                user = userRepository.save(user);
            }
        } else {
        	
        	// Load default branch and company using EntityManager // Try to use BranchRepository and CompanyRepository in future
            Branch defaultBranch = entityManager.find(Branch.class, 1L);
            Company defaultCompany = entityManager.find(Company.class, 1L);

            if (defaultBranch == null || defaultCompany == null) {
                throw new RuntimeException("Default branch or company with ID = 1 not found.");
            }
            
            user = new User();
            user.setUsername(email);
            user.setEmail(email);
            user.setName(name);
            user.setProvider(provider);
            user.setRole("ROLE_USER");
            user.setBranch(defaultBranch);       // Set default branch
            user.setCompany(defaultCompany);     // Set default company
            
            user = userRepository.save(user);
        }
        
        // Create authorities for the user
        var authorities = Collections.singletonList(
            new SimpleGrantedAuthority(user.getRole())
        );
        
        // Return a DefaultOAuth2User with the proper attributes
        return new DefaultOAuth2User(
            authorities,
            attributes,
            userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName()
        );
    }

    private String extractEmail(Map<String, Object> attributes, String provider) {
        if ("google".equals(provider)) {
            return (String) attributes.get("email");
        } else if ("github".equals(provider)) {
            String email = (String) attributes.get("email");
            return email != null ? email : (String) attributes.get("login") + "@github.com";
        }
        throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
    }

    private String extractName(Map<String, Object> attributes, String provider) {
        if ("google".equals(provider)) {
            return (String) attributes.get("name");
        } else if ("github".equals(provider)) {
            String name = (String) attributes.get("name");
            return name != null ? name : (String) attributes.get("login");
        }
        return "Unknown";
    }
}