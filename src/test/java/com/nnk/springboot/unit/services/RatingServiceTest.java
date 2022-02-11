package com.nnk.springboot.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.NewRatingDTO;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.dtoconverters.IRatingDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingServiceImpl;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

	@Mock
	private RatingRepository ratingRepository;
	
	@Mock
	private IRatingDTOConverter ratingConverter;
	
	@InjectMocks
	private RatingServiceImpl ratingService;
	
	private static NewRatingDTO rightNewRatingDTO;
	private static NewRatingDTO wrongNewRatingDTO;
	private static Rating rating1;
	private static Rating rating2;
	private static RatingDTO ratingDTO1;
	private static RatingDTO ratingDTO2;
	private static List<Rating> ratings;
	private static List<RatingDTO> ratingDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightNewRatingDTO = new NewRatingDTO();
		wrongNewRatingDTO = new NewRatingDTO();
		rating1 = new Rating();
		rating2 = new Rating();
		ratingDTO1 = new RatingDTO();
		ratingDTO2 = new RatingDTO();
		ratings = new ArrayList<>();
		ratingDTOs = new ArrayList<>();
		
		rightNewRatingDTO.setFitchRating("fitch1");
		rightNewRatingDTO.setMoodysRating("moodys1");
		rightNewRatingDTO.setOrderNumber(1);
		rightNewRatingDTO.setSandPRating("sand1");
		
		wrongNewRatingDTO.setFitchRating("fitch1");
		wrongNewRatingDTO.setMoodysRating("moodys1");
		wrongNewRatingDTO.setOrderNumber(0);
		wrongNewRatingDTO.setSandPRating("sand1");
		
		rating1.setId(1);
		rating1.setFitchRating("fitch1");
		rating1.setMoodysRating("moodys1");
		rating1.setOrderNumber(1);
		rating1.setSandPRating("sand1");
		
		rating2.setId(2);
		rating2.setFitchRating("fitch2");
		rating2.setMoodysRating("moodys2");
		rating2.setOrderNumber(2);
		rating2.setSandPRating("sand2");
		
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
		
		ratings.add(rating1);
		ratings.add(rating2);
		
		ratingDTOs.add(ratingDTO1);
		ratingDTOs.add(ratingDTO2);
	}
	

	@Test
	void addBidListReturnsDTOWhenOk() throws ConverterException, ServiceException {
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating1);
		when(ratingConverter.convertEntityToDTO(any(Rating.class))).thenReturn(ratingDTO1);
		assertEquals(rightNewRatingDTO.getOrderNumber(), 
				ratingService.addItem(rightNewRatingDTO).getOrderNumber());
	}

	@Test
	void addBidListThrowsServiceExceptionWhenError() {
		
	}

	@Test
	void getAllBidsReturnsListDTOWhenOk() throws ConverterException, ServiceException {
		when(ratingRepository.findAll()).thenReturn(ratings);
		when(ratingConverter.convertListEntityToDTO(ratings)).thenReturn(ratingDTOs);
		assertEquals(ratings.size(), ratingService.getAllItems().size());
	}

	@Test
	void getBidListReturnsDTOWhenOk() throws ConverterException, NotFoundException, ServiceException {
		when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating1));
		when(ratingConverter.convertEntityToDTO(rating1)).thenReturn(ratingDTO1);
		assertEquals(rating1.getId(), ratingService.getItemById(1).getId());
	}

	@Test
	void getBidListThrowsNotFoundExceptionWhenNotFound() {
		when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> ratingService.getItemById(1));
	}

	@Test
	void updateBidListWhenOk() throws NotFoundException, ServiceException {
		when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(new Rating()));
		ratingService.updateItem(1, rightNewRatingDTO);
		Mockito.verify(ratingRepository).save(any(Rating.class));
	}

	@Test
	void updateBidListThrowsNotFoundExceptionWhenNotFound() {
		when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> ratingService.updateItem(10, new NewRatingDTO()));
	}

	@Test
	void deleteBidListWhenOk() throws NotFoundException, ServiceException {
		when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(new Rating()));
		ratingService.deleteItem(1);
		Mockito.verify(ratingRepository).delete(any(Rating.class));
	}

	@Test
	void deleteBidListThrowsNotFoundExceptionWhenNotFound() {
		when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> ratingService.deleteItem(10));
	}

}
