package com.nnk.springboot.controllers;

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
		LOGGER.debug("Getting home page.");
		return "home";
	}

	/**
	 * Get admin home page
	 * @param model
	 * @return String
	 */
	@GetMapping("/admin/home")
	public String adminHome(Model model) {
		LOGGER.debug("Getting admin home page.");
		return "redirect:/user/list";
	}


}
