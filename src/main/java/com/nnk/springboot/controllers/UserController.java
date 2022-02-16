package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.NewUserDTO;
import com.nnk.springboot.dto.UpdateUserDTO;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.services.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
    @Autowired
    private IUserService userService;

    /**
     * Get all users.
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/user/list")
    public String home(HttpServletRequest request, Model model) {
    	LOGGER.debug("Getting all users.");
		try {
			List<UserDTO> dtos = userService.getAllItems();
	        model.addAttribute("users", dtos);
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
        return "user/list";
    }

    /**
     * Get add user form.
     * @param request
     * @param model
     * @return String
     */
    @GetMapping("/user/add")
    public String addUserForm(HttpServletRequest request, Model model) {
    	LOGGER.debug("Getting add user form");
    	if(!model.containsAttribute("user")) {
    		model.addAttribute("user", new UserDTO());
    	}
        return "user/add";
    }

    /**
     * Add a user.
     * @param request
     * @param newUserDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/user/validate")
    public String validate(
    		HttpServletRequest request, 
    		@ModelAttribute("user") @Valid NewUserDTO newUserDTO, 
    		BindingResult result, 
    		Model model) {

    	LOGGER.debug("Posting a user");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/user/add?error=" + sb;
    	}
    	
    	try {
			UserDTO user = userService.addItem(newUserDTO);
			model.addAttribute("user", user);
	    	return "redirect:/user/list?success=New User added.";
		} catch (ServiceException e) {
			LOGGER.debug("Validate: ServiceException: " + e.getMessage());
			return "redirect:/user/add?error=Unable to process new User.";
		} catch (ConverterException e) {
			LOGGER.debug("Validate: ConverterException: " + e.getMessage());
			return "redirect:/user/add?error=Unable to process new User.";
		} catch (Exception e) {
			LOGGER.debug("Validate: Exception: " + e.getMessage());
			return "redirect:/user/add?error=Unable to process new User.";
		}
    }

    /**
     * Get update user form.
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a user to update");

		if(model.containsAttribute("user")) {
			return "user/update";
		}
    	
    	try {
			UserDTO user = userService.getItemById(id);
			model.addAttribute("user", user);
			return "user/update";
		} catch (NotFoundException e) {
			LOGGER.debug("ShowUpdateForm: NotFoundException: " + e.getMessage());
			return "error/404";
		} catch (ConverterException e) {
			LOGGER.debug("ShowUpdateForm: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("ShowUpdateForm: Exception: " + e.getMessage());
			return "error/400";
		}
    }

    /**
     * Update a user.
     * @param request
     * @param id
     * @param updateUserDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		@ModelAttribute("user") @Valid UpdateUserDTO updateUserDTO,
            BindingResult result, 
            Model model) {
    	
    	LOGGER.debug("Updating a user");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/user/update/" + id + "?error=" + sb;
    	}
    	
    	try {
    		userService.updateItem(id, updateUserDTO);
            return "redirect:/user/list?success=User has been updated.";
		} catch (NotFoundException e) {
			LOGGER.debug("updateUser: NotFoundException: " + e.getMessage());
			return "redirect:/user/update/" + id + "?error=User not found.";
		} catch (Exception e) {
			LOGGER.debug("updateUser: Exception: " + e.getMessage());
			return "redirect:/user/update/" + id + "?error=Unable to process update User.";
		}
    }

    /**
     * Delete a user.
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a user");
    	try {
    		userService.deleteItem(id);
		} catch (NotFoundException e) {
			LOGGER.debug("deleteUser: NotFoundException: " + e.getMessage());
			return "redirect:/user/delete/" + id + "?error=User not found.";
		} catch (Exception e) {
			LOGGER.debug("deleteUser: Exception: " + e.getMessage());
			return "redirect:/user/delete/" + id + "?error=Unable to process delete User.";
		}
        return "redirect:/user/list?success=User has been deleted.";
    }
}
