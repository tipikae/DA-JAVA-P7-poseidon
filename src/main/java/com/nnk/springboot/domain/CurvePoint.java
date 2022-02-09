package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * CurvePoint entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "CurvePoint")
public class CurvePoint {
    
	/**
	 * Id.
	 */
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "Id", columnDefinition = "TINYINT")
	private Integer id;

	/**
	 * CurveId.
	 */
    @Column(name = "CurveId", columnDefinition = "TINYINT")
	private Integer curveId;
    
    /**
     * As of date.
     */
	private Timestamp asOfDate;
	
	/**
	 * Term.
	 */
	private double term;
	
	/**
	 * Value.
	 */
	private double value;
	
	/**
	 * Creation date.
	 */
	private Timestamp creationDate;
}
