/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "Username is mandatory")
    private String username;
    
    /**
     * Fullname.
     */
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
}
