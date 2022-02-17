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

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.dto.NewUserDTO;
import com.nnk.springboot.dto.UpdateUserDTO;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.dtoconverters.IUserDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.IUserService;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private IUserDTOConverter userDTOConverter;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private IUserService userService;
	
	private static NewUserDTO rightUserDTO;
	
	private static final String ROOT_REQUEST = "/user";
	private static final String ROOT_TEMPLATE = "user";
	
	@BeforeAll
	private static void setUp() {
		rightUserDTO = new NewUserDTO();
		rightUserDTO.setFullname("fullname1");
		rightUserDTO.setPassword("password1");
		rightUserDTO.setUsername("username1");
		rightUserDTO.setRole("role1");
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// home
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void homeReturnsListWhenOk() throws Exception {
		when(userService.getAllItems()).thenReturn(new ArrayList<>());
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/list"));
	}

	@WithMockUser
	@Test
	void homeReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(userService).getAllItems();
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// addUserForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void addUserFormReturnsForm() throws Exception {
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
		when(userService.addItem(any(NewUserDTO.class))).thenReturn(new UserDTO());
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("user", rightUserDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=New User added."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenValidationError() throws Exception {
		NewUserDTO wrongUserDTO = new NewUserDTO();
		wrongUserDTO.setFullname("fullname1");
		wrongUserDTO.setPassword("");
		wrongUserDTO.setUsername("username1");
		wrongUserDTO.setRole("role1");
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("user", wrongUserDTO))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/add"));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenServiceException() throws Exception {
		doThrow(ServiceException.class).when(userService).addItem(any(NewUserDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("user", rightUserDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=Unable to process new User."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(userService).addItem(any(NewUserDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("user", rightUserDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=Unable to process new User."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// showUpdateForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void showUpdateFormReturnsFormWhenOk() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setFullname("fullname1");
		userDTO.setUsername("username1");
		userDTO.setRole("role1");
		userDTO.setId(1);
		when(userService.getItemById(anyInt())).thenReturn(userDTO);
		mockMvc.perform(get(ROOT_REQUEST + "/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/update"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError404WhenNotFoundException() throws Exception {
		doThrow(NotFoundException.class).when(userService).getItemById(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/update/10"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(userService).getItemById(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// updateUser
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void updateUserReturnsListWhenOk() throws Exception {
		UpdateUserDTO rightUpdateUserDTO = new UpdateUserDTO();
		rightUpdateUserDTO.setFullname("fullname2");
		rightUpdateUserDTO.setUsername("username1");
		rightUpdateUserDTO.setRole("role1");
		mockMvc.perform(post(ROOT_REQUEST + "/update/1")
				.flashAttr("user", rightUpdateUserDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=User has been updated."));
	}

	@WithMockUser
	@Test
	void updateUserReturnsFormWithErrorWhenValidationError() throws Exception {
		UpdateUserDTO wrongUpdateUserDTO = new UpdateUserDTO();
		wrongUpdateUserDTO.setFullname("");
		wrongUpdateUserDTO.setUsername("username2");
		wrongUpdateUserDTO.setRole("role1");
		mockMvc.perform(post(ROOT_REQUEST + "/update/1")
				.flashAttr("user", wrongUpdateUserDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/update/1?error=Fullname is mandatory. "));
	}

	@WithMockUser
	@Test
	void updateUserReturnsError404WhenNotFoundException() throws Exception {
		UpdateUserDTO rightUpdateUserDTO = new UpdateUserDTO();
		rightUpdateUserDTO.setFullname("fullname2");
		rightUpdateUserDTO.setUsername("username1");
		rightUpdateUserDTO.setRole("role1");
		doThrow(NotFoundException.class).when(userService).updateItem(anyInt(), any(UpdateUserDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/update/10")
				.flashAttr("user", rightUpdateUserDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=User not found."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// deleteUser
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void deleteUserReturnsListWhenOk() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=User has been deleted."));
	}

	@WithMockUser
	@Test
	void deleteUserReturnsError404WhenNotFoundException() throws Exception {
		doThrow(NotFoundException.class).when(userService).deleteItem(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/delete/10"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=User not found."));
	}
}
