package de.simonscholz.telegrambot.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

	private String file_id;

	private PhotoSize thumb;

	private String file_name;

	private String mime_type;

	private Integer file_size;

}
