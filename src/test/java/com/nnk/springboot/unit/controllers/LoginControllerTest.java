package com.nnk.springboot.unit.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.repositories.UserRepository;

@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// login
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	void loginReturnsLogin() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// getAllUsers
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	void getAllUsersReturnsUsers() throws Exception {
		mockMvc.perform(get("/secure/users"))
		.andExpect(status().isOk())
		.andExpect(view().name("user/list"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// error
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	void errorReturnsError() throws Exception {
		mockMvc.perform(get("/error"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/403"));
	}
}
