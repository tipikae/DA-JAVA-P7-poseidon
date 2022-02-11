/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Trade DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class TradeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Id.
	 */
	private Integer tradeId;

	/**
	 * Account.
	 */
	private String account;

	/**
	 * Type.
	 */
	private String type;
}
