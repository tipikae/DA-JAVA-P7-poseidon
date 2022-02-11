/**
 * 
 */
package com.nnk.springboot.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * RuleName DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class RuleNameDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Id.
	 */
	private Integer id;
	
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
