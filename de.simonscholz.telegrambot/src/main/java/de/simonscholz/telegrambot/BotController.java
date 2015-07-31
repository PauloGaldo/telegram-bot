package de.simonscholz.telegrambot;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.simonscholz.telegrambot.internal.CommandImpl;
import de.simonscholz.telegrambot.model.Message;
import de.simonscholz.telegrambot.model.Update;
import de.simonscholz.telegrambot.weather.dmi.DmiCityModel;
import de.simonscholz.telegrambot.weather.dmi.DmiRest;

@RestController
public class BotController {

	@Autowired
	private CommandHandler commandHandler;

	@Autowired
	private DmiRest dmiRest;

	@RequestMapping("/test")
	public void test() throws IOException {
		DmiCityModel findCityId = dmiRest.findCityId("Hamburg");
		System.out.println(findCityId.getLabel());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/dmi_weather")
	public void dmiWeatherRequest(@RequestBody Update update) throws IOException {

		Command command = getCommand(update);
		commandHandler.handleCommand(update, command);
	}

	private Command getCommand(Update update) {
		Message message = update.getMessage();
		String text = message.getText();
		CommandImpl command = new CommandImpl();
		int commandIndex = text.indexOf(" ");
		if (text.length() > commandIndex) {
			command.setCommand(text.substring(0, commandIndex));
			command.setArgs(text.substring(commandIndex + 1));
		} else {
			command.setCommand(text.trim());
		}

		return command;
	}

	@RequestMapping("/sampleKeyboard")
	public void sampleKeyboard() {
		//
		// UpdateResponse updates = methods.getUpdates();
		//
		// System.out.println(updates);
		//
		// String[][] buttons = new String[3][1];
		// buttons[0][0] = "Eins";
		// buttons[1][0] = "Zwei";
		// buttons[2][0] = "Drei";
		//
		// ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		// replyKeyboardMarkup.setKeyboard(buttons);
		//
		// MultiValueMap<String, Object> vars = new LinkedMultiValueMap<String,
		// Object>();
		// vars.add("chat_id", 3130440);
		// vars.add("text", "Hallo vom Spring BotController");
		// vars.add("reply_markup", buttons);

		// methods.sendMessage(3130440, "Hallo vom Spring BotController", false,
		// 0, replyKeyboardMarkup);
	}
}
