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
import com.nnk.springboot.dto.UpdateBidListDTO;
import com.nnk.springboot.dtoconverters.IBidListDTOConverter;
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
	private IBidListDTOConverter converterBidList;

	@Override
	public BidListDTO addItem(NewBidListDTO newBidList) throws ServiceException, ConverterException {
		LOGGER.debug("Service: addItem: account=" + newBidList.getAccount() + ", type=" + newBidList.getType() 
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
	public List<BidListDTO> getAllItems() throws ConverterException {
		LOGGER.debug("Service: getAllItems");
		return converterBidList.convertListEntityToDTO(bidListRepository.findAll());
	}

	@Override
	public BidListDTO getItemById(Integer id) throws NotFoundException, ConverterException {
		LOGGER.debug("Service: getItemById: id=" + id);
		Optional<BidList> optional = bidListRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("BidList with id=" + id + " not found.");
			throw new NotFoundException("BidList not found.");
		}
		
		return converterBidList.convertEntityToDTO(optional.get());
	}

	@Override
	public void updateItem(Integer id, UpdateBidListDTO updatedBidList) throws NotFoundException {
		LOGGER.debug("Service: updateItem: id=" + id);
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
	public void deleteItem(Integer id) throws NotFoundException {
		LOGGER.debug("Service: deleteItem: id=" + id);
		Optional<BidList> optional = bidListRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("BidList with id=" + id + " not found.");
			throw new NotFoundException("BidList not found.");
		}

		bidListRepository.delete(optional.get());
	}

}
