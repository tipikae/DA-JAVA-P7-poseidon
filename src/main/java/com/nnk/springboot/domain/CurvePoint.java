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
@Table(name = "curvepoint")
public class CurvePoint {
    
	/**
	 * Id.
	 */
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "TINYINT")
	private Integer id;

	/**
	 * CurveId.
	 */
    @Column(name = "curve_id", columnDefinition = "TINYINT")
	private Integer curveId;
    
    /**
     * As of date.
     */
    @Column(name = "asof_date")
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
    @Column(name = "creation_date")
	private Timestamp creationDate;
    
    public CurvePoint() {}
	
	public CurvePoint(Integer curveId, double term, double value) {
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}
}
