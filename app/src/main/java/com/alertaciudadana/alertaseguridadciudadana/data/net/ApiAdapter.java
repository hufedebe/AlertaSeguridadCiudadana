package com.alertaciudadana.alertaseguridadciudadana.data.net;

import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ApiAdapter {

    private static ApiService API_SERVICE;


    public static ApiService getApiService(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);  // <-- this is the important line!


        String baseUrl = "http://9fec1dab.ngrok.io/api/";
        //String baseUrl = "http://10.0.2.2:9090/AtencionVehicular/";
        if (API_SERVICE==null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            Log.d("getTasksListHTTP", "Se añadio bien:"+baseUrl);
            API_SERVICE = retrofit.create(ApiService.class);


        }
        return API_SERVICE;

    }
}
