/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: An exception when data integrity has been violated. 
	*/

package net.etwig.webapp.util.exception;

public class DataException extends java.lang.Exception {

	private static final long serialVersionUID = 1L;

	public DataException(String msg) {
		super(msg);
	}
	
	public DataException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
