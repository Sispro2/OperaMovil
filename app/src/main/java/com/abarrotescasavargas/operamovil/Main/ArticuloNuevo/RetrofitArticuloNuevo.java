package com.abarrotescasavargas.operamovil.Main.ArticuloNuevo;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitArticuloNuevo {
    static final String BASE_URL = "https://abarrotescasavargas.mx/api/mobile/comercial/";
    Retrofit retrofit;
    public RetrofitArticuloNuevo() { }
    public Retrofit getRuta() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        return retrofit;
    }
}
