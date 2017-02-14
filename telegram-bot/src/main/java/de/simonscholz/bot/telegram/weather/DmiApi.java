package de.simonscholz.bot.telegram.weather;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface DmiApi {
	
	public static final String MODE_NOW = "dag1_2";

	public static final String MODE_WEEK = "dag3_9";
	
	@GET("getData?type=forecast")
	Single<List<DmiCity>> getDmiCities(@Query("term") String cityName);

	@Streaming
	@GET("http://servlet.dmi.dk/byvejr/servlet/world_image")
	Single<ResponseBody> getWeatherImage(@Query("city") String cityId, @Query("mode") String mode);
}
