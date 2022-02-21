package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.NewRatingDTO;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.dto.UpdateRatingDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.services.IRatingService;

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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
public class RatingController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);
	
	@Autowired
	private IRatingService ratingService;

	/**
	 * Get all ratings.
	 * @param model
	 * @return String
	 */
    @RequestMapping("/rating/list")
    public String home(HttpServletRequest request, Model model) {
    	LOGGER.debug("Getting all ratings");
    	try {
			List<RatingDTO> dtos = ratingService.getAllItems();
			model.addAttribute("ratings", dtos);
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
        return "rating/list";
    }

    /**
     * Get add rating form.
     * @param request
     * @param model
     * @return String
     */
    @GetMapping("/rating/add")
    public String addRatingForm(HttpServletRequest request, Model model) {
    	LOGGER.debug("Getting add rating form");
    	if(!model.containsAttribute("rating")) {
    		model.addAttribute("rating", new RatingDTO());
    	}
        return "rating/add";
    }

    /**
     * Add a rating.
     * @param request
     * @param newRatingDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/rating/validate")
    public String validate(
    		HttpServletRequest request, 
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
			RatingDTO ratingPoint = ratingService.addItem(newRatingDTO);
			model.addAttribute("rating", ratingPoint);
	    	return "redirect:/rating/list?success=New Rating added.";
		} catch (ItemAlreadyExistsException e) {
			LOGGER.debug("Validate: ItemAlreadyExistsException: " + e.getMessage());
			return "redirect:/rating/list?error=Unable to process new Rating.";
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
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a rating to update");
    	try {
			RatingDTO rating = ratingService.getItemById(id);
			model.addAttribute("rating", rating);
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
     * @param request
     * @param id
     * @param updateRatingDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(
    		HttpServletRequest request, 
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
	        return "redirect:/rating/list?success=Rating has been updated.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("updateRating: NotFoundException: " + e.getMessage());
			return "redirect:/rating/list?error=Rating not found.";
		} catch (Exception e) {
			LOGGER.debug("updateRating: Exception: " + e.getMessage());
			return "redirect:/rating/list?error=Unable to process update Rating.";
		}
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a rating");
    	try {
			ratingService.deleteItem(id);
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
