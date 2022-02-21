package com.nnk.springboot.domain;

import javax.persistence.*;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Trade entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "trade")
public class Trade {
	
	/**
	 * Id.
	 */
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
	private BigDecimal buyQuantity;
	
	/**
	 * Sell quantity.
	 */
    @Column(name = "sell_quantity")
	private BigDecimal sellQuantity;
	
	/**
	 * Buy price.
	 */
    @Column(name = "buy_price")
	private BigDecimal buyPrice;
	
	/**
	 * Sell price.
	 */
    @Column(name = "sell_price")
	private BigDecimal sellPrice;
	
	/**
	 * Trade date.
	 */
    @Column(name = "trade_date")
	private Timestamp tradeDate;
	
	/**
	 * Security.
	 */
	private String security;
	
	/**
	 * Status.
	 */
	private String status;
	
	/**
	 * Trader.
	 */
	private String trader;
	
	/**
	 * Benchmark.
	 */
	private String benchmark;
	
	/**
	 * Book.
	 */
	private String book;
	
	/**
	 * Creation name.
	 */
    @Column(name = "creation_name")
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
	private String dealName;
	
	/**
	 * Deal type.
	 */
    @Column(name = "deal_type")
	private String dealType;
	
	/**
	 * Source list id.
	 */
    @Column(name = "sourcelist_id")
	private String sourceListId;
	
	/**
	 * Side.
	 */
	private String side;

	public Trade() {}

	public Trade(String account, String type, BigDecimal buyQuantity) {
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
	}
	
}
