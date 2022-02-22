/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

/**
 * Update CurvePoint DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class UpdateCurvePointDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Curve id.
	 */
    @NotNull(message = "Curve Id is mandatory.")
	@Positive(message = "CurveId must be strictly positive.")
	private Integer curveId;
	
	/**
	 * Term.
	 */
    @NotNull(message = "Term is mandatory.")
	@Positive(message = "Term must be strictly positive.")
	private BigDecimal term;
	
	/**
	 * Value.
	 */
    @NotNull(message = "Value is mandatory.")
	@Positive(message = "Value must be strictly positive.")
	private BigDecimal value;
}
