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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.NewTradeDTO;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.dto.UpdateTradeDTO;
import com.nnk.springboot.dtoconverters.ITradeDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.TradeRepository;

/**
 * Trade service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class TradeServiceImpl implements ITradeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeServiceImpl.class);
	
	@Autowired
	private TradeRepository tradeRepository;
	
	@Autowired
	private ITradeDTOConverter tradeConverter;

	@Override
	public TradeDTO addItem(NewTradeDTO newDTO) throws ServiceException, ConverterException {
		LOGGER.debug("Service: addItem: account=" + newDTO.getAccount() + ", type=" + newDTO.getType());
		/*if(false) {
			LOGGER.debug("");
			throw new ServiceException("");
		}*/
		
		Trade trade = new Trade();
		trade.setAccount(newDTO.getAccount());
		trade.setType(newDTO.getType());
		
		return tradeConverter.convertEntityToDTO(tradeRepository.save(trade));
	}

	@Override
	public List<TradeDTO> getAllItems() throws ConverterException {
		LOGGER.debug("Service: getAllItems");
		return tradeConverter.convertListEntityToDTO(tradeRepository.findAll());
	}

	@Override
	public TradeDTO getItemById(Integer id) throws NotFoundException, ConverterException {
		LOGGER.debug("Service: getItemById: id=" + id);
		Optional<Trade> optional = tradeRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("Trade with id=" + id + " not found.");
			throw new NotFoundException("Trade not found.");
		}
		
		return tradeConverter.convertEntityToDTO(optional.get());
	}

	@Override
	public void updateItem(Integer id, UpdateTradeDTO updatedDTO) throws NotFoundException {
		LOGGER.debug("Service: updateItem: id=" + id);
		Optional<Trade> optional = tradeRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("Trade with id=" + id + " not found.");
			throw new NotFoundException("Trade not found.");
		}

		Trade trade = optional.get();
		trade.setAccount(updatedDTO.getAccount());
		trade.setType(updatedDTO.getType());
		
		tradeRepository.save(trade);
	}

	@Override
	public void deleteItem(Integer id) throws NotFoundException {
		LOGGER.debug("Service: deleteItem: id=" + id);
		Optional<Trade> optional = tradeRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("Trade with id=" + id + " not found.");
			throw new NotFoundException("Trade not found.");
		}

		tradeRepository.delete(optional.get());
	}

}
