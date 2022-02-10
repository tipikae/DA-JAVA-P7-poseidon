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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dto.NewBidListDTO;
import com.nnk.springboot.dtoconverters.IConverterBidList;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListServiceImpl;

@ExtendWith(MockitoExtension.class)
class BidListServiceTest {
	
	@Mock
	private BidListRepository bidListRepository;
	
	@Mock
	private IConverterBidList converterBidList;
	
	@InjectMocks
	private BidListServiceImpl bidListService;
	
	private static NewBidListDTO rightNewBidListDTO;
	private static NewBidListDTO wrongNewBidListDTO;
	private static BidList bidList1;
	private static BidList bidList2;
	private static BidListDTO bidListDTO1;
	private static BidListDTO bidListDTO2;
	private static List<BidList> bidLists;
	private static List<BidListDTO> bidListDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightNewBidListDTO = new NewBidListDTO();
		wrongNewBidListDTO = new NewBidListDTO();
		bidList1 = new BidList();
		bidList2 = new BidList();
		bidListDTO1 = new BidListDTO();
		bidListDTO2 = new BidListDTO();
		bidLists = new ArrayList<>();
		bidListDTOs = new ArrayList<>();
		
		rightNewBidListDTO.setAccount("account1");
		rightNewBidListDTO.setBidQuantity(100);
		rightNewBidListDTO.setType("type1");
		
		wrongNewBidListDTO.setAccount("account1");
		wrongNewBidListDTO.setBidQuantity(100);
		wrongNewBidListDTO.setType("type1");
		
		bidList1.setAccount("account1");
		bidList1.setBidQuantity(100);
		bidList1.setBidListId(1);
		bidList1.setType("type1");
		
		bidList2.setAccount("account2");
		bidList2.setBidQuantity(200);
		bidList2.setBidListId(2);
		bidList2.setType("type2");
		
		bidListDTO1.setAccount("account1");
		bidListDTO1.setBidQuantity(100);
		bidListDTO1.setId(1);
		bidListDTO1.setType("type1");
		
		bidListDTO2.setAccount("account2");
		bidListDTO2.setBidQuantity(200);
		bidListDTO2.setId(2);
		bidListDTO2.setType("type2");
		
		bidLists.add(bidList1);
		bidLists.add(bidList2);
		
		bidListDTOs.add(bidListDTO1);
		bidListDTOs.add(bidListDTO2);
	}
	

	@Test
	void addBidListReturnsDTOWhenOk() throws ConverterException, ServiceException {
		when(bidListRepository.save(any(BidList.class))).thenReturn(bidList1);
		when(converterBidList.convertEntityToDTO(any(BidList.class))).thenReturn(bidListDTO1);
		assertEquals(rightNewBidListDTO.getAccount(), bidListService.addBidList(rightNewBidListDTO).getAccount());
	}

	@Test
	void addBidListThrowsServiceExceptionWhenError() {
		
	}

	@Test
	void getAllBidsReturnsListDTOWhenOk() throws ConverterException, ServiceException {
		when(bidListRepository.findAll()).thenReturn(bidLists);
		when(converterBidList.convertListEntityToDTO(bidLists)).thenReturn(bidListDTOs);
		assertEquals(bidLists.size(), bidListService.getAllBids().size());
	}

	@Test
	void getBidListReturnsDTOWhenOk() throws ConverterException, NotFoundException, ServiceException {
		when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidList1));
		when(converterBidList.convertEntityToDTO(bidList1)).thenReturn(bidListDTO1);
		assertEquals(bidList1.getBidListId(), bidListService.getBidList(1).getId());
	}

	@Test
	void getBidListThrowsNotFoundExceptionWhenNotFound() {
		when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> bidListService.getBidList(1));
	}

	@Test
	void updateBidListWhenOk() throws NotFoundException, ServiceException {
		when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(new BidList()));
		bidListService.updateBidList(1, rightNewBidListDTO);
		Mockito.verify(bidListRepository).save(any(BidList.class));
	}

	@Test
	void updateBidListThrowsNotFoundExceptionWhenNotFound() {
		when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> bidListService.updateBidList(10, new NewBidListDTO()));
	}

	@Test
	void deleteBidListWhenOk() throws NotFoundException, ServiceException {
		when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(new BidList()));
		bidListService.deleteBidList(1);
		Mockito.verify(bidListRepository).delete(any(BidList.class));
	}

	@Test
	void deleteBidListThrowsNotFoundExceptionWhenNotFound() {
		when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> bidListService.deleteBidList(10));
	}

}
