package com.abarrotescasavargas.operamovil.Main;



import com.abarrotescasavargas.operamovil.Main.Modelos.RespuestaWS;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface RealizaPeticion {
    @PUT("usuarios.php")
    Call<Respuesta> registraComida(
            @Body Comensal comensal
    );

    /**/
    @Multipart
    @POST("fotos.php")
    Call<RespuestaWS> subeFoto
    (
            @Part("num_factura") RequestBody num_factura,
            @Part("num_placa") RequestBody num_placa,
            @Part("cve_suc") RequestBody cve_suc,
            @Part("nom_proveedor") RequestBody nom_proveedor,
            @Part("id_recepcion") RequestBody id_recepcion,
            @Part("cve_proveedor") RequestBody cve_proveedor,
            @Part("observaciones") RequestBody observaciones,
            @Part MultipartBody.Part transporte,
            @Part MultipartBody.Part placa,
            @Part MultipartBody.Part sello,
            @Part MultipartBody.Part factura
            , @Part("nom_transportista") RequestBody nom_transportista
            , @Part("firm_transportista") RequestBody firm_transportista
            , @Part("nom_recibe") RequestBody nom_recibe
            , @Part("firm_recibe") RequestBody firm_recibe
    );

    @Multipart
    @POST("fotos.php")
    Call<RespuestaWS> subeDocumento
            (
                    @Part("id_reclutamiento") RequestBody id_reclutamiento,
                    @Part("cve_suc") RequestBody cve_suc,
                    @Part MultipartBody.Part transporte
            );
}
