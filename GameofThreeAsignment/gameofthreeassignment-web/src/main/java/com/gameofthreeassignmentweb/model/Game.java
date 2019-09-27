package com.gameofthreeassignmentweb.model;

import java.util.List;

public class Game {

	private String id;

	private String name;

	private int number;

	private int gameStatus;

	private String userTurn;

	private String creator;

	private List<String> playerList;

	public Game(String id, String name, int number, int gameStatus, String userTurn, String creator,
			List<String> playerList) {
		this.gameStatus = gameStatus;
		this.userTurn = userTurn;
		this.number = number;
		this.creator = creator;
		this.playerList = playerList;
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}

	public String getUserTurn() {
		return userTurn;
	}

	public void setUserTurn(String userTurn) {
		this.userTurn = userTurn;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public List<String> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<String> playerList) {
		this.playerList = playerList;
	}

}
