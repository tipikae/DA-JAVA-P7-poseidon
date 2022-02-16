package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Get login page.
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Get all users.
     * @return ModelAndView
     */
    @GetMapping("/secure/users")
    public ModelAndView getAllUsers() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Get error page
     * @return ModelAndView
     */
    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("error/403");
        return mav;
    }
}
