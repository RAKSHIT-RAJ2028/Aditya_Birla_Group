package com.ABG.Security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ABG.Repository.UserRepository;
import com.ABG.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{
	private final CustomOAuth2UserService customOAuth2UserService;

	public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
		this.customOAuth2UserService = customOAuth2UserService;
	}
	
//	 @Bean
//	     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        http.csrf(AbstractHttpConfigurer::disable)
//	                .authorizeHttpRequests(auth -> auth
//	                        .requestMatchers("/auth/**").permitAll()
//	                        .anyRequest().authenticated()
//	                )
//	                .httpBasic(Customizer.withDefaults());
//
//	        return http.build();
//	    }
	 
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	  
		http
	        .csrf(AbstractHttpConfigurer::disable)
	        .authorizeHttpRequests(auth -> auth
	            // .requestMatchers("/auth/**").permitAll()
				.requestMatchers("/auth/send-otp", "/auth/verify-otp", "/auth/**", "/register", "/login","/api/employees/import","/api/employees/**").permitAll()
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	            .requestMatchers("/user/**").hasRole("USER")
	            .anyRequest().authenticated()
	        )
			 .oauth2Login(oauth2 -> oauth2
					.loginPage("/login")
					.defaultSuccessUrl("http://localhost:3000/oauth2-redirect", true)
					.failureUrl("http://localhost:3000/login?error")
					.authorizationEndpoint(authorization -> authorization
						.baseUri("/oauth2/authorization")
					)
					.redirectionEndpoint(redirection -> redirection
						.baseUri("/login/oauth2/code/*")
					)
					.userInfoEndpoint(userInfo -> userInfo
						.userService(customOAuth2UserService)
					)
				)
				.exceptionHandling(exception -> exception
					.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				)
	        // .sessionManagement(session -> session.disable()) // Disable session if using JWT (optional)
	        // .formLogin(AbstractHttpConfigurer::disable) // Disable form login
	        // .httpBasic(AbstractHttpConfigurer::disable) // Disable browser popup login
	        .cors(cors -> cors.configurationSource(corsConfigurationSource()));

	    return http.build();
	}

	 
	 @Bean
	  CorsConfigurationSource corsConfigurationSource() {
	     CorsConfiguration configuration = new CorsConfiguration();
	     configuration.setAllowedOrigins(List.of("*")); // Adjust this as needed
	     configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	     configuration.setAllowedHeaders(List.of("*"));

	     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	     source.registerCorsConfiguration("/**", configuration);

	     return source;
	 }

	    @Bean
	     UserDetailsService userDetailsService(UserRepository userRepository) {
	        return username -> userRepository.findByUsername(username)
	                .map(user -> new org.springframework.security.core.userdetails.User(
	                        user.getUsername(),
	                        user.getPassword(),
	                        List.of(new SimpleGrantedAuthority(user.getRole()))
	                ))
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	    }

	    @Bean
	    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	    
	    @Bean
	     PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
