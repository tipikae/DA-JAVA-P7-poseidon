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
@Table(name = "rulename")
public class RuleName {

	/**
	 * Id.
	 */
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "TINYINT")
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
    @Column(name = "sql_str")
	private String sqlStr;

	/**
	 * Sql part.
	 */
    @Column(name = "sql_part")
	private String sqlPart;
	
	public RuleName() {}
	
	public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}
}
