package de.simonscholz.telegrambot;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.simonscholz.telegrambot.model.Message;
import de.simonscholz.telegrambot.model.ReplyKeyboardMarkup;
import de.simonscholz.telegrambot.model.Update;
import de.simonscholz.telegrambot.model.response.UpdateResponse;
import de.simonscholz.telegrambot.weather.dmi.DmiRest;

@RestController
public class BotController {

	@Autowired
	private Methods methods;

	@Autowired
	private DmiRest dmiRest;

	@RequestMapping(method = RequestMethod.POST, value = "/dmi_weather")
	public void dmiWeatherRequest(@RequestBody Update update)
			throws IOException {
		URL imageUrl = getImageUrl(update);

		if (null == imageUrl) {
			methods.sendMessage(update.getMessage().getChat().getId(),
					"Please use /now or /week + cityname");
			return;
		}

		Path createTempFile = Files.createTempFile("", ".png",
				new FileAttribute[0]);
		File file = createTempFile.toFile();
		methods.sendPhoto(3130440, imageUrl, file, "DMI Wetter in Hamburg");
		file.delete();
	}

	private URL getImageUrl(Update update) throws UnsupportedEncodingException,
			MalformedURLException {
		URL imageUrl = null;
		Message message = update.getMessage();
		String text = message.getText();
		String[] dmiCommandSplit = text.split(" ");
		if (dmiCommandSplit.length > 1) {
			int findCityId = dmiRest.findCityId(URLEncoder.encode(dmiCommandSplit[1],
					"UTF-8"));
			if (findCityId > -1) {
				if (Commands.TWO_DAY_WHEATHER.equalsIgnoreCase(dmiCommandSplit[0].trim())) {
					imageUrl = getImageUrl(findCityId, "dag1_2");
				} else if (Commands.WEEK_WHEATHER.equalsIgnoreCase(dmiCommandSplit[0]
						.trim())) {
					imageUrl = getImageUrl(findCityId, "dag3_9");
				}
			}
		}
		return imageUrl;
	}

	private URL getImageUrl(int cityId, String mode)
			throws MalformedURLException {
		return new URL("http://servlet.dmi.dk/byvejr/servlet/world_image?city="
				+ cityId + "&mode=" + mode);
	}

	@RequestMapping("/sampleKeyboard")
	public void sampleKeyboard() {

		UpdateResponse updates = methods.getUpdates();

		System.out.println(updates);

		String[][] buttons = new String[3][1];
		buttons[0][0] = "Eins";
		buttons[1][0] = "Zwei";
		buttons[2][0] = "Drei";

		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setKeyboard(buttons);

		MultiValueMap<String, Object> vars = new LinkedMultiValueMap<String, Object>();
		vars.add("chat_id", 3130440);
		vars.add("text", "Hallo vom Spring BotController");
		vars.add("reply_markup", buttons);

		methods.sendMessage(3130440, "Hallo vom Spring BotController", false,
				0, replyKeyboardMarkup);
	}
}
