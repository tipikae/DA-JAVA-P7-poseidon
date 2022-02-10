package com.nnk.springboot.dto;

import java.io.Serializable;

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
	private double term;
	
	/**
	 * Value.
	 */
	private double value;
}
