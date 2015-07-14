package de.simonscholz.telegrambot;

import java.io.IOException;
import java.net.MalformedURLException;

import de.simonscholz.telegrambot.model.Update;

public interface CommandHandler {
	void handleCommand(Update update, Command command) throws MalformedURLException, IOException;
}
