/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Update RuleName DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class UpdateRuleNameDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Name.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String name;

	/**
	 * Description.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String description;

	/**
	 * Json.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String json ;

	/**
	 * Template.
	 */
	@Size(max=512, message="{validation.name.size.too_long}")
	private String template;

	/**
	 * Sql string.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String sqlStr;

	/**
	 * Sql part.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String sqlPart;
}
