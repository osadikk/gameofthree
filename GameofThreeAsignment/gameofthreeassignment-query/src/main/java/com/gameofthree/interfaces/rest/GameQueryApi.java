package com.gameofthree.interfaces.rest;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gameofthree.infrastructure.entity.Game;

import io.swagger.annotations.ApiOperation;

public interface GameQueryApi {

    @RequestMapping(value = "/getGames/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "GetGames")
    public List<Game> findGames(@PathVariable String userId);

    @RequestMapping(value = "/getGame/{gameId}", method = RequestMethod.GET)
    @ApiOperation(value = "GetGame")
    public Game findGame(@PathVariable String gameId);

}
