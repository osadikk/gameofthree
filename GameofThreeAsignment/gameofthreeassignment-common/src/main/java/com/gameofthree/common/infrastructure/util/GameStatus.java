package com.gameofthree.common.infrastructure.util;

import java.util.Arrays;
import java.util.Optional;

public enum GameStatus {
	NEW(0), NOWPLAYING(1), GAMEESTABLISHED(2), CLOSED(3);

	private final int status;

	private GameStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public static Optional<GameStatus> valueOf(int value) {
		return Arrays.stream(values()).filter(stat -> stat.status == value).findFirst();
	}
}
