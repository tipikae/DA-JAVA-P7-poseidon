package com.nnk.springboot.unit.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.nnk.springboot.repositories.CurvePointRepository;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
