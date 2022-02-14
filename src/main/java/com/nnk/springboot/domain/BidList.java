package com.nnk.springboot.domain;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    private double bidQuantity;

    /**
     * AskQuantity.
     */
    @Column(name = "ask_quantity")
    private double askQuantity;

    /**
     * Bid.
     */
    private double bid;

    /**
     * Ask.
     */
    private double ask;

    /**
     * Benchmark.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String benchmark;

    /**
     * BidList date.
     */
    @Column(name = "bidlist_date")
    private Timestamp bidListDate;

    /**
     * Commentary.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String commentary;

    /**
     * Security.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String security;

    /**
     * Status.
     */
    @Size(max = 10, message = "{validation.name.size.too_long}")
    private String status;

    /**
     * Trader.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String trader;

    /**
     * Book.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String book;

    /**
     * Creation name.
     */
    @Column(name = "creation_name")
    @Size(max = 125, message = "{validation.name.size.too_long}")
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
    @Size(max = 125, message = "{validation.name.size.too_long}")
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
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String dealName;

    /**
     * Deal type.
     */
    @Column(name = "deal_type")
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String dealType;

    /**
     * Source list id.
     */
    @Column(name = "sourcelist_id")
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String sourceListId;

    /**
     * Side.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String side;
    
    public BidList() {}
    
    public BidList(String account, String type, double bidQuantity) {
    	this.account = account;
    	this.type = type;
    	this.bidQuantity = bidQuantity;
    }

}
