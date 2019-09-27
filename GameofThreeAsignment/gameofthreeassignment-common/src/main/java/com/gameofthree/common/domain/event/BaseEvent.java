package com.gameofthree.common.domain.event;

public class BaseEvent<T> {

	private T id;
	public BaseEvent(){}
	public BaseEvent(T id) {
		this.id = id;

	}
	public T getId() {
		return id;
	}

}