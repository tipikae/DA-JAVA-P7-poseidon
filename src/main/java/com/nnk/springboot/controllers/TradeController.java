package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.NewTradeDTO;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.dto.UpdateTradeDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.services.ITradeService;
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
 * Trade controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class TradeController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);
	
	@Autowired
	private ITradeService tradeService;
	
	@Autowired
	private IAuthenticationInformation authenticationInfo;

	/**
	 * Get all trades.
	 * @param model
	 * @param principal
	 * @return String
	 */
    @RequestMapping("/trade/list")
    public String home(Model model, Principal principal) {
    	LOGGER.debug("Getting all trades");
    	try {
			List<TradeDTO> dtos = tradeService.getAllItems();
			String username = authenticationInfo.getUsername(principal);
			model.addAttribute("trades", dtos);
			model.addAttribute("username", username);
			LOGGER.info("Show all Trades.");
	        return "trade/list";
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
    }

    /**
     * Get add trade form.
     * @param model
     * @return String
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
    	LOGGER.debug("Getting add trade form");
    	if(!model.containsAttribute("trade")) {
    		model.addAttribute("trade", new TradeDTO());
    	}
		LOGGER.info("Show add Trade form.");
        return "trade/add";
    }

    /**
     * Add a trade.
     * @param newTradeDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/trade/validate")
    public String validate(
    		@ModelAttribute("trade") @Valid NewTradeDTO newTradeDTO, 
    		BindingResult result, 
    		Model model) {

    	LOGGER.debug("Posting a trade");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "trade/add";
    	}
    	
    	try {
			TradeDTO trade = tradeService.addItem(newTradeDTO);
			model.addAttribute("trade", trade);
			LOGGER.info("New Trade added: id=" + trade.getTradeId());
	        return "redirect:/trade/list?success=New Trade added.";
		} catch (ItemAlreadyExistsException e) {
			LOGGER.debug("Validate: ItemAlreadyExistsException: " + e.getMessage());
			return "redirect:/trade/list?error=Trade already exists.";
		} catch (ConverterException e) {
			LOGGER.debug("Validate: ConverterException: " + e.getMessage());
			return "redirect:/trade/list?error=Unable to process new Trade.";
		} catch (Exception e) {
			LOGGER.debug("Validate: Exception: " + e.getMessage());
			return "redirect:/trade/list?error=Unable to process new Trade.";
		}
    }

    /**
     * Get update trade form.
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a trade to update");
    	try {
			TradeDTO trade = tradeService.getItemById(id);
			model.addAttribute("trade", trade);
			LOGGER.info("Show update Trade form.");
			return "trade/update";
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
     * Update a trade.
     * @param id
     * @param updateTradeDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(
    		@PathVariable("id") @Positive Integer id, 
    		@ModelAttribute("trade") @Valid UpdateTradeDTO updateTradeDTO,
            BindingResult result, 
            Model model) {
    	
    	LOGGER.debug("Updating a trade");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/trade/update/" + id + "?error=" + sb;
    	}
    	
    	try {
    		tradeService.updateItem(id, updateTradeDTO);
			LOGGER.info("Trade updated: id=" + id);
            return "redirect:/trade/list?success=Trade has been updated.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("updateTrade: NotFoundException: " + e.getMessage());
			return "redirect:/trade/list?error=Trade not found.";
		} catch (Exception e) {
			LOGGER.debug("updateTrade: Exception: " + e.getMessage());
			return "redirect:/trade/list?error=Unable to process update Trade.";
		}
    }

    /**
     * Delete a trade.
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a trade");
    	try {
    		tradeService.deleteItem(id);
			LOGGER.info("Trade deleted: id=" + id);
            return "redirect:/trade/list?success=Trade has been deleted.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("deleteTrade: NotFoundException: " + e.getMessage());
			return "redirect:/trade/list?error=Trade not found.";
		} catch (Exception e) {
			LOGGER.debug("deleteTrade: Exception: " + e.getMessage());
			return "redirect:/trade/list?error=Unable to process delete Trade.";
		}
    }
}
