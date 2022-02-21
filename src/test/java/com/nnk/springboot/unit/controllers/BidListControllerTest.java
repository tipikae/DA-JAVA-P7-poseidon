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

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dto.NewBidListDTO;
import com.nnk.springboot.dto.UpdateBidListDTO;
import com.nnk.springboot.dtoconverters.IBidListDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.IBidListService;
import com.nnk.springboot.util.IAuthenticationInformation;

@WebMvcTest(controllers = BidListController.class)
class BidListControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BidListRepository bidListRepository;
	
	@MockBean
	private IBidListDTOConverter bidListDTOConverter;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private IBidListService bidListService;
	
	@MockBean
	private IAuthenticationInformation authenticationInfo;
	
	private static NewBidListDTO rightBidListDTO;
	
	@BeforeAll
	private static void setUp() {
		rightBidListDTO = new NewBidListDTO();
		rightBidListDTO.setAccount("account1");
		rightBidListDTO.setBidQuantity(new BigDecimal(10));
		rightBidListDTO.setType("type1");
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// home
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void homeReturnsListWhenOk() throws Exception {
		when(bidListService.getAllItems()).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/bidList/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("bidList/list"));
	}

	@WithMockUser(roles = {""})
	@Test
	void homeReturnsErrorWhenBadRole() throws Exception {
		mockMvc.perform(get("/bidList/list"))
			.andExpect(status().is(403));
	}

	@Test
	void homeReturnsLoginWhenNoLog() throws Exception {
		mockMvc.perform(get("/bidList/list"))
			.andExpect(status().is3xxRedirection());

	}

	@WithMockUser
	@Test
	void homeReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(bidListService).getAllItems();
		mockMvc.perform(get("/bidList/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// addBidForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void addBidFormReturnsForm() throws Exception {
		mockMvc.perform(get("/bidList/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("bidList/add"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// validate
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void validateReturnsFormWhenOk() throws Exception {
		when(bidListService.addItem(any(NewBidListDTO.class))).thenReturn(new BidListDTO());
		mockMvc.perform(post("/bidList/validate")
				.flashAttr("bidList", rightBidListDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/bidList/list?success=New BidList added."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenValidationError() throws Exception {
		NewBidListDTO wrongBidListDTO = new NewBidListDTO();
		wrongBidListDTO.setAccount("");
		wrongBidListDTO.setBidQuantity(new BigDecimal(10));
		wrongBidListDTO.setType("type1");
		mockMvc.perform(post("/bidList/validate")
				.flashAttr("bidList", wrongBidListDTO))
			.andExpect(status().isOk())
			.andExpect(view().name("bidList/add"));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenAlreadyExistsException() throws Exception {
		doThrow(ItemAlreadyExistsException.class).when(bidListService).addItem(any(NewBidListDTO.class));
		mockMvc.perform(post("/bidList/validate")
				.flashAttr("bidList", rightBidListDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/bidList/list?error=BidList already exists."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(bidListService).addItem(any(NewBidListDTO.class));
		mockMvc.perform(post("/bidList/validate")
				.flashAttr("bidList", rightBidListDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/bidList/list?error=Unable to process new BidList."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// showUpdateForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void showUpdateFormReturnsFormWhenOk() throws Exception {
		BidListDTO bidListDTO = new BidListDTO();
		bidListDTO.setAccount("account1");
		bidListDTO.setBidQuantity(new BigDecimal(10));
		bidListDTO.setBidListId(1);
		bidListDTO.setType("type1");
		when(bidListService.getItemById(anyInt())).thenReturn(bidListDTO);
		mockMvc.perform(get("/bidList/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("bidList/update"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError404WhenNotFoundException() throws Exception {
		doThrow(ItemNotFoundException.class).when(bidListService).getItemById(anyInt());
		mockMvc.perform(get("/bidList/update/10"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(bidListService).getItemById(anyInt());
		mockMvc.perform(get("/bidList/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// updateBid
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void updateBidReturnsListWhenOk() throws Exception {
		UpdateBidListDTO rightUpdateBidListDTO = new UpdateBidListDTO();
		rightUpdateBidListDTO.setAccount("account1");
		rightUpdateBidListDTO.setBidQuantity(new BigDecimal(20));
		rightUpdateBidListDTO.setType("type1");
		mockMvc.perform(post("/bidList/update/1")
				.flashAttr("bidList", rightUpdateBidListDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/bidList/list?success=BidList has been updated."));
	}

	@WithMockUser
	@Test
	void updateBidReturnsFormWithErrorWhenValidationError() throws Exception {
		UpdateBidListDTO wrongUpdateBidListDTO = new UpdateBidListDTO();
		wrongUpdateBidListDTO.setAccount("account1");
		wrongUpdateBidListDTO.setBidQuantity(new BigDecimal(0));
		wrongUpdateBidListDTO.setType("type1");
		mockMvc.perform(post("/bidList/update/1")
				.flashAttr("bidList", wrongUpdateBidListDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/bidList/update/1?error=Quantity must be positive. "));
	}

	@WithMockUser
	@Test
	void updateBidReturnsError404WhenNotFoundException() throws Exception {
		UpdateBidListDTO rightUpdateBidListDTO = new UpdateBidListDTO();
		rightUpdateBidListDTO.setAccount("account1");
		rightUpdateBidListDTO.setBidQuantity(new BigDecimal(20));
		rightUpdateBidListDTO.setType("type1");
		doThrow(ItemNotFoundException.class).when(bidListService).updateItem(anyInt(), any(UpdateBidListDTO.class));
		mockMvc.perform(post("/bidList/update/10")
				.flashAttr("bidList", rightUpdateBidListDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/bidList/list?error=BidList not found."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// deleteBid
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void deleteBidReturnsListWhenOk() throws Exception {
		mockMvc.perform(get("/bidList/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/bidList/list?success=BidList has been deleted."));
	}

	@WithMockUser
	@Test
	void deleteBidReturnsError404WhenNotFoundException() throws Exception {
		doThrow(ItemNotFoundException.class).when(bidListService).deleteItem(anyInt());
		mockMvc.perform(get("/bidList/delete/10"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/bidList/list?error=BidList not found."));
	}
}
