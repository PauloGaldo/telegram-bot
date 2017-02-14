package de.simonscholz.bot.telegram;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

@RestController
public class BotController {

	@Autowired
	private UpdateHandler handler;
	
	@Autowired
	private TelegramBot bot;
	
	@RequestMapping("/test")
	public void test() {

		GetUpdates getUpdates = new GetUpdates().limit(100).offset(0).timeout(0);
		GetUpdatesResponse getUpdatesResponse = bot.execute(getUpdates);

		List<Update> updates = getUpdatesResponse.updates();
		updates.forEach(this::bot);
	}

	@RequestMapping(value = "/bot", method = RequestMethod.POST)
	public void bot(@RequestBody Update update) {
		handler.handleUpdate(update);
	}
}
