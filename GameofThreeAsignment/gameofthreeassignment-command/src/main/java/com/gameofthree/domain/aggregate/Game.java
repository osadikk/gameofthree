package com.gameofthree.domain.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import com.gameofthree.common.domain.event.game.GameCreatedEvent;
import com.gameofthree.common.domain.event.game.GamePlayedEvent;
import com.gameofthree.common.domain.event.game.GameStartedEvent;
import com.gameofthree.common.domain.event.game.PlayerJoinedEvent;
import com.gameofthree.common.infrastructure.exception.GameException;
import com.gameofthree.common.infrastructure.util.GameStatus;
import com.gameofthree.common.infrastructure.util.MessageSourceUtil;
import com.gameofthree.domain.command.game.GameCreateCommand;
import com.gameofthree.domain.command.game.GamePlayCommand;
import com.gameofthree.domain.command.game.GameStartCommand;
import com.gameofthree.domain.command.game.PlayerJoinCommand;
import com.gameofthree.infrastructure.PlayUtil;

@Aggregate
public class Game {

	@AggregateIdentifier
	private String id;

	private String name;

	private int number;

	private int gameStatus;

	private String userTurn;

	private String creator;

	private List<String> playerList = new ArrayList<String>();;

	@Autowired
	private MessageSourceUtil messageSourceUtil;

	public Game() {
	}

	@CommandHandler
	public Game(GameCreateCommand command) {
		apply(new GameCreatedEvent(command.getId(), command.getName(), command.getNumber(), command.getGameStatus(),
				command.getUserId()));
	}

	@EventSourcingHandler
	protected void handle(GameCreatedEvent event) {
		this.id = event.getId();
		this.playerList.add(event.getUserId());
		this.id = event.getId();
		this.gameStatus = event.getGameStatus();
		this.name = event.getName();
		this.creator = event.getUserId();

	}

	@CommandHandler
	protected void on(GameStartCommand command) {

		if (!this.creator.equals(command.getUserId()))
			throw new GameException(messageSourceUtil.getKey("MSG_WRN10", command.getUserId(), command.getId()));

		GameStatus gameStatus = GameStatus.valueOf(this.gameStatus).get();

		switch (gameStatus) {

		case GAMEESTABLISHED:

			apply(new GameStartedEvent(command.getId(), command.getGameStatus(), command.getNumber(),
					command.getUserId()));
			break;
		case NEW:
			throw new GameException(messageSourceUtil.getKey("MSG_WRN13", command.getId()));
		case NOWPLAYING:
			throw new GameException(messageSourceUtil.getKey("MSG_WRN14", command.getId()));
		default:
			throw new GameException(messageSourceUtil.getKey("MSG_WRN03", command.getId()));
		}

	}

	@EventSourcingHandler
	protected void handle(GameStartedEvent event) {
		this.gameStatus = event.getGameStatus();
		this.number = event.getNumber();
		this.userTurn = event.getUserId();
		this.id = event.getId();
	}

	@CommandHandler
	protected void on(PlayerJoinCommand command) {

		GameStatus gameStatus = GameStatus.valueOf(this.gameStatus).get();

		switch (gameStatus) {

		case NEW:
			apply(new PlayerJoinedEvent(command.getId(), command.getUserId(), command.getGameStatus(), this.creator));
			break;
		default:
			throw new GameException(messageSourceUtil.getKey("MSG_WRN03", command.getId()));
		}

	}

	@EventSourcingHandler
	protected void handle(PlayerJoinedEvent event) {
		this.gameStatus = event.getGameStatus();
		this.playerList.add(event.getUserId());
		this.id = event.getId();
		this.creator = event.getCreator();
	}

	@CommandHandler
	protected void on(GamePlayCommand command) {

		GameStatus gameStatus = GameStatus.valueOf(this.gameStatus).get();

		switch (gameStatus) {

		case NOWPLAYING:
			if (this.userTurn.equals(command.getUserId())) {
				throw new GameException(messageSourceUtil.getKey("MSG_WRN11", command.getUserId(), command.getId()));
			} else if (this.number != command.getNumber()) {
				throw new GameException(messageSourceUtil.getKey("MSG_WRN18", command.getUserId(), command.getNumber(),
						command.getId()));
			}
			boolean isDivisible = PlayUtil.isDivisible(command.getAdj(), command.getNumber());
			if (isDivisible) {
				int divined = PlayUtil.getDivined(command.getAdj(), command.getNumber());
				if (divined == 1) {
					apply(new GamePlayedEvent(command.getId(), GameStatus.CLOSED.ordinal(), divined,
							command.getUserId()));
				} else {
					apply(new GamePlayedEvent(command.getId(), GameStatus.NOWPLAYING.ordinal(), divined,
							command.getUserId()));
				}

			} else {
				throw new GameException(
						messageSourceUtil.getKey("MSG_WRN12", command.getAdj() + command.getNumber(), command.getId()));
			}
			break;
		case NEW:
			throw new GameException(messageSourceUtil.getKey("MSG_WRN13", command.getId()));
		case GAMEESTABLISHED:
			throw new GameException(messageSourceUtil.getKey("MSG_WRN17", command.getId()));
		default:
			throw new GameException(messageSourceUtil.getKey("MSG_WRN03", command.getId()));
		}

	}

	@EventSourcingHandler
	protected void handle(GamePlayedEvent event) {
		this.gameStatus = event.getGameStatus();
		this.number = event.getNumber();
		this.userTurn = event.getUserId();
		this.id = event.getId();

	}

}
