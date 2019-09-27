package com.gameofthreeassignmentweb.util.exception;

import java.io.IOException;
import java.util.List;
import com.gameofthreeassignmentweb.model.MessageStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseErrorHandler;

public class ResponseEntityErrorHandler implements ResponseErrorHandler {

	private List<HttpMessageConverter<?>> messageConverters;

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return hasError(response.getStatusCode());
	}

	protected boolean hasError(HttpStatus statusCode) {
		return (statusCode.is4xxClientError() || statusCode.is5xxServerError());
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		HttpMessageConverterExtractor<MessageStatus> errorMessageExtractor = new HttpMessageConverterExtractor(
				MessageStatus.class, messageConverters);
		MessageStatus errorObject = errorMessageExtractor.extractData(response);
		throw new ResponseEntityErrorException(
				ResponseEntity.status(response.getRawStatusCode()).headers(response.getHeaders()).body(errorObject));
	}

	public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
		this.messageConverters = messageConverters;
	}
}