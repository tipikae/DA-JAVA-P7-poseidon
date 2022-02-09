package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dto.NewBidListDTO;

/**
 * BidList Service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IBidListService {

	BidListDTO addBidList(NewBidListDTO bid);
	List<BidListDTO> getAllBids();
	BidListDTO getBidList(Integer id);
	void updateBidList(Integer id, NewBidListDTO bid);
	void deleteBidList(Integer id);
}
