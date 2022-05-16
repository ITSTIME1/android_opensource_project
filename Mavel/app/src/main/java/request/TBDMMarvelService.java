package request;

import java.util.List;

import constants.Constants;
import model.Result;
import response.MarvelResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * [TBDMMarvleService Interface]
 *
 * This is "TBDM Interface"
 * I used Search for Details If you don't want to use it so then change @GET() endPoint.
 */
public interface TBDMMarvelService {
    @GET("search/movie")
    Call<MarvelResponse> getMarvelMovieList(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page);
}
