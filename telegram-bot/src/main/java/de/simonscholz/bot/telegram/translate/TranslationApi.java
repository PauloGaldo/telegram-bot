package de.simonscholz.bot.telegram.translate;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TranslationApi {

	@GET("http://transltr.org/api/translate")
	Single<Translation> getTranslation(@Query("text") String text, @Query("to") String toLanguage,
			@Query("from") String fromLanguage);
}
