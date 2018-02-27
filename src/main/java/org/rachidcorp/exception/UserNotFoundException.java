package org.rachidcorp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long userId) {
		super("Could not find user with the specified ID!");
	}
	
	public UserNotFoundException(String username) {
		super("Could not find user with the specified username!");
	}
}
