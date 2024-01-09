/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: All utilities for Maps.
	*/

package net.grinecraft.etwig.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapUtils {
	
	@FunctionalInterface
	public interface IdExtractor<T> {
	    Long getId(T object);
	}

	public <T> LinkedHashMap<Long, T> listToLinkedHashMap(List<T> list, IdExtractor<T> extractor) {
        return list.stream().collect(Collectors.toMap(
            extractor::getId,
            Function.identity(),
            (existing, replacement) -> existing,
            LinkedHashMap::new
        ));
    }
}
