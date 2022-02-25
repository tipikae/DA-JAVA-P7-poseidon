package com.nnk.springboot.dtoconverters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.exceptions.ConverterException;

/**
 * Converter BidList to BidListDTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class BidListDTOConverterImpl implements IBidListDTOConverter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BidListDTOConverterImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BidListDTO convertEntityToDTO(BidList bidList) throws ConverterException {
		if(bidList.getAccount().equals("") || bidList.getType().equals("")) {
			LOGGER.debug("convertEntityToDTO: ConverterException: account=" 
					+ bidList.getAccount() + ", type=" + bidList.getType());
			throw new ConverterException("Empty field");
		}
		BidListDTO bidListDTO = new BidListDTO();
		bidListDTO.setAccount(bidList.getAccount());
		bidListDTO.setBidQuantity(bidList.getBidQuantity());
		bidListDTO.setBidListId(bidList.getBidListId());
		bidListDTO.setType(bidList.getType());
		
		return bidListDTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BidListDTO> convertListEntityToDTO(List<BidList> bidLists) throws ConverterException {
		List<BidListDTO> bidListDTOs = new ArrayList<>();
		for(BidList bidList: bidLists) {
			try {
				bidListDTOs.add(convertEntityToDTO(bidList));
			} catch (ConverterException e) {
				LOGGER.debug("convertListEntityToDTO: ConverterException: " + e.getMessage());
				throw new ConverterException("Empty field");
			}
		}
		
		return bidListDTOs;
	}

}
