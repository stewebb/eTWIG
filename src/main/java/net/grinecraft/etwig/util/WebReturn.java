/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: Utilities for returns in the controllers.
	 */

package net.grinecraft.etwig.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class WebReturn {

	/**
	 * Return a common "error and message" for an JSON response.
	 * @param msg
	 * @param success 
	 * @return
	 */
	
	public static Map<String, Object> errorMsg(String msg, boolean success){
		Map<String, Object> myReturn = new LinkedHashMap<String, Object>();
		if(success) {
			myReturn.put("error", 0);
			myReturn.put("msg", StringUtils.isEmpty(msg)? "success." : msg);
		}else {
			myReturn.put("error", 1);
	    	myReturn.put("msg", msg);
		}
		return myReturn;
	}
}
