/**
 * 
 */
package com.nnk.springboot.dtoconverters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.exceptions.ConverterException;

/**
 * Converter RuleName to RuleNameDTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class RuleNameDTOConverterImpl implements IRuleNameDTOConverter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleNameDTOConverterImpl.class);

	@Override
	public RuleNameDTO convertEntityToDTO(RuleName ruleName) throws ConverterException {
		if(ruleName.getName().equals("")) {
			LOGGER.debug("convertEntityToDTO: ConverterException: name is empty");
			throw new ConverterException("Empty field");
		}
		RuleNameDTO ruleNameDTO = new RuleNameDTO();
		ruleNameDTO.setDescription(ruleName.getDescription());
		ruleNameDTO.setId(ruleName.getId());
		ruleNameDTO.setJson(ruleName.getJson());
		ruleNameDTO.setName(ruleName.getName());
		ruleNameDTO.setSqlPart(ruleName.getSqlPart());
		ruleNameDTO.setSqlStr(ruleName.getSqlStr());
		ruleNameDTO.setTemplate(ruleName.getTemplate());
		
		return ruleNameDTO;
	}

	@Override
	public List<RuleNameDTO> convertListEntityToDTO(List<RuleName> ruleNames) throws ConverterException {
		List<RuleNameDTO> ruleNameDTOs = new ArrayList<>();
		for(RuleName bidList: ruleNames) {
			try {
				ruleNameDTOs.add(convertEntityToDTO(bidList));
			} catch (ConverterException e) {
				LOGGER.debug("convertListEntityToDTO: ConverterException: " + e.getMessage());
				throw new ConverterException("Empty field");
			}
		}
		
		return ruleNameDTOs;
	}

}
