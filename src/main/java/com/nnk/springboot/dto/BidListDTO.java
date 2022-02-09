package com.nnk.springboot.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * BidList DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class BidListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Id.
	 */
	private Integer id;
	
	/**
	 * Account.
	 */
	private String account;
	
	/**
	 * Type.
	 */
	private String type;
	
	/**
	 * Bid quantity.
	 */
	private double bidQuantity;
}
