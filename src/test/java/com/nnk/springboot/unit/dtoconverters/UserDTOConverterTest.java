package com.nnk.springboot.unit.dtoconverters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.dtoconverters.UserDTOConverterImpl;
import com.nnk.springboot.exceptions.ConverterException;

class UserDTOConverterTest {

	private UserDTOConverterImpl userConverter = new UserDTOConverterImpl();

	private static User rightUser1;
	private static User rightUser2;
	private static User wrongUser;
	private static UserDTO userDTO1;
	private static UserDTO userDTO2;
	private static List<User> rightUsers;
	private static List<User> wrongUsers;
	private static List<UserDTO> userDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightUser1 = new User();
		rightUser2 = new User();
		wrongUser = new User();
		userDTO1 = new UserDTO();
		userDTO2 = new UserDTO();
		rightUsers = new ArrayList<>();
		wrongUsers = new ArrayList<>();
		userDTOs = new ArrayList<>();
		
		rightUser1.setId(1);
		rightUser1.setFullname("fullname1");
		rightUser1.setPassword("password1");
		rightUser1.setRole("role1");
		rightUser1.setUsername("username1");
		
		rightUser2.setId(2);
		rightUser2.setFullname("fullname2");
		rightUser2.setPassword("password2");
		rightUser2.setRole("role2");
		rightUser2.setUsername("username2");
		
		wrongUser.setId(1);
		wrongUser.setFullname("fullname1");
		wrongUser.setPassword("password1");
		wrongUser.setRole("role1");
		wrongUser.setUsername("");
		
		rightUsers.add(rightUser1);
		rightUsers.add(rightUser2);
		
		wrongUsers.add(rightUser1);
		wrongUsers.add(wrongUser);
		
		userDTO1.setId(1);
		userDTO1.setFullname("fullname1");
		userDTO1.setUsername("username1");
		
		userDTO2.setId(2);
		userDTO2.setFullname("fullname2");
		userDTO2.setUsername("username2");
		
		userDTOs.add(userDTO1);
		userDTOs.add(userDTO2);
	}

	@Test
	void convertEntityToDTOReturnsDTOWhenOk() throws ConverterException {
		assertEquals(userDTO1.getId(), 
				userConverter.convertEntityToDTO(rightUser1).getId());
	}

	@Test
	void convertEntityToDTOThrowsConverterExceptionWhenEmptyField() {
		assertThrows(ConverterException.class, () -> userConverter.convertEntityToDTO(wrongUser));
	}

	@Test
	void convertListEntityToDTOReturnsListDTOWhenOk() throws ConverterException {
		assertEquals(2, userConverter.convertListEntityToDTO(rightUsers).size());
	}

	@Test
	void convertListEntityToDTOThrowsConverterExceptionWhenEmptyField() {
		assertThrows(ConverterException.class, () -> userConverter.convertListEntityToDTO(wrongUsers));
	}
}
