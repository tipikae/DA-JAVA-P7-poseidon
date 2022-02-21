package com.nnk.springboot.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * CurvePoint DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class CurvePointDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Id.
	 */
	private Integer id;
	
	/**
	 * Curve id.
	 */
	private Integer curveId;
	
	/**
	 * Term.
	 */
	private BigDecimal term;
	
	/**
	 * Value.
	 */
	private BigDecimal value;
}
