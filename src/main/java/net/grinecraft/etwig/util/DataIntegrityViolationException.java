/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: An exception when data integrity has been violated. 
	 */

package net.grinecraft.etwig.util;

public class DataIntegrityViolationException extends org.springframework.dao.DataIntegrityViolationException {

	private static final long serialVersionUID = 1L;

	public DataIntegrityViolationException(String msg) {
		super("Data integrity has been violated. " + msg);
	}

}
