package com.gameofthreeassignmentweb.model.dto;

public class GameRequest {

	private String gameId;
	private String userId;

	public GameRequest() {
	}

	public GameRequest(String gameId, String userId) {
		super();
		this.gameId = gameId;
		this.userId = userId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
