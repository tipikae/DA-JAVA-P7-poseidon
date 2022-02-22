package com.nnk.springboot.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Bid quantity is mandatory.")
	@Positive(message = "Bid quantity must be positive.")
	private BigDecimal bidQuantity;
	
}
