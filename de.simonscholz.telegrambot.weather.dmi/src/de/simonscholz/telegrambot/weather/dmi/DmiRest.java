package de.simonscholz.telegrambot.weather.dmi;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DmiRest {

	public int findCityId(String city) {
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

		return Double.valueOf(id).intValue();
	}
}
