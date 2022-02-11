package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BidTest {
	
	@MockBean
	private UserDetailsService userDetailsService;

	@Autowired
	private BidListRepository bidListRepository;

	@Transactional
	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		Integer id;

		// Save
		bid = bidListRepository.save(bid);
		assertNotNull(bid.getBidListId());
		assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find all
		List<BidList> listResult = bidListRepository.findAll();
		assertTrue(listResult.size() > 0);
		
		// Find by id
		id = bid.getBidListId();
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertTrue(bidList.isPresent());

		// Delete
		bidListRepository.delete(bid);
		bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}
