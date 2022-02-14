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
@Table(name = "rating")
public class Rating {
    
	/**
	 * Id.
	 */
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "TINYINT")
	private Integer id;
	
	/**
	 * Moodys rating.
	 */
    @Column(name = "moodys_rating")
	private String moodysRating;
	
	/**
	 * Sand Prating.
	 */
    @Column(name = "sandp_rating")
	private String sandPRating;

	/**
	 * Fitch rating.
	 */
    @Column(name = "fitch_rating")
	private String fitchRating;

	/**
	 * Order number.
	 */
	@Column(name = "order_number", columnDefinition = "TINYINT")
	private Integer orderNumber;
	
	public Rating() {}
	
	public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}
}
