package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.NewTradeDTO;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.dto.UpdateTradeDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.services.ITradeService;

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
public class TradeController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);
	
	@Autowired
	private ITradeService tradeService;

	/**
	 * Get all trades.
	 * @param request
	 * @param model
	 * @return String
	 */
    @RequestMapping("/trade/list")
    public String home(HttpServletRequest request, Model model) {
    	LOGGER.debug("Getting all trades");
    	try {
			List<TradeDTO> dtos = tradeService.getAllItems();
			model.addAttribute("trades", dtos);
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
        return "trade/list";
    }

    /**
     * Get add trade form.
     * @param request
     * @param model
     * @return String
     */
    @GetMapping("/trade/add")
    public String addTradeForm(HttpServletRequest request, Model model) {
    	LOGGER.debug("Getting add trade form");
    	if(!model.containsAttribute("trade")) {
    		model.addAttribute("trade", new TradeDTO());
    	}
        return "trade/add";
    }

    /**
     * Add a trade.
     * @param request
     * @param newTradeDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/trade/validate")
    public String validate(
    		HttpServletRequest request, 
    		@ModelAttribute("trade") @Valid NewTradeDTO newTradeDTO, 
    		BindingResult result, 
    		Model model) {

    	LOGGER.debug("Posting a trade");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/trade/add?error=" + sb;
    	}
    	
    	try {
			TradeDTO trade = tradeService.addItem(newTradeDTO);
			model.addAttribute("trade", trade);
	        return "redirect:/trade/list?success=New Trade added.";
		} catch (ServiceException e) {
			LOGGER.debug("Validate: ServiceException: " + e.getMessage());
			return "redirect:/trade/add?error=Unable to process new Trade.";
		} catch (ConverterException e) {
			LOGGER.debug("Validate: ConverterException: " + e.getMessage());
			return "redirect:/trade/add?error=Unable to process new Trade.";
		} catch (Exception e) {
			LOGGER.debug("Validate: Exception: " + e.getMessage());
			return "redirect:/trade/add?error=Unable to process new Trade.";
		}
    }

    /**
     * Get update trade form.
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a trade to update");
    	if(model.containsAttribute("trade")) {
    		return "trade/update";
    	}
    	
    	try {
			TradeDTO trade = tradeService.getItemById(id);
			model.addAttribute("trade", trade);
			return "trade/update";
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
     * Update a trade.
     * @param request
     * @param id
     * @param updateTradeDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(
    		HttpServletRequest request, 
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
            return "redirect:/trade/list?success=Trade has been updated.";
		} catch (NotFoundException e) {
			LOGGER.debug("updateTrade: NotFoundException: " + e.getMessage());
			return "redirect:/trade/update/" + id + "?error=Trade not found.";
		} catch (Exception e) {
			LOGGER.debug("updateTrade: Exception: " + e.getMessage());
			return "redirect:/trade/update/" + id + "?error=Unable to process update Trade.";
		}
    }

    /**
     * Delete a trade.
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a trade");
    	try {
    		tradeService.deleteItem(id);
		} catch (NotFoundException e) {
			LOGGER.debug("deleteTrade: NotFoundException: " + e.getMessage());
			return "redirect:/trade/delete/" + id + "?error=Trade not found.";
		} catch (Exception e) {
			LOGGER.debug("deleteTrade: Exception: " + e.getMessage());
			return "redirect:/trade/delete/" + id + "?error=Unable to process delete Trade.";
		}
    	
        return "redirect:/trade/list?success=Trade has been deleted.";
    }
}
