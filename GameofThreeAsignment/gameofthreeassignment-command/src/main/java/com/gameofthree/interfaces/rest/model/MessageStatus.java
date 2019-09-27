package com.gameofthree.interfaces.rest.model;

public class MessageStatus {

	private String message;

	public MessageStatus(String message) {
		super();
		this.message = message;
	}

	public MessageStatus() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
