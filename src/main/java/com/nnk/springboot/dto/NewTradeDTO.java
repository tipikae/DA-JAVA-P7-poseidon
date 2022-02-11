/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * New Trade DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class NewTradeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Account.
	 */
	@Size(max=30, message="{validation.name.size.too_long}")
	@NotBlank
	private String account;

	/**
	 * Type.
	 */
	@Size(max=30, message="{validation.name.size.too_long}")
	@NotBlank
	private String type;
}