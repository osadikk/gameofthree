package com.gameofthree.interfaces.rest.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.gameofthree.infrastructure.entity.Game;
import com.gameofthree.infrastructure.repository.GameRepository;
import com.gameofthree.interfaces.rest.GameQueryApi;

import io.swagger.annotations.Api;

@RestController
@Api(value = "GameQueryService", description = "Game of three")
@CrossOrigin
public class GameQueryApiImpl implements GameQueryApi {

    @Autowired
    GameRepository repo;

    @Override
    public List<Game> findGames(String userId) {
	List<Game> games = repo.findGamesCustomByUserId(userId);

	return games;
    }

    @Override
    public Game findGame(String gameId) {
	Optional<Game> game = repo.findById(gameId);

	return game.get();
    }

}
