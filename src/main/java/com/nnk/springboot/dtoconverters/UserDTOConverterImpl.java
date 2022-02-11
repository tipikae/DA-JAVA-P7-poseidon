/**
 * 
 */
package com.nnk.springboot.dtoconverters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exceptions.ConverterException;

/**
 * User DTO converter implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class UserDTOConverterImpl implements IUserDTOConverter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDTOConverterImpl.class);

	@Override
	public UserDTO convertEntityToDTO(User user) throws ConverterException {
		if(user.getUsername().equals("")) {
			LOGGER.debug("convertEntityToDTO: ConverterException: username is empty");
			throw new ConverterException("Empty field.");
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setFullname(user.getFullname());
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		
		return userDTO;
	}

	@Override
	public List<UserDTO> convertListEntityToDTO(List<User> users) throws ConverterException {
		List<UserDTO> userDTOs = new ArrayList<>();
		for(User user: users) {
			try {
				userDTOs.add(convertEntityToDTO(user));
			} catch (ConverterException e) {
				LOGGER.debug("convertListEntityToDTO: ConverterException: " + e.getMessage());
				throw new ConverterException("Incorrect field");
			}
		}
		
		return userDTOs;
	}

}
