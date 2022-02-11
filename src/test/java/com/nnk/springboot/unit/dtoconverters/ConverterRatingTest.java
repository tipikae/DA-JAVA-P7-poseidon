package com.nnk.springboot.unit.dtoconverters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.dtoconverters.ConverterRatingImpl;
import com.nnk.springboot.exceptions.ConverterException;

class ConverterRatingTest {
	
	private ConverterRatingImpl converterRating = new ConverterRatingImpl();

	private static Rating rightRating1;
	private static Rating rightRating2;
	private static Rating wrongRating;
	private static RatingDTO ratingDTO1;
	private static RatingDTO ratingDTO2;
	private static List<Rating> rightRatings;
	private static List<Rating> wrongRatings;
	private static List<RatingDTO> ratingDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightRating1 = new Rating();
		rightRating2 = new Rating();
		wrongRating = new Rating();
		ratingDTO1 = new RatingDTO();
		ratingDTO2 = new RatingDTO();
		rightRatings = new ArrayList<>();
		wrongRatings = new ArrayList<>();
		ratingDTOs = new ArrayList<>();
		
		rightRating1.setId(1);
		rightRating1.setFitchRating("fitch1");
		rightRating1.setMoodysRating("moodys1");
		rightRating1.setOrderNumber(1);
		rightRating1.setSandPRating("sand1");
		
		rightRating2.setId(2);
		rightRating2.setFitchRating("fitch2");
		rightRating2.setMoodysRating("moodys2");
		rightRating2.setOrderNumber(2);
		rightRating2.setSandPRating("sand2");
		
		wrongRating.setId(3);
		wrongRating.setFitchRating("fitch3");
		wrongRating.setMoodysRating("moodys3");
		wrongRating.setOrderNumber(0);
		wrongRating.setSandPRating("sand3");
		
		rightRatings.add(rightRating1);
		rightRatings.add(rightRating2);
		
		wrongRatings.add(rightRating1);
		wrongRatings.add(wrongRating);
		
		ratingDTO1.setId(1);
		ratingDTO1.setFitchRating("fitch1");
		ratingDTO1.setMoodysRating("moodys1");
		ratingDTO1.setOrderNumber(1);
		ratingDTO1.setSandPRating("sand1");
		
		ratingDTO2.setId(2);
		ratingDTO2.setFitchRating("fitch2");
		ratingDTO2.setMoodysRating("moodys2");
		ratingDTO2.setOrderNumber(2);
		ratingDTO2.setSandPRating("sand2");
		
		ratingDTOs.add(ratingDTO1);
		ratingDTOs.add(ratingDTO2);
	}

	@Test
	void convertEntityToDTOReturnsDTOWhenOk() throws ConverterException {
		assertEquals(ratingDTO1.getId(), 
				converterRating.convertEntityToDTO(rightRating1).getId());
	}

	@Test
	void convertEntityToDTOThrowsConverterExceptionWhenEmptyField() {
		assertThrows(ConverterException.class, () -> converterRating.convertEntityToDTO(wrongRating));
	}

	@Test
	void convertListEntityToDTOReturnsListDTOWhenOk() throws ConverterException {
		assertEquals(2, converterRating.convertListEntityToDTO(rightRatings).size());
	}

	@Test
	void convertListEntityToDTOThrowsConverterExceptionWhenEmptyField() {
		assertThrows(ConverterException.class, () -> converterRating.convertListEntityToDTO(wrongRatings));
	}

}
