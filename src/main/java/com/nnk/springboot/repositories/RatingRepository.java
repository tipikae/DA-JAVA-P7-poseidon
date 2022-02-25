package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Rating repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
