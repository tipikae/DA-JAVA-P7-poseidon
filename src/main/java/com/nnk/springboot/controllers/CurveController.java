package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.dto.NewCurvePointDTO;
import com.nnk.springboot.dto.UpdateCurvePointDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.services.ICurvePointService;

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

import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * CurvePoint controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class CurveController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurveController.class);
	
	@Autowired
	private ICurvePointService curveService;

	/**
	 * Get all curvePoints.
	 * @param model
	 * @return String
	 */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
    	LOGGER.debug("Getting all curvePoints");
    	try {
			List<CurvePointDTO> dtos = curveService.getAllItems();
			model.addAttribute("curvePoints", dtos);
			LOGGER.info("Show all CurvePoints.");
	        return "curvePoint/list";
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
    }

    /**
     * Get add curvePoint form.
     * @param model
     * @return String
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(Model model) {
    	LOGGER.debug("Getting add curvePoint form");
    	if(!model.containsAttribute("curvePoint")) {
    		model.addAttribute("curvePoint", new CurvePointDTO());
    	}
		LOGGER.info("Show add CurvePoint form.");
        return "curvePoint/add";
    }

    /**
     * Add a curvePoint.
     * @param newCurvePointDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/curvePoint/validate")
    public String validate(
    		@ModelAttribute("curvePoint") @Valid NewCurvePointDTO newCurvePointDTO, 
    		BindingResult result, 
    		Model model) {

    	LOGGER.debug("Posting a curvePoint");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "curvePoint/add";
    	}
    	
    	try {
			CurvePointDTO curvePoint = curveService.addItem(newCurvePointDTO);
			model.addAttribute("curvePoint", curvePoint);
			LOGGER.info("New CurvePoint added: id=" + curvePoint.getId());
	    	return "redirect:/curvePoint/list?success=New CurvePoint added.";
		} catch (ItemAlreadyExistsException e) {
			LOGGER.debug("Validate: ItemAlreadyExistsException: " + e.getMessage());
			return "redirect:/curvePoint/list?error=CurvePoint already exists.";
		} catch (ConverterException e) {
			LOGGER.debug("Validate: ConverterException: " + e.getMessage());
			return "redirect:/curvePoint/list?error=Unable to process new CurvePoint.";
		} catch (Exception e) {
			LOGGER.debug("Validate: Exception: " + e.getMessage());
			return "redirect:/curvePoint/list?error=Unable to process new CurvePoint.";
		}	
    }

    /**
     * Get update form.
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a curvePoint to update");
    	try {
			CurvePointDTO curvePoint = curveService.getItemById(id);
			model.addAttribute("curvePoint", curvePoint);
			LOGGER.info("Show update CurvePoint form.");
			return "curvePoint/update";
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
     * Update a curvePoint.
     * @param id
     * @param updateCurvePointDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(
    		@PathVariable("id") @Positive Integer id, 
    		@ModelAttribute("curvePoint") @Valid UpdateCurvePointDTO updateCurvePointDTO,
    		BindingResult result, 
    		Model model) {
    	
    	LOGGER.debug("Updating a curvePoint");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/curvePoint/update/" + id + "?error=" + sb;
    	}
    	
    	try {
			curveService.updateItem(id, updateCurvePointDTO);
			LOGGER.info("CurvePoint updated: id=" + id);
	        return "redirect:/curvePoint/list?success=CurvePoint has been updated.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("UpdateCurve: NotFoundException: " + e.getMessage());
			return "redirect:/curvePoint/list?error=CurvePoint not found.";
		} catch (Exception e) {
			LOGGER.debug("UpdateCurve: Exception: " + e.getMessage());
			return "redirect:/curvePoint/list?error=Unable to process update CurvePoint.";
		}
    }

    /**
     * Delete a curvePoint.
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a curvePoint");
    	try {
			curveService.deleteItem(id);
			LOGGER.info("CurvePoint deleted: id=" + id);
	        return "redirect:/curvePoint/list?success=CurvePoint has been deleted.";
		} catch (ItemNotFoundException e) {
			LOGGER.debug("DeleteCurve: NotFoundException: " + e.getMessage());
			return "redirect:/curvePoint/list?error=CurvePoint not found.";
		} catch (Exception e) {
			LOGGER.debug("DeleteCurve: Exception: " + e.getMessage());
			return "redirect:/curvePoint/list?error=Unable to process delete CurvePoint.";
		}
    }
}
