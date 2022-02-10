package com.nnk.springboot.dto;

import java.io.Serializable;

import javax.validation.constraints.Digits;

import lombok.Data;

/**
 *  New CurvePoint DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class NewCurvePointDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Curve id.
	 */
	private Integer curveId;
	
	/**
	 * Term.
	 */
	@Digits(integer = 10, fraction = 2, message = "Term must be a decimal number.")
	private double term;
	
	/**
	 * Value.
	 */
	@Digits(integer = 10, fraction = 2, message = "Value must be a decimal number.")
	private double value;
}
