package com.nnk.springboot.dtoconverters;

import java.util.List;

import com.nnk.springboot.exceptions.ConverterException;

/**
 * Converter generic interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IConverter <E, D> {

	D convertEntityToDTO(E entity) throws ConverterException;
	List<D> convertListEntityToDTO(List<E> entities) throws ConverterException;
}
