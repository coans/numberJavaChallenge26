package com.number26.exception;

public class IDExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public IDExistException() {
		super();
	}
	
	public IDExistException(String message) {
		super(message);
	}
}
