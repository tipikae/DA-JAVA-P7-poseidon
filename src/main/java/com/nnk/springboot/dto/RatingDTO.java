/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Rating DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class RatingDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Id.
	 */
	private Integer id;
	
	/**
	 * Moodys rating.
	 */
	private String moodysRating;
	
	/**
	 * Sand Prating.
	 */
	private String sandPRating;

	/**
	 * Fitch rating.
	 */
    private String fitchRating;

	/**
	 * Order number.
	 */
	private Integer orderNumber;

}
