package com.jpadata.sb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResponseNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5857381137824300338L;
	
	public ResponseNotFoundException(String message)
	{
		super( message);
	}

}
