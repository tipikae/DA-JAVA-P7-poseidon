package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.NewRatingDTO;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.dto.UpdateRatingDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.services.IRatingService;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * Rating controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class RatingController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);
	
	@Autowired
	private IRatingService ratingService;
	
	@Autowired
	private IAuthenticationInformation authenticationInfo;

	/**
	 * Get all ratings.
	 * @param model
	 * @param principal
	 * @return String
	 */
    @RequestMapping("/rating/list")
    public String home(Model model, Principal principal) {
    	LOGGER.debug("Getting all ratings");
    	try {
			List<RatingDTO> dtos = ratingService.getAllItems();
			String username = authenticationInfo.getUsername(principal);
			model.addAttribute("ratings", dtos);
			model.addAttribute("username", username);
			LOGGER.info("Show all Ratings.");
	        return "rating/list";
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
    }

    /**
     * Get add rating form.
     * @param model
     * @return String
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
    	LOGGER.debug("Getting add rating form");
    	if(!model.containsAttribute("rating")) {
    		model.addAttribute("rating", new RatingDTO());
    	}
		LOGGER.info("Show add Rating form.");
        return "rating/add";
    }

    /**
     * Add a rating.
     * @param newRatingDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/rating/validate")
    public String validate(
    		@ModelAttribute("rating") @Valid NewRatingDTO newRatingDTO, 
    		BindingResult result, 
    		Model model) {

    	LOGGER.debug("Posting a rating");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "rating/add";
    	}
    	
    	try {
			RatingDTO rating = ratingService.addItem(newRatingDTO);
			model.addAttribute("rating", rating);
			LOGGER.info("New Rating added: id=" + rating.getId());
	    	return "redirect:/rating/list?success=New Rating added.";
		} catch (ItemAlreadyExistsException e) {
			LOGGER.debug("Validate: ItemAlreadyExistsException: " + e.getMessage());
			return "redirect:/rating/list?error=Rating already exists.";
		} catch (ConverterException e) {
			LOGGER.debug("Validate: ConverterException: " + e.getMessage());
			return "redirect:/rating/list?error=Unable to process new Rating.";
		} catch (Exception e) {
			LOGGER.debug("Validate: Exception: " + e.getMessage());
			return "redirect:/rating/list?error=Unable to process new Rating.";
		}
    }

    /**
     * Get rating update form.
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a rating to update");
    	try {
			RatingDTO rating = ratingService.getItemById(id);
			model.addAttribute("rating", rating);
			LOGGER.info("Show update Rating form.");
			return "rating/update";
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
     * Update a rating.
     * @param id
     * @param updateRatingDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(
    		@PathVariable("id") @Positive Integer id, 
    		@ModelAttribute("rating") @Valid UpdateRatingDTO updateRatingDTO,
            BindingResult result, 
            Model model) {
    	
    	LOGGER.debug("Updating a rating");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/rating/update/" + id + "?error=" + sb;
    	}
    	
    	try {
			ratingService.updateItem(id, updateRatingDTO);
			LOGGER.info("Rating updated: id=" + id);
	        return "redirect:/rating/list?success=Rating has been updated.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("updateRating: NotFoundException: " + e.getMessage());
			return "redirect:/rating/list?error=Rating not found.";
		} catch (Exception e) {
			LOGGER.debug("updateRating: Exception: " + e.getMessage());
			return "redirect:/rating/list?error=Unable to process update Rating.";
		}
    }

    /**
     * Delete a rating.
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a rating");
    	try {
			ratingService.deleteItem(id);
			LOGGER.info("Rating deleted: id=" + id);
	        return "redirect:/rating/list?success=Rating has been deleted.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("deleteRating: NotFoundException: " + e.getMessage());
			return "redirect:/rating/list?error=Rating not found.";
		} catch (Exception e) {
			LOGGER.debug("deleteRating: Exception: " + e.getMessage());
			return "redirect:/rating/list?error=Unable to process delete Rating.";
		}
    }
}
