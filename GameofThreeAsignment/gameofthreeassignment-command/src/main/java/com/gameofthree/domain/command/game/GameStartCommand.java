package com.gameofthree.domain.command.game;

import com.gameofthree.domain.command.BaseCommand;

public class GameStartCommand extends BaseCommand<String> {

	private String userId;
	private int gameStatus;
	private int number;

	public GameStartCommand() {
	}

	public GameStartCommand(String id, int gameStatus, int number, String userId) {
		super(id);
		this.userId = userId;
		this.gameStatus = gameStatus;
		this.number = number;
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public int getNumber() {
		return number;
	}

}
