package com.nnk.springboot.unit.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.dto.NewTradeDTO;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.dto.UpdateTradeDTO;
import com.nnk.springboot.dtoconverters.ITradeDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.ITradeService;
import com.nnk.springboot.util.IAuthenticationInformation;

@WebMvcTest(controllers = TradeController.class)
class TradeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TradeRepository tradeRepository;
	
	@MockBean
	private ITradeDTOConverter tradeDTOConverter;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private ITradeService tradeService;
	
	@MockBean
	private IAuthenticationInformation authenticationInfo;
	
	private static NewTradeDTO rightTradeDTO;
	
	private static final String ROOT_REQUEST = "/trade";
	private static final String ROOT_TEMPLATE = "trade";
	
	@BeforeAll
	private static void setUp() {
		rightTradeDTO = new NewTradeDTO();
		rightTradeDTO.setAccount("account1");
		rightTradeDTO.setType("type1");
		rightTradeDTO.setBuyQuantity(10d);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// home
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void homeReturnsListWhenOk() throws Exception {
		when(tradeService.getAllItems()).thenReturn(new ArrayList<>());
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
		doThrow(ConverterException.class).when(tradeService).getAllItems();
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// addTradeForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void addTradeFormReturnsForm() throws Exception {
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
		when(tradeService.addItem(any(NewTradeDTO.class))).thenReturn(new TradeDTO());
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("trade", rightTradeDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=New Trade added."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenValidationError() throws Exception {
		NewTradeDTO wrongTradeDTO = new NewTradeDTO();
		wrongTradeDTO.setAccount("");
		wrongTradeDTO.setType("type1");
		wrongTradeDTO.setBuyQuantity(10d);
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("trade", wrongTradeDTO))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/add"));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenAlreadyExistsException() throws Exception {
		doThrow(ItemAlreadyExistsException.class).when(tradeService).addItem(any(NewTradeDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("trade", rightTradeDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=Trade already exists."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(tradeService).addItem(any(NewTradeDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("trade", rightTradeDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=Unable to process new Trade."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// showUpdateForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void showUpdateFormReturnsFormWhenOk() throws Exception {
		TradeDTO tradeDTO = new TradeDTO();
		tradeDTO.setAccount("account1");
		tradeDTO.setType("type1");
		tradeDTO.setBuyQuantity(10d);
		when(tradeService.getItemById(anyInt())).thenReturn(tradeDTO);
		mockMvc.perform(get(ROOT_REQUEST + "/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/update"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError404WhenNotFoundException() throws Exception {
		doThrow(ItemNotFoundException.class).when(tradeService).getItemById(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/update/10"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(tradeService).getItemById(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// updateTrade
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void updateTradeReturnsListWhenOk() throws Exception {
		UpdateTradeDTO rightUpdateTradeDTO = new UpdateTradeDTO();
		rightUpdateTradeDTO.setAccount("account2");
		rightUpdateTradeDTO.setType("type1");
		rightUpdateTradeDTO.setBuyQuantity(10d);
		mockMvc.perform(post(ROOT_REQUEST + "/update/1")
				.flashAttr("trade", rightUpdateTradeDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=Trade has been updated."));
	}

	@WithMockUser
	@Test
	void updateTradeReturnsFormWithErrorWhenValidationError() throws Exception {
		UpdateTradeDTO wrongUpdateTradeDTO = new UpdateTradeDTO();
		wrongUpdateTradeDTO.setAccount("account2");
		wrongUpdateTradeDTO.setType("");
		wrongUpdateTradeDTO.setBuyQuantity(10d);
		mockMvc.perform(post(ROOT_REQUEST + "/update/1")
				.flashAttr("trade", wrongUpdateTradeDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/update/1?error=Type must not be empty. "));
	}

	@WithMockUser
	@Test
	void updateTradeReturnsError404WhenNotFoundException() throws Exception {
		UpdateTradeDTO rightUpdateTradeDTO = new UpdateTradeDTO();
		rightUpdateTradeDTO.setAccount("account2");
		rightUpdateTradeDTO.setType("type1");
		rightUpdateTradeDTO.setBuyQuantity(10d);
		doThrow(ItemNotFoundException.class).when(tradeService).updateItem(anyInt(), any(UpdateTradeDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/update/10")
				.flashAttr("trade", rightUpdateTradeDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=Trade not found."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// deleteTrade
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void deleteTradeReturnsListWhenOk() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=Trade has been deleted."));
	}

	@WithMockUser
	@Test
	void deleteTradeReturnsError404WhenNotFoundException() throws Exception {
		doThrow(ItemNotFoundException.class).when(tradeService).deleteItem(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/delete/10"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=Trade not found."));
	}
}
