package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Rating entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "Rating")
public class Rating {
    
	/**
	 * Id.
	 */
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "Id", columnDefinition = "TINYINT")
	private Integer id;
	
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
	@Column(name = "orderNumber", columnDefinition = "TINYINT")
	private Integer orderNumber;
	
	public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}
}
