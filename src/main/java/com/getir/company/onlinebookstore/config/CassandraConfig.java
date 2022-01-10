package com.getir.company.onlinebookstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

/**
 * config related changes for cassandra
 * 
 * @author Saravanan Perumal
 *
 */
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Override
	protected String getKeyspaceName() {
		return "getir";
	}

	@Override
	protected String getLocalDataCenter() {
		return "datacenter1";
	}
}
