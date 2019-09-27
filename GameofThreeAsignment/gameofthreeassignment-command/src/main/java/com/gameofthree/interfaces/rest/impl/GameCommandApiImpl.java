package com.gameofthree.interfaces.rest.impl;

import java.util.Random;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.gameofthree.common.infrastructure.util.GameStatus;
import com.gameofthree.common.infrastructure.util.MessageSourceUtil;
import com.gameofthree.domain.command.game.GameCreateCommand;
import com.gameofthree.domain.command.game.GamePlayCommand;
import com.gameofthree.domain.command.game.GameStartCommand;
import com.gameofthree.domain.command.game.PlayerJoinCommand;
import com.gameofthree.infrastructure.PlayUtil;
import com.gameofthree.interfaces.rest.GameCommandApi;
import com.gameofthree.interfaces.rest.model.GameRequest;
import com.gameofthree.interfaces.rest.model.MessageStatus;
import com.gameofthree.interfaces.rest.model.PlayGameRequest;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin
@Api(value = "GameCommandService", description = "Game of three")
public class GameCommandApiImpl implements GameCommandApi {

	private static final Logger LOG = LoggerFactory.getLogger(GameCommandApiImpl.class);

	private static final String TOPIC = "/topic/messages/";

	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private MessageSourceUtil messageSourceUtil;

	@Autowired
	private SimpMessagingTemplate messageTemplate;

	@Override
	public ResponseEntity<MessageStatus> createGame(String userId) {

		Assert.hasLength(userId, messageSourceUtil.getKey("MSG_WRN06"));
		String id = UUID.randomUUID().toString();

		commandGateway.sendAndWait(
				new GameCreateCommand(id, "Game" + new Random().nextInt(500), -1, GameStatus.NEW.ordinal(), userId));
		LOG.info(messageSourceUtil.getKey("MSG_INF02", userId, id));
		return new ResponseEntity<MessageStatus>(new MessageStatus(messageSourceUtil.getKey("MSG_INF02", userId, id)),
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<MessageStatus> joinGame(GameRequest gameRequest) {
		Assert.notNull(gameRequest, messageSourceUtil.getKey("MSG_WRN05"));
		Assert.hasLength(gameRequest.getUserId(), messageSourceUtil.getKey("MSG_WRN06"));
		Assert.hasLength(gameRequest.getGameId(), messageSourceUtil.getKey("MSG_WRN07"));
		LOG.info((messageSourceUtil.getKey("MSG_INF04", gameRequest.getUserId(), gameRequest.getGameId())));

		PlayerJoinCommand command = new PlayerJoinCommand(gameRequest.getGameId(), gameRequest.getUserId(),
				GameStatus.GAMEESTABLISHED.ordinal());

		commandGateway.sendAndWait(command);

		LOG.info(messageSourceUtil.getKey("MSG_INF05", gameRequest.getUserId(), gameRequest.getGameId()));
		messageTemplate.convertAndSend(TOPIC + gameRequest.getGameId(),
				messageSourceUtil.getKey("MSG_INF05", gameRequest.getUserId(), gameRequest.getGameId()));
		return new ResponseEntity<MessageStatus>(
				new MessageStatus(
						messageSourceUtil.getKey("MSG_INF05", gameRequest.getUserId(), gameRequest.getGameId())),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<MessageStatus> startGame(GameRequest gameRequest) {
		Assert.notNull(gameRequest, messageSourceUtil.getKey("MSG_WRN05"));
		Assert.hasLength(gameRequest.getUserId(), messageSourceUtil.getKey("MSG_WRN06"));
		Assert.hasLength(gameRequest.getGameId(), messageSourceUtil.getKey("MSG_WRN07"));

		LOG.info((messageSourceUtil.getKey("MSG_INF06", gameRequest.getUserId(), gameRequest.getGameId())));
		commandGateway.sendAndWait(new GameStartCommand(gameRequest.getGameId(), GameStatus.NOWPLAYING.ordinal(),
				new Random().nextInt(100), gameRequest.getUserId()));
		LOG.info(messageSourceUtil.getKey("MSG_INF07", gameRequest.getUserId(), gameRequest.getGameId()));

		messageTemplate.convertAndSend(TOPIC + gameRequest.getGameId(),
				messageSourceUtil.getKey("MSG_INF07", gameRequest.getUserId(), gameRequest.getGameId()));

		return new ResponseEntity<MessageStatus>(
				new MessageStatus(
						messageSourceUtil.getKey("MSG_INF07", gameRequest.getUserId(), gameRequest.getGameId())),
				HttpStatus.OK);

	}

	@Override
	public ResponseEntity<MessageStatus> playGame(PlayGameRequest playGameRequest) {

		Assert.notNull(playGameRequest, messageSourceUtil.getKey("MSG_WRN16"));
		Assert.hasLength(playGameRequest.getUserId(), messageSourceUtil.getKey("MSG_WRN06"));
		Assert.hasLength(playGameRequest.getGameId(), messageSourceUtil.getKey("MSG_WRN07"));
		Assert.hasLength(playGameRequest.getAdjustment(), messageSourceUtil.getKey("MSG_WRN15"));
		Assert.hasLength(playGameRequest.getNumber(), messageSourceUtil.getKey("MSG_WRN14"));

		int adj = Integer.parseInt(playGameRequest.getAdjustment());
		int number = Integer.parseInt(playGameRequest.getNumber());

		LOG.info(messageSourceUtil.getKey("MSG_INF08", playGameRequest.getUserId(), playGameRequest.getGameId()));
		commandGateway.sendAndWait(new GamePlayCommand(playGameRequest.getGameId(), GameStatus.NOWPLAYING.ordinal(),
				number, adj, playGameRequest.getUserId()));

		if (!PlayUtil.isFinished(adj, number)) {
			LOG.info(messageSourceUtil.getKey("MSG_INF09", playGameRequest.getUserId(), playGameRequest.getGameId()));

			messageTemplate.convertAndSend(TOPIC + playGameRequest.getGameId(),
					messageSourceUtil.getKey("MSG_INF09", playGameRequest.getUserId(), playGameRequest.getGameId()));

			return new ResponseEntity<MessageStatus>(new MessageStatus(
					messageSourceUtil.getKey("MSG_INF09", playGameRequest.getUserId(), playGameRequest.getGameId())),
					HttpStatus.OK);
		} else {
			LOG.info(messageSourceUtil.getKey("MSG_INF10", playGameRequest.getUserId(), playGameRequest.getGameId()));

			messageTemplate.convertAndSend(TOPIC + playGameRequest.getGameId(),
					messageSourceUtil.getKey("MSG_INF10", playGameRequest.getUserId(), playGameRequest.getGameId()));

			return new ResponseEntity<MessageStatus>(new MessageStatus(
					messageSourceUtil.getKey("MSG_INF10", playGameRequest.getUserId(), playGameRequest.getGameId())),
					HttpStatus.OK);
		}

	}

	public GameCommandApiImpl() {
		super();
	}

	public GameCommandApiImpl(CommandGateway commandGateway, MessageSourceUtil messageSourceUtil,
			SimpMessagingTemplate messageTemplate) {
		super();
		this.commandGateway = commandGateway;
		this.messageSourceUtil = messageSourceUtil;
		this.messageTemplate = messageTemplate;
	}

}
