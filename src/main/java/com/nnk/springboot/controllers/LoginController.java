package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	/**
     * Get login page.
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView login() {
    	LOGGER.debug("Getting login page.");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("security/login");
        return mav;
    }

    /**
     * Get error page
     * @return ModelAndView
     */
    @GetMapping("/error")
    public ModelAndView error() {
    	LOGGER.debug("Getting error page.");
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("error/403");
        return mav;
    }
}
