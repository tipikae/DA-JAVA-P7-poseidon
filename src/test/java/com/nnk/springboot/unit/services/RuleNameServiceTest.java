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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.NewRuleNameDTO;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.dtoconverters.IRuleNameDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameServiceImpl;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {

	@Mock
	private RuleNameRepository ruleNameRepository;
	
	@Mock
	private IRuleNameDTOConverter ruleNameConverter;
	
	@InjectMocks
	private RuleNameServiceImpl ruleNameService;
	
	private static NewRuleNameDTO rightNewRuleNameDTO;
	private static NewRuleNameDTO wrongNewRuleNameDTO;
	private static RuleName ruleName1;
	private static RuleName ruleName2;
	private static RuleNameDTO ruleNameDTO1;
	private static RuleNameDTO ruleNameDTO2;
	private static List<RuleName> ruleNames;
	private static List<RuleNameDTO> ruleNameDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightNewRuleNameDTO = new NewRuleNameDTO();
		wrongNewRuleNameDTO = new NewRuleNameDTO();
		ruleName1 = new RuleName();
		ruleName2 = new RuleName();
		ruleNameDTO1 = new RuleNameDTO();
		ruleNameDTO2 = new RuleNameDTO();
		ruleNames = new ArrayList<>();
		ruleNameDTOs = new ArrayList<>();
		
		rightNewRuleNameDTO.setDescription("description1");
		rightNewRuleNameDTO.setJson("json1");
		rightNewRuleNameDTO.setName("name1");
		rightNewRuleNameDTO.setSqlPart("sqlpart1");
		rightNewRuleNameDTO.setSqlStr("sqlstr1");
		rightNewRuleNameDTO.setTemplate("template1");
		
		wrongNewRuleNameDTO.setDescription("description0");
		wrongNewRuleNameDTO.setJson("json0");
		wrongNewRuleNameDTO.setName("");
		wrongNewRuleNameDTO.setSqlPart("sqlpart0");
		wrongNewRuleNameDTO.setSqlStr("sqlstr0");
		wrongNewRuleNameDTO.setTemplate("template0");
		
		ruleName1.setId(1);
		ruleName1.setDescription("description1");
		ruleName1.setJson("json1");
		ruleName1.setName("name1");
		ruleName1.setSqlPart("sqlpart1");
		ruleName1.setSqlStr("sqlstr1");
		ruleName1.setTemplate("template1");
		
		ruleName2.setId(2);
		ruleName2.setDescription("description2");
		ruleName2.setJson("json2");
		ruleName2.setName("name2");
		ruleName2.setSqlPart("sqlpart2");
		ruleName2.setSqlStr("sqlstr2");
		ruleName2.setTemplate("template2");
		
		ruleNameDTO1.setId(1);
		ruleNameDTO1.setDescription("description1");
		ruleNameDTO1.setJson("json1");
		ruleNameDTO1.setName("name1");
		ruleNameDTO1.setSqlPart("sqlpart1");
		ruleNameDTO1.setSqlStr("sqlstr1");
		ruleNameDTO1.setTemplate("template1");
		
		ruleNameDTO2.setId(2);
		ruleNameDTO2.setDescription("description1");
		ruleNameDTO2.setJson("json1");
		ruleNameDTO2.setName("name1");
		ruleNameDTO2.setSqlPart("sqlpart1");
		ruleNameDTO2.setSqlStr("sqlstr1");
		ruleNameDTO2.setTemplate("template1");
		
		ruleNames.add(ruleName1);
		ruleNames.add(ruleName2);
		
		ruleNameDTOs.add(ruleNameDTO1);
		ruleNameDTOs.add(ruleNameDTO2);
	}
	

	@Test
	void addItemReturnsDTOWhenOk() throws ConverterException, ServiceException {
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName1);
		when(ruleNameConverter.convertEntityToDTO(any(RuleName.class))).thenReturn(ruleNameDTO1);
		assertEquals(rightNewRuleNameDTO.getDescription(), 
				ruleNameService.addItem(rightNewRuleNameDTO).getDescription());
	}

	@Test
	void addItemThrowsServiceExceptionWhenError() {
		
	}

	@Test
	void getAllItemsReturnsListDTOWhenOk() throws ConverterException, ServiceException {
		when(ruleNameRepository.findAll()).thenReturn(ruleNames);
		when(ruleNameConverter.convertListEntityToDTO(ruleNames)).thenReturn(ruleNameDTOs);
		assertEquals(ruleNames.size(), ruleNameService.getAllItems().size());
	}

	@Test
	void getItemByIdReturnsDTOWhenOk() throws ConverterException, NotFoundException, ServiceException {
		when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleName1));
		when(ruleNameConverter.convertEntityToDTO(ruleName1)).thenReturn(ruleNameDTO1);
		assertEquals(ruleName1.getId(), ruleNameService.getItemById(1).getId());
	}

	@Test
	void getItemByIdThrowsNotFoundExceptionWhenNotFound() {
		when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> ruleNameService.getItemById(1));
	}

	@Test
	void updateItemWhenOk() throws NotFoundException, ServiceException {
		when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(new RuleName()));
		ruleNameService.updateItem(1, rightNewRuleNameDTO);
		Mockito.verify(ruleNameRepository).save(any(RuleName.class));
	}

	@Test
	void updateItemThrowsNotFoundExceptionWhenNotFound() {
		when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> ruleNameService.updateItem(10, new NewRuleNameDTO()));
	}

	@Test
	void deleteItemWhenOk() throws NotFoundException, ServiceException {
		when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(new RuleName()));
		ruleNameService.deleteItem(1);
		Mockito.verify(ruleNameRepository).delete(any(RuleName.class));
	}

	@Test
	void deleteItemThrowsNotFoundExceptionWhenNotFound() {
		when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> ruleNameService.deleteItem(10));
	}

}
