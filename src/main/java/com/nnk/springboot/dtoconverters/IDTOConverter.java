package com.nnk.springboot.dtoconverters;

import java.util.List;

import com.nnk.springboot.exceptions.ConverterException;

/**
 * Generic converter entity to DTO interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IDTOConverter <E, D> {

	/**
	 * Convert entity E to DTO D.
	 * @param entity E
	 * @return D
	 * @throws ConverterException
	 */
	D convertEntityToDTO(E entity) throws ConverterException;
	
	/**
	 * Convert List entities E to List DTOs D.
	 * @param entities List<E>
	 * @return List<D>
	 * @throws ConverterException
	 */
	List<D> convertListEntityToDTO(List<E> entities) throws ConverterException;
}
