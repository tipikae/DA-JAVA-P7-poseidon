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
	BidListDTO addBidList(NewBidListDTO bid) throws ServiceException, ConverterException;
	
	/**
	 * Get all BidLists.
	 * @return List<BidListDTO>
	 * @throws ServiceException
	 * @throws ConverterException 
	 */
	List<BidListDTO> getAllBids() throws ServiceException, ConverterException;
	
	/**
	 * Get a BidList.
	 * @param id
	 * @return BidListDTO
	 * @throws NotFoundException
	 * @throws ServiceException
	 * @throws ConverterException 
	 */
	BidListDTO getBidList(Integer id) throws NotFoundException, ServiceException, ConverterException;
	
	/**
	 * Update a BidList.
	 * @param id
	 * @param bid
	 * @throws NotFoundException
	 * @throws ServiceException
	 */
	void updateBidList(Integer id, NewBidListDTO bid) throws NotFoundException, ServiceException;
	
	/**
	 * Delete a BidList.
	 * @param id
	 * @throws NotFoundException
	 * @throws ServiceException
	 */
	void deleteBidList(Integer id) throws NotFoundException, ServiceException;
}
