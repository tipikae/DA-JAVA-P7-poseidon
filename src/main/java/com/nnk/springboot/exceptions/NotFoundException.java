package com.nnk.springboot.exceptions;

/**
 * BidList not found exception.
 * @author tipikae
 * @version 1.0
 *
 */
public class NotFoundException extends PoseidonException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String arg0) {
		super(arg0);
	}

}
