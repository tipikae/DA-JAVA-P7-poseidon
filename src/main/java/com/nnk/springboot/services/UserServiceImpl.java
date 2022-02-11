/**
 * 
 */
package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.NewUserDTO;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.dtoconverters.IUserDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.UserRepository;

/**
 * User Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserDTOConverter userConverter;

	@Override
	public UserDTO addItem(NewUserDTO newDTO) throws ServiceException, ConverterException {
		LOGGER.debug("Service: addItem: fullname=" + newDTO.getFullname() + ", password=" + newDTO.getPassword()
				+ ", username=" + newDTO.getUsername());
		/*if(false) {
			LOGGER.debug("");
			throw new ServiceException("");
		}*/
		
		User user = new User();
		user.setFullname(newDTO.getFullname());
		user.setPassword(passwordEncoder.encode(newDTO.getPassword()));
		user.setRole("USER");
		user.setUsername(newDTO.getUsername());
		
		return userConverter.convertEntityToDTO(userRepository.save(user));
	}

	@Override
	public List<UserDTO> getAllItems() throws ConverterException {
		LOGGER.debug("Service: getAllItems");
		return userConverter.convertListEntityToDTO(userRepository.findAll());
	}

	@Override
	public UserDTO getItemById(Integer id) throws NotFoundException, ConverterException {
		LOGGER.debug("Service: getItemById: id=" + id);
		Optional<User> optional = userRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("User with id=" + id + " not found.");
			throw new NotFoundException("User not found.");
		}
		
		return userConverter.convertEntityToDTO(optional.get());
	}

	@Override
	public void updateItem(Integer id, NewUserDTO updatedDTO) throws NotFoundException {
		LOGGER.debug("Service: updateItem: id=" + id);
		Optional<User> optional = userRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("User with id=" + id + " not found.");
			throw new NotFoundException("Trade not found.");
		}

		User user = optional.get();
		user.setFullname(updatedDTO.getFullname());
		user.setUsername(updatedDTO.getUsername());
		
		userRepository.save(user);
	}

	@Override
	public void deleteItem(Integer id) throws NotFoundException {
		LOGGER.debug("Service: deleteItem: id=" + id);
		Optional<User> optional = userRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("User with id=" + id + " not found.");
			throw new NotFoundException("User not found.");
		}

		userRepository.delete(optional.get());
	}

}
