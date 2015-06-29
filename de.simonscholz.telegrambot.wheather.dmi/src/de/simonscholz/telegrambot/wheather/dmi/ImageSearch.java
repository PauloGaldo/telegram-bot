package de.simonscholz.telegrambot.wheather.dmi;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ImageSearch {

	public URL getTwoDaysForecastImageUrl(String city) throws IOException {

		Document doc = navigateToCity(city);

		Element twoDayImageElement = doc.getElementById("w_days_two_forecast");
		String twoDayImageSrcAttrValue = twoDayImageElement.attr("src");

		return new URL(twoDayImageSrcAttrValue);
	}

	public URL getDaysThreeForecastImageUrl(String city) throws IOException {

		Document doc = navigateToCity(city);

		Element threeDayImageElement = doc
				.getElementById("w_days_three_forecast");
		String twoDayImageSrcAttrValue = threeDayImageElement.attr("src");

		return new URL(twoDayImageSrcAttrValue);
	}

	private Document navigateToCity(String city) throws IOException {
		Document doc = Jsoup
				.connect(
						"http://www.dmi.dk/vejr/til-lands/byvejr/by/vis/DE/2911298/Hamburg,%20Tyskland/")
				.get();

		return doc;
	}
}
