package com.nnk.springboot.exceptions;

/**
 * Poseidon exception, parent for application's exceptions.
 * @author tipikae
 * @version 1.0
 *
 */
public class PoseidonException extends Exception {

	private static final long serialVersionUID = 1L;

	public PoseidonException(String arg0) {
		super(arg0);
	}

	
}
