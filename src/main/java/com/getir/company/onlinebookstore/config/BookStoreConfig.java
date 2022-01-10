package com.getir.company.onlinebookstore.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Read and store the property from getir.properties
 * 
 * @author Saravanan Perumal
 *
 */
@Component
@PropertySource("getir.properties")
@ConfigurationProperties(prefix = "getir")
@Data
public class BookStoreConfig {

	public Map<String, String> message = new HashMap<>();

	public String apikey;

	public String signingkey;

	public String jwtEnabled;

	public String devUser;
}
