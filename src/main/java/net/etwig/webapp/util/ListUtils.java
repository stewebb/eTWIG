/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: All utilities for lists. 
	*/

package net.etwig.webapp.util;

import java.util.ArrayList;
import java.util.Arrays;

public class ListUtils {

	/**
	 * Convert an "Array String" (e.g., "[1, 2, 3]") to an Array.
	 * @param str The "Array String".
	 * @return The converted array (all elements are String)
	 */
	
	public static ArrayList<String> stringToArrayList(String str) {
		
		// Array well-formed check.
		int lastPos = str.length() - 1;
		if(str.charAt(0) != '[' || str.charAt(lastPos) != ']') {
			throw new IllegalArgumentException("The Array String must starts with [ and ends with ].");
		}
		
		// Extract the element.
        str = str.substring(1, str.length() - 1);
        String[] items = str.split(",");
        return new ArrayList<>(Arrays.asList(items));
    }
	
	/**
	 * Convert an String array (i.e., all elements are String) to a Long array (i.e., all elements are Long)
	 * @param stringArray
	 * @return
	 * @throws NumberFormatException if at least 1 element cannot be converted as Long number.
	 */
	
	public static ArrayList<Long> stringArrayToLongArray(ArrayList<String> stringArray){
		ArrayList<Long> longArray = new ArrayList<Long>();
		
		// Just iterate and push to a new array...
		for(String str : stringArray) {
			
			// Trim the String before convert!
			String trimmed = str.trim();

			// Only convert it to number if it's not empty.
			if(!trimmed.isEmpty()){
				longArray.add(Long.parseLong(trimmed));
			}
		}
		return longArray;
	}
}
