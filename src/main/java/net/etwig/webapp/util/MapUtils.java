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
import java.util.Map;
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

	/**
	 * Returns the smallest key from the given map based on natural ordering of the keys.
	 * Assumes that all keys in the map implement the Comparable interface. If the map is empty,
	 * this method returns null.
	 *
	 * @param <K> the type of keys in the map, which extends Comparable
	 * @param <V> the type of values in the map
	 * @param map the map from which the smallest key is to be found
	 * @return the smallest key if present; null if the map is empty
	 */

	public static <K extends Comparable<K>, V> K getSmallestKey(Map<K, V> map) {
		K smallestKey = null;
		for (K key : map.keySet()) {
			if (smallestKey == null || key.compareTo(smallestKey) < 0) {
				smallestKey = key;
			}
		}
		return smallestKey;
	}
}
