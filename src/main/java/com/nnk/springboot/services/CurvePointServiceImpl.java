package com.nnk.springboot.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.dto.NewCurvePointDTO;
import com.nnk.springboot.dtoconverters.ConverterCurvePointImpl;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;

/**
 * CurvePoint service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class CurvePointServiceImpl implements ICurvePointService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurvePointServiceImpl.class);

	@Override
	public CurvePointDTO addCurvePoint(NewCurvePointDTO curvePoint) throws ServiceException, ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CurvePointDTO> getAllCurvePoints() throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurvePointDTO getCurvePoint(Integer id) throws NotFoundException, ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCurvePoint(Integer id, NewCurvePointDTO curvePoint) throws NotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCurvePoint(Integer id) throws NotFoundException {
		// TODO Auto-generated method stub

	}

}
