package com.nnk.springboot.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.NewUserDTO;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.dtoconverters.IUserDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private IUserDTOConverter userConverter;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	private static NewUserDTO rightNewUserDTO;
	private static NewUserDTO wrongNewUserDTO;
	private static User user1;
	private static User user2;
	private static UserDTO userDTO1;
	private static UserDTO userDTO2;
	private static List<User> users;
	private static List<UserDTO> userDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightNewUserDTO = new NewUserDTO();
		wrongNewUserDTO = new NewUserDTO();
		user1 = new User();
		user2 = new User();
		userDTO1 = new UserDTO();
		userDTO2 = new UserDTO();
		users = new ArrayList<>();
		userDTOs = new ArrayList<>();
		
		rightNewUserDTO.setFullname("fullname1");
		rightNewUserDTO.setPassword("password1");
		rightNewUserDTO.setUsername("username1");
		
		wrongNewUserDTO.setFullname("fullname2");
		wrongNewUserDTO.setPassword("password2");
		wrongNewUserDTO.setUsername("");
		
		user1.setFullname("fullname1");
		user1.setId(1);
		user1.setPassword("password1");
		user1.setUsername("username1");
		
		user2.setFullname("fullname2");
		user2.setId(2);
		user2.setPassword("password2");
		user2.setUsername("username2");
		
		userDTO1.setFullname("fullname1");
		userDTO1.setId(1);
		userDTO1.setUsername("username1");
		
		userDTO2.setFullname("fullname1");
		userDTO2.setId(1);
		userDTO2.setUsername("username1");
		
		users.add(user1);
		users.add(user2);
		
		userDTOs.add(userDTO1);
		userDTOs.add(userDTO2);
	}
	

	@Test
	void addItemReturnsDTOWhenOk() throws ConverterException, ServiceException {
		when(passwordEncoder.encode(anyString())).thenReturn("");
		when(userRepository.save(any(User.class))).thenReturn(user1);
		when(userConverter.convertEntityToDTO(any(User.class))).thenReturn(userDTO1);
		assertEquals(rightNewUserDTO.getFullname(), 
				userService.addItem(rightNewUserDTO).getFullname());
	}

	@Test
	void addItemThrowsServiceExceptionWhenError() {
		
	}

	@Test
	void getAllItemsReturnsListDTOWhenOk() throws ConverterException, ServiceException {
		when(userRepository.findAll()).thenReturn(users);
		when(userConverter.convertListEntityToDTO(users)).thenReturn(userDTOs);
		assertEquals(users.size(), userService.getAllItems().size());
	}

	@Test
	void getItemByIdReturnsDTOWhenOk() throws ConverterException, NotFoundException, ServiceException {
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user1));
		when(userConverter.convertEntityToDTO(user1)).thenReturn(userDTO1);
		assertEquals(user1.getId(), userService.getItemById(1).getId());
	}

	@Test
	void getItemByIdThrowsNotFoundExceptionWhenNotFound() {
		when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> userService.getItemById(1));
	}

	@Test
	void updateItemWhenOk() throws NotFoundException, ServiceException {
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
		userService.updateItem(1, rightNewUserDTO);
		Mockito.verify(userRepository).save(any(User.class));
	}

	@Test
	void updateItemThrowsNotFoundExceptionWhenNotFound() {
		when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> userService.updateItem(10, new NewUserDTO()));
	}

	@Test
	void deleteItemWhenOk() throws NotFoundException, ServiceException {
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
		userService.deleteItem(1);
		Mockito.verify(userRepository).delete(any(User.class));
	}

	@Test
	void deleteItemThrowsNotFoundExceptionWhenNotFound() {
		when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> userService.deleteItem(10));
	}
}
