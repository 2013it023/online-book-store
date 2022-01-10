package com.getir.company.onlinebookstore.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.EntityWriteResult;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.stereotype.Repository;

import com.getir.company.onlinebookstore.dao.ProfileDao;
import com.getir.company.onlinebookstore.dto.ProfileDto;

/**
 * 
 * Provides implementation for the profileDao
 * 
 * @author Saravanan Perumal
 *
 */
@Repository
public class ProfileDaoImpl implements ProfileDao {

	@Autowired
	private CassandraOperations cassandraTemplate;

	@Override
	public boolean save(ProfileDto profileDto) {

		EntityWriteResult<ProfileDto> insert = cassandraTemplate.insert(profileDto,
				InsertOptions.builder().ifNotExists(true).build());

		return insert != null ? insert.wasApplied() : false;
	}

}
