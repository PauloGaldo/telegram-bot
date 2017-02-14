package de.simonscholz.bot.telegram;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

import de.simonscholz.bot.telegram.translate.Translation;
import de.simonscholz.bot.telegram.translate.TranslationApi;
import io.reactivex.Single;
import okhttp3.ResponseBody;

@Component
public class UpdateHandlerImpl implements UpdateHandler {

	private Logger LOG = LoggerFactory.getLogger(UpdateHandlerImpl.class);

	@Autowired
	private DmiApi dmiApi;

	@Autowired
	private TranslationApi translationApi;

	@Autowired
	private TelegramBot telegramBot;

	@Override
	public void handleUpdate(Update update) {
		Message message = update.message();

		Long chatId = message.chat().id();
		String text = message.text();

		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + text);

		int indexOf = text.indexOf(" ");

		if (indexOf > -1) {
			String city = text.substring(indexOf);

			if (text.startsWith("/now")) {
				Single<List<DmiCity>> dmiCities = dmiApi.getDmiCities(city.trim());
				sendDmiPhoto(chatId, dmiCities, DmiApi.MODE_NOW);
			} else if (text.startsWith("/week")) {
				Single<List<DmiCity>> dmiCities = dmiApi.getDmiCities(city.trim());
				sendDmiPhoto(chatId, dmiCities, DmiApi.MODE_WEEK);
			} else if (text.startsWith("/de")) {
				Single<Translation> translation = translationApi.getTranslation(city, "de", "en");
				translation.subscribe(t -> {
					SendMessage sendTranslation = new SendMessage(chatId, t.getTranslationText());
					telegramBot.execute(sendTranslation);
				});
			} else if (text.startsWith("/en")) {
				Single<Translation> translation = translationApi.getTranslation(city, "en", "de");
				translation.subscribe(t -> {
					SendMessage sendTranslation = new SendMessage(chatId, t.getTranslationText());
					telegramBot.execute(sendTranslation);
				});
			}

		}

	}

	private void sendDmiPhoto(Long chatId, Single<List<DmiCity>> dmiCities, String mode) {
		dmiCities.subscribe(cities -> cities.stream().findFirst().ifPresent(dmiCity -> {
			Single<ResponseBody> weatherImage = dmiApi.getWeatherImage(String.valueOf(dmiCity.getId()), mode);
			weatherImage.subscribe(rb -> {
				SendPhoto sendPhoto = new SendPhoto(chatId, rb.bytes());
				telegramBot.execute(sendPhoto);
			});
		}));
	}

}
