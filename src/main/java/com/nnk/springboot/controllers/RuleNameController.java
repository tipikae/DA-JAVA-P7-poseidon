package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.NewRuleNameDTO;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.dto.UpdateRuleNameDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.services.IRuleNameService;

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
public class RuleNameController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleNameController.class);
	
	@Autowired
	private IRuleNameService ruleNameService;

	/**
	 * Get all ruleNames.
	 * @param model
	 * @return String
	 */
    @RequestMapping("/ruleName/list")
    public String home(HttpServletRequest request, Model model) {
    	LOGGER.debug("Getting all ruleNames");
    	try {
			List<RuleNameDTO> dtos = ruleNameService.getAllItems();
			model.addAttribute("ruleNames", dtos);
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
        return "ruleName/list";
    }

    /**
     * Get add ruleName form.
     * @param request
     * @param model
     * @return String
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(HttpServletRequest request, Model model) {
    	LOGGER.debug("Getting add ruleName form");
    	if(!model.containsAttribute("ruleName")) {
    		model.addAttribute("ruleName", new RuleNameDTO());
    	}
        return "ruleName/add";
    }

    /**
     * Add a ruleName.
     * @param request
     * @param newRuleNameDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/ruleName/validate")
    public String validate(
    		HttpServletRequest request, 
    		@ModelAttribute("ruleName") @Valid NewRuleNameDTO newRuleNameDTO, 
    		BindingResult result, 
    		Model model) {

    	LOGGER.debug("Posting a ruleName");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "ruleName/add";
    	}
    	
    	try {
			RuleNameDTO ruleName = ruleNameService.addItem(newRuleNameDTO);
			model.addAttribute("ruleName", ruleName);
	        return "redirect:/ruleName/list?success=New RuleName added.";
		} catch (ItemAlreadyExistsException e) {
			LOGGER.debug("Validate: ItemAlreadyExistsException: " + e.getMessage());
			return "redirect:/ruleName/list?error=Unable to process new RuleName.";
		} catch (ConverterException e) {
			LOGGER.debug("Validate: ConverterException: " + e.getMessage());
			return "redirect:/ruleName/list?error=Unable to process new RuleName.";
		} catch (Exception e) {
			LOGGER.debug("Validate: Exception: " + e.getMessage());
			return "redirect:/ruleName/list?error=Unable to process new RuleName.";
		}
    }

    /**
     * Get update ruleName form.
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a ruleName to update");
    	try {
			RuleNameDTO ruleName = ruleNameService.getItemById(id);
			model.addAttribute("ruleName", ruleName);
			return "ruleName/update";
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
     * Update a ruleName.
     * @param request
     * @param id
     * @param updateRuleNameDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		@ModelAttribute("ruleName") @Valid UpdateRuleNameDTO updateRuleNameDTO,
            BindingResult result, 
            Model model) {
    	
    	LOGGER.debug("Updating a ruleName");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/ruleName/update/" + id + "?error=" + sb;
    	}
    	
    	try {
    		ruleNameService.updateItem(id, updateRuleNameDTO);
            return "redirect:/ruleName/list?success=RuleName has been updated.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("updateRuleName: NotFoundException: " + e.getMessage());
			return "redirect:/ruleName/list?error=RuleName not found.";
		} catch (Exception e) {
			LOGGER.debug("updateRuleName: Exception: " + e.getMessage());
			return "redirect:/ruleName/list?error=Unable to process update RuleName.";
		}
    }

    /**
     * Delete a ruleName.
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a ruleName");
    	try {
    		ruleNameService.deleteItem(id);
            return "redirect:/ruleName/list?success=RuleName has been deleted.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("deleteRuleName: NotFoundException: " + e.getMessage());
			return "redirect:/ruleName/list?error=RuleName not found.";
		} catch (Exception e) {
			LOGGER.debug("deleteRuleName: Exception: " + e.getMessage());
			return "redirect:/ruleName/list?error=Unable to process delete RuleName.";
		}
    }
}
