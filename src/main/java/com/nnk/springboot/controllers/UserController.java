package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.NewUserDTO;
import com.nnk.springboot.dto.UpdateUserDTO;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.services.IUserService;
import com.nnk.springboot.util.IAuthenticationInformation;

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

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * User controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
    @Autowired
    private IUserService userService;
	
	@Autowired
	private IAuthenticationInformation authenticationInfo;

    /**
     * Get all users.
     * @param model
	 * @param principal
     * @return String
     */
    @GetMapping("/user/list")
    public String home(Model model, Principal principal) {
    	LOGGER.debug("Getting all users.");
		try {
			List<UserDTO> dtos = userService.getAllItems();
			String username = authenticationInfo.getUsername(principal);
	        model.addAttribute("users", dtos);
			model.addAttribute("username", username);
			LOGGER.info("Show all Users.");
	        return "user/list";
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
    }

    /**
     * Get add user form.
     * @param model
     * @return String
     */
    @GetMapping("/user/add")
    public String addUserForm(Model model) {
    	LOGGER.debug("Getting add user form");
    	if(!model.containsAttribute("user")) {
    		model.addAttribute("user", new UserDTO());
    	}
		LOGGER.info("Show add User form.");
        return "user/add";
    }

    /**
     * Add a user.
     * @param newUserDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/user/validate")
    public String validate(
    		@ModelAttribute("user") @Valid NewUserDTO newUserDTO, 
    		BindingResult result, 
    		Model model) {

    	LOGGER.debug("Posting a user");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "user/add";
    	}
    	
    	try {
			UserDTO user = userService.addItem(newUserDTO);
			model.addAttribute("user", user);
			LOGGER.info("New User added: id=" + user.getId());
	    	return "redirect:/user/list?success=New User added.";
		} catch (ItemAlreadyExistsException e) {
			LOGGER.debug("Validate: ItemAlreadyExistsException: " + e.getMessage());
			return "redirect:/user/list?error=User already exists.";
		} catch (ConverterException e) {
			LOGGER.debug("Validate: ConverterException: " + e.getMessage());
			return "redirect:/user/list?error=Unable to process new User.";
		} catch (Exception e) {
			LOGGER.debug("Validate: Exception: " + e.getMessage());
			return "redirect:/user/list?error=Unable to process new User.";
		}
    }

    /**
     * Get update user form.
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a user to update");

		try {
			UserDTO user = userService.getItemById(id);
			model.addAttribute("user", user);
			LOGGER.info("Show update User form.");
			return "user/update";
		} catch (ItemNotFoundException e) {
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
     * @param id
     * @param updateUserDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(
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
			LOGGER.info("User updated: id=" + id);
            return "redirect:/user/list?success=User has been updated.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("updateUser: NotFoundException: " + e.getMessage());
			return "redirect:/user/list?error=User not found.";
		} catch (Exception e) {
			LOGGER.debug("updateUser: Exception: " + e.getMessage());
			return "redirect:/user/list?error=Unable to process update User.";
		}
    }

    /**
     * Delete a user.
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a user");
    	try {
    		userService.deleteItem(id);
			LOGGER.info("User deleted: id=" + id);
            return "redirect:/user/list?success=User has been deleted.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("deleteUser: NotFoundException: " + e.getMessage());
			return "redirect:/user/list?error=User not found.";
		} catch (Exception e) {
			LOGGER.debug("deleteUser: Exception: " + e.getMessage());
			return "redirect:/user/list?error=Unable to process delete User.";
		}
    }
}
