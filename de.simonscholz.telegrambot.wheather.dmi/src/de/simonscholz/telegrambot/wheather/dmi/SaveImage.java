package de.simonscholz.telegrambot.wheather.dmi;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class SaveImage {

	public static void main(String[] args) {
		try {
			ImageSearch imageSearch = new ImageSearch();
			URL url = imageSearch.getImageUrl("Hamburg");
			String fileName = url.getFile();

			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(
					"/home/simon/Documents/dmifiles"
							+ fileName.substring(fileName.lastIndexOf("/")));

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
}
