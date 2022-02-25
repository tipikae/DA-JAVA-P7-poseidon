package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * User repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	/**
	 * Find a user by username.
	 * @param username
	 * @return Optional<User>
	 */
	Optional<User> findByUsername(String username);
}
