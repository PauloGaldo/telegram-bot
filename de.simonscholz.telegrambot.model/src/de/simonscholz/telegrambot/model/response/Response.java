package de.simonscholz.telegrambot.model.response;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Response<T> {

	private boolean ok;

	private T result;

}
