/**
 * 
 */
package com.nnk.springboot.dtoconverters;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;

/**
 * Converter User to UserDTO interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IUserDTOConverter extends IDTOConverter<User, UserDTO> {

}
