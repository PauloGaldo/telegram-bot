package de.simonscholz.bot.telegram.translate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Translation {
	private String from;
	private String to;
	private String text;
	private String translationText;
}
