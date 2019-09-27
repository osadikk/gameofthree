package com.gameofthree.common.domain.event.game;

import com.gameofthree.common.domain.event.BaseEvent;

public class GameCreatedEvent extends BaseEvent<String> {
	
	private int number;
	
	private String name;
	
	private int gameStatus;
	
	private String userId;
	
	
	public GameCreatedEvent() {
		
	}

	public GameCreatedEvent(String id,String name, int number, int gameStatus,String userId) {
		super(id);
		this.name=name;
		this.number=number;
		this.gameStatus = gameStatus;
		this.userId=userId;
		}

	public int getGameStatus() {
		return gameStatus;
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

}
