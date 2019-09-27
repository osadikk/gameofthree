package com.gameofthreeassignmentweb.util.exception;

import org.springframework.http.ResponseEntity;

import com.gameofthreeassignmentweb.model.MessageStatus;

public class ResponseEntityErrorException extends RuntimeException {
	private ResponseEntity<MessageStatus> errorResponse;

	public ResponseEntityErrorException(ResponseEntity<MessageStatus> errorResponse) {
		this.errorResponse = errorResponse;
	}

	public ResponseEntity<MessageStatus> getErrorResponse() {
		return errorResponse;
	}
}