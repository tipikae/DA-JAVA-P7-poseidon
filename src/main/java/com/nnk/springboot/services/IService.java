/**
 * 
 */
package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;

/**
 * Generic service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IService<D, N, U> {

	/**
	 * Add an item.
	 * @param newDTO
	 * @return D
	 * @throws ItemAlreadyExistsException
	 * @throws ConverterException
	 */
	D addItem(N newDTO) throws ItemAlreadyExistsException, ConverterException;
	
	/**
	 * Get all items.
	 * @return List<D>
	 * @throws ConverterException
	 */
	List<D> getAllItems() throws ConverterException;
	
	/**
	 * Get an item by id.
	 * @param id
	 * @return D
	 * @throws ItemNotFoundException
	 * @throws ConverterException
	 */
	D getItemById(Integer id) throws ItemNotFoundException, ConverterException;
	
	/**
	 * Update an item.
	 * @param id
	 * @param updatedDTO
	 * @throws ItemNotFoundException
	 */
	void updateItem(Integer id, U updatedDTO) throws ItemNotFoundException;
	
	/**
	 * Delete an item.
	 * @param id
	 * @throws ItemNotFoundException
	 */
	void deleteItem(Integer id) throws ItemNotFoundException;
}
