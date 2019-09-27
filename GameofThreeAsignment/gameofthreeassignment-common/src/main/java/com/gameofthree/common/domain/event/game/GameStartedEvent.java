package com.gameofthree.common.domain.event.game;

import com.gameofthree.common.domain.event.BaseEvent;

public class GameStartedEvent extends BaseEvent<String> {

	private int gameStatus;
	private int number;
	private String userId;

	public GameStartedEvent() {
	}

	public GameStartedEvent(String id, int gameStatus, int number, String userId) {
		super(id);
		this.gameStatus = gameStatus;
		this.number = number;
		this.userId = userId;

	}

	public int getGameStatus() {
		return gameStatus;
	}

	public int getNumber() {
		return number;
	}

	public String getUserId() {
		return userId;
	}

}
