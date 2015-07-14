package de.simonscholz.telegrambot.weather.dmi;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DmiRest {

	public DmiCityModel findCityId(String city) {
		if (null == city) {
			return null;
		}

		RestTemplate restTemplate = new RestTemplate();
		URI cityQueryURI = URI.create("http://www.dmi.dk/Data4DmiDk/getData?type=forecast&term=" + city);
		DmiCityModel[] dmiModels = restTemplate.getForObject(cityQueryURI, DmiCityModel[].class);
		if (dmiModels.length > 0) {
			return dmiModels[0];
		}

		return null;
	}

	public URL getWeatherImageURL(DmiCityModel cityModel, WeatherImageMode imageType) throws MalformedURLException {
		if (cityModel != null && cityModel.getId() != -1) {
			String mode = "dag1_2";
			if (WeatherImageMode.WEEK.equals(imageType)) {
				mode = "dag3_9";
			}
			return new URL("http://servlet.dmi.dk/byvejr/servlet/world_image?city=" + ((int) cityModel.getId())
					+ "&mode=" + mode);
		}
		return null;
	}
}
