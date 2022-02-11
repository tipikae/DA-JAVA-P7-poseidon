package com.nnk.springboot.unit.dtoconverters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dtoconverters.BidListDTOConverterImpl;
import com.nnk.springboot.exceptions.ConverterException;

class BidListDTOConverterTest {
	
	private BidListDTOConverterImpl converterBidList = new BidListDTOConverterImpl();

	private static BidList rightBidList1;
	private static BidList rightBidList2;
	private static BidList wrongBidList;
	private static BidListDTO bidListDTO1;
	private static BidListDTO bidListDTO2;
	private static List<BidList> rightBidLists;
	private static List<BidList> wrongBidLists;
	private static List<BidListDTO> bidListDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightBidList1 = new BidList();
		rightBidList2 = new BidList();
		wrongBidList = new BidList();
		bidListDTO1 = new BidListDTO();
		bidListDTO2 = new BidListDTO();
		rightBidLists = new ArrayList<>();
		wrongBidLists = new ArrayList<>();
		bidListDTOs = new ArrayList<>();
		
		rightBidList1.setAccount("account1");
		rightBidList1.setBidListId(1);
		rightBidList1.setType("type1");
		rightBidList1.setBidQuantity(100);
		
		rightBidList2.setAccount("account2");
		rightBidList2.setBidListId(2);
		rightBidList2.setType("type2");
		rightBidList2.setBidQuantity(200);
		
		wrongBidList.setAccount("");
		wrongBidList.setType("type");
		wrongBidList.setBidQuantity(100);
		
		rightBidLists.add(rightBidList1);
		rightBidLists.add(rightBidList2);
		
		wrongBidLists.add(rightBidList1);
		wrongBidLists.add(wrongBidList);
		
		bidListDTO1.setAccount("account1");
		bidListDTO1.setType("type1");
		bidListDTO1.setId(1);
		bidListDTO1.setBidQuantity(100);
		
		bidListDTO2.setAccount("account2");
		bidListDTO2.setType("type2");
		bidListDTO2.setId(2);
		bidListDTO2.setBidQuantity(200);
		
		bidListDTOs.add(bidListDTO1);
		bidListDTOs.add(bidListDTO2);
	}

	@Test
	void convertEntityToDTOReturnsDTOWhenOk() throws ConverterException {
		assertEquals(bidListDTO1.getId(), 
				converterBidList.convertEntityToDTO(rightBidList1).getId());
	}

	@Test
	void convertEntityToDTOThrowsConverterExceptionWhenEmptyField() {
		assertThrows(ConverterException.class, () -> converterBidList.convertEntityToDTO(wrongBidList));
	}

	@Test
	void convertListEntityToDTOReturnsListDTOWhenOk() throws ConverterException {
		assertEquals(2, converterBidList.convertListEntityToDTO(rightBidLists).size());
	}

	@Test
	void convertListEntityToDTOThrowsConverterExceptionWhenEmptyField() {
		assertThrows(ConverterException.class, () -> converterBidList.convertListEntityToDTO(wrongBidLists));
	}

}