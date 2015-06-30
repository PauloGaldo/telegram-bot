package de.simonscholz.telegrambot;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import de.simonscholz.telegrambot.model.Message;
import de.simonscholz.telegrambot.model.ReplyKeyboardMarkup;
import de.simonscholz.telegrambot.model.Update;
import de.simonscholz.telegrambot.model.response.MessageResponse;
import de.simonscholz.telegrambot.model.response.UpdateResponse;
import de.simonscholz.telegrambot.wheather.dmi.ImageSearch;

@RestController
public class BotController {

	@Autowired
	BotProperties props;

	@RequestMapping("/send_photo")
	public MessageResponse sendPhoto(UriComponentsBuilder ucb)
			throws IOException {
		Methods methods = new Methods(props.getApiKey());
		ImageSearch imageSearch = new ImageSearch();

		DmiRest dmiRest = new DmiRest();
		UpdateResponse updates = methods.getUpdates();

		URL imageUrl = null;

		if (null == imageUrl) {
			Update[] result = updates.getResult();
			if (result.length > 0) {
				for (Update update : result) {
					Message message = update.getMessage();
					String text = message.getText();
					String[] split = text.split(" ");
					if (split.length > 1) {
						double findCityId = dmiRest.findCityId(URLEncoder
								.encode(split[1], "UTF-8"));
						if (findCityId > -1) {
							if ("/now".equalsIgnoreCase(split[0].trim())) {
								String twoDayURL = "http://servlet.dmi.dk/byvejr/servlet/world_image?city="
										+ Double.valueOf(findCityId).intValue()
										+ "&mode=dag1_2";
								imageUrl = new URL(twoDayURL);
							} else if ("/week"
									.equalsIgnoreCase(split[0].trim())) {
								String weekURL = "http://servlet.dmi.dk/byvejr/servlet/world_image?city="
										+ Double.valueOf(findCityId).intValue()
										+ "&mode=dag3_9";
								imageUrl = new URL(weekURL);
							}
						}
					}
				}
			}
		}

		if (null == imageUrl) {
			Message message = new Message();
			message.setText("command not found");
			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setResult(message);
			return messageResponse;
		}

		Path createTempFile = Files.createTempFile("", ".png",
				new FileAttribute[0]);
		File file = createTempFile.toFile();
		MessageResponse sendPhoto = methods.sendPhoto(3130440, imageUrl, file,
				"DMI Wetter in Hamburg");
		file.delete();

		return sendPhoto;
	}

	@RequestMapping("/send_photo_deprecated")
	public String sendPhotoDeprecated(UriComponentsBuilder ucb)
			throws IOException {
		String apiUrl = "https://api.telegram.org/bot" + props.getApiKey()
				+ "/";

		RestTemplate restTemplate = new RestTemplate();

		URL url = getTwoDaysForecastImageUrl("Hamburg");

		File imageFile = new File("/home/simon/Documents/dmifiles/", "test.png");
		Files.copy(url.openStream(), imageFile.toPath(),
				StandardCopyOption.REPLACE_EXISTING);

		Resource image = new FileSystemResource(imageFile);

		MultiValueMap<String, Object> vars = new LinkedMultiValueMap<String, Object>();
		vars.add("chat_id", 3130440);
		vars.add("photo", image);
		vars.add("caption", "DMI Wetter in Hamburg");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<Object> request = new HttpEntity<Object>(vars, headers);

		URI sendPhoto = URI.create(apiUrl + "sendPhoto");

		MessageResponse httpResponse = restTemplate.postForObject(sendPhoto,
				request, MessageResponse.class);

		// MessageResponse postForObject =
		// restTemplate.postForObject(getUpdates,
		// vars, MessageResponse.class);

		return "check";
	}

	@RequestMapping("/dmi_weather")
	public void dmiWeatherRequest() {
		String apiUrl = "https://api.telegram.org/bot" + props.getApiKey()
				+ "/";

		RestTemplate restTemplate = new RestTemplate();

		URI getUpdates = URI.create(apiUrl + "getUpdates");

		UpdateResponse forObject = restTemplate.getForObject(getUpdates,
				UpdateResponse.class);
		System.out.println(forObject);

		URI getMeUri = URI.create("https://api.telegram.org/bot"
				+ props.getApiKey() + "/sendMessage");

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

		MessageResponse postForObject = restTemplate.postForObject(getMeUri,
				vars, MessageResponse.class);
	}

	public URL getTwoDaysForecastImageUrl(String city) throws IOException {

		Document doc = navigateToCity(city);

		Element twoDayImageElement = doc.getElementById("w_days_two_forecast");
		String twoDayImageSrcAttrValue = twoDayImageElement.attr("src");

		return new URL(twoDayImageSrcAttrValue);
	}

	private Document navigateToCity(String city) throws IOException {
		Document doc = Jsoup
				.connect(
						"http://www.dmi.dk/vejr/til-lands/byvejr/by/vis/DE/2911298/Hamburg,%20Tyskland/")
				.get();

		return doc;
	}
}