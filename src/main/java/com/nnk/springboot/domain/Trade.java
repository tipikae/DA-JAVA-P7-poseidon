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
    @Column(name = "trade_id", columnDefinition = "TINYINT")
	private Integer tradeId;

	/**
	 * Account.
	 */
	private String account;

	/**
	 * Type.
	 */
	private String type;
	
	/**
	 * Buy quantity.
	 */
    @Column(name = "buy_quantity")
	private double buyQuantity;
	
	/**
	 * Sell quantity.
	 */
    @Column(name = "sell_quantity")
	private double sellQuantity;
	
	/**
	 * Buy price.
	 */
    @Column(name = "buy_price")
	private double buyPrice;
	
	/**
	 * Sell price.
	 */
    @Column(name = "sell_price")
	private double sellPrice;
	
	/**
	 * Trade date.
	 */
    @Column(name = "trade_date")
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
    @Column(name = "creation_name")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String creationName;
	
	/**
	 * Creation date.
	 */
    @Column(name = "creation_date")
	private Timestamp creationDate;
	
	/**
	 * Revision name.
	 */
    @Column(name = "revision_name")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String revisionName;
	
	/**
	 * Revision date.
	 */
    @Column(name = "revision_date")
	private Timestamp revisionDate;
	
	/**
	 * Deal name.
	 */
    @Column(name = "deal_name")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String dealName;
	
	/**
	 * Deal type.
	 */
    @Column(name = "deal_type")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String dealType;
	
	/**
	 * Source list id.
	 */
    @Column(name = "sourcelist_id")
	@Size(max=125, message="{validation.name.size.too_long}")
	private String sourceListId;
	
	/**
	 * Side.
	 */
	@Size(max=125, message="{validation.name.size.too_long}")
	private String side;

	public Trade() {}

	public Trade(String account, String type) {
		this.account = account;
		this.type = type;
	}
	
}
