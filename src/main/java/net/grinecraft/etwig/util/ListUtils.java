package net.grinecraft.etwig.util;

import java.util.ArrayList;
import java.util.Arrays;

public class ListUtils {

	public static ArrayList<String> stringToArrayList(String str) {
		
		// Array well-formed check.
		int lastPos = str.length() - 1;
		if(str.charAt(0) != '[' || str.charAt(lastPos) != ']') {
			throw new IllegalArgumentException("The Array String must starts with [ and ends with ].");
		}
		
        str = str.substring(1, str.length() - 1);
        String[] items = str.split(",");

        return new ArrayList<>(Arrays.asList(items));
    }
	
	public static ArrayList<Long> stringArrayToLongArray(ArrayList<String> stringArray){
		ArrayList<Long> longArray = new ArrayList<Long>();
		for(String str : stringArray) {
			
			// Trim the String before convert!
			longArray.add(Long.parseLong(str.trim()));
		}
		
		return longArray;
	}
}
