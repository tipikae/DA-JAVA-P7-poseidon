/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
     * Username.
     */
    @NotBlank(message = "Username is mandatory.")
    private String username;
    
    /**
     * Password.
     */
    @NotBlank(message = "Password is mandatory.")
    @Pattern(
    	regexp = "(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-+]).{8,}",
    	message = "Password must contain minimum 8 characters, at least on upper case letter, one number and one special character.")
    private String password;
    
    /**
     * Fullname.
     */
    @NotBlank(message = "Fullname is mandatory.")
    private String fullname;
    
    /**
     * Role.
     */
    @NotBlank(message = "Role is mandatory.")
    private String role;
}
