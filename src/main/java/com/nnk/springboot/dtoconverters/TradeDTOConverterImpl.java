/**
 * 
 */
package com.nnk.springboot.dtoconverters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.exceptions.ConverterException;

/**
 * Trade DTO converter implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class TradeDTOConverterImpl implements ITradeDTOConverter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeDTOConverterImpl.class);

	@Override
	public TradeDTO convertEntityToDTO(Trade trade) throws ConverterException {
		if(trade.getAccount().equals("") || trade.getType().equals("")) {
			LOGGER.debug("convertEntityToDTO: ConverterException: account=" + trade.getAccount()
					+ ", type=" + trade.getType());
			throw new ConverterException("Empty field.");
		}
		TradeDTO tradeDTO = new TradeDTO();
		tradeDTO.setTradeId(trade.getTradeId());
		tradeDTO.setAccount(trade.getAccount());
		tradeDTO.setType(trade.getType());
		
		return tradeDTO;
	}

	@Override
	public List<TradeDTO> convertListEntityToDTO(List<Trade> trades) throws ConverterException {
		List<TradeDTO> tradeDTOs = new ArrayList<>();
		for(Trade trade: trades) {
			try {
				tradeDTOs.add(convertEntityToDTO(trade));
			} catch (ConverterException e) {
				LOGGER.debug("convertListEntityToDTO: ConverterException: " + e.getMessage());
				throw new ConverterException("Incorrect field");
			}
		}
		
		return tradeDTOs;
	}

}
