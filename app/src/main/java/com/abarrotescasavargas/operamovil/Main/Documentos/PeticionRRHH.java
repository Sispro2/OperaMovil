package com.abarrotescasavargas.operamovil.Main.Documentos;

import com.abarrotescasavargas.operamovil.Main.Candidatos.ResponseCandidatos;
import com.abarrotescasavargas.operamovil.Main.Modelos.RespuestaWS;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PeticionRRHH {
    @Multipart
    @POST("candidatoFoto.php")
    Call<RespuestaWS> subeDocumento
            (
                    @Part("id_reclutamiento") RequestBody id_reclutamiento,
                    @Part("cve_suc") RequestBody cve_suc,
                    @Part("nombreCorto") RequestBody nombreCorto,
                    @Part MultipartBody.Part transporte

            );

    @GET("candidatoFoto.php")
    Call<ResponseDocumentos> obtieneListado
            (
                    @Query("idReclutamiento") String idReclutamiento,
                    @Query("cveSucursal") String cveSucursal
            );
    @GET("obtieneCandidatos.php")
    Call<ResponseCandidatos> obtieneCandidatos
            (
                    @Query("cveSucursal") String cveSucursal
            );
}
