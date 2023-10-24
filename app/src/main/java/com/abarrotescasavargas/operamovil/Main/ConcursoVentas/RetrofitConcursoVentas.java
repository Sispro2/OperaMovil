package com.abarrotescasavargas.operamovil.Main.ConcursoVentas;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConcursoVentas {
    static final String BASE_URL = "https://abarrotescasavargas.mx/api/mobile/comercial/";
    Retrofit retrofit;
    public RetrofitConcursoVentas() { }
    public Retrofit getRuta() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        return retrofit;
    }
}
