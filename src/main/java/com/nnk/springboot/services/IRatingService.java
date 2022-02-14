/**
 * 
 */
package com.nnk.springboot.services;

import com.nnk.springboot.dto.NewRatingDTO;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.dto.UpdateRatingDTO;

/**
 * Rating service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IRatingService extends IService<RatingDTO, NewRatingDTO, UpdateRatingDTO> {

}
