package de.simonscholz.telegrambot.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoSize {

	private String file_id;

	private Integer width;

	private Integer height;

	private Integer file_size;

}
