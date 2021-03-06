package com.nnk.springboot.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.util.IAuthenticationInformation;

/**
 * Custom mapping error controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class CustomErrorController implements ErrorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);
	
	@Autowired
	private IAuthenticationInformation authenticationInfo;

	/**
     * Get error 403 page.
	 * @param principal
     * @return ModelAndView
     */
    @GetMapping("/error")
    public ModelAndView error(Principal principal) {
    	LOGGER.debug("Getting error page.");
        ModelAndView mav = new ModelAndView();
		String username = authenticationInfo.getUsername(principal);
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.addObject("username", username);
        mav.setViewName("error/403");
        return mav;
    }
}
