package de.simonscholz.telegrambot.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

	private String phone_number;

	private String first_name;

	private String last_name;

	private String user_id;

}
