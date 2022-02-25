/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * New RuleName DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class NewRuleNameDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Name.
	 */
	@NotBlank(message = "Name must not be empty.")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String name;

	/**
	 * Description.
	 */
	@NotBlank(message = "Description must not be empty.")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String description;

	/**
	 * Json.
	 */
	@NotBlank(message = "Json must not be empty.")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String json ;

	/**
	 * Template.
	 */
	@NotBlank(message = "Template must not be empty.")
	@Size(max=512, message="{validation.name.size.too_long}")
	private String template;

	/**
	 * Sql string.
	 */
	@NotBlank(message = "SQL string must not be empty.")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String sqlStr;

	/**
	 * Sql part.
	 */
	@NotBlank(message = "SQL part must not be empty.")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String sqlPart;
}
