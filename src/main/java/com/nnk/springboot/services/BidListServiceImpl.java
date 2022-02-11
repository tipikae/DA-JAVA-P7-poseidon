package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dto.NewBidListDTO;
import com.nnk.springboot.dtoconverters.IConverterBidList;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.BidListRepository;

/**
 * BidList Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Transactional
@Service
public class BidListServiceImpl implements IBidListService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BidListServiceImpl.class);
	
	@Autowired
	private BidListRepository bidListRepository;
	
	@Autowired
	private IConverterBidList converterBidList;

	@Override
	public BidListDTO addBidList(NewBidListDTO newBidList) throws ServiceException, ConverterException {
		LOGGER.debug("Service: addBidList: account=" + newBidList.getAccount() + ", type=" + newBidList.getType() 
			+ ", qty=" + newBidList.getBidQuantity());
		/*if(false) {
			LOGGER.debug("");
			throw new ServiceException("");
		}*/
		
		BidList bidList = new BidList();
		bidList.setAccount(newBidList.getAccount());
		bidList.setType(newBidList.getType());
		bidList.setBidQuantity(newBidList.getBidQuantity());
		bidList.setCreationDate(Timestamp.from(Instant.now()));
		
		return converterBidList.convertEntityToDTO(bidListRepository.save(bidList));
	}

	@Override
	public List<BidListDTO> getAllBids() throws ConverterException {
		LOGGER.debug("Service: getAllBids");
		return converterBidList.convertListEntityToDTO(bidListRepository.findAll());
	}

	@Override
	public BidListDTO getBidList(Integer id) throws NotFoundException, ConverterException {
		LOGGER.debug("Service: getBidList: id=" + id);
		Optional<BidList> optional = bidListRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("BidList with id=" + id + " not found.");
			throw new NotFoundException("BidList not found.");
		}
		
		return converterBidList.convertEntityToDTO(optional.get());
	}

	@Override
	public void updateBidList(Integer id, NewBidListDTO updatedBidList) throws NotFoundException {
		LOGGER.debug("Service: updateBidList: id=" + id);
		Optional<BidList> optional = bidListRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("BidList with id=" + id + " not found.");
			throw new NotFoundException("BidList not found.");
		}

		BidList bidList = optional.get();
		bidList.setAccount(updatedBidList.getAccount());
		bidList.setType(updatedBidList.getType());
		bidList.setBidQuantity(updatedBidList.getBidQuantity());
		
		bidListRepository.save(bidList);
	}

	@Override
	public void deleteBidList(Integer id) throws NotFoundException {
		LOGGER.debug("Service: deleteBidList: id=" + id);
		Optional<BidList> optional = bidListRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("BidList with id=" + id + " not found.");
			throw new NotFoundException("BidList not found.");
		}

		bidListRepository.delete(optional.get());
	}

}
