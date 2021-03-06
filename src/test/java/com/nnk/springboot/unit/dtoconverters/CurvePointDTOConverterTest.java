package com.nnk.springboot.unit.dtoconverters;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.dtoconverters.CurvePointDTOConverterImpl;
import com.nnk.springboot.exceptions.ConverterException;

class CurvePointDTOConverterTest {
	
	private CurvePointDTOConverterImpl converterCurvePoint = new CurvePointDTOConverterImpl();
	
	private static CurvePoint rightCurvePoint1;
	private static CurvePoint rightCurvePoint2;
	private static CurvePoint wrongCurvePoint;
	private static CurvePointDTO curvePointDTO1;
	private static CurvePointDTO curvePointDTO2;
	private static List<CurvePoint> rightCurvePoints;
	private static List<CurvePoint> wrongCurvePoints;
	private static List<CurvePointDTO> curvePointDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightCurvePoint1 = new CurvePoint();
		rightCurvePoint2 = new CurvePoint();
		wrongCurvePoint = new CurvePoint();
		curvePointDTO1 = new CurvePointDTO();
		curvePointDTO2 = new CurvePointDTO();
		rightCurvePoints = new ArrayList<>();
		wrongCurvePoints = new ArrayList<>();
		curvePointDTOs = new ArrayList<>();
		
		rightCurvePoint1.setCurveId(10);
		rightCurvePoint1.setId(1);
		rightCurvePoint1.setTerm(new BigDecimal(10));
		rightCurvePoint1.setValue(new BigDecimal(10));
		
		rightCurvePoint2.setCurveId(20);
		rightCurvePoint2.setId(2);
		rightCurvePoint2.setTerm(new BigDecimal(20));
		rightCurvePoint2.setValue(new BigDecimal(20));
		
		wrongCurvePoint.setCurveId(30);
		wrongCurvePoint.setId(3);
		wrongCurvePoint.setTerm(new BigDecimal(0));
		wrongCurvePoint.setValue(new BigDecimal(20));
		
		rightCurvePoints.add(rightCurvePoint1);
		rightCurvePoints.add(rightCurvePoint2);
		
		wrongCurvePoints.add(rightCurvePoint1);
		wrongCurvePoints.add(wrongCurvePoint);
		
		curvePointDTO1.setCurveId(10);
		curvePointDTO1.setId(1);
		curvePointDTO1.setTerm(new BigDecimal(10));
		curvePointDTO1.setValue(new BigDecimal(10));
		
		curvePointDTO2.setCurveId(20);
		curvePointDTO2.setId(2);
		curvePointDTO2.setTerm(new BigDecimal(20));
		curvePointDTO2.setValue(new BigDecimal(20));
		
		curvePointDTOs.add(curvePointDTO1);
		curvePointDTOs.add(curvePointDTO2);
	}

	@Test
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
	}

}
