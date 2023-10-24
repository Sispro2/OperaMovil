package com.abarrotescasavargas.operamovil.Main.Mantenimiento;



import com.abarrotescasavargas.operamovil.Main.Modelos.RespuestaWS;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PeticionMtto {
    @Multipart
    @POST("mantenimientoFoto.php")
    Call<RespuestaWS> subeEvidencia
            (
                    @Part("idReporte") RequestBody idReporte,
                    @Part("cveSucursal") RequestBody cveSucursal,
                    @Part("nombreCorto") RequestBody nombreCorto,
                    @Part MultipartBody.Part imgReporteM
            );

    @GET("mantenimientoFoto.php")
     Call<ResponseEvidencias> obtieneListado
            (
                    @Query("idReporte") int idReclutamiento,
                    @Query("cveSucursal") String cveSucursal
            );
    @GET("obtieneReportes.php")
    Call<ResponseReporteMantto> obtieneReportes
            (
                    @Query("cveSucursal") String cveSucursal
            );
}
