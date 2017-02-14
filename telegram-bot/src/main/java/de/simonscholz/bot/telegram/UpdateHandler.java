package de.simonscholz.bot.telegram;

import com.pengrad.telegrambot.model.Update;

public interface UpdateHandler {

	void handleUpdate(Update update);
}
