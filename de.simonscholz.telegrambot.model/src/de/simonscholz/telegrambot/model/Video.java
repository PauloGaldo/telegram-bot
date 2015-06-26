package de.simonscholz.telegrambot.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {

	private String file_id;

	private Integer width;

	private Integer height;

	private Integer duration;

	private PhotoSize thumb;

	private String mime_type;

	private Integer file_size;

	private String caption;
}
