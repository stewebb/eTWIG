/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: All utilities for JSON. 
	*/

package net.etwig.webapp.util;

import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class JSONUtils {
	
	ObjectMapper mapper;
	
	public JSONUtils() {
		this.mapper = new ObjectMapper();
		this.mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
	}

	/**
	 * A simple JSON validator without exception
	 * @param str Input string
	 * @return true if the input is a valid JSON, false otherwise.
	 */
	public boolean isStringJson(String str) {
		try {
			mapper.readTree(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * A simple JSON validator, if the input is not a valid JSON, exception will be thrown.
	 * @param str Input string
	 * @throws JsonMappingException, JsonProcessingException
	 */
	
	public Map<String, Object>  jsonToMap(String str) throws Exception {
		ObjectReader reader = this.mapper.readerFor(Map.class);
		return reader.readValue(str);
	}
}
