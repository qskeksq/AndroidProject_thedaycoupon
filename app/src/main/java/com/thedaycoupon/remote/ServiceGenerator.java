package com.thedaycoupon.remote;

import com.thedaycoupon.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Generate service interface for Retrofit2 networking
 */
public class ServiceGenerator {

    private static Retrofit retrofit;

    public static <S> S createService(Class<S> className){
        if(retrofit == null){
            buildRetrofit();
        }
        return retrofit.create(className);
    }

    private static Retrofit buildRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(IRemoteService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkhttp())
                .build();
        return retrofit;
    }

    private static OkHttpClient buildOkhttp(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG){
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
        return okHttpClient;
    }

}
