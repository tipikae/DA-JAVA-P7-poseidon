package com.nnk.springboot.unit.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.dto.NewRuleNameDTO;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.dto.UpdateRuleNameDTO;
import com.nnk.springboot.dtoconverters.IRuleNameDTOConverter;
import com.nnk.springboot.exceptions.ConverterException;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.exceptions.ServiceException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.IRuleNameService;

@WebMvcTest(controllers = RuleNameController.class)
class RuleNameControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RuleNameRepository ruleNameRepository;
	
	@MockBean
	private IRuleNameDTOConverter ruleNameDTOConverter;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private IRuleNameService ruleNameService;
	
	private static NewRuleNameDTO rightRuleNameDTO;
	
	private static final String ROOT_REQUEST = "/ruleName";
	private static final String ROOT_TEMPLATE = "ruleName";
	
	@BeforeAll
	private static void setUp() {
		rightRuleNameDTO = new NewRuleNameDTO();
		rightRuleNameDTO.setDescription("description1");
		rightRuleNameDTO.setJson("json1");
		rightRuleNameDTO.setName("name1");
		rightRuleNameDTO.setSqlPart("sqlPart1");
		rightRuleNameDTO.setSqlStr("sqlStr1");
		rightRuleNameDTO.setTemplate("template1");
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// home
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void homeReturnsListWhenOk() throws Exception {
		when(ruleNameService.getAllItems()).thenReturn(new ArrayList<>());
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/list"));
	}

	@WithMockUser(roles = {""})
	@Test
	void homeReturnsErrorWhenBadRole() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().is(403));
	}

	@Test
	void homeReturnsLoginWhenNoLog() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().is3xxRedirection());

	}

	@WithMockUser
	@Test
	void homeReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(ruleNameService).getAllItems();
		mockMvc.perform(get(ROOT_REQUEST + "/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// addRuleForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void addRuleFormReturnsForm() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/add"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/add"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// validate
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void validateReturnsFormWhenOk() throws Exception {
		when(ruleNameService.addItem(any(NewRuleNameDTO.class))).thenReturn(new RuleNameDTO());
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("ruleName", rightRuleNameDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=New RuleName added."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenValidationError() throws Exception {
		NewRuleNameDTO wrongRuleNameDTO = new NewRuleNameDTO();
		wrongRuleNameDTO.setDescription("");
		wrongRuleNameDTO.setJson("json1");
		wrongRuleNameDTO.setName("name1");
		wrongRuleNameDTO.setSqlPart("sqlPart1");
		wrongRuleNameDTO.setSqlStr("sqlStr1");
		wrongRuleNameDTO.setTemplate("template1");
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("ruleName", wrongRuleNameDTO))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/add"));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenServiceException() throws Exception {
		doThrow(ServiceException.class).when(ruleNameService).addItem(any(NewRuleNameDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("ruleName", rightRuleNameDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=Unable to process new RuleName."));
	}

	@WithMockUser
	@Test
	void validateReturnsFormWithErrorWhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(ruleNameService).addItem(any(NewRuleNameDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/validate")
				.flashAttr("ruleName", rightRuleNameDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=Unable to process new RuleName."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// showUpdateForm
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void showUpdateFormReturnsFormWhenOk() throws Exception {
		RuleNameDTO ruleNameDTO = new RuleNameDTO();
		ruleNameDTO.setDescription("description1");
		ruleNameDTO.setJson("json1");
		ruleNameDTO.setName("name1");
		ruleNameDTO.setSqlPart("sqlPart1");
		ruleNameDTO.setSqlStr("sqlStr1");
		ruleNameDTO.setTemplate("template1");
		ruleNameDTO.setId(1);
		when(ruleNameService.getItemById(anyInt())).thenReturn(ruleNameDTO);
		mockMvc.perform(get(ROOT_REQUEST + "/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name(ROOT_TEMPLATE + "/update"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError404WhenNotFoundException() throws Exception {
		doThrow(NotFoundException.class).when(ruleNameService).getItemById(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/update/10"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

	@WithMockUser
	@Test
	void showUpdateFormReturnsError400WhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(ruleNameService).getItemById(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// updateRuleName
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void updateRuleNameReturnsListWhenOk() throws Exception {
		UpdateRuleNameDTO rightUpdateRuleNameDTO = new UpdateRuleNameDTO();
		rightUpdateRuleNameDTO.setDescription("description1");
		rightUpdateRuleNameDTO.setJson("json2");
		rightUpdateRuleNameDTO.setName("name1");
		rightUpdateRuleNameDTO.setSqlPart("sqlPart1");
		rightUpdateRuleNameDTO.setSqlStr("sqlStr1");
		rightUpdateRuleNameDTO.setTemplate("template1");
		mockMvc.perform(post(ROOT_REQUEST + "/update/1")
				.flashAttr("ruleName", rightUpdateRuleNameDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=RuleName has been updated."));
	}

	@WithMockUser
	@Test
	void updateRuleNameReturnsFormWithErrorWhenValidationError() throws Exception {
		UpdateRuleNameDTO wrongUpdateRuleNameDTO = new UpdateRuleNameDTO();
		wrongUpdateRuleNameDTO.setDescription("description1");
		wrongUpdateRuleNameDTO.setJson("json2");
		wrongUpdateRuleNameDTO.setName("name1");
		wrongUpdateRuleNameDTO.setSqlPart("sqlPart1");
		wrongUpdateRuleNameDTO.setSqlStr("sqlStr1");
		wrongUpdateRuleNameDTO.setTemplate("");
		mockMvc.perform(post(ROOT_REQUEST + "/update/1")
				.flashAttr("ruleName", wrongUpdateRuleNameDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/update/1?error=Template must not be empty. "));
	}

	@WithMockUser
	@Test
	void updateRuleNameReturnsError404WhenNotFoundException() throws Exception {
		UpdateRuleNameDTO rightUpdateRuleNameDTO = new UpdateRuleNameDTO();
		rightUpdateRuleNameDTO.setDescription("description1");
		rightUpdateRuleNameDTO.setJson("json2");
		rightUpdateRuleNameDTO.setName("name1");
		rightUpdateRuleNameDTO.setSqlPart("sqlPart1");
		rightUpdateRuleNameDTO.setSqlStr("sqlStr1");
		rightUpdateRuleNameDTO.setTemplate("template1");
		doThrow(NotFoundException.class).when(ruleNameService).updateItem(anyInt(), any(UpdateRuleNameDTO.class));
		mockMvc.perform(post(ROOT_REQUEST + "/update/10")
				.flashAttr("ruleName", rightUpdateRuleNameDTO))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=RuleName not found."));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// deleteRuleName
	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser
	@Test
	void deleteRuleNameReturnsListWhenOk() throws Exception {
		mockMvc.perform(get(ROOT_REQUEST + "/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?success=RuleName has been deleted."));
	}

	@WithMockUser
	@Test
	void deleteRuleNameReturnsError404WhenNotFoundException() throws Exception {
		doThrow(NotFoundException.class).when(ruleNameService).deleteItem(anyInt());
		mockMvc.perform(get(ROOT_REQUEST + "/delete/10"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:" + ROOT_REQUEST + "/list?error=RuleName not found."));
	}
}
