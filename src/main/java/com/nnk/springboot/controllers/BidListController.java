package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dto.NewBidListDTO;
import com.nnk.springboot.dto.UpdateBidListDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.services.IBidListService;

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

import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * BidList controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class BidListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BidListController.class);
	
	@Autowired
	private IBidListService bidListService;

	/**
	 * Get all bidLists
	 * @param model
	 * @return String
	 */
    @GetMapping("/bidList/list")
    public String home(Model model)
    {
    	LOGGER.debug("Getting all bidlists");
    	try {
			List<BidListDTO> dtos = bidListService.getAllItems();
			model.addAttribute("bidLists", dtos);
			LOGGER.info("Show all BidLists.");
	        return "bidList/list";
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
    }

    /**
     * Get add bidList form.
     * @param request
     * @return String
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
    	LOGGER.debug("Getting add bidlist form");
    	if(!model.containsAttribute("bidList")) {
    		model.addAttribute("bidList", new BidListDTO());
    	}
		LOGGER.info("Show add BidList form.");
        return "bidList/add";
    }

    /**
     * Add a bidList.
     * @param newBidListDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/bidList/validate")
    public String validate(
    		@ModelAttribute("bidList") @Valid NewBidListDTO newBidListDTO, 
    		BindingResult result, 
    		Model model) {
    	
    	LOGGER.debug("Posting a bidlist");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "bidList/add";
    	}
    	
    	try {
			BidListDTO bidList = bidListService.addItem(newBidListDTO);
			model.addAttribute("bidList", bidList);
			LOGGER.info("New Bidlist added: id=" + bidList.getBidListId());
	    	return "redirect:/bidList/list?success=New BidList added.";
		} catch (ItemAlreadyExistsException e) {
			LOGGER.debug("Validate: ItemAlreadyExistsException: " + e.getMessage());
			return "redirect:/bidList/list?error=BidList already exists.";
		} catch (ConverterException e) {
			LOGGER.debug("Validate: ConverterException: " + e.getMessage());
			return "redirect:/bidList/list?error=Unable to process new BidList.";
		} catch (Exception e) {
			LOGGER.debug("Validate: Exception: " + e.getMessage());
			return "redirect:/bidList/list?error=Unable to process new BidList.";
		}
    }

    /**
     * Show update form.
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a bidlist to update");
    	try {
			BidListDTO bidList = bidListService.getItemById(id);
			model.addAttribute("bidList", bidList);
			LOGGER.info("Show update BidList form.");
			return "bidList/update";
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
     * Update a bidlist.
     * @param id
     * @param updateBidListDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(
    		@PathVariable("id") @Positive Integer id, 
    		@ModelAttribute("bidList") @Valid UpdateBidListDTO updateBidListDTO,
            BindingResult result, 
    		Model model) {
    	
    	LOGGER.debug("Updating a bidlist");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/bidList/update/" + id + "?error=" + sb;
    	}
    	
    	try {
			bidListService.updateItem(id, updateBidListDTO);
			LOGGER.info("Bidlist updated: id=" + id);
	        return "redirect:/bidList/list?success=BidList has been updated.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("UpdateBid: NotFoundException: " + e.getMessage());
			return "redirect:/bidList/list?error=BidList not found.";
		} catch (Exception e) {
			LOGGER.debug("UpdateBid: Exception: " + e.getMessage());
			return "redirect:/bidList/list?error=Unable to process update BidList.";
		}
    }

    /**
     * Delete a BidList
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a bidlist");
    	try {
			bidListService.deleteItem(id);
			LOGGER.info("Bidlist deleted: id=" + id);
	        return "redirect:/bidList/list?success=BidList has been deleted.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("DeleteBid: NotFoundException: " + e.getMessage());
			return "redirect:/bidList/list?error=BidList not found.";
		} catch (Exception e) {
			LOGGER.debug("DeleteBid: Exception: " + e.getMessage());
			return "redirect:/bidList/list?error=Unable to process delete BidList.";
		}
    }
}
