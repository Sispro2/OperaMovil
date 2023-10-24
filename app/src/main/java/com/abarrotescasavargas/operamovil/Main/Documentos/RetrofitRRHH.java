package com.abarrotescasavargas.operamovil.Main.Documentos;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRRHH {
    static final String BASE_URL = "https://abarrotescasavargas.mx/api/mobile/rh/";
    Retrofit retrofit;
    public RetrofitRRHH() { }
    public Retrofit getRuta() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        return retrofit;
    }
}
