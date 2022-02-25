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

import com.nnk.springboot.controllers.HomeController;

@WebMvcTest(controllers = HomeController.class)
class HomeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserDetailsService userDetailsService;

	/////////////////////////////////////////////////////////////////////////////////////////////
	// home
	/////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	void homeReturnsHome() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("home"));
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// adminHome
	/////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser(roles = {"ADMIN"})
	@Test
	void adminHomereturnsUserList() throws Exception {
		mockMvc.perform(get("/admin/home"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/user/list"));
	}
	
	@WithMockUser(roles = {"USER"})
	@Test
	void adminHomereturns403WhenBadRole() throws Exception {
		mockMvc.perform(get("/admin/home"))
			.andExpect(status().is(403));
	}
}
