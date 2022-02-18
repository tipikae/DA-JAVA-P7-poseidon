package com.nnk.springboot.unit.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.CustomErrorController;

@WebMvcTest(controllers = CustomErrorController.class)
class CustomErrorControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// error
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void errorReturnsError() throws Exception {
		mockMvc.perform(get("/error"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/403"));
	}
}
