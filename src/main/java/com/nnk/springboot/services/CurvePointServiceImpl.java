package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.dto.NewCurvePointDTO;
import com.nnk.springboot.dto.UpdateCurvePointDTO;
import com.nnk.springboot.dtoconverters.ICurvePointDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.ItemAlreadyExistsException;
import com.nnk.springboot.exceptions.ItemNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;

/**
 * CurvePoint service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
@Transactional
public class CurvePointServiceImpl implements ICurvePointService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurvePointServiceImpl.class);
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	@Autowired
	private ICurvePointDTOConverter converterCurvePoint;

	@Override
	public CurvePointDTO addItem(NewCurvePointDTO newCurvePoint) throws ItemAlreadyExistsException, ConverterException {
		LOGGER.debug("Service: addItem: curveId=" + newCurvePoint.getCurveId() + ", term=" + newCurvePoint.getTerm() 
				+ ", value=" + newCurvePoint.getValue());
		
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setCurveId(newCurvePoint.getCurveId());
		curvePoint.setTerm(newCurvePoint.getTerm());
		curvePoint.setValue(newCurvePoint.getValue());
		curvePoint.setCreationDate(Timestamp.from(Instant.now()));
		
		return converterCurvePoint.convertEntityToDTO(curvePointRepository.save(curvePoint));
	}

	@Override
	public List<CurvePointDTO> getAllItems() throws ConverterException {
		LOGGER.debug("Service: getAllItems");
		return converterCurvePoint.convertListEntityToDTO(curvePointRepository.findAll());
	}

	@Override
	public CurvePointDTO getItemById(Integer id) throws ItemNotFoundException, ConverterException {
		LOGGER.debug("Service: getItemById: id=" + id);
		Optional<CurvePoint> optional = curvePointRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("CurvePoint with id=" + id + " not found.");
			throw new ItemNotFoundException("CurvePoint not found.");
		}
		
		return converterCurvePoint.convertEntityToDTO(optional.get());
	}

	@Override
	public void updateItem(Integer id, UpdateCurvePointDTO updatedCurvePoint) throws ItemNotFoundException {
		LOGGER.debug("Service: updateItem: id=" + id);
		Optional<CurvePoint> optional = curvePointRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("CurvePoint with id=" + id + " not found.");
			throw new ItemNotFoundException("CurvePoint not found.");
		}

		CurvePoint curvePoint = optional.get();
		curvePoint.setCurveId(updatedCurvePoint.getCurveId());
		curvePoint.setTerm(updatedCurvePoint.getTerm());
		curvePoint.setValue(updatedCurvePoint.getValue());
		
		curvePointRepository.save(curvePoint);
	}

	@Override
	public void deleteItem(Integer id) throws ItemNotFoundException {
		LOGGER.debug("Service: deleteItem: id=" + id);
		Optional<CurvePoint> optional = curvePointRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("CurvePoint with id=" + id + " not found.");
			throw new ItemNotFoundException("CurvePoint not found.");
		}

		curvePointRepository.delete(optional.get());
	}

}
