package de.simonscholz.telegrambot.weather.dmi;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DmiCityModel {
	private double id;

	private double longitude;

	private String label;

	private double latitude;

	private String country_code;

	private String country;
}
