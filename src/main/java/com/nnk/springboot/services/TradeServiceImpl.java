/**
 * 
 */
package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.NewTradeDTO;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.dto.UpdateTradeDTO;
import com.nnk.springboot.dtoconverters.ITradeDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;

/**
 * Trade service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
@Transactional
public class TradeServiceImpl implements ITradeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeServiceImpl.class);
	
	@Autowired
	private TradeRepository tradeRepository;
	
	@Autowired
	private ITradeDTOConverter tradeConverter;

	@Override
	public TradeDTO addItem(NewTradeDTO newDTO) throws ItemAlreadyExistsException, ConverterException {
		LOGGER.debug("Service: addItem: account=" + newDTO.getAccount() + ", type=" + newDTO.getType());
		
		Trade trade = new Trade();
		trade.setAccount(newDTO.getAccount());
		trade.setType(newDTO.getType());
		trade.setBuyQuantity(newDTO.getBuyQuantity());
		
		return tradeConverter.convertEntityToDTO(tradeRepository.save(trade));
	}

	@Override
	public List<TradeDTO> getAllItems() throws ConverterException {
		LOGGER.debug("Service: getAllItems");
		return tradeConverter.convertListEntityToDTO(tradeRepository.findAll());
	}

	@Override
	public TradeDTO getItemById(Integer id) throws ItemNotFoundException, ConverterException {
		LOGGER.debug("Service: getItemById: id=" + id);
		Optional<Trade> optional = tradeRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("Trade with id=" + id + " not found.");
			throw new ItemNotFoundException("Trade not found.");
		}
		
		return tradeConverter.convertEntityToDTO(optional.get());
	}

	@Override
	public void updateItem(Integer id, UpdateTradeDTO updatedDTO) throws ItemNotFoundException {
		LOGGER.debug("Service: updateItem: id=" + id);
		Optional<Trade> optional = tradeRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("Trade with id=" + id + " not found.");
			throw new ItemNotFoundException("Trade not found.");
		}

		Trade trade = optional.get();
		trade.setAccount(updatedDTO.getAccount());
		trade.setType(updatedDTO.getType());
		trade.setBuyQuantity(updatedDTO.getBuyQuantity());
		
		tradeRepository.save(trade);
	}

	@Override
	public void deleteItem(Integer id) throws ItemNotFoundException {
		LOGGER.debug("Service: deleteItem: id=" + id);
		Optional<Trade> optional = tradeRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("Trade with id=" + id + " not found.");
			throw new ItemNotFoundException("Trade not found.");
		}

		tradeRepository.delete(optional.get());
	}

}
