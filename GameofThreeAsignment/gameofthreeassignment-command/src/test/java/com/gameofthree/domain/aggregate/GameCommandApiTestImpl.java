package com.gameofthree.domain.aggregate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.gameofthree.common.infrastructure.exception.GameException;
import com.gameofthree.common.infrastructure.util.MessageSourceUtil;
import com.gameofthree.interfaces.rest.GameCommandApi;
import com.gameofthree.interfaces.rest.impl.GameCommandApiImpl;
import com.gameofthree.interfaces.rest.model.GameRequest;
import com.gameofthree.interfaces.rest.model.MessageStatus;
import com.gameofthree.interfaces.rest.model.PlayGameRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameCommandApiTestImpl {

	@Inject
	ApplicationContext applicationContext;

	GameCommandApi commandController;

	@Mock
	CommandGateway gateway;

	@Mock
	private MessageSourceUtil messageSourceUtil;

	@Mock
	private SimpMessagingTemplate messageTemplate;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		commandController = new GameCommandApiImpl(gateway, messageSourceUtil, messageTemplate);

	}

	@Test
	public void contextLoads() {

		assertNotNull("application context loaded", applicationContext);
	}

	@Test
	public void createGameGoodRequestParams() {

		when(gateway.sendAndWait(any())).thenReturn(null);
		when(messageSourceUtil.getKey(any(String.class), any(String.class), any(String.class)))
				.thenReturn("createGameGoodRequest");
		when(messageSourceUtil.getKey(any(String.class))).thenReturn("createGameGoodRequest");
		doNothing().when(messageTemplate).convertAndSend(any(String.class), any(String.class));

		ResponseEntity<MessageStatus> messageStatus = commandController.createGame("testOnur");

		verify(gateway).sendAndWait(any());
		verify(messageSourceUtil, times(2))
				.getKey(messageSourceUtil.getKey(any(String.class), any(String.class), any(String.class)));
		verify(messageSourceUtil).getKey(messageSourceUtil.getKey(any(String.class)));

		assertTrue(messageStatus.getStatusCode() == HttpStatus.CREATED);
	}

	@Test
	public void createGameNullRequestParams() throws Exception {

		when(messageSourceUtil.getKey((any()))).thenReturn("createGameNullRequest");
		thrown.expect(IllegalArgumentException.class);
		commandController.joinGame(null);

		verify(messageSourceUtil).getKey(messageSourceUtil.getKey(any(String.class)));

	}

	@Test
	public void testFailedCreateWithCommandExecutionException() {

		when(gateway.sendAndWait(any())).thenThrow(CommandExecutionException.class);

		thrown.expect(CommandExecutionException.class);
		commandController.createGame("onur123");

		verify(gateway).sendAndWait(any());

	}

	@Test
	public void testFailedCreateWithGameException() {

		when(gateway.sendAndWait(any())).thenThrow(GameException.class);

		thrown.expect(GameException.class);
		commandController.createGame("onur123");

		verify(gateway).sendAndWait(any());

	}

	@Test
	public void joinGameGoodRequestParams() {

		when(gateway.sendAndWait(any())).thenReturn(null);
		when(messageSourceUtil.getKey((any()))).thenReturn("joinGameGoodRequest");
		when(messageSourceUtil.getKey(any(String.class), any(String.class), any(String.class)))
				.thenReturn("joinGameGoodRequest");
		doNothing().when(messageTemplate).convertAndSend(any(String.class), any(String.class));

		GameRequest gameRequest = new GameRequest("test01", "onur123");
		ResponseEntity<MessageStatus> messageStatus = commandController.joinGame(gameRequest);

		verify(gateway).sendAndWait(any());
		verify(messageSourceUtil, times(3)).getKey((any()));
		verify(messageSourceUtil, times(4)).getKey(any(String.class), any(String.class), any(String.class));
		verify(messageTemplate).convertAndSend(any(String.class), any(String.class));

		assertTrue(messageStatus.getStatusCode() == HttpStatus.OK);
	}

	@Test
	public void playGameGoodRequestParams() {

		when(gateway.sendAndWait(any())).thenReturn(null);
		when(messageSourceUtil.getKey((any()))).thenReturn("playGameGoodRequest");
		when(messageSourceUtil.getKey(any(String.class), any(String.class), any(String.class)))
				.thenReturn("playGameGoodRequest");
		doNothing().when(messageTemplate).convertAndSend(any(String.class), any(String.class));

		PlayGameRequest playGameRequest = new PlayGameRequest("test01", "onur123", "-1", "36");
		ResponseEntity<MessageStatus> messageStatus = commandController.playGame(playGameRequest);

		verify(gateway).sendAndWait(any());
		verify(messageSourceUtil, times(5)).getKey((any()));
		verify(messageSourceUtil, times(4)).getKey(any(String.class), any(String.class), any(String.class));
		verify(messageTemplate).convertAndSend(any(String.class), any(String.class));

		assertTrue(messageStatus.getStatusCode() == HttpStatus.OK);
	}

	@Test
	public void startGameGoodRequestParams() {

		when(gateway.sendAndWait(any())).thenReturn(null);
		when(messageSourceUtil.getKey((any()))).thenReturn("startGameGoodRequest");
		when(messageSourceUtil.getKey(any(String.class), any(String.class), any(String.class)))
				.thenReturn("startGameGoodRequest");
		doNothing().when(messageTemplate).convertAndSend(any(String.class), any(String.class));

		GameRequest startGameRequest = new GameRequest("test01", "onur123");
		ResponseEntity<MessageStatus> messageStatus = commandController.startGame(startGameRequest);

		verify(gateway).sendAndWait(any());
		verify(messageSourceUtil, times(3)).getKey((any()));
		verify(messageSourceUtil, times(4)).getKey(any(String.class), any(String.class), any(String.class));
		verify(messageTemplate).convertAndSend(any(String.class), any(String.class));

		assertTrue(messageStatus.getStatusCode() == HttpStatus.OK);
	}

}
