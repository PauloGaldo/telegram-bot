package de.simonscholz.bot.telegram;

import org.springframework.context.annotation.Bean;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;

import de.simonscholz.bot.telegram.translate.TranslationApi;
import de.simonscholz.bot.telegram.weather.DmiApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@org.springframework.context.annotation.Configuration
public class Configuration {

	private static final String BASE_URL = "http://www.dmi.dk/Data4DmiDk/";

	@Bean
	public Retrofit retrofit() {
		return new Retrofit.Builder().addConverterFactory(JacksonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(BASE_URL).build();
	}

	@Bean
	public DmiApi dmiApi(Retrofit retrofit) {
		return retrofit.create(DmiApi.class);
	}

	@Bean
	public TranslationApi translationApi(Retrofit retrofit) {
		return retrofit.create(TranslationApi.class);
	}

	@Bean
	public TelegramBot telegramBbot(BotProperties botProperties) {
		return TelegramBotAdapter.build(botProperties.getApiKey());
	}

}
