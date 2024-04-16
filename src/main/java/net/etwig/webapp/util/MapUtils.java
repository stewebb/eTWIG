/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: All utilities for Maps.
	*/

package net.etwig.webapp.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapUtils {
	
	@FunctionalInterface
	public interface IdExtractor<T> {
	    Long getId(T object);
	}

	/**
	 * Convert a list of objects to a LinkedHashMap, whose keys are the id of the object, and values are the objects. 
	 * @param list The list of objects.
	 * @param extractor The rule to extract all ids as the key.
	 * @return
	 */
	public <T> LinkedHashMap<Long, T> listToLinkedHashMap(List<T> list, IdExtractor<T> extractor) {
        return list.stream().collect(Collectors.toMap(
            extractor::getId,
            Function.identity(),
            (existing, replacement) -> existing,
            LinkedHashMap::new
        ));
    }
}
