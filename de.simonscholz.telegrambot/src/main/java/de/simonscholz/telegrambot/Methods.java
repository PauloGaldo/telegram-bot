package de.simonscholz.telegrambot;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import de.simonscholz.telegrambot.model.ForceReply;
import de.simonscholz.telegrambot.model.ReplyKeyboardHide;
import de.simonscholz.telegrambot.model.ReplyKeyboardMarkup;
import de.simonscholz.telegrambot.model.response.MessageResponse;
import de.simonscholz.telegrambot.model.response.UpdateResponse;
import de.simonscholz.telegrambot.model.response.UserResponse;

public class Methods {

	private String apiBaseUrl;
	private RestTemplate restTemplate;

	public Methods(String apiKey) {
		apiBaseUrl = "https://api.telegram.org/bot" + apiKey + "/";
	}

	protected RestTemplate getRestTemplate() {
		if (null == restTemplate) {
			restTemplate = new RestTemplate();
		}
		return restTemplate;
	}

	protected URI getSendMessageURI() {
		return URI.create(apiBaseUrl + "sendMessage");
	}

	protected URI getSendPhotoURI() {
		return URI.create(apiBaseUrl + "sendPhoto");
	}

	protected URI getUpdatesURI() {
		return URI.create(apiBaseUrl + "getUpdates");
	}

	public UserResponse getMe() {
		return getRestTemplate().getForObject(URI.create(apiBaseUrl + "getMe"),
				UserResponse.class);
	}

	public UpdateResponse getUpdates() {
		return getRestTemplate().getForObject(getUpdatesURI(),
				UpdateResponse.class);
	}

	public MessageResponse sendMessage(int chat_id, String text) {
		return sendMessageInternal(chat_id, text, false, 0, null);
	}

	public MessageResponse sendMessage(int chat_id, String text,
			boolean disable_web_page_preview, int reply_to_message_id,
			ReplyKeyboardMarkup reply_markup) {
		return sendMessageInternal(chat_id, text, disable_web_page_preview,
				reply_to_message_id, reply_markup);
	}

	public MessageResponse sendMessage(int chat_id, String text,
			boolean disable_web_page_preview, int reply_to_message_id,
			ReplyKeyboardHide reply_markup) {
		return sendMessageInternal(chat_id, text, disable_web_page_preview,
				reply_to_message_id, reply_markup);
	}

	public MessageResponse sendMessage(int chat_id, String text,
			boolean disable_web_page_preview, int reply_to_message_id,
			ForceReply reply_markup) {
		return sendMessageInternal(chat_id, text, disable_web_page_preview,
				reply_to_message_id, reply_markup);
	}

	private MessageResponse sendMessageInternal(int chat_id, String text,
			boolean disable_web_page_preview, int reply_to_message_id,
			Object reply_markup) {
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		data.add("chat_id", chat_id);
		data.add("text", text);
		data.add("disable_web_page_preview", disable_web_page_preview);
		if (reply_to_message_id != 0) {
			data.add("reply_to_message_id", reply_to_message_id);
		}
		if (reply_markup != null) {
			data.add("reply_markup", reply_markup);
		}

		return restTemplate.postForObject(getSendMessageURI(), data,
				MessageResponse.class);
	}

	public MessageResponse sendPhoto(int chat_id, URL imageUrl, File tempFile)
			throws IOException {
		return sendPhotoInternal(chat_id, imageUrl, tempFile, null, 0, null);
	}

	public MessageResponse sendPhoto(int chat_id, URL imageUrl, File tempFile,
			String caption) throws IOException {
		return sendPhotoInternal(chat_id, imageUrl, tempFile, caption, 0, null);
	}

	public MessageResponse sendPhoto(int chat_id, URL imageUrl, File tempFile,
			String caption, int reply_to_message_id,
			ReplyKeyboardMarkup reply_markup) throws IOException {
		return sendPhotoInternal(chat_id, imageUrl, tempFile, caption,
				reply_to_message_id, reply_markup);
	}

	public MessageResponse sendPhoto(int chat_id, URL imageUrl, File tempFile,
			String caption, int reply_to_message_id,
			ReplyKeyboardHide reply_markup) throws IOException {
		return sendPhotoInternal(chat_id, imageUrl, tempFile, caption,
				reply_to_message_id, reply_markup);
	}

	public MessageResponse sendPhoto(int chat_id, URL imageUrl, File tempFile,
			String caption, int reply_to_message_id, ForceReply reply_markup)
			throws IOException {
		return sendPhotoInternal(chat_id, imageUrl, tempFile, caption,
				reply_to_message_id, reply_markup);
	}

	private MessageResponse sendPhotoInternal(int chat_id, URL imageUrl,
			File tempFile, String caption, int reply_to_message_id,
			Object reply_markup) throws IOException {

		Files.copy(imageUrl.openStream(), tempFile.toPath(),
				StandardCopyOption.REPLACE_EXISTING);

		Resource photoResource = new FileSystemResource(tempFile);

		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		data.add("chat_id", chat_id);
		data.add("photo", photoResource);
		data.add("caption", caption);
		if (reply_to_message_id != 0) {
			data.add("reply_to_message_id", reply_to_message_id);
		}
		if (reply_markup != null) {
			data.add("reply_markup", reply_markup);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<Object> request = new HttpEntity<Object>(data, headers);

		return getRestTemplate().postForObject(getSendPhotoURI(), request,
				MessageResponse.class);
	}

}
