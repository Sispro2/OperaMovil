package com.abarrotescasavargas.operamovil.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abarrotescasavargas.operamovil.Main.Mantenimiento.AdapterEvidenciasMtto;
import com.abarrotescasavargas.operamovil.Main.Mantenimiento.Captura_Evidencia;
import com.abarrotescasavargas.operamovil.Main.Mantenimiento.Evidencia;
import com.abarrotescasavargas.operamovil.Main.Mantenimiento.ListEvidencias;
import com.abarrotescasavargas.operamovil.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityEvidencia extends AppCompatActivity {
    RecyclerView rcvEvidencia;
    private int idReporte;
    List<ListEvidencias> listEvidencias = new ArrayList<>();
    ArrayList<Evidencia> jalaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidencia);
        Setup();
    }
    private void Setup(){
        rcvEvidencia= findViewById(R.id.rcvEvidencia);
        rcvEvidencia.setHasFixedSize(true);
        rcvEvidencia.setLayoutManager(new LinearLayoutManager(this));
        idReporte= getIntent().getIntExtra("idReporte",0);


        ArrayList<Evidencia> evidenciasList = getIntent().getParcelableArrayListExtra("evidenciasList");//Aqui truena
        jalaData=evidenciasList;
        if (evidenciasList.size() > 0) {
            for (int i = 0; i < evidenciasList.size(); i++) {
                listEvidencias.add(new ListEvidencias(evidenciasList.get(i).getNombreEvidencia(),
                        evidenciasList.get(i).getStatus(),
                        evidenciasList.get(i).getNombreCorto(),
                        evidenciasList.get(i).getUrl()
                        ));
            }
            AdapterEvidenciasMtto adapterEvidencias = new AdapterEvidenciasMtto(listEvidencias, this, this::SeleccionaElemento);
            rcvEvidencia.setAdapter(adapterEvidencias);
        }
    }

    private void SeleccionaElemento(ListEvidencias item) {


        Intent intent = new Intent(this, Captura_Evidencia.class);
        intent.putExtra("idReporte",idReporte);
        intent.putExtra("evidenciasList",item.getNombreCorto());
        intent.putExtra("evidenciasListTotal",jalaData);
        startActivity(intent);
    }
}