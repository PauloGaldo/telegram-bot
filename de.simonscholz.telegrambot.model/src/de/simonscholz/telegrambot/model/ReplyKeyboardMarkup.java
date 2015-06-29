package de.simonscholz.telegrambot.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyKeyboardMarkup {

	private String[][] keyboard;

	private boolean resize_keyboard;

	private boolean one_time_keyboard;

	private boolean selective;
}
