package com.gameofthree.domain.command.game;

import com.gameofthree.domain.command.BaseCommand;

public class GameCreateCommand extends BaseCommand<String> {

	private int number;

	private String name;

	private int gameStatus;

	private String userId;

	public GameCreateCommand() {
	}

	public GameCreateCommand(String id, String name, int number, int gameStatus, String userId) {
		super(id);
		this.userId = userId;
		this.number = number;
		this.name = name;
		this.gameStatus = gameStatus;
	}

	public String getUserId() {
		return userId;
	}

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public int getGameStatus() {
		return gameStatus;
	}

}
