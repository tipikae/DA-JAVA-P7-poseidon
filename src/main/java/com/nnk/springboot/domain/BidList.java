package com.nnk.springboot.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * BidList entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "bidlist")
public class BidList {

	/**
	 * Id.
	 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "bidlist_id", columnDefinition = "TINYINT")
    private Integer bidListId;

    /**
     * Account.
     */
    private String account;

    /**
     * Type.
     */
    private String type;

    /**
     * BiQuantity.
     */
    @Column(name = "bid_quantity")
    private BigDecimal bidQuantity;

    /**
     * AskQuantity.
     */
    @Column(name = "ask_quantity")
    private BigDecimal askQuantity;

    /**
     * Bid.
     */
    private BigDecimal bid;

    /**
     * Ask.
     */
    private BigDecimal ask;

    /**
     * Benchmark.
     */
    private String benchmark;

    /**
     * BidList date.
     */
    @Column(name = "bidlist_date")
    private Timestamp bidListDate;

    /**
     * Commentary.
     */
    private String commentary;

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
    
    public BidList() {}
    
    public BidList(String account, String type, BigDecimal bidQuantity) {
    	this.account = account;
    	this.type = type;
    	this.bidQuantity = bidQuantity;
    }

}
