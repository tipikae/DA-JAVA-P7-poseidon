/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Update Trade DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class UpdateTradeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Account.
	 */
	@Size(max=30, message="{validation.name.size.too_long}")
	@NotBlank(message = "Account must not be empty.")
	private String account;

	/**
	 * Type.
	 */
	@Size(max=30, message="{validation.name.size.too_long}")
	@NotBlank(message = "Type must not be empty.")
	private String type;
	
	/**
	 * Buy quantity.
	 */
    @NotNull(message = "Buy quantity is mandatory.")
	@Positive(message = "Buy quantity must be strictly positive.")
	@Digits(integer = 10, fraction = 2, message = "Buy quantity must be a decimal number.")
	private BigDecimal buyQuantity;
}
