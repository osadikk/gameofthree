package com.gameofthree.infrastructure.exception;

import org.axonframework.commandhandling.CommandExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gameofthree.common.infrastructure.exception.GameException;
import com.gameofthree.common.infrastructure.util.MessageSourceUtil;
import com.gameofthree.interfaces.rest.impl.GameCommandApiImpl;
import com.gameofthree.interfaces.rest.model.MessageStatus;

@ControllerAdvice
public class RestExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GameCommandApiImpl.class);

	@Autowired
	private MessageSourceUtil messageSourceUtil;

	@ExceptionHandler(value = IllegalArgumentException.class)
	protected ResponseEntity<MessageStatus> illegalArgumentException(IllegalArgumentException ex) {
		LOG.warn(ex.getMessage());
		return new ResponseEntity<MessageStatus>(new MessageStatus(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = CommandExecutionException.class)
	protected ResponseEntity<MessageStatus> commandExecutionException(CommandExecutionException ex) {

		LOG.warn(messageSourceUtil.getKey("MSG_WRN02", ex.getMessage()));
		return new ResponseEntity<MessageStatus>(
				new MessageStatus(messageSourceUtil.getKey("MSG_WRN02", ex.getMessage())), HttpStatus.CONFLICT);

	}

	@ExceptionHandler(value = GameException.class)
	protected ResponseEntity<MessageStatus> gameException(GameException ex) {

		LOG.warn(ex.getMessage());
		return new ResponseEntity<MessageStatus>(new MessageStatus(ex.getMessage()), HttpStatus.FORBIDDEN);

	}

	public RestExceptionHandler() {
		super();
	}

	public RestExceptionHandler(MessageSourceUtil messageSourceUtil) {
		super();
		this.messageSourceUtil = messageSourceUtil;
	}

}