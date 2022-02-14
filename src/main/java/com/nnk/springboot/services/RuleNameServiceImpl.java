/**
 * 
 */
package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.NewRuleNameDTO;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.dto.UpdateRuleNameDTO;
import com.nnk.springboot.dtoconverters.IRuleNameDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.RuleNameRepository;

/**
 * RuleName service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class RuleNameServiceImpl implements IRuleNameService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleNameServiceImpl.class);
	
	@Autowired
	private RuleNameRepository ruleNameRepository;
	
	@Autowired
	private IRuleNameDTOConverter ruleNameConverter;

	@Override
	public RuleNameDTO addItem(NewRuleNameDTO newDTO) throws ServiceException, ConverterException {
		LOGGER.debug("Service: addItem: description=" + newDTO.getDescription() + ", json=" + newDTO.getJson()
				+ ", name=" + newDTO.getName() + ", sqlPart=" + newDTO.getSqlPart() 
				+ ", sqlStr=" + newDTO.getSqlStr() + ", template=" + newDTO.getTemplate());
		/*if(false) {
			LOGGER.debug("");
			throw new ServiceException("");
		}*/
		
		RuleName ruleName = new RuleName();
		ruleName.setDescription(newDTO.getDescription());
		ruleName.setJson(newDTO.getJson());
		ruleName.setName(newDTO.getName());
		ruleName.setSqlPart(newDTO.getSqlPart());
		ruleName.setSqlStr(newDTO.getSqlStr());
		ruleName.setTemplate(newDTO.getTemplate());
		
		return ruleNameConverter.convertEntityToDTO(ruleNameRepository.save(ruleName));
	}

	@Override
	public List<RuleNameDTO> getAllItems() throws ConverterException {
		LOGGER.debug("Service: getAllItems");
		return ruleNameConverter.convertListEntityToDTO(ruleNameRepository.findAll());
	}

	@Override
	public RuleNameDTO getItemById(Integer id) throws NotFoundException, ConverterException {
		LOGGER.debug("Service: getItemById: id=" + id);
		Optional<RuleName> optional = ruleNameRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("RuleName with id=" + id + " not found.");
			throw new NotFoundException("RuleName not found.");
		}
		
		return ruleNameConverter.convertEntityToDTO(optional.get());
	}

	@Override
	public void updateItem(Integer id, UpdateRuleNameDTO updatedDTO) throws NotFoundException {
		LOGGER.debug("Service: updateItem: id=" + id);
		Optional<RuleName> optional = ruleNameRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("RuleName with id=" + id + " not found.");
			throw new NotFoundException("RuleName not found.");
		}

		RuleName ruleName = optional.get();
		ruleName.setDescription(updatedDTO.getDescription());
		ruleName.setJson(updatedDTO.getJson());
		ruleName.setName(updatedDTO.getName());
		ruleName.setSqlPart(updatedDTO.getSqlPart());
		ruleName.setSqlStr(updatedDTO.getSqlStr());
		ruleName.setTemplate(updatedDTO.getTemplate());
		
		ruleNameRepository.save(ruleName);
	}

	@Override
	public void deleteItem(Integer id) throws NotFoundException {
		LOGGER.debug("Service: deleteItem: id=" + id);
		Optional<RuleName> optional = ruleNameRepository.findById(id);
		if(!optional.isPresent()) {
			LOGGER.debug("RuleName with id=" + id + " not found.");
			throw new NotFoundException("RuleName not found.");
		}

		ruleNameRepository.delete(optional.get());
	}

}
