package com.ABG.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ABG.Repository.UserRepository;
import com.ABG.model.User;

@Service
public class UserServiceImpl {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public User registerUser(String username, String password, String role)
	{
	 if(	userRepository.findByUsername(username).isPresent()) 
	   {
		throw new RuntimeException("User already exist");
	   }
	 User user = new User();
	 user.setUsername(username);
	 user.setPassword(passwordEncoder.encode(password));
	 user.setRole(role);
	 
	 return userRepository.save(user);
	}
	
	public List<User> getAllUser()
	{
		return userRepository.findAll();
	}

   public Optional<User> findByUsernameOrEmail(String identifier) {
        return userRepository.findByUsernameOrEmail(identifier);
    }
	
	
}
