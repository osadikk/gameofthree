package com.gameofthree.common.domain.event.game;

import com.gameofthree.common.domain.event.BaseEvent;

public class PlayerJoinedEvent extends BaseEvent<String> {

	private int gameStatus;
	private String userId;
	private String creator;

	public PlayerJoinedEvent() {
	}

	public PlayerJoinedEvent(String id, String userId, int gameStatus, String creator) {
		super(id);
		this.gameStatus = gameStatus;
		this.userId = userId;
		this.creator = creator;

	}

	public int getGameStatus() {
		return gameStatus;
	}

	public String getUserId() {
		return userId;
	}

	public String getCreator() {
		return creator;
	}

}
