package com.ABG.service;

import java.util.List;

import com.ABG.model.User;

public interface UserService 
{
	
	
	List<User> getAllUser();
	User registerUser(String username, String password, String role);

}
