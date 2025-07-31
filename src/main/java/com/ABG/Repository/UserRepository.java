package com.ABG.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ABG.model.User;

public interface UserRepository  extends JpaRepository<User , Long> 
{
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.username = :identifier OR u.email = :identifier")
    Optional<User> findByUsernameOrEmail(@Param("identifier") String identifier);
	
	
	@Query("SELECT u FROM User u " +
		       "JOIN ResourceTag rt ON rt.resourceId = u.id AND rt.resourceType = 'User' " +
		       "JOIN Tag t ON rt.tag = t " +
		       "WHERE u.id = :userId AND t.name = :tagName")
		Optional<User> findByIdAndTags_Name(@Param("userId") Long userId, @Param("tagName") String tagName);


}
 