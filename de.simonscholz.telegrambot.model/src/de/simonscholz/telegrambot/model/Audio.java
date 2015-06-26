package de.simonscholz.telegrambot.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audio {

	private String file_id;

	private Integer duration;

	private String mime_type;

	private Integer file_size;
}
