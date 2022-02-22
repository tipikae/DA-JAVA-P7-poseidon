package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;
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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
	private BigDecimal term;
	
	/**
	 * Value.
	 */
	private BigDecimal value;
	
	/**
	 * Creation date.
	 */
    @Column(name = "creation_date")
	private Timestamp creationDate;
    
    public CurvePoint() {}
	
	public CurvePoint(Integer curveId, BigDecimal term, BigDecimal value) {
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}
}
