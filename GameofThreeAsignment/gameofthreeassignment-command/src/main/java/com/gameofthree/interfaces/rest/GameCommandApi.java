package com.gameofthree.interfaces.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gameofthree.interfaces.rest.model.GameRequest;
import com.gameofthree.interfaces.rest.model.MessageStatus;
import com.gameofthree.interfaces.rest.model.PlayGameRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

public interface GameCommandApi {

	@RequestMapping(value = "/createGame/{userId}", method = RequestMethod.POST)
	@ApiOperation(value = "CreateGame")
	public @ResponseBody ResponseEntity<MessageStatus> createGame(@PathVariable String userId);

	@PutMapping(value = "/joinGame", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "JoinGame")
	public @ResponseBody ResponseEntity<MessageStatus> joinGame(@RequestBody GameRequest gameRequest);

	@RequestMapping(value = "/startGame", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	@ApiOperation(value = "StartGame")
	public @ResponseBody ResponseEntity<MessageStatus> startGame(@RequestBody GameRequest startGameRequest);

	@PutMapping(value = "/playGame", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "PlayGame")
	public @ResponseBody ResponseEntity<MessageStatus> playGame(@RequestBody PlayGameRequest playGameRequest);
}
