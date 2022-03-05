package com.mytrang.moviesapplication.Util;

import com.mytrang.moviesapplication.Client.MovieClient;
import com.mytrang.moviesapplication.Service.MovieService;

public class APIUtil {
    public static final String BASE_URL ="http://training-movie.bsp.vn:82/";
    public static MovieService getMovieService(){
        return MovieClient.getMovieRetrofit(BASE_URL).create(MovieService.class);
    }
}
