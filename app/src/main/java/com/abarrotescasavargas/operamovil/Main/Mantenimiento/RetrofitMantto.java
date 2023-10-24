package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMantto {

    static final String BASE_URL = "https://abarrotescasavargas.mx/api/mobile/mantenimiento/";
    Retrofit retrofit;
    public RetrofitMantto() { }
    public Retrofit getRuta() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        return retrofit;
    }
}
