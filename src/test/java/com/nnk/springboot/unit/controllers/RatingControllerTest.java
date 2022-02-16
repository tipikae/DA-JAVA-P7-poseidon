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

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.dto.NewRatingDTO;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.dto.UpdateRatingDTO;
import com.nnk.springboot.dtoconverters.IRatingDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.IRatingService;

@WebMvcTest(controllers = RatingController.class)
class RatingControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RatingRepository ratingRepository;
	
	@MockBean
	private IRatingDTOConverter ratingDTOConverter;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private IRatingService ratingService;
	
	private static NewRatingDTO rightRatingDTO;
	
	private static final String ROOT_REQUEST = "/rating";
	private static final String ROOT_TEMPLATE = "rating";
	
	@BeforeAll
	private static void setUp() {
		rightRatingDTO = new NewRatingDTO();
		rightRatingDTO.setFitchRating("fitch1");
		rightRatingDTO.setMoodysRating("moodys1");
		rightRatingDTO.setOrderNumber(1);
		rightRatingDTO.setSandPRating("sand1");
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// home
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void homeReturnsListWhenOk() throws Exception {
		when(ratingService.getAllItems()).thenReturn(new ArrayList<>());
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/list"));
	}

	@WithMockUser
	@Test
	void homeReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(ratingService).getAllItems();
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// addRatingForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void addRatingFormReturnsForm() throws Exception {
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
		when(ratingService.addItem(any(NewRatingDTO.class))).thenReturn(new RatingDTO());
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("rating", rightRatingDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=New Rating added."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenValidationError() throws Exception {
		NewRatingDTO wrongRatingDTO = new NewRatingDTO();
		wrongRatingDTO.setFitchRating("");
		wrongRatingDTO.setMoodysRating("moodys1");
		wrongRatingDTO.setOrderNumber(1);
		wrongRatingDTO.setSandPRating("sand1");
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("rating", wrongRatingDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/add?error=Fitch must not be empty. "));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenServiceException() throws Exception {
		doThrow(ServiceException.class).when(ratingService).addItem(any(NewRatingDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("rating", rightRatingDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/add?error=Unable to process new Rating."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(ratingService).addItem(any(NewRatingDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("rating", rightRatingDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/add?error=Unable to process new Rating."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// showUpdateForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void showUpdateFormReturnsFormWhenOk() throws Exception {
		RatingDTO ratingDTO = new RatingDTO();
		ratingDTO.setFitchRating("fitch1");
		ratingDTO.setMoodysRating("moodys1");
		ratingDTO.setOrderNumber(1);
		ratingDTO.setSandPRating("sand1");
		ratingDTO.setId(1);
		when(ratingService.getItemById(anyInt())).thenReturn(ratingDTO);
		mockMvc.perform(get(ROOT_REQUEST + "/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/update"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError404WhenNotFoundException() throws Exception {
		doThrow(NotFoundException.class).when(ratingService).getItemById(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/update/10"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(ratingService).getItemById(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// updateRating
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void updateRatingReturnsListWhenOk() throws Exception {
		UpdateRatingDTO rightUpdateRatingDTO = new UpdateRatingDTO();
		rightUpdateRatingDTO.setFitchRating("fitch1");
		rightUpdateRatingDTO.setMoodysRating("moodys1");
		rightUpdateRatingDTO.setOrderNumber(2);
		rightUpdateRatingDTO.setSandPRating("sand1");
		mockMvc.perform(post(ROOT_REQUEST + "/update/1")
				.flashAttr("rating", rightUpdateRatingDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=Rating has been updated."));
	}

	@WithMockUser
	@Test
	void updateRatingReturnsFormWithErrorWhenValidationError() throws Exception {
		UpdateRatingDTO wrongUpdateRatingDTO = new UpdateRatingDTO();
		wrongUpdateRatingDTO.setFitchRating("fitch1");
		wrongUpdateRatingDTO.setMoodysRating("moodys1");
		wrongUpdateRatingDTO.setOrderNumber(0);
		wrongUpdateRatingDTO.setSandPRating("sand1");
		mockMvc.perform(post(ROOT_REQUEST + "/update/1")
				.flashAttr("rating", wrongUpdateRatingDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/update/1?error=Order number must be strictly positive. "));
	}

	@WithMockUser
	@Test
	void updateRatingReturnsError404WhenNotFoundException() throws Exception {
		UpdateRatingDTO rightUpdateRatingDTO = new UpdateRatingDTO();
		rightUpdateRatingDTO.setFitchRating("fitch1");
		rightUpdateRatingDTO.setMoodysRating("moodys1");
		rightUpdateRatingDTO.setOrderNumber(2);
		rightUpdateRatingDTO.setSandPRating("sand1");
		doThrow(NotFoundException.class).when(ratingService).updateItem(anyInt(), any(UpdateRatingDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/update/10")
				.flashAttr("rating", rightUpdateRatingDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/update/10?error=Rating not found."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// deleteRating
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void deleteRatingReturnsListWhenOk() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=Rating has been deleted."));
	}

	@WithMockUser
	@Test
	void deleteRatingReturnsError404WhenNotFoundException() throws Exception {
		doThrow(NotFoundException.class).when(ratingService).deleteItem(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/delete/10"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/delete/10?error=Rating not found."));
	}
}
