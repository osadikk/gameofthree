package com.gameofthree.common.domain.event.game;

import com.gameofthree.common.domain.event.BaseEvent;

public class GamePlayedEvent extends BaseEvent<String> {

	private int gameStatus;
	private int number;
	private String userId;

	public GamePlayedEvent() {
	}

	public GamePlayedEvent(String id, int gameStatus, int number, String userId) {
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
