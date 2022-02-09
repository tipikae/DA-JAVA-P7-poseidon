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
@Table(name = "Bidlist")
public class BidList {

	/**
	 * Id.
	 */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "BidListId", columnDefinition = "TINYINT")
    private Integer id;

    /**
     * Account.
     */
    @Size(max = 30, message = "{validation.name.size.too_long}")
    @NotBlank(message = "Account is mandatory")
    private String account;

    /**
     * Type.
     */
    @Size(max = 30, message = "{validation.name.size.too_long}")
    @NotBlank(message = "Type is mandatory")
    private String type;

    /**
     * BiQuantity.
     */
    private double bidQuantity;

    /**
     * AskQuantity.
     */
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
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String creationName;

    /**
     * Creation date.
     */
    private Timestamp creationDate;

    /**
     * Revision name.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String revisionName;

    /**
     * Revision date.
     */
    private Timestamp revisionDate;

    /**
     * Deal name.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String dealName;

    /**
     * Deal type.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String dealType;

    /**
     * Source list id.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String sourceListId;

    /**
     * Side.
     */
    @Size(max = 125, message = "{validation.name.size.too_long}")
    private String side;
}
