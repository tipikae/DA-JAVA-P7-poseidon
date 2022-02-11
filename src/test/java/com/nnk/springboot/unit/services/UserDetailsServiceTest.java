package com.nnk.springboot.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private static UserDetailsServiceImpl userDetailsService;
	
	private static String username = "bob"; 
	private static String password = "123456789";
	private static User user;
	
	@BeforeAll
	private static void setUp() {
		userDetailsService = new UserDetailsServiceImpl();
		user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("USER");
	}

	@Test
	void testLoadUserByUsernameWhenFound() {
		when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
		assertEquals(org.springframework.security.core.userdetails.User.class, 
				userDetailsService.loadUserByUsername(username).getClass());
	}

	@Test
	void testLoadUserByUsernameWhenNotFound() {
		when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
	}
}
