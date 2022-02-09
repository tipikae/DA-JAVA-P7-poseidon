package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Trade entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "Trade")
public class Trade {
	
	/**
	 * Id.
	 */
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "TradeId", columnDefinition = "TINYINT")
	private Integer tradeId;

	/**
	 * Account.
	 */
	@Size(max=30, message="{validation.name.size.too_long}")
	@NotBlank
	private String account;

	/**
	 * Type.
	 */
	@Size(max=30, message="{validation.name.size.too_long}")
	@NotBlank
	private String type;
	
	/**
	 * Buy quantity.
	 */
	private double buyQuantity;
	
	/**
	 * Sell quantity.
	 */
	private double sellQuantity;
	
	/**
	 * Buy price.
	 */
	private double buyPrice;
	
	/**
	 * Sell price.
	 */
	private double sellPrice;
	
	/**
	 * Trade date.
	 */
	private Timestamp tradeDate;
	
	/**
	 * Security.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String security;
	
	/**
	 * Status.
	 */
	@Size(max=10, message="{validation.name.size.too_long}")
	private String status;
	
	/**
	 * Trader.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String trader;
	
	/**
	 * Benchmark.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String benchmark;
	
	/**
	 * Book.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String book;
	
	/**
	 * Creation name.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String creationName;
	
	/**
	 * Creation date.
	 */
	private Timestamp creationDate;
	
	/**
	 * Revision name.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String revisionName;
	
	/**
	 * Revision date.
	 */
	private Timestamp revisionDate;
	
	/**
	 * Deal name.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String dealName;
	
	/**
	 * Deal type.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String dealType;
	
	/**
	 * Source list id.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String sourceListId;
	
	/**
	 * Side.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String side;
}
