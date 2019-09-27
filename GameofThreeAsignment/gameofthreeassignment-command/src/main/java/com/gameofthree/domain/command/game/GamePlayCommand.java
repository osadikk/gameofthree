package com.gameofthree.domain.command.game;

import com.gameofthree.domain.command.BaseCommand;

public class GamePlayCommand extends BaseCommand<String> {

	private int gameStatus;
	private int number;
	private String userId;
	private int adj;

	public GamePlayCommand() {
	}

	public GamePlayCommand(String id, int gameStatus, int number, int adj, String userId) {
		super(id);
		this.gameStatus = gameStatus;
		this.number = number;
		this.userId = userId;
		this.adj = adj;
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

	public int getAdj() {
		return adj;
	}

}