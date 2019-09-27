package com.gameofthreeassignmentweb.model;

public class MessageStatus {

	private int status;

	private String message;

	public MessageStatus(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public MessageStatus() {
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
