package de.simonscholz.bot.telegram.weather;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DmiCity {
	private int id;

	private double longitude;

	private String label;

	private double latitude;

	private String country_code;

	private String country;
}