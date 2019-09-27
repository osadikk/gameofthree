package com.gameofthreeassignmentweb.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gameofthreeassignmentweb.model.Game;
import com.gameofthreeassignmentweb.model.MessageStatus;
import com.gameofthreeassignmentweb.model.dto.GameRequest;
import com.gameofthreeassignmentweb.util.GameStatus;
import com.gameofthreeassignmentweb.util.exception.ResponseEntityErrorException;

@Controller
public class WebController {

	@Autowired
	RestTemplate restTemplate;

	private static final String commandBaseUri = "http://gameofthreeoperations-command/";
	private static final String queryBaseUri = "http://gameofthreeoperations-query/";
	HttpHeaders headers;

	@GetMapping("/")
	public String hello() {
		return "Index";
	}

	public WebController() {
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}

	@RequestMapping("/games")
	public String games(@RequestParam String userId, Model model, RedirectAttributes redir) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		List<Game> gameList = null;
		try {
			gameList = restTemplate.getForObject(queryBaseUri + "getGames/{userId}", List.class, params);
		} catch (Exception ex) {
			redir.addFlashAttribute("message", ex.getMessage());
			return "redirect:/";
		}
		model.addAttribute("games", gameList);
		model.addAttribute("userId", userId);
		return "Game";
	}

	@RequestMapping(value = "/join")
	public String join(@RequestParam String gameId, @RequestParam("userId") String userId,
			@RequestParam("creator") String creator, @RequestParam("status") int status, @RequestParam int number,
			Model model, RedirectAttributes redir) {

		if (!creator.equals(userId) && status == GameStatus.NEW.ordinal()) {

			GameRequest gameRequest = new GameRequest(gameId, userId);

			try {
				HttpEntity<GameRequest> requestJoin = new HttpEntity<>(gameRequest, headers);
				restTemplate.exchange(commandBaseUri + "joinGame/", HttpMethod.PUT, requestJoin, MessageStatus.class);
				model.addAttribute("gameId", gameId);
				model.addAttribute("userId", userId);
				model.addAttribute("status", status);
				model.addAttribute("creator", creator);
				model.addAttribute("number", number);
				return "Play";
			} catch (ResponseEntityErrorException ex) {
				redir.addFlashAttribute("message", ex.getErrorResponse().getBody().getMessage());
				return "redirect:/games?userId=" + userId;
			}

		}
		model.addAttribute("userId", userId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("status", status);
		model.addAttribute("creator", creator);
		model.addAttribute("number", number);
		model.addAttribute("message", userId + " joined game:" + gameId);
		return "Play";

	}

	@RequestMapping(value = "/createGame")
	public String createGame(@RequestParam String userId, RedirectAttributes redir) {
		try {
			ResponseEntity<MessageStatus> response = restTemplate.exchange(commandBaseUri + "createGame/" + userId,
					HttpMethod.POST, null, MessageStatus.class);
			redir.addFlashAttribute("message", response.getBody().getMessage());
			return "redirect:/games?userId=" + userId;
		} catch (ResponseEntityErrorException ex) {
			redir.addFlashAttribute("message", ex.getErrorResponse().getBody().getMessage());
			return "redirect:/games?userId=" + userId;
		}
	}

}
