package com.nnk.springboot.unit.dtoconverters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dtoconverters.ConverterBidListImpl;
import com.nnk.springboot.exceptions.ConverterException;

class ConverterBidListTest {
	
	private ConverterBidListImpl converterBidList;

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
		
		/*rightCurvePoint1.setCurveId(10);
		rightCurvePoint1.setId(1);
		rightCurvePoint1.setTerm(10d);
		rightCurvePoint1.setValue(10d);
		
		rightCurvePoint2.setCurveId(20);
		rightCurvePoint2.setId(2);
		rightCurvePoint2.setTerm(20d);
		rightCurvePoint2.setValue(20d);
		
		wrongCurvePoint.setCurveId(30);
		wrongCurvePoint.setId(3);
		wrongCurvePoint.setTerm(0d);
		wrongCurvePoint.setValue(20d);*/
		
		rightBidLists.add(rightBidList1);
		rightBidLists.add(rightBidList2);
		
		wrongBidLists.add(rightBidList1);
		wrongBidLists.add(wrongBidList);
		
		/*curvePointDTO1.setCurveId(10);
		curvePointDTO1.setId(1);
		curvePointDTO1.setTerm(10d);
		curvePointDTO1.setValue(10d);
		
		curvePointDTO2.setCurveId(20);
		curvePointDTO2.setId(2);
		curvePointDTO2.setTerm(20d);
		curvePointDTO2.setValue(20d);*/
		
		bidListDTOs.add(bidListDTO1);
		bidListDTOs.add(bidListDTO2);
	}

	/*@Test
	void convertEntityToDTOReturnsDTOWhenOk() throws ConverterException {
		assertEquals(curvePointDTO1.getCurveId(), 
				converterCurvePoint.convertEntityToDTO(rightCurvePoint1).getCurveId());
	}

	@Test
	void convertEntityToDTOThrowsConverterExceptionWhenZeroField() {
		assertThrows(ConverterException.class, () -> converterCurvePoint.convertEntityToDTO(wrongCurvePoint));
	}

	@Test
	void convertListEntityToDTOReturnsListDTOWhenOk() throws ConverterException {
		assertEquals(2, converterCurvePoint.convertListEntityToDTO(rightCurvePoints).size());
	}

	@Test
	void convertListEntityToDTOThrowsConverterExceptionWhenZeroField() {
		assertThrows(ConverterException.class, () -> converterCurvePoint.convertListEntityToDTO(wrongCurvePoints));
	}*/

}
