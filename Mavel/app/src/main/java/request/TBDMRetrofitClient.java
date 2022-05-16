package request;
import static constants.Constants.BASE_URL;

import constants.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TBDMRetrofitClient {

    private static Retrofit retrofit;


    /**
     * [Retrofit Client single pattern]
     * @return
     */
    public static Retrofit getInstance(){
        if(retrofit == null) {
            retrofit =
                    new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }



}
