package com.nnk.springboot.exceptions;

/**
 * Item already exists exception.
 * @author tipikae
 * @version 1.0
 *
 */
public class ItemAlreadyExistsException extends PoseidonException {

	private static final long serialVersionUID = 1L;

	public ItemAlreadyExistsException(String arg0) {
		super(arg0);
	}

}
