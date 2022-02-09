package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

import java.sql.Timestamp;

/**
 * RuleName entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "RuleName")
public class RuleName {

	/**
	 * Id.
	 */
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "Id", columnDefinition = "TINYINT")
	private Integer id;
	
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
	
	public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}
}
