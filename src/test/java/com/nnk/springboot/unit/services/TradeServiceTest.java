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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.NewTradeDTO;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.dtoconverters.ITradeDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeServiceImpl;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

	@Mock
	private TradeRepository tradeRepository;
	
	@Mock
	private ITradeDTOConverter tradeConverter;
	
	@InjectMocks
	private TradeServiceImpl tradeService;
	
	private static NewTradeDTO rightNewTradeDTO;
	private static NewTradeDTO wrongNewTradeDTO;
	private static Trade trade1;
	private static Trade trade2;
	private static TradeDTO tradeDTO1;
	private static TradeDTO tradeDTO2;
	private static List<Trade> trades;
	private static List<TradeDTO> tradeDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightNewTradeDTO = new NewTradeDTO();
		wrongNewTradeDTO = new NewTradeDTO();
		trade1 = new Trade();
		trade2 = new Trade();
		tradeDTO1 = new TradeDTO();
		tradeDTO2 = new TradeDTO();
		trades = new ArrayList<>();
		tradeDTOs = new ArrayList<>();
		
		rightNewTradeDTO.setAccount("account1");
		rightNewTradeDTO.setType("type1");
		
		wrongNewTradeDTO.setAccount("");
		wrongNewTradeDTO.setType("type0");
		
		trade1.setTradeId(1);
		trade1.setAccount("account1");
		trade1.setType("type1");
		
		trade2.setTradeId(2);
		trade2.setAccount("account2");
		trade2.setType("type2");
		
		tradeDTO1.setTradeId(1);
		tradeDTO1.setAccount("account1");
		tradeDTO1.setType("type1");
		
		tradeDTO2.setTradeId(2);
		tradeDTO2.setAccount("account2");
		tradeDTO2.setType("type2");
		
		trades.add(trade1);
		trades.add(trade2);
		
		tradeDTOs.add(tradeDTO1);
		tradeDTOs.add(tradeDTO2);
	}
	

	@Test
	void addBidListReturnsDTOWhenOk() throws ConverterException, ServiceException {
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade1);
		when(tradeConverter.convertEntityToDTO(any(Trade.class))).thenReturn(tradeDTO1);
		assertEquals(rightNewTradeDTO.getAccount(), 
				tradeService.addItem(rightNewTradeDTO).getAccount());
	}

	@Test
	void addBidListThrowsServiceExceptionWhenError() {
		
	}

	@Test
	void getAllBidsReturnsListDTOWhenOk() throws ConverterException, ServiceException {
		when(tradeRepository.findAll()).thenReturn(trades);
		when(tradeConverter.convertListEntityToDTO(trades)).thenReturn(tradeDTOs);
		assertEquals(trades.size(), tradeService.getAllItems().size());
	}

	@Test
	void getBidListReturnsDTOWhenOk() throws ConverterException, NotFoundException, ServiceException {
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade1));
		when(tradeConverter.convertEntityToDTO(trade1)).thenReturn(tradeDTO1);
		assertEquals(trade1.getTradeId(), tradeService.getItemById(1).getTradeId());
	}

	@Test
	void getBidListThrowsNotFoundExceptionWhenNotFound() {
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> tradeService.getItemById(1));
	}

	@Test
	void updateBidListWhenOk() throws NotFoundException, ServiceException {
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(new Trade()));
		tradeService.updateItem(1, rightNewTradeDTO);
		Mockito.verify(tradeRepository).save(any(Trade.class));
	}

	@Test
	void updateBidListThrowsNotFoundExceptionWhenNotFound() {
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> tradeService.updateItem(10, new NewTradeDTO()));
	}

	@Test
	void deleteBidListWhenOk() throws NotFoundException, ServiceException {
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(new Trade()));
		tradeService.deleteItem(1);
		Mockito.verify(tradeRepository).delete(any(Trade.class));
	}

	@Test
	void deleteBidListThrowsNotFoundExceptionWhenNotFound() {
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> tradeService.deleteItem(10));
	}
}
