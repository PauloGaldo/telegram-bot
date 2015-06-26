package de.simonscholz.telegrambot.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

	private int message_id;

	private User from;

	private int date;

	private User chat;

	private User forward_from;

	private Integer forward_date;

	private Message reply_to_message;

	private String text;

	private Audio audio;

	private Document document;

	private PhotoSize[] photo;

	private Sticker sticker;

	private Video video;

	private Contact contact;

	private Location location;

	private User new_chat_participant;

	private User left_chat_participant;

	private String new_chat_title;

	private PhotoSize[] new_chat_photo;

	private Boolean delete_chat_photo;

	private Boolean group_chat_created;
}
