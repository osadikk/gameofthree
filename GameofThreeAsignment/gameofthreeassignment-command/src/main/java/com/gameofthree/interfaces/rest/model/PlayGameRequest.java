package com.gameofthree.interfaces.rest.model;

public class PlayGameRequest extends GameRequest {

	private String adjustment;
	private String number;

	public PlayGameRequest() {
	}

	public PlayGameRequest(String gameId, String userId, String adjustment, String number) {
		super(gameId, userId);
		this.adjustment = adjustment;
		this.number = number;
	}

	public String getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(String adjustment) {
		this.adjustment = adjustment;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
