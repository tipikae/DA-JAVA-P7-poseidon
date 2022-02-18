package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);

	/**
     * Get error 403 page.
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
