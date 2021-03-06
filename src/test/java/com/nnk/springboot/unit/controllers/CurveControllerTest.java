package com.nnk.springboot.unit.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.dto.NewCurvePointDTO;
import com.nnk.springboot.dto.UpdateCurvePointDTO;
import com.nnk.springboot.dtoconverters.ICurvePointDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.ICurvePointService;
import com.nnk.springboot.util.IAuthenticationInformation;

@WebMvcTest(controllers = CurveController.class)
class CurveControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CurvePointRepository curveRepository;
	
	@MockBean
	private ICurvePointDTOConverter curveDTOConverter;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private ICurvePointService curveService;
	
	@MockBean
	private IAuthenticationInformation authenticationInfo;
	
	private static NewCurvePointDTO rightCurveDTO;
	
	private static final String ROOT_REQUEST = "/curvePoint";
	private static final String ROOT_TEMPLATE = "curvePoint";
	
	@BeforeAll
	private static void setUp() {
		rightCurveDTO = new NewCurvePointDTO();
		rightCurveDTO.setCurveId(10);
		rightCurveDTO.setTerm(new BigDecimal(10));
		rightCurveDTO.setValue(new BigDecimal(10));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// home
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void homeReturnsListWhenOk() throws Exception {
		when(curveService.getAllItems()).thenReturn(new ArrayList<>());
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/list"));
	}

	@WithMockUser(roles = {""})
	@Test
	void homeReturnsErrorWhenBadRole() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().is(403));
	}

	@Test
	void homeReturnsLoginWhenNoLog() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().is3xxRedirection());

	}

	@WithMockUser
	@Test
	void homeReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(curveService).getAllItems();
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// addCurveForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void addCurveFormReturnsForm() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/add"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/add"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// validate
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void validateReturnsFormWhenOk() throws Exception {
		when(curveService.addItem(any(NewCurvePointDTO.class))).thenReturn(new CurvePointDTO());
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("curvePoint", rightCurveDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=New CurvePoint added."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenValidationError() throws Exception {
		NewCurvePointDTO wrongCurveDTO = new NewCurvePointDTO();
		wrongCurveDTO.setCurveId(10);
		wrongCurveDTO.setTerm(new BigDecimal(0));
		wrongCurveDTO.setValue(new BigDecimal(10));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("curvePoint", wrongCurveDTO))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/add"));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenAlreadyExistsException() throws Exception {
		doThrow(ItemAlreadyExistsException.class).when(curveService).addItem(any(NewCurvePointDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("curvePoint", rightCurveDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=CurvePoint already exists."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(curveService).addItem(any(NewCurvePointDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("curvePoint", rightCurveDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=Unable to process new CurvePoint."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// showUpdateForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void showUpdateFormReturnsFormWhenOk() throws Exception {
		CurvePointDTO curvePointDTO = new CurvePointDTO();
		curvePointDTO.setCurveId(10);
		curvePointDTO.setTerm(new BigDecimal(10));
		curvePointDTO.setValue(new BigDecimal(10));
		curvePointDTO.setId(1);
		when(curveService.getItemById(anyInt())).thenReturn(curvePointDTO);
		mockMvc.perform(get(ROOT_REQUEST + "/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/update"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError404WhenNotFoundException() throws Exception {
		doThrow(ItemNotFoundException.class).when(curveService).getItemById(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/update/10"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(curveService).getItemById(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// updateCurve
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void updateCurveReturnsListWhenOk() throws Exception {
		UpdateCurvePointDTO rightUpdateCurveDTO = new UpdateCurvePointDTO();
		rightUpdateCurveDTO.setCurveId(20);
		rightUpdateCurveDTO.setTerm(new BigDecimal(10));
		rightUpdateCurveDTO.setValue(new BigDecimal(10));
		mockMvc.perform(post(ROOT_REQUEST + "/update/1")
				.flashAttr("curvePoint", rightUpdateCurveDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=CurvePoint has been updated."));
	}

	@WithMockUser
	@Test
	void updateCurveReturnsFormWithErrorWhenValidationError() throws Exception {
		UpdateCurvePointDTO wrongUpdateCurveDTO = new UpdateCurvePointDTO();
		wrongUpdateCurveDTO.setCurveId(20);
		wrongUpdateCurveDTO.setTerm(new BigDecimal(10));
		wrongUpdateCurveDTO.setValue(new BigDecimal(0));
		mockMvc.perform(post(ROOT_REQUEST + "/update/1")
				.flashAttr("curvePoint", wrongUpdateCurveDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/update/1?error=Value must be strictly positive. "));
	}

	@WithMockUser
	@Test
	void updateCurveReturnsError404WhenNotFoundException() throws Exception {
		UpdateCurvePointDTO rightUpdateCurveDTO = new UpdateCurvePointDTO();
		rightUpdateCurveDTO.setCurveId(20);
		rightUpdateCurveDTO.setTerm(new BigDecimal(10));
		rightUpdateCurveDTO.setValue(new BigDecimal(10));
		doThrow(ItemNotFoundException.class).when(curveService).updateItem(anyInt(), any(UpdateCurvePointDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/update/10")
				.flashAttr("curvePoint", rightUpdateCurveDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=CurvePoint not found."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// deleteCurve
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void deleteCurveReturnsListWhenOk() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=CurvePoint has been deleted."));
	}

	@WithMockUser
	@Test
	void deleteCurveReturnsError404WhenNotFoundException() throws Exception {
		doThrow(ItemNotFoundException.class).when(curveService).deleteItem(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/delete/10"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=CurvePoint not found."));
	}
}
