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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.dto.NewCurvePointDTO;
import com.nnk.springboot.dtoconverters.IConverterCurvePoint;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointServiceImpl;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {
	
	@Mock
	private CurvePointRepository curvePointRepository;
	
	@Mock
	private IConverterCurvePoint converterCurvePoint;
	
	@InjectMocks
	private CurvePointServiceImpl curvePointService;
	
	private static NewCurvePointDTO rightNewCurvePointDTODTO;
	private static NewCurvePointDTO wrongNewCurvePointDTODTO;
	private static CurvePoint curvePoint1;
	private static CurvePoint curvePoint2;
	private static CurvePointDTO curvePointDTO1;
	private static CurvePointDTO curvePointDTO2;
	private static List<CurvePoint> curvePoints;
	private static List<CurvePointDTO> curvePointDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightNewCurvePointDTODTO = new NewCurvePointDTO();
		wrongNewCurvePointDTODTO = new NewCurvePointDTO();
		curvePoint1 = new CurvePoint();
		curvePoint2 = new CurvePoint();
		curvePointDTO1 = new CurvePointDTO();
		curvePointDTO2 = new CurvePointDTO();
		curvePoints = new ArrayList<>();
		curvePointDTOs = new ArrayList<>();
		
		rightNewCurvePointDTODTO.setCurveId(10);
		rightNewCurvePointDTODTO.setTerm(10d);
		rightNewCurvePointDTODTO.setValue(10d);
		
		wrongNewCurvePointDTODTO.setCurveId(20);
		wrongNewCurvePointDTODTO.setTerm(0d);
		wrongNewCurvePointDTODTO.setValue(20d);
		
		curvePoint1.setCurveId(10);
		curvePoint1.setId(1);
		curvePoint1.setTerm(10d);
		curvePoint1.setValue(10d);
		
		curvePoint2.setCurveId(20);
		curvePoint2.setId(2);
		curvePoint2.setTerm(20d);
		curvePoint2.setValue(20d);
		
		curvePointDTO1.setCurveId(10);
		curvePointDTO1.setId(1);
		curvePointDTO1.setTerm(10d);
		curvePointDTO1.setValue(10d);
		
		curvePointDTO2.setCurveId(20);
		curvePointDTO2.setId(2);
		curvePointDTO2.setTerm(20d);
		curvePointDTO2.setValue(20d);
		
		curvePoints.add(curvePoint1);
		curvePoints.add(curvePoint2);
		
		curvePointDTOs.add(curvePointDTO1);
		curvePointDTOs.add(curvePointDTO2);
	}
	

	@Test
	void addBidListReturnsDTOWhenOk() throws ConverterException, ServiceException {
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint1);
		when(converterCurvePoint.convertEntityToDTO(any(CurvePoint.class))).thenReturn(curvePointDTO1);
		assertEquals(rightNewCurvePointDTODTO.getCurveId(), 
				curvePointService.addItem(rightNewCurvePointDTODTO).getCurveId());
	}

	@Test
	void addBidListThrowsServiceExceptionWhenError() {
		
	}

	@Test
	void getAllBidsReturnsListDTOWhenOk() throws ConverterException, ServiceException {
		when(curvePointRepository.findAll()).thenReturn(curvePoints);
		when(converterCurvePoint.convertListEntityToDTO(curvePoints)).thenReturn(curvePointDTOs);
		assertEquals(curvePoints.size(), curvePointService.getAllItems().size());
	}

	@Test
	void getBidListReturnsDTOWhenOk() throws ConverterException, NotFoundException, ServiceException {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint1));
		when(converterCurvePoint.convertEntityToDTO(curvePoint1)).thenReturn(curvePointDTO1);
		assertEquals(curvePoint1.getId(), curvePointService.getItemById(1).getId());
	}

	@Test
	void getBidListThrowsNotFoundExceptionWhenNotFound() {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> curvePointService.getItemById(1));
	}

	@Test
	void updateBidListWhenOk() throws NotFoundException, ServiceException {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(new CurvePoint()));
		curvePointService.updateItem(1, rightNewCurvePointDTODTO);
		Mockito.verify(curvePointRepository).save(any(CurvePoint.class));
	}

	@Test
	void updateBidListThrowsNotFoundExceptionWhenNotFound() {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> curvePointService.updateItem(10, new NewCurvePointDTO()));
	}

	@Test
	void deleteBidListWhenOk() throws NotFoundException, ServiceException {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(new CurvePoint()));
		curvePointService.deleteItem(1);
		Mockito.verify(curvePointRepository).delete(any(CurvePoint.class));
	}

	@Test
	void deleteBidListThrowsNotFoundExceptionWhenNotFound() {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> curvePointService.deleteItem(10));
	}

}
