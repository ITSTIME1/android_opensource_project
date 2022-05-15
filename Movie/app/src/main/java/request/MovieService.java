package request;

import retrofit2.http.GET;

public interface MovieService {

    @GET()
    Call<T>
}
