/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * User DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Id.
	 */
    private Integer id;
    
    /**
     * Username.
     */
    private String username;
    
    /**
     * Fullname.
     */
    private String fullname;
}
