package com.mytrang.moviesapplication.Service;

import com.mytrang.moviesapplication.model.Data;
import com.mytrang.moviesapplication.model.LoginRequest;
import com.mytrang.moviesapplication.model.User;
import com.mytrang.moviesapplication.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MovieService {
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @GET("movie/list?page=1&per_page=10")
    Call<Data> getAnswer();

    ///
    @FormUrlEncoded
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @POST("user/registry")
    Call<UserModel> getRegister(@Field("full_name") String full_name,
                           @Field("email") String email,
                           @Field("password") String password );



    //////////////
    @FormUrlEncoded
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @POST("user/login")
    Call<UserModel> getLoginUser(@Field("email") String mail,
                                 @Field("password") String password);
}
