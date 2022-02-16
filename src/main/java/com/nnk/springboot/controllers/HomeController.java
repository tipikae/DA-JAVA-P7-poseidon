package com.nnk.springboot.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Get home page.
	 * @param model
	 * @return String
	 */
	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}

	/**
	 * Get admin home page
	 * @param request
	 * @param model
	 * @return String
	 */
	@GetMapping("/admin/home")
	public String adminHome(HttpServletRequest request, Model model) {
		return "redirect:/bidList/list";
	}


}
