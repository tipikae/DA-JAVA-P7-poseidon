/**
 * 
 */
package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.dto.NewRatingDTO;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;

/**
 * Rating service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class RatingServiceImpl implements IRatingService {

	@Override
	public RatingDTO addItem(NewRatingDTO newDTO) throws ServiceException, ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RatingDTO> getAllItems() throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RatingDTO getItemById(Integer id) throws NotFoundException, ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateItem(Integer id, NewRatingDTO updatedDTO) throws NotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteItem(Integer id) throws NotFoundException {
		// TODO Auto-generated method stub

	}

}
