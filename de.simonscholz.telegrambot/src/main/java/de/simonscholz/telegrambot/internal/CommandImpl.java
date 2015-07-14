package de.simonscholz.telegrambot.internal;

import de.simonscholz.telegrambot.Command;

public class CommandImpl implements Command {

	private String command;
	private String args;

	public CommandImpl() {
	}

	@Override
	public String getCommand() {
		return command;
	}

	@Override
	public String getArgs() {
		return args;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setArgs(String args) {
		this.args = args;
	}

}
