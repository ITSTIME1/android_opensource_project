package api;

import java.util.List;

import model.Posts;
import retrofit2.Call;
import retrofit2.http.GET;


// 내가 요청하고 싶은 결과물의 대한 API Interface 규격을 만들어둔것.
public interface ApiInterface {

    @GET("/posts")
    Call<List<Posts>> getPosts();
}
