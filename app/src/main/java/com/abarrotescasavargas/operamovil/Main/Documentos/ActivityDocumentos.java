package com.abarrotescasavargas.operamovil.Main.Documentos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abarrotescasavargas.operamovil.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class ActivityDocumentos extends AppCompatActivity {
   // private static final String TAG = "ACTIVITY DOCUMENTOS";
    Retrofit retrofit = new RetrofitRRHH().getRuta();

   // PeticionRRHH peticionRRHH = retrofit.create(PeticionRRHH.class);
    //ProgressDialog progressDialog;
    TextView txtvDocumentos,   txtvPuesto;
    private String  nombre, puesto,  idReclutamiento;
    List<ListDocumentos> ListDocumentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos);
        Setup();
    }

    private void Setup() {
        RecyclerView rcvDocumentos = findViewById(R.id.rcvDocumentos);
        txtvDocumentos = findViewById(R.id.txtvDocumentos);
        txtvPuesto = findViewById(R.id.txtvPuesto);
        rcvDocumentos.setHasFixedSize(true);
        rcvDocumentos.setLayoutManager(new LinearLayoutManager(this));
        //cveSucursal = getIntent().getStringExtra("cveSucursal");
        idReclutamiento = getIntent().getStringExtra("idReclutamiento");
        nombre =  getIntent().getStringExtra("nombre");
        txtvDocumentos.setText(getIntent().getStringExtra("nombre"));
        txtvPuesto.setText(getIntent().getStringExtra("puesto"));
        puesto = getIntent().getStringExtra("puesto");

        ArrayList<Documentos> documentList = getIntent().getParcelableArrayListExtra("documentList");
        if (documentList.size() > 0) {
            for (int i = 0; i < documentList.size(); i++) {
                ListDocumentos.add(new ListDocumentos(documentList.get(i).getNombreDocumento(), documentList.get(i).getStatus(), documentList.get(i).getUrl(), documentList.get(i).getNombreCorto()));
            }
            AdapterDocumentos adapterDocumentos = new AdapterDocumentos(ListDocumentos, this, this::moveToDescription);
            rcvDocumentos.setAdapter(adapterDocumentos);
        }
    }


    private void moveToDescription(ListDocumentos item) {
        Intent intent = new Intent(this, CapturaDocumento.class);
        intent.putExtra("DetalleDocumento", item);
        intent.putExtra("idReporte", idReclutamiento);
        intent.putExtra("nombreCorto", item.getNombreCorto());

        intent.putExtra("nombre",nombre);
        intent.putExtra("puesto", puesto);
        startActivity(intent);
        finish(); // quizá deba quitar
    }


}