package com.nnk.springboot.dto;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * New BidList DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class NewBidListDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Account.
	 */
    @Size(max = 30, message = "{validation.name.size.too_long}")
    @NotBlank(message = "Account is mandatory.")
	private String account;

    /**
     * Type.
     */
    @Size(max = 30, message = "{validation.name.size.too_long}")
    @NotBlank(message = "Type is mandatory.")
	private String type;
    
    /**
     * Bid quantity.
     */
	@Positive(message = "Quantity must be positive.")
	@Digits(integer = 10, fraction = 2, message = "Quantity must be a decimal number.")
	private double bidQuantity;
	
}
