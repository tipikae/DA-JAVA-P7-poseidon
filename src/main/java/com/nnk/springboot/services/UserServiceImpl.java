/**
 * 
 */
package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.NewUserDTO;
import com.nnk.springboot.dto.UpdateUserDTO;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.dtoconverters.IUserDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.repositories.UserRepository;

/**
 * User Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserDTOConverter userConverter;
	
	@Value("${poseidon.user.role}")
	private String USER_ROLE;
	
	@Value("${poseidon.admin.role}")
	private String ADMIN_ROLE;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDTO addItem(NewUserDTO newDTO) throws ItemAlreadyExistsException, ConverterException {
		LOGGER.debug("Service: addItem: fullname=" + newDTO.getFullname() + ", password=" + newDTO.getPassword()
				+ ", username=" + newDTO.getUsername());
		Optional<User> optional = userRepository.findByUsername(newDTO.getUsername());
		if(optional.isPresent()) {
			LOGGER.debug("User with username:" + newDTO.getUsername() + " already exists");
			throw new ItemAlreadyExistsException("User already exists.");
		}
		
		User user = new User();
		user.setFullname(newDTO.getFullname());
		user.setPassword(passwordEncoder.encode(newDTO.getPassword()));
		user.setRole(setRole(newDTO.getRole()));
		user.setUsername(newDTO.getUsername());
		
		return userConverter.convertEntityToDTO(userRepository.save(user));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserDTO> getAllItems() throws ConverterException {
		LOGGER.debug("Service: getAllItems");
		return userConverter.convertListEntityToDTO(userRepository.findAll());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDTO getItemById(Integer id) throws ItemNotFoundException, ConverterException {
		LOGGER.debug("Service: getItemById: id=" + id);
		Optional<User> optional = userRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("User with id=" + id + " not found.");
			throw new ItemNotFoundException("User not found.");
		}
		
		return userConverter.convertEntityToDTO(optional.get());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateItem(Integer id, UpdateUserDTO updatedDTO) throws ItemNotFoundException {
		LOGGER.debug("Service: updateItem: id=" + id);
		Optional<User> optional = userRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("User with id=" + id + " not found.");
			throw new ItemNotFoundException("Trade not found.");
		}

		User user = optional.get();
		user.setFullname(updatedDTO.getFullname());
		user.setUsername(updatedDTO.getUsername());
		user.setRole(setRole(updatedDTO.getRole()));
		
		userRepository.save(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteItem(Integer id) throws ItemNotFoundException {
		LOGGER.debug("Service: deleteItem: id=" + id);
		Optional<User> optional = userRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("User with id=" + id + " not found.");
			throw new ItemNotFoundException("User not found.");
		}

		userRepository.delete(optional.get());
	}
	
	private String setRole(String roleDTO) {
		if(roleDTO.equals(ADMIN_ROLE)) {
			return ADMIN_ROLE;
		} else {
			return USER_ROLE;
		}
	}

}
