package com.abarrotescasavargas.operamovil.Main.ConcursoVentas;


import com.abarrotescasavargas.operamovil.Main.Documentos.ResponseDocumentos;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PeticionConcursoVentas {
    @Multipart
    @POST("articuloNuevoFoto.php")
    Call<RespuestaWSConcursoVentas> subeDocumento
            (
                    @Part("nombreCorto") RequestBody nombreCorto,
                    @Part("cveArt") RequestBody cveArt,
                    @Part("cveSucursal") RequestBody cveSucursal,
                    @Part MultipartBody.Part imgLD
            );

    @GET("articuloNuevoFoto.php")
    Call<ResponseDocumentos> obtieneListado
            (
                    @Query("cveSucursal") String cveSucursal
            );
}
