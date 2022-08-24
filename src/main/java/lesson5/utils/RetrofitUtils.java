package lesson5.utils;

import lombok.experimental.UtilityClass;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@UtilityClass
public class RetrofitUtils {

    private static String baseUrl = "http://localhost:8189/market/api/v1/";
    public static String getBaseUrl() {
        return baseUrl;
    }


//    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//    LoggingInterceptor logging2 = new LoggingInterceptor();
//    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


   public Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

//    public Retrofit getRetrofit(){
//        logging.setLevel(BODY);
//        httpClient.addInterceptor(logging2);
//        return new Retrofit.Builder()
//                .baseUrl(getBaseUrl())
//                .addConverterFactory(JacksonConverterFactory.create())
//                .client(httpClient.build())
//                .build();
//    }

}