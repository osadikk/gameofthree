package com.gameofthree.domain.aggregate;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;

import com.gameofthree.common.domain.event.game.GameCreatedEvent;
import com.gameofthree.common.domain.event.game.GamePlayedEvent;
import com.gameofthree.common.domain.event.game.GameStartedEvent;
import com.gameofthree.common.domain.event.game.PlayerJoinedEvent;
import com.gameofthree.common.infrastructure.util.GameStatus;
import com.gameofthree.domain.command.game.GameCreateCommand;
import com.gameofthree.domain.command.game.GamePlayCommand;
import com.gameofthree.domain.command.game.GameStartCommand;
import com.gameofthree.domain.command.game.PlayerJoinCommand;

public class GameTest {

	private AggregateTestFixture<Game> fixture;

	@Before
	public void setUp() {
		fixture = new AggregateTestFixture<>(Game.class);
	}

	@Test
	public void testCreateGame() throws Exception {
		fixture.given().when(new GameCreateCommand("123", "Game123", -1, GameStatus.NEW.ordinal(), "test123"))
				.expectEvents(new GameCreatedEvent("123", "Game123", -1, GameStatus.NEW.ordinal(), "test123"));

	}

	@Test
	public void testJoinGame() {
		fixture.given(new GameCreatedEvent("123", "Game123", -1, GameStatus.NEW.ordinal(), "test123"))
				.when(new PlayerJoinCommand("123", "onur123", GameStatus.GAMEESTABLISHED.ordinal()))
				.expectEvents(new PlayerJoinedEvent("123", "onur123", GameStatus.GAMEESTABLISHED.ordinal(), "test123"));
	}

	@Test
	public void testJoinGameAlreadyEstablished() {
		fixture.given(new PlayerJoinedEvent("123", "onur123", GameStatus.GAMEESTABLISHED.ordinal(), "test123"))
				.when(new PlayerJoinCommand("123", "onur123", GameStatus.GAMEESTABLISHED.ordinal())).expectNoEvents();
	}

	@Test
	public void testPlayingGameJoin() {
		fixture.given(new GamePlayedEvent("123", GameStatus.NOWPLAYING.ordinal(), 15, "onur123"))
				.when(new PlayerJoinCommand("123", "test123", GameStatus.GAMEESTABLISHED.ordinal())).expectNoEvents();
	}

	@Test
	public void testClosedGameJoin() {
		fixture.given(new GamePlayedEvent("123", GameStatus.CLOSED.ordinal(), 15, "onur123"))
				.when(new PlayerJoinCommand("123", "test123", GameStatus.GAMEESTABLISHED.ordinal())).expectNoEvents();
	}

	@Test
	public void testStartGame() {
		fixture.given(new PlayerJoinedEvent("123", "onur123", GameStatus.GAMEESTABLISHED.ordinal(), "test123"))
				.when(new GameStartCommand("123", GameStatus.NOWPLAYING.ordinal(), 56, "test123"))
				.expectEvents(new GameStartedEvent("123", GameStatus.NOWPLAYING.ordinal(), 56, "test123"));
	}

	@Test
	public void testStatusNewStartGame() {
		fixture.given(new GameCreatedEvent("123", "Game123", -1, GameStatus.NEW.ordinal(), "test123"))
				.when(new GameStartCommand("123", GameStatus.NOWPLAYING.ordinal(), 56, "test123")).expectNoEvents();

	}

	@Test
	public void testStatusPlayingStartGame() {
		fixture.given(new PlayerJoinedEvent("123", "onur123", GameStatus.NOWPLAYING.ordinal(), "test123"))
				.when(new GameStartCommand("123", GameStatus.NOWPLAYING.ordinal(), 56, "test123")).expectNoEvents();

	}

	@Test
	public void testStatusCloseStartGame() {
		fixture.given(new PlayerJoinedEvent("123", "onur123", GameStatus.CLOSED.ordinal(), "test123"))
				.when(new GameStartCommand("123", GameStatus.NOWPLAYING.ordinal(), 56, "test123")).expectNoEvents();

	}

	@Test
	public void testFinishGame() {
		fixture.given(new GameStartedEvent("123", GameStatus.NOWPLAYING.ordinal(), 4, "test123"))
				.when(new GamePlayCommand("123", GameStatus.NOWPLAYING.ordinal(), 4, -1, "onur123"))
				.expectEvents(new GamePlayedEvent("123", GameStatus.CLOSED.ordinal(), 1, "onur123"));
	}

	@Test
	public void testContinueGame() {
		fixture.given(new GameStartedEvent("123", GameStatus.NOWPLAYING.ordinal(), 13, "test123"))
				.when(new GamePlayCommand("123", GameStatus.NOWPLAYING.ordinal(), 13, -1, "onur123"))
				.expectEvents(new GamePlayedEvent("123", GameStatus.NOWPLAYING.ordinal(), 4, "onur123"));
	}

	@Test
	public void testPlayGameNoDivisibleBy3() {
		fixture.given(new GamePlayedEvent("123", GameStatus.NOWPLAYING.ordinal(), 14, "test123"))
				.when(new GamePlayCommand("123", GameStatus.NOWPLAYING.ordinal(), 14, -1, "onur123")).expectNoEvents();
	}

	@Test
	public void testPlayNewGame() {
		fixture.given(new GameCreatedEvent("123", "Game123", -1, GameStatus.NEW.ordinal(), "test123"))
				.when(new GamePlayCommand("123", GameStatus.NOWPLAYING.ordinal(), 14, -1, "onur123")).expectNoEvents();
	}

	@Test
	public void testPlayPlayClosedGame() {
		fixture.given(new GamePlayedEvent("123", GameStatus.CLOSED.ordinal(), 56, "test123"))
				.when(new GamePlayCommand("123", GameStatus.NOWPLAYING.ordinal(), 56, -1, "onur123")).expectNoEvents();
	}

	@Test
	public void testPlayGameTurn() {
		fixture.given(new GamePlayedEvent("123", GameStatus.NOWPLAYING.ordinal(), 16, "test123"))
				.when(new GamePlayCommand("123", GameStatus.NOWPLAYING.ordinal(), 16, -1, "test123")).expectNoEvents();
	}

	@Test
	public void testPlayNumberUpdated() {
		fixture.given(new GamePlayedEvent("123", GameStatus.NOWPLAYING.ordinal(), 15, "test123"))
				.when(new GamePlayCommand("123", GameStatus.NOWPLAYING.ordinal(), 16, -1, "onur123")).expectNoEvents();
	}

	@Test
	public void testkCreatorStart() {
		fixture.given(new PlayerJoinedEvent("123", "onur123", GameStatus.GAMEESTABLISHED.ordinal(), "test123"))
				.when(new GameStartCommand("123", GameStatus.NOWPLAYING.ordinal(), -1, "onur123")).expectNoEvents();
	}

}
