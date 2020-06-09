package com.mlatta.beer.exceptions;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 5327089490425606132L;
	private static final String DEFAULT_MSG = "Unable to find object.";
	
	public NotFoundException() {
		super(DEFAULT_MSG);
	}
	
	public NotFoundException(String message) {
		super(DEFAULT_MSG + " | " +message);
	}

}
