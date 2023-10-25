package com.abarrotescasavargas.operamovil.Main;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //static final String BASE_URL = "https://abarrotescasavargas.mx/api/convencion/android/";
    static final String BASE_URL = "https://abarrotescasavargas.mx/api/mobile/comercial/"; // siempre tiene que terminar en diagonal
    Retrofit retrofit;
    public RetrofitClient() { }

    public Retrofit getRetrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        return retrofit;
    }
}
