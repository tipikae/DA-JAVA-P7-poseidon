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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.NewRatingDTO;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.dto.UpdateRatingDTO;
import com.nnk.springboot.dtoconverters.IRatingDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;

/**
 * Rating service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
@Transactional
public class RatingServiceImpl implements IRatingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RatingServiceImpl.class);
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private IRatingDTOConverter ratingConverter;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RatingDTO addItem(NewRatingDTO newDTO) throws ItemAlreadyExistsException, ConverterException {
		LOGGER.debug("Service: addItem: fitch=" + newDTO.getFitchRating() + ", moodys=" + newDTO.getMoodysRating()
				+ ", sand=" + newDTO.getSandPRating() + ", orderNumber=" + newDTO.getOrderNumber());
		
		Rating rating = new Rating();
		rating.setFitchRating(newDTO.getFitchRating());
		rating.setMoodysRating(newDTO.getMoodysRating());
		rating.setOrderNumber(newDTO.getOrderNumber());
		rating.setSandPRating(newDTO.getSandPRating());
		
		return ratingConverter.convertEntityToDTO(ratingRepository.save(rating));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RatingDTO> getAllItems() throws ConverterException {
		LOGGER.debug("Service: getAllItems");
		return ratingConverter.convertListEntityToDTO(ratingRepository.findAll());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RatingDTO getItemById(Integer id) throws ItemNotFoundException, ConverterException {
		LOGGER.debug("Service: getItemById: id=" + id);
		Optional<Rating> optional = ratingRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("Rating with id=" + id + " not found.");
			throw new ItemNotFoundException("Rating not found.");
		}
		
		return ratingConverter.convertEntityToDTO(optional.get());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateItem(Integer id, UpdateRatingDTO updatedDTO) throws ItemNotFoundException {
		LOGGER.debug("Service: updateItem: id=" + id);
		Optional<Rating> optional = ratingRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("Rating with id=" + id + " not found.");
			throw new ItemNotFoundException("CurvePoint not found.");
		}

		Rating rating = optional.get();
		rating.setFitchRating(updatedDTO.getFitchRating());
		rating.setMoodysRating(updatedDTO.getMoodysRating());
		rating.setOrderNumber(updatedDTO.getOrderNumber());
		rating.setSandPRating(updatedDTO.getSandPRating());
		
		ratingRepository.save(rating);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteItem(Integer id) throws ItemNotFoundException {
		LOGGER.debug("Service: deleteItem: id=" + id);
		Optional<Rating> optional = ratingRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("Rating with id=" + id + " not found.");
			throw new ItemNotFoundException("Rating not found.");
		}

		ratingRepository.delete(optional.get());
	}

}
