package com.nnk.springboot.unit.dtoconverters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.dtoconverters.RuleNameDTOConverterImpl;
import com.nnk.springboot.exceptions.ConverterException;

class RuleNameDTOConverterTest {

	private RuleNameDTOConverterImpl ruleNameConverter = new RuleNameDTOConverterImpl();

	private static RuleName rightRuleName1;
	private static RuleName rightRuleName2;
	private static RuleName wrongRuleName;
	private static RuleNameDTO ruleNameDTO1;
	private static RuleNameDTO ruleNameDTO2;
	private static List<RuleName> rightRuleNames;
	private static List<RuleName> wrongRuleNames;
	private static List<RuleNameDTO> ruleNameDTOs;
	
	@BeforeAll
	private static void setUp() {
		rightRuleName1 = new RuleName();
		rightRuleName2 = new RuleName();
		wrongRuleName = new RuleName();
		ruleNameDTO1 = new RuleNameDTO();
		ruleNameDTO2 = new RuleNameDTO();
		rightRuleNames = new ArrayList<>();
		wrongRuleNames = new ArrayList<>();
		ruleNameDTOs = new ArrayList<>();
		
		rightRuleName1.setId(1);
		rightRuleName1.setDescription("description1");
		rightRuleName1.setJson("json1");
		rightRuleName1.setName("name1");
		rightRuleName1.setSqlPart("sqlPart1");
		rightRuleName1.setSqlStr("sqlStr1");
		rightRuleName1.setTemplate("template1");
		
		rightRuleName2.setId(2);
		rightRuleName2.setDescription("description2");
		rightRuleName2.setJson("json2");
		rightRuleName2.setName("name2");
		rightRuleName2.setSqlPart("sqlPart2");
		rightRuleName2.setSqlStr("sqlStr2");
		rightRuleName2.setTemplate("template2");
		
		wrongRuleName.setId(3);
		wrongRuleName.setDescription("description3");
		wrongRuleName.setJson("json3");
		wrongRuleName.setName("");
		wrongRuleName.setSqlPart("sqlPart3");
		wrongRuleName.setSqlStr("sqlStr3");
		wrongRuleName.setTemplate("template3");
		
		rightRuleNames.add(rightRuleName1);
		rightRuleNames.add(rightRuleName2);
		
		wrongRuleNames.add(rightRuleName1);
		wrongRuleNames.add(wrongRuleName);
		
		ruleNameDTO1.setId(1);
		ruleNameDTO1.setDescription("description1");
		ruleNameDTO1.setJson("json1");
		ruleNameDTO1.setName("name1");
		ruleNameDTO1.setSqlPart("sqlPart1");
		ruleNameDTO1.setSqlStr("sqlStr1");
		ruleNameDTO1.setTemplate("template1");
		
		ruleNameDTO2.setId(2);
		ruleNameDTO2.setDescription("description2");
		ruleNameDTO2.setJson("json2");
		ruleNameDTO2.setName("name2");
		ruleNameDTO2.setSqlPart("sqlPart2");
		ruleNameDTO2.setSqlStr("sqlStr2");
		ruleNameDTO2.setTemplate("template2");
		
		ruleNameDTOs.add(ruleNameDTO1);
		ruleNameDTOs.add(ruleNameDTO2);
	}

	@Test
	void convertEntityToDTOReturnsDTOWhenOk() throws ConverterException {
		assertEquals(ruleNameDTO1.getId(), 
				ruleNameConverter.convertEntityToDTO(rightRuleName1).getId());
	}

	@Test
	void convertEntityToDTOThrowsConverterExceptionWhenEmptyField() {
		assertThrows(ConverterException.class, () -> ruleNameConverter.convertEntityToDTO(wrongRuleName));
	}

	@Test
	void convertListEntityToDTOReturnsListDTOWhenOk() throws ConverterException {
		assertEquals(2, ruleNameConverter.convertListEntityToDTO(rightRuleNames).size());
	}

	@Test
	void convertListEntityToDTOThrowsConverterExceptionWhenEmptyField() {
		assertThrows(ConverterException.class, () -> ruleNameConverter.convertListEntityToDTO(wrongRuleNames));
	}
}
