package de.simonscholz.telegrambot;

import java.net.URI;

import org.springframework.web.client.RestTemplate;

public class DmiRest {

	public double findCityId(String city) {
		RestTemplate restTemplate = new RestTemplate();
		URI cityQueryURI = URI
				.create("http://www.dmi.dk/Data4DmiDk/getData?type=forecast&term="
						+ city);
		DmiModel[] dmiModels = restTemplate.getForObject(cityQueryURI,
				DmiModel[].class);
		double id = -1;
		if (dmiModels.length > 0) {
			id = dmiModels[0].getId();
		}

		return id;
	}
}
