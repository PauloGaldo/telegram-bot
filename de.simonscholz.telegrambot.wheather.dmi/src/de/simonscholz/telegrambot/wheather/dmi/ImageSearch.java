package de.simonscholz.telegrambot.wheather.dmi;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ImageSearch {

	public URL getImageUrl(String city) throws IOException {

		Document doc = Jsoup
				.connect(
						"http://www.dmi.dk/vejr/til-lands/byvejr/by/vis/DE/2911298/Hamburg,%20Tyskland/")
				.get();

		Element twoDayImageElement = doc.getElementById("w_days_two_forecast");
		String twoDayImageSrcAttrValue = twoDayImageElement.attr("src");

		return new URL(twoDayImageSrcAttrValue);
	}

	public BufferedImage getBufferedImage(String city) throws IOException {
		URL imageUrl = getImageUrl(city);

		return ImageIO.read(imageUrl);
	}

	public InputStream getInputStream(String city) throws IOException {
		URL imageUrl = getImageUrl(city);

		return imageUrl.openStream();
	}
}
