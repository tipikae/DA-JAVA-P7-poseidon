package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dto.NewBidListDTO;
import com.nnk.springboot.dto.UpdateBidListDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;


@Controller
public class BidListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BidListController.class);
	
	@Autowired
	private IBidListService bidListService;

	/**
	 * Get all bidLists
	 * @param request
	 * @param model
	 * @return String
	 */
    @GetMapping("/bidList/list")
    public String home(HttpServletRequest request, Model model)
    {
    	LOGGER.debug("Getting all bidlists");
    	try {
			List<BidListDTO> dtos = bidListService.getAllItems();
			model.addAttribute("bids", dtos);
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
        return "bidList/list";
    }

    /**
     * Get add bidList form.
     * @param request
     * @return String
     */
    @GetMapping("/bidList/add")
    public String addBidForm(HttpServletRequest request) {
    	LOGGER.debug("Getting add bidlist form");
        return "bidList/add";
    }

    /**
     * Add a bidList.
     * @param request
     * @param newBidListDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/bidList/validate")
    public String validate(
    		HttpServletRequest request, 
    		@ModelAttribute("bidlist") @Valid NewBidListDTO newBidListDTO, 
    		BindingResult result, 
    		Model model) {
    	
    	LOGGER.debug("Posting a bidlist");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/bidList/add?error=" + sb;
    	}
    	
    	try {
			BidListDTO bidList = bidListService.addItem(newBidListDTO);
			model.addAttribute("bidlist", bidList);
		} catch (ServiceException e) {
			LOGGER.debug("Validate: ServiceException: " + e.getMessage());
			return "redirect:/bidList/add?error=Unable to process new BidList.";
		} catch (ConverterException e) {
			LOGGER.debug("Validate: ConverterException: " + e.getMessage());
			return "redirect:/bidList/add?error=Unable to process new BidList.";
		} catch (Exception e) {
			LOGGER.debug("Validate: Exception: " + e.getMessage());
			return "redirect:/bidList/add?error=Unable to process new BidList.";
		}
    	
    	return "redirect:/bidList/add?success=New BidList added.";
    }

    /**
     * Show update form.
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a bidlist to update");
    	
    	try {
			BidListDTO bidList = bidListService.getItemById(id);
			model.addAttribute("bidlist", bidList);
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
    	
        return "bidList/update";
    }

    /**
     * Update a bidlist.
     * @param request
     * @param id
     * @param updateBidListDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		@ModelAttribute("bidlist") @Valid UpdateBidListDTO updateBidListDTO,
            BindingResult result) {
    	
    	LOGGER.debug("Updating a bidlist");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/bidList/update/" + id + "?error=" + sb;
    	}
    	
    	try {
			bidListService.updateItem(id, updateBidListDTO);
		} catch (NotFoundException e) {
			LOGGER.debug("UpdateBid: NotFoundException: " + e.getMessage());
			return "redirect:/bidList/update/" + id + "?error=BidList not found.";
		} catch (Exception e) {
			LOGGER.debug("UpdateBid: Exception: " + e.getMessage());
			return "redirect:/bidList/update/" + id + "?error=Unable to process update BidList.";
		}
    	
        return "redirect:/bidList/list?success=BidList updated.";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a bidlist");
    	try {
			bidListService.deleteItem(id);
		} catch (NotFoundException e) {
			LOGGER.debug("DeleteBid: NotFoundException: " + e.getMessage());
			return "redirect:/bidList/delete/" + id + "?error=BidList not found.";
		} catch (Exception e) {
			LOGGER.debug("DeleteBid: Exception: " + e.getMessage());
			return "redirect:/bidList/delete/" + id + "?error=Unable to process delete BidList.";
		}
        return "redirect:/bidList/list";
    }
}
