package com.abarrotescasavargas.operamovil.Main.Candidatos;

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


import com.abarrotescasavargas.operamovil.Main.Documentos.ActivityDocumentos;
import com.abarrotescasavargas.operamovil.Main.Documentos.Documentos;
import com.abarrotescasavargas.operamovil.Main.Documentos.PeticionRRHH;
import com.abarrotescasavargas.operamovil.Main.Documentos.ResponseDocumentos;
import com.abarrotescasavargas.operamovil.Main.Documentos.RetrofitRRHH;
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;
import com.abarrotescasavargas.operamovil.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityCandidatos extends AppCompatActivity {
    private RecyclerView rcvCandidatos;
    ProgressDialog progressDialog;
    Retrofit retrofit = new RetrofitRRHH().getRuta();
    PeticionRRHH peticionRRHH = retrofit.create(PeticionRRHH.class);
    private final Handler mainHandler = new Handler();
    SucursalRepository sucursalRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatos);
        Setup();
        //TraeCandidatos();
    }

    private void Setup() {
        sucursalRepository = new SucursalRepository(this);
        rcvCandidatos = findViewById(R.id.rcvCandidatos);
        rcvCandidatos.setHasFixedSize(true);
        rcvCandidatos.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Candidatos> cdList= getIntent().getParcelableArrayListExtra("candidatosList");
        List<ListCandidatos> listCandidatos = new ArrayList<>();
        if (cdList.size() > 0) {
            for (int i = 0; i < cdList.size(); i++) {
                listCandidatos.add(new ListCandidatos(cdList.get(i).getNombre(), cdList.get(i).getFechRegistro(), cdList.get(i).getPuesto(), "Etapa "+cdList.get(i).getEstatus(), cdList.get(i).getIdReclutamiento()));
            }
            AdapterCandidatos adapterCandidatos = new AdapterCandidatos(listCandidatos, this, this::moveToDescription);
            rcvCandidatos.setAdapter(adapterCandidatos);
        }
    }



    private void moveToDescription(ListCandidatos item) {

        ExampleRunnable runnable = new ExampleRunnable(sucursalRepository.GetDetalleSucursal().getKS_CVESUC(), item.getIdReclutamiento(), item.getNombre(), item.getPuesto());
        new Thread(runnable).start();
        progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.sincronizando_dialog);
    }

    private void LlenaDatos(ArrayList<Documentos> documentList, String idReclutamiento, String nombre, String puesto) {
        Intent intent = new Intent(this, ActivityDocumentos.class);
        intent.putExtra("idReclutamiento", idReclutamiento);
        intent.putExtra("documentList", documentList);
        intent.putExtra("nombre", nombre);
        intent.putExtra("puesto", puesto);
        startActivity(intent);
        progressDialog.cancel();
       // finish();
    }

    class ExampleRunnable implements Runnable {
        String cveSucursal, idReclutamiento, nombre, puesto;
        ExampleRunnable(String cveSucursal, String idReclutamiento, String nombre, String puesto) {
            this.cveSucursal = cveSucursal;
            this.idReclutamiento = idReclutamiento;
            this.nombre = nombre;
            this.puesto= puesto;
        }
        @Override
        public void run() {
            Call<ResponseDocumentos> call = peticionRRHH.obtieneListado(idReclutamiento, cveSucursal);
            call.enqueue(new Callback<ResponseDocumentos>() {
                public void onResponse(@NonNull Call<ResponseDocumentos> call, @NonNull Response<ResponseDocumentos> response) {
                    if (response.body().isStatus()) {
                        ArrayList<Documentos> documentList = response.body().getDocumentos();
                        try {
                            Thread.sleep(100); // mil es un segundo
                            mainHandler.post(() -> {
                                LlenaDatos(documentList,  idReclutamiento, nombre, puesto);
                            });
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                public void onFailure(@NonNull Call<ResponseDocumentos> call, @NonNull Throwable t) {
                    Log.i("ERROR en ", "onFailure: " + t.toString());
                }
            });
        }
    }
}