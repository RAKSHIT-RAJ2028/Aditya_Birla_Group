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

}
 