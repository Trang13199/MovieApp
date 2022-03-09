package com.mytrang.moviesapplication.Client;

import com.mytrang.moviesapplication.Service.MovieService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserClient {
    private static String BASE_URL = "http://training-movie.bsp.vn:82/";
    private static UserClient userClient;
    private static Retrofit retrofit;

    private OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    private UserClient() {
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }

    public static synchronized UserClient getInstance() {
        if (userClient == null) {
            userClient = new UserClient();
        }
        return userClient;
    }

    public MovieService getApi() {
        return retrofit.create(MovieService.class);
    }
}
