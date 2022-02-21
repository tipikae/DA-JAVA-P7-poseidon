/**
 * 
 */
package com.nnk.springboot.dtoconverters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.exceptions.ConverterException;

/**
 * Converter CurvePoint to CurvePointDTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class CurvePointDTOConverterImpl implements ICurvePointDTOConverter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurvePointDTOConverterImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CurvePointDTO convertEntityToDTO(CurvePoint curvePoint) throws ConverterException {
		if(curvePoint.getTerm() == 0.0 || curvePoint.getValue() == 0.0) {
			LOGGER.debug("convertEntityToDTO: ConverterException: term=" 
					+ curvePoint.getTerm() + ", value=" + curvePoint.getValue());
			throw new ConverterException("Zero field");
		}
		CurvePointDTO curvePointDTO = new CurvePointDTO();
		curvePointDTO.setCurveId(curvePoint.getCurveId());
		curvePointDTO.setId(curvePoint.getId());
		curvePointDTO.setTerm(curvePoint.getTerm());
		curvePointDTO.setValue(curvePoint.getValue());
		
		return curvePointDTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CurvePointDTO> convertListEntityToDTO(List<CurvePoint> curvePoints) throws ConverterException {
		List<CurvePointDTO> curvePointDTOs = new ArrayList<>();
		for(CurvePoint curvePoint: curvePoints) {
			try {
				curvePointDTOs.add(convertEntityToDTO(curvePoint));
			} catch (ConverterException e) {
				LOGGER.debug("convertListEntityToDTO: ConverterException: " + e.getMessage());
				throw new ConverterException("Zero field");
			}
		}
		
		return curvePointDTOs;
	}

}
