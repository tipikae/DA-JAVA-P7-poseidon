package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.dto.NewCurvePointDTO;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;

/**
 * CurvePoint Service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface ICurvePointService {

	/**
	 * Add a CurvePoint.
	 * @param curvePoint
	 * @return CurvePointDTO
	 * @throws ServiceException
	 * @throws ConverterException 
	 */
	CurvePointDTO addCurvePoint(NewCurvePointDTO newCurvePoint) throws ServiceException, ConverterException;
	
	/**
	 * Get all CurvePoints.
	 * @return List<CurvePointDTO>
	 * @throws ConverterException 
	 */
	List<CurvePointDTO> getAllCurvePoints() throws ConverterException;
	
	/**
	 * Get a CurvePoint.
	 * @param id
	 * @return CurvePointDTO
	 * @throws NotFoundException
	 * @throws ConverterException 
	 */
	CurvePointDTO getCurvePoint(Integer id) throws NotFoundException, ConverterException;
	
	/**
	 * Update a CurvePoint.
	 * @param id
	 * @param curvePoint
	 * @throws NotFoundException
	 */
	void updateCurvePoint(Integer id, NewCurvePointDTO updatedCurvePoint) throws NotFoundException;
	
	/**
	 * Delete a CurvePoint.
	 * @param id
	 * @throws NotFoundException
	 */
	void deleteCurvePoint(Integer id) throws NotFoundException;
}
