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
	@Test
	void adminHomereturnsBidLists() throws Exception {
		mockMvc.perform(get("/admin/home"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/bidList/list"));
	}
}
