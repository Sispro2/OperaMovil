package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abarrotescasavargas.operamovil.Main.ActivityEvidencia;
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;
import com.abarrotescasavargas.operamovil.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityMantenimiento extends AppCompatActivity {
    RecyclerView rcvReportesMantto;
    SucursalRepository sucursalRepository;
    ProgressDialog progressDialog;
    private final Handler mainHandler = new Handler();
    Retrofit retrofit = new RetrofitMantto().getRuta();
    PeticionMtto peticionMtto = retrofit.create(PeticionMtto.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento);
        Setup();
    }

    private void Setup() {
        sucursalRepository = new SucursalRepository(this);
        rcvReportesMantto = findViewById(R.id.rcvReportesMantto);
        rcvReportesMantto.setHasFixedSize(true);
        rcvReportesMantto.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ReporteMantenimiento> rpList= getIntent().getParcelableArrayListExtra("reportesMantenimientoList");
        List<ListReporteMantenimiento> listReportes = new ArrayList<>();
        if (rpList.size() > 0) {
            for (int i = 0; i < rpList.size(); i++) {
                listReportes.add(new ListReporteMantenimiento(
                        rpList.get(i).getCategoria()
                        ,rpList.get(i).getSubCategoria()
                        ,rpList.get(i).getDescripcion()
                        ,rpList.get(i).getStatus()
                        ,rpList.get(i).getIdReporte()));
            }
            AdapterReporteMtto adapterReporteMtto = new AdapterReporteMtto(listReportes, this, this::SeleccionaElemento);
            rcvReportesMantto.setAdapter(adapterReporteMtto);
        }
    }
    private void LlenaDatos(ArrayList<Evidencia> evidenciasList, int idReporte) {
        Intent intent = new Intent(this, ActivityEvidencia.class);
        intent.putExtra("idReporte", idReporte);
        intent.putExtra("evidenciasList", evidenciasList);
        startActivity(intent);
        progressDialog.cancel();
        finish();
    }
    private void SeleccionaElemento(ListReporteMantenimiento item) {
        SolicitaEvidenciaRunnable runnable = new  SolicitaEvidenciaRunnable(sucursalRepository.GetDetalleSucursal().getKS_CVESUC(),item.getIdReporte());
        new Thread(runnable).start();
        progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.sincronizando_dialog);
    }
    class SolicitaEvidenciaRunnable implements Runnable {
        String cveSucursal;
        int idReporte;
        SolicitaEvidenciaRunnable(String cveSucursal, int idReporte) {
            this.cveSucursal = cveSucursal;
            this.idReporte = idReporte;
        }
        @Override
        public void run() {
            Call<ResponseEvidencias> call = peticionMtto.obtieneListado(idReporte, cveSucursal);   ;
            call.enqueue(new Callback<ResponseEvidencias>() {
                public void onResponse(@NonNull Call<ResponseEvidencias> call, @NonNull Response<ResponseEvidencias> response) {
                    if (response.body().isStatus()) {
                        ArrayList<Evidencia> evidenciasList = response.body().getEvidencias();
                        try {
                            Thread.sleep(100); // mil es un segundo
                            mainHandler.post(() -> {
                                LlenaDatos(evidenciasList,  idReporte);
                            });
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                public void onFailure(@NonNull Call<ResponseEvidencias> call, @NonNull Throwable t) {
                    Log.i("ERROR en ", "onFailure: " + t);
                }
            });
        }
    }
}