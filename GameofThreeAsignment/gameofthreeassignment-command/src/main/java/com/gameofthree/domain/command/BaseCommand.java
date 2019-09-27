package com.gameofthree.domain.command;

import java.io.Serializable;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class BaseCommand<T> {

	@TargetAggregateIdentifier
	private T id;

	public BaseCommand() {
	}

	public BaseCommand(T id) {
		this.id = id;

	}

	public T getId() {
		return id;
	}

}
