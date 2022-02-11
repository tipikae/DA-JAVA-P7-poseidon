/**
 * 
 */
package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;

/**
 * Service generic interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IService<D, N> {

	/**
	 * Add an item.
	 * @param newDTO
	 * @return D
	 * @throws ServiceException
	 * @throws ConverterException
	 */
	D addItem(N newDTO) throws ServiceException, ConverterException;
	
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
	 * @throws NotFoundException
	 * @throws ConverterException
	 */
	D getItemById(Integer id) throws NotFoundException, ConverterException;
	
	/**
	 * Update an item.
	 * @param id
	 * @param updatedDTO
	 * @throws NotFoundException
	 */
	void updateItem(Integer id, N updatedDTO) throws NotFoundException;
	
	/**
	 * Delete an item.
	 * @param id
	 * @throws NotFoundException
	 */
	void deleteItem(Integer id) throws NotFoundException;
}
