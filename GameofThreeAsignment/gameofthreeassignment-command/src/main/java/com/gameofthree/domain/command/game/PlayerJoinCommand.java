package com.gameofthree.domain.command.game;

import com.gameofthree.domain.command.BaseCommand;

public class PlayerJoinCommand extends BaseCommand<String> {

	private int gameStatus;
	private String userId;

	public PlayerJoinCommand() {
	}

	public PlayerJoinCommand(String id, String userId, int gameStatus) {
		super(id);
		this.userId = userId;
		this.gameStatus = gameStatus;

	}

	public String getUserId() {
		return userId;
	}

	public int getGameStatus() {
		return gameStatus;
	}

}
