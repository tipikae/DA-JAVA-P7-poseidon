package com.nnk.springboot.unit.dtoconverters;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.dtoconverters.TradeDTOConverterImpl;
import com.nnk.springboot.exceptions.ConverterException;

class TradeDTOConverterTest {

	private TradeDTOConverterImpl tradeConverter = new TradeDTOConverterImpl();

	private static Trade rightTrade1;
	private static Trade rightTrade2;
	private static Trade wrongTrade;
	private static TradeDTO tradeDTO1;
	private static TradeDTO tradeDTO2;
	private static List<Trade> rightTrades;
	private static List<Trade> wrongTrades;
	private static List<TradeDTO> tradeDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightTrade1 = new Trade();
		rightTrade2 = new Trade();
		wrongTrade = new Trade();
		tradeDTO1 = new TradeDTO();
		tradeDTO2 = new TradeDTO();
		rightTrades = new ArrayList<>();
		wrongTrades = new ArrayList<>();
		tradeDTOs = new ArrayList<>();
		
		rightTrade1.setTradeId(1);
		rightTrade1.setAccount("account1");
		rightTrade1.setType("type1");
		rightTrade1.setBuyQuantity(new BigDecimal(10));
		
		rightTrade2.setTradeId(2);
		rightTrade2.setAccount("account2");
		rightTrade2.setType("type2");
		rightTrade2.setBuyQuantity(new BigDecimal(20));
		
		wrongTrade.setTradeId(3);
		wrongTrade.setAccount("");
		wrongTrade.setType("type3");
		wrongTrade.setBuyQuantity(new BigDecimal(30));
		
		rightTrades.add(rightTrade1);
		rightTrades.add(rightTrade2);
		
		wrongTrades.add(rightTrade1);
		wrongTrades.add(wrongTrade);
		
		tradeDTO1.setTradeId(1);
		tradeDTO1.setAccount("account1");
		tradeDTO1.setType("type1");
		tradeDTO1.setBuyQuantity(new BigDecimal(10));
		
		tradeDTO2.setTradeId(2);
		tradeDTO2.setAccount("account2");
		tradeDTO2.setType("type2");
		tradeDTO2.setBuyQuantity(new BigDecimal(20));
		
		tradeDTOs.add(tradeDTO1);
		tradeDTOs.add(tradeDTO2);
	}

	@Test
	void convertEntityToDTOReturnsDTOWhenOk() throws ConverterException {
		assertEquals(tradeDTO1.getTradeId(), 
				tradeConverter.convertEntityToDTO(rightTrade1).getTradeId());
	}

	@Test
	void convertEntityToDTOThrowsConverterExceptionWhenEmptyField() {
		assertThrows(ConverterException.class, () -> tradeConverter.convertEntityToDTO(wrongTrade));
	}

	@Test
	void convertListEntityToDTOReturnsListDTOWhenOk() throws ConverterException {
		assertEquals(2, tradeConverter.convertListEntityToDTO(rightTrades).size());
	}

	@Test
	void convertListEntityToDTOThrowsConverterExceptionWhenEmptyField() {
		assertThrows(ConverterException.class, () -> tradeConverter.convertListEntityToDTO(wrongTrades));
	}
}
