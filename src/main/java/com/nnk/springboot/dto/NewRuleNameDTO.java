/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

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
	private String name;

	/**
	 * Description.
	 */
	private String description;

	/**
	 * Json.
	 */
	private String json ;

	/**
	 * Template.
	 */
	private String template;

	/**
	 * Sql string.
	 */
	private String sqlStr;

	/**
	 * Sql part.
	 */
	private String sqlPart;
}
