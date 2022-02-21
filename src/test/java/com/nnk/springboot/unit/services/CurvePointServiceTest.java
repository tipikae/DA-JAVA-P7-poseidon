package com.nnk.springboot.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import com.nnk.springboot.dto.UpdateCurvePointDTO;
import com.nnk.springboot.dtoconverters.ICurvePointDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointServiceImpl;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {
	
	@Mock
	private CurvePointRepository curvePointRepository;
	
	@Mock
	private ICurvePointDTOConverter converterCurvePoint;
	
	@InjectMocks
	private CurvePointServiceImpl curvePointService;
	
	private static NewCurvePointDTO rightNewCurvePointDTO;
	private static NewCurvePointDTO wrongNewCurvePointDTO;
	private static UpdateCurvePointDTO updatedCurvePointDTO;
	private static CurvePoint curvePoint1;
	private static CurvePoint curvePoint2;
	private static CurvePointDTO curvePointDTO1;
	private static CurvePointDTO curvePointDTO2;
	private static List<CurvePoint> curvePoints;
	private static List<CurvePointDTO> curvePointDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightNewCurvePointDTO = new NewCurvePointDTO();
		wrongNewCurvePointDTO = new NewCurvePointDTO();
		updatedCurvePointDTO = new UpdateCurvePointDTO();
		curvePoint1 = new CurvePoint();
		curvePoint2 = new CurvePoint();
		curvePointDTO1 = new CurvePointDTO();
		curvePointDTO2 = new CurvePointDTO();
		curvePoints = new ArrayList<>();
		curvePointDTOs = new ArrayList<>();
		
		rightNewCurvePointDTO.setCurveId(10);
		rightNewCurvePointDTO.setTerm(new BigDecimal(10));
		rightNewCurvePointDTO.setValue(new BigDecimal(10));
		
		wrongNewCurvePointDTO.setCurveId(20);
		wrongNewCurvePointDTO.setTerm(new BigDecimal(0));
		wrongNewCurvePointDTO.setValue(new BigDecimal(20));
		
		updatedCurvePointDTO.setCurveId(20);
		updatedCurvePointDTO.setTerm(new BigDecimal(50));
		updatedCurvePointDTO.setValue(new BigDecimal(20));
		
		curvePoint1.setCurveId(10);
		curvePoint1.setId(1);
		curvePoint1.setTerm(new BigDecimal(10));
		curvePoint1.setValue(new BigDecimal(20));
		
		curvePoint2.setCurveId(20);
		curvePoint2.setId(2);
		curvePoint2.setTerm(new BigDecimal(20));
		curvePoint2.setValue(new BigDecimal(20));
		
		curvePointDTO1.setCurveId(10);
		curvePointDTO1.setId(1);
		curvePointDTO1.setTerm(new BigDecimal(10));
		curvePointDTO1.setValue(new BigDecimal(10));
		
		curvePointDTO2.setCurveId(20);
		curvePointDTO2.setId(2);
		curvePointDTO2.setTerm(new BigDecimal(20));
		curvePointDTO2.setValue(new BigDecimal(20));
		
		curvePoints.add(curvePoint1);
		curvePoints.add(curvePoint2);
		
		curvePointDTOs.add(curvePointDTO1);
		curvePointDTOs.add(curvePointDTO2);
	}
	

	@Test
	void addItemReturnsDTOWhenOk() throws ConverterException, ItemAlreadyExistsException  {
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint1);
		when(converterCurvePoint.convertEntityToDTO(any(CurvePoint.class))).thenReturn(curvePointDTO1);
		assertEquals(rightNewCurvePointDTO.getCurveId(), 
				curvePointService.addItem(rightNewCurvePointDTO).getCurveId());
	}

	@Test
	void addItemThrowsServiceExceptionWhenError() {
		
	}

	@Test
	void getAllItemsReturnsListDTOWhenOk() throws ConverterException {
		when(curvePointRepository.findAll()).thenReturn(curvePoints);
		when(converterCurvePoint.convertListEntityToDTO(curvePoints)).thenReturn(curvePointDTOs);
		assertEquals(curvePoints.size(), curvePointService.getAllItems().size());
	}

	@Test
	void getItemByIdReturnsDTOWhenOk() throws ConverterException, ItemNotFoundException {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint1));
		when(converterCurvePoint.convertEntityToDTO(curvePoint1)).thenReturn(curvePointDTO1);
		assertEquals(curvePoint1.getId(), curvePointService.getItemById(1).getId());
	}

	@Test
	void getItemByIdThrowsNotFoundExceptionWhenNotFound() {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(ItemNotFoundException.class, () -> curvePointService.getItemById(1));
	}

	@Test
	void updateItemWhenOk() throws ItemNotFoundException {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(new CurvePoint()));
		curvePointService.updateItem(1, updatedCurvePointDTO);
		Mockito.verify(curvePointRepository).save(any(CurvePoint.class));
	}

	@Test
	void updateItemThrowsNotFoundExceptionWhenNotFound() {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(ItemNotFoundException.class, () -> curvePointService.updateItem(10, new UpdateCurvePointDTO()));
	}

	@Test
	void deleteItemWhenOk() throws ItemNotFoundException {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(new CurvePoint()));
		curvePointService.deleteItem(1);
		Mockito.verify(curvePointRepository).delete(any(CurvePoint.class));
	}

	@Test
	void deleteItemThrowsNotFoundExceptionWhenNotFound() {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(ItemNotFoundException.class, () -> curvePointService.deleteItem(10));
	}

}
