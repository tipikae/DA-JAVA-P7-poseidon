package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dto.NewBidListDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;

/**
 * BidList Service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IBidListService {

	/**
	 * Add a BidList.
	 * @param bid
	 * @return BidListDTO
	 * @throws ServiceException
	 * @throws ConverterException 
	 */
	BidListDTO addBidList(NewBidListDTO newBidList) throws ServiceException, ConverterException;
	
	/**
	 * Get all BidLists.
	 * @return List<BidListDTO>
	 * @throws ConverterException 
	 */
	List<BidListDTO> getAllBids() throws ConverterException;
	
	/**
	 * Get a BidList.
	 * @param id
	 * @return BidListDTO
	 * @throws NotFoundException
	 * @throws ConverterException 
	 */
	BidListDTO getBidList(Integer id) throws NotFoundException, ConverterException;
	
	/**
	 * Update a BidList.
	 * @param id
	 * @param bid
	 * @throws NotFoundException
	 */
	void updateBidList(Integer id, NewBidListDTO updatedBidList) throws NotFoundException;
	
	/**
	 * Delete a BidList.
	 * @param id
	 * @throws NotFoundException
	 */
	void deleteBidList(Integer id) throws NotFoundException;
}
