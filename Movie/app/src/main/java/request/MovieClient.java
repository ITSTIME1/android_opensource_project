package request;

import static constants.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * [MovieClient]
 *
 * It's movieClient
 * Movie Client made single-object
 * In this page, you can create retrofit instance
 * so you can using [MovieService Interface]
 *
 */
public class MovieClient {

    // Create RetrofitBuilder Instance
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());

    // Using RetrofitBuilder Instance
    private static Retrofit retrofit = retrofitBuilder.build();

    // Service 구현체. Retrofit Class 안에 create 라는 메서드를 사용해서 TbdmService 의 인터페이스 구조체를 가져오고
    // 만약 그 구조체가 Interface가 아닌경우 throw를 리턴하며
    // 정상적인 Interface 라면 TbdmService 의 메서드를 반환해준다.
    private static MovieClient tbdmService = retrofit.create(MovieClient.class);

    // Create HTTP Method
    public MovieService getMovie(){
        return (MovieService) tbdmService;
    }
}
