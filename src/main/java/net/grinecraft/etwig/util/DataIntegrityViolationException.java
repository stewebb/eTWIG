package net.grinecraft.etwig.util;

public class DataIntegrityViolationException extends org.springframework.dao.DataIntegrityViolationException {

	private static final long serialVersionUID = 1L;

	public DataIntegrityViolationException(String msg) {
		super("Data integrity has been violated. " + msg);
	}

}
