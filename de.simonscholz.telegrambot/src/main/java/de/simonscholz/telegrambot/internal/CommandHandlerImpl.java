package de.simonscholz.telegrambot.internal;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.simonscholz.telegrambot.Command;
import de.simonscholz.telegrambot.CommandHandler;
import de.simonscholz.telegrambot.Commands;
import de.simonscholz.telegrambot.Methods;
import de.simonscholz.telegrambot.model.Update;
import de.simonscholz.telegrambot.weather.dmi.DmiCityModel;
import de.simonscholz.telegrambot.weather.dmi.DmiRest;
import de.simonscholz.telegrambot.weather.dmi.WeatherImageMode;

@Component
public class CommandHandlerImpl implements CommandHandler {

	@Autowired
	private Methods methods;

	@Autowired
	private DmiRest dmiRest;

	@Override
	public void handleCommand(Update update, Command command) throws MalformedURLException, IOException {
		if (Commands.HELP.equalsIgnoreCase(command.getCommand())) {
			handleHelpCommand(update);
			return;
		}

		handleWeatherCommand(update, command);
	}

	private void handleHelpCommand(Update update) {
		methods.sendMessage(update.getMessage().getChat().getId(),
				"You like the weather charts from the dmi.dk site?" + System.lineSeparator()
						+ "This bot can show you the weather forecast graphs for your desired city."
						+ System.lineSeparator() + "The following commands can be used:" + System.lineSeparator()
						+ System.lineSeparator() + "/now cityname - showing the two day weather"
						+ System.lineSeparator() + "/week cityname - showing furhter weather of the week");
	}

	private void handleWeatherCommand(Update update, Command command) throws MalformedURLException, IOException {
		WeatherImageMode imageType = WeatherImageMode.NOW;

		if (Commands.WEEK_WHEATHER.equalsIgnoreCase(command.getCommand())) {
			imageType = WeatherImageMode.WEEK;
		}

		DmiCityModel cityModel = dmiRest.findCityId(command.getArgs());
		URL weatherImageURL = dmiRest.getWeatherImageURL(cityModel, imageType);

		if (null == weatherImageURL) {
			methods.sendMessage(update.getMessage().getChat().getId(),
					"Please use /now + cityname or /week + cityname");
			return;
		}

		Path createTempFile = Files.createTempFile("", ".png", new FileAttribute[0]);
		File file = createTempFile.toFile();
		methods.sendPhoto(update.getMessage().getChat().getId(), weatherImageURL, file,
				"DMI weather in " + cityModel.getLabel());
		file.delete();
	}

}
