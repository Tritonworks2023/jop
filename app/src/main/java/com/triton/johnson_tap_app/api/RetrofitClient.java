package com.triton.johnson_tap_app.api;

import com.grapesnberries.curllogger.CurlLoggerInterceptor;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Iddinesh.
 */
public class RetrofitClient {


    private static Retrofit retrofit = null;
    private static OkHttpClient client;


    /*dev*/
  /*public static String BASE_URL = "http://54.202.95.145:3000/api/";
  public static String IMAGE_BASE_URL = "http://54.202.95.145::3000/";*/

    /*live*/
    public static String BASE_URL = "http://smart.johnsonliftsltd.com:3000/api/";
    public static String IMAGE_BASE_URL = "http://smart.johnsonliftsltd.com:3000/";

    /*Banner Image*/
    public static String BANNER_IMAGE_URL = BASE_URL+"uploads/bannerempty.jpg";

    /* Profile or other Image*/
    public static String PROFILE_IMAGE_URL = BASE_URL+"uploads/picempty.jpg";

    public static Retrofit getClient() {
        String DEBUG_TAG = "RETROFIT_DEBUG";
        CurlLoggerInterceptor debugInceptor = new CurlLoggerInterceptor(DEBUG_TAG);


        //This line is useleess
        //client = new OkHttpClient();
        Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(5));
        client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES )
                .addInterceptor(debugInceptor) // Debug Interceptor
                .cache(null)
                .dispatcher(dispatcher)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
    public static Retrofit getImageClient() {
        client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES )
                .cache(null)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(IMAGE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }


}



