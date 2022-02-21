/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Update User DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class UpdateUserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * Username.
     */
    @NotBlank(message = "Username is mandatory.")
	@Size(max=125, message="{validation.name.size.too_long}")
    private String username;
    
    /**
     * Fullname.
     */
    @NotBlank(message = "Fullname is mandatory.")
	@Size(max=125, message="{validation.name.size.too_long}")
    private String fullname;
    
    /**
     * Role.
     */
    @NotBlank(message = "Role is mandatory.")
	@Size(max=125, message="{validation.name.size.too_long}")
    private String role;
}
