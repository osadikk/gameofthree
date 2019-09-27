package com.gameofthree.domain.handler.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gameofthree.common.domain.event.game.GameCreatedEvent;
import com.gameofthree.common.domain.event.game.GamePlayedEvent;
import com.gameofthree.common.domain.event.game.GameStartedEvent;
import com.gameofthree.common.domain.event.game.PlayerJoinedEvent;
import com.gameofthree.infrastructure.entity.Game;
import com.gameofthree.infrastructure.repository.GameRepository;

@Component
@ProcessingGroup("Games")
public class GameEventHandler {

    @Autowired
    private GameRepository gameRepo;

    @EventHandler
    protected void on(GameCreatedEvent event) {

	List<String> userList = new ArrayList<String>();
	userList.add(event.getUserId());

	Game game = new Game(event.getId(), event.getName(), event.getNumber(), event.getGameStatus(),
		event.getUserId(), event.getUserId(), userList);

	gameRepo.insert(game);
    }

    @EventHandler
    protected void on(PlayerJoinedEvent event) {
	Optional<Game> game = gameRepo.findById(event.getId());
	game.get().setGameStatus(event.getGameStatus());
	List<String> playerList = game.get().getPlayerList();
	playerList.add(event.getUserId());
	game.get().setPlayerList(playerList);
	gameRepo.save(game.get());

    }

    @EventHandler
    protected void on(GameStartedEvent event) {
	Optional<Game> game = gameRepo.findById(event.getId());
	game.get().setGameStatus(event.getGameStatus());
	game.get().setUserTurn(event.getUserId());
	game.get().setNumber(event.getNumber());
	gameRepo.save(game.get());

    }

    @EventHandler
    protected void handle(GamePlayedEvent event) {
	Optional<Game> game = gameRepo.findById(event.getId());
	game.get().setGameStatus(event.getGameStatus());
	game.get().setUserTurn(event.getUserId());
	game.get().setNumber(event.getNumber());
	gameRepo.save(game.get());
    }
}