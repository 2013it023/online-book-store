package com.getir.company.onlinebookstore.dao;

import com.getir.company.onlinebookstore.dto.ProfileDto;

/**
 * 
 * Provides abstract methods to interact with profileDto.
 * 
 * @author Saravanan Perumal
 *
 */
public interface ProfileDao {

	boolean save(ProfileDto profileDto);

}
