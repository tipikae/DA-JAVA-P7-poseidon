/**
 * 
 */
package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.dto.NewTradeDTO;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;

/**
 * Trade service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class TradeServiceImpl implements ITradeService {

	@Override
	public TradeDTO addItem(NewTradeDTO newDTO) throws ServiceException, ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TradeDTO> getAllItems() throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TradeDTO getItemById(Integer id) throws NotFoundException, ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateItem(Integer id, NewTradeDTO updatedDTO) throws NotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteItem(Integer id) throws NotFoundException {
		// TODO Auto-generated method stub

	}

}
