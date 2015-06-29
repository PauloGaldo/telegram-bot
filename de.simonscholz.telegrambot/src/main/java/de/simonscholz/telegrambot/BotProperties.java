package de.simonscholz.telegrambot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("bot")
public class BotProperties {

	/**
	 * Pssst.
	 */
	private String apiKey = "apikey";

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String greeting) {
		this.apiKey = greeting;
	}
}