/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * New User DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class NewUserDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Id.
	 */
    private Integer id;
    
    /**
     * Username.
     */
    @NotBlank(message = "Username is mandatory")
    private String username;
    
    /**
     * Password.
     */
    @NotBlank(message = "Password is mandatory")
    private String password;
    
    /**
     * Fullname.
     */
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
}
