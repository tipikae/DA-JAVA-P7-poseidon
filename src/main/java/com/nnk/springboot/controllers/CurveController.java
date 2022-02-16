package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.dto.NewCurvePointDTO;
import com.nnk.springboot.dto.UpdateCurvePointDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
public class CurveController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurveController.class);
	
	@Autowired
	private ICurvePointService curveService;

	/**
	 * Get all curvePoints.
	 * @param request
	 * @param model
	 * @return String
	 */
    @RequestMapping("/curvePoint/list")
    public String home(HttpServletRequest request, Model model)
    {
    	LOGGER.debug("Getting all curvePoints");
    	try {
			List<CurvePointDTO> dtos = curveService.getAllItems();
			model.addAttribute("curvePoints", dtos);
		} catch (ConverterException e) {
			LOGGER.debug("Home: ConverterException: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Home: Exception: " + e.getMessage());
			return "error/400";
		}
        return "curvePoint/list";
    }

    /**
     * Get add curvePoint form.
     * @param request
     * @return String
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(HttpServletRequest request) {
    	LOGGER.debug("Getting add curvePoint form");
        return "curvePoint/add";
    }

    /**
     * Add a curvePoint.
     * @param request
     * @param newCurvePointDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/curvePoint/validate")
    public String validate(
    		HttpServletRequest request, 
    		@ModelAttribute("curvePoint") @Valid NewCurvePointDTO newCurvePointDTO, 
    		BindingResult result, 
    		Model model) {

    	LOGGER.debug("Posting a curvePoint");
    	if(result.hasErrors()) {
    		StringBuilder sb = new StringBuilder();
    		result.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/curvePoint/add?error=" + sb;
    	}
    	
    	try {
			CurvePointDTO curvePoint = curveService.addItem(newCurvePointDTO);
			model.addAttribute("curvePoint", curvePoint);
		} catch (ServiceException e) {
			LOGGER.debug("Validate: ServiceException: " + e.getMessage());
			return "redirect:/curvePoint/add?error=Unable to process new CurvePoint.";
		} catch (ConverterException e) {
			LOGGER.debug("Validate: ConverterException: " + e.getMessage());
			return "redirect:/curvePoint/add?error=Unable to process new CurvePoint.";
		} catch (Exception e) {
			LOGGER.debug("Validate: Exception: " + e.getMessage());
			return "redirect:/curvePoint/add?error=Unable to process new CurvePoint.";
		}
    	
    	return "redirect:/curvePoint/list?success=New CurvePoint added.";
    }

    /**
     * Get update form.
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Getting a curvePoint to update");
    	
    	try {
			CurvePointDTO curvePoint = curveService.getItemById(id);
			model.addAttribute("curvePoint", curvePoint);
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
    	
        return "curvePoint/update";
    }

    /**
     * Update a curvePoint.
     * @param request
     * @param id
     * @param updateCurvePointDTO
     * @param result
     * @param model
     * @return String
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(
    		HttpServletRequest request, 
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
		} catch (NotFoundException e) {
			LOGGER.debug("UpdateCurve: NotFoundException: " + e.getMessage());
			return "redirect:/curvePoint/update/" + id + "?error=CurvePoint not found.";
		} catch (Exception e) {
			LOGGER.debug("UpdateCurve: Exception: " + e.getMessage());
			return "redirect:/curvePoint/update/" + id + "?error=Unable to process update CurvePoint.";
		}
    	
        return "redirect:/curvePoint/list?success=CurvePoint has been updated.";
    }

    /**
     * Delete a curvePoint.
     * @param request
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(
    		HttpServletRequest request, 
    		@PathVariable("id") @Positive Integer id, 
    		Model model) {
    	
    	LOGGER.debug("Deleting a curvePoint");
    	try {
			curveService.deleteItem(id);
		} catch (NotFoundException e) {
			LOGGER.debug("DeleteCurve: NotFoundException: " + e.getMessage());
			return "redirect:/curvePoint/delete/" + id + "?error=CurvePoint not found.";
		} catch (Exception e) {
			LOGGER.debug("DeleteCurve: Exception: " + e.getMessage());
			return "redirect:/curvePoint/delete/" + id + "?error=Unable to process delete CurvePoint.";
		}
        return "redirect:/curvePoint/list?success=CurvePoint has been deleted.";
    }
}
