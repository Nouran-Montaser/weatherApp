package com.example.lenovo.weatherapp;

import com.example.lenovo.weatherapp.Pojo.MyPojo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Lenovo on 3/28/2018.
 */

public interface Client {
//    List<Client> reposForUser(String user);
    /*Retrofit get annotation with our URL
       And our method that will return us the list of students info
    */

    @GET("forecast.json?key=66411d4e534944b3bdb193244182703&Q=Egypt&days=7")
    Call<MyPojo> getRes();
    //public void getres(Callback<Weather> response);

    @GET("forecast.json?key=66411d4e534944b3bdb193244182703&days=7")
//@POST("current.json?key=66411d4e534944b3bdb193244182703")
    Call<MyPojo> addlocation(@Query("q") String name);

}
