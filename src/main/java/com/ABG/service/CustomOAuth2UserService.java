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
import com.ABG.model.User;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

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
            user = new User();
            user.setUsername(email);
            user.setEmail(email);
            user.setName(name);
            user.setProvider(provider);
            user.setRole("ROLE_USER");
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