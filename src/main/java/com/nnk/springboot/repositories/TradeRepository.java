package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Trade repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
