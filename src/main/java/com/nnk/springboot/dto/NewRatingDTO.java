/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

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
	@Size(max=125, message="{validation.name.size.too_long}")
	private String moodysRating;
	
	/**
	 * Sand Prating.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String sandPRating;

	/**
	 * Fitch rating.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
    private String fitchRating;

	/**
	 * Order number.
	 */
	@Positive
	private Integer orderNumber;
}
