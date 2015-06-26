package de.simonscholz.telegrambot;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BotController {

	@Autowired
	BotProperties props;

	@RequestMapping("/dmi_weather")
	public String hello(@RequestParam String name) {

		RestTemplate restTemplate = new RestTemplate();

		URI getMeUri = URI
				.create("https://api.telegram.org/bot97307424:AAEI0VLtRVIv6QjPC_bi9Oj1eJSMkYRxBO4/sendMessage");

		MultiValueMap<String, Object> vars = new LinkedMultiValueMap<String, Object>();
		vars.add("chat_id", 3130440);
		vars.add("text", "Hallo vom Spring BotController");

		String postForObject = restTemplate.postForObject(getMeUri, vars,
				String.class);
		// MessageResponse postForObject = restTemplate.postForObject(getMeUri,
		// vars, MessageResponse.class);

		return props.getGreeting() + name;
	}
}