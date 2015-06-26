package de.simonscholz.telegrambot.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sticker {

	private String file_id;

	private Integer width;

	private Integer height;

	private PhotoSize thumb;

	private Integer file_size;

}
