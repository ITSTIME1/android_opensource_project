package request;

import models.MovieModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {

    @GET("/movie/popular")
    Call<MovieModel> getMovieList();
}
