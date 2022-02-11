/**
 * 
 */
package com.nnk.springboot.dtoconverters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.exceptions.ConverterException;

/**
 * Rating converter implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class ConverterRatingImpl implements IConverterRating {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterRatingImpl.class);

	@Override
	public RatingDTO convertEntityToDTO(Rating rating) throws ConverterException {
		if(rating.getOrderNumber() == 0) {
			LOGGER.debug("convertEntityToDTO: ConverterException: orderNumber=" + rating.getOrderNumber());
			throw new ConverterException("Order number is incorrect.");
		}
		RatingDTO ratingDTO = new RatingDTO();
		ratingDTO.setId(rating.getId());
		ratingDTO.setFitchRating(rating.getFitchRating());
		ratingDTO.setMoodysRating(rating.getMoodysRating());
		ratingDTO.setOrderNumber(rating.getOrderNumber());
		ratingDTO.setSandPRating(rating.getSandPRating());
		
		return ratingDTO;
	}

	@Override
	public List<RatingDTO> convertListEntityToDTO(List<Rating> ratings) throws ConverterException {
		List<RatingDTO> ratingDTOs = new ArrayList<>();
		for(Rating rating: ratings) {
			try {
				ratingDTOs.add(convertEntityToDTO(rating));
			} catch (ConverterException e) {
				LOGGER.debug("convertListEntityToDTO: ConverterException: " + e.getMessage());
				throw new ConverterException("Incorrect field");
			}
		}
		
		return ratingDTOs;
	}

}
