/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * New Rating DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class NewRatingDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Moodys rating.
	 */
	@NotBlank(message = "Moodys must not be empty.")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String moodysRating;
	
	/**
	 * Sand Prating.
	 */
	@NotBlank(message = "Sand must not be empty.")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String sandPRating;

	/**
	 * Fitch rating.
	 */
	@NotBlank(message = "Fitch must not be empty.")
	@Size(max=125, message="{validation.name.size.too_long}")
    private String fitchRating;

	/**
	 * Order number.
	 */
	@Positive(message = "Order number must be strictly positive.")
	private Integer orderNumber;
}
