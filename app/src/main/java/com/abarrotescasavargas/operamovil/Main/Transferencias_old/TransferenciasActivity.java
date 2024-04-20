package com.abarrotescasavargas.operamovil.Main.Transferencias_old;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abarrotescasavargas.operamovil.Main.DetalleTransferencias.DetalleTranferencias;
import com.abarrotescasavargas.operamovil.Main.Transferencias.Detalle.TransferenciasDetalleActivity;
import com.abarrotescasavargas.operamovil.R;

import java.util.ArrayList;
import java.util.List;


public class TransferenciasActivity extends AppCompatActivity {
    private List<listTransferencias> elements;
    private RecyclerView recyclerView;
    private ListTransferenciasAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencias);
        setup();
        TraeTransferencias();
    }

    private void setup(){
        recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void TraeTransferencias() {

        int _id_premovimiento_almacen = 0;  int _id_sucursal_origen =0 ;int _id_sucursal_destino =0 ;
        float _subtotal= 0; float _total_neto= 0; float _total_iva= 0; float _total_ieps= 0;
        String _folio = ""; String _observaciones = ""; String _referencia = "";
        String _sucOrigen= "";

        elements = new ArrayList<>();
        Cursor cursor = MovimientoAlmacenRepository.GetSetTransferenciasPendientes(getApplicationContext());
        try {
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        _id_premovimiento_almacen = cursor.getInt(0);
                        _folio = cursor.getString(1);
                        _observaciones = cursor.getString(2).replace("\n", " ");
                        _sucOrigen= cursor.getString(3);
                        elements.add(new listTransferencias( _id_premovimiento_almacen
                                ,_folio
                                ,_observaciones
                                ,_sucOrigen));
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                listAdapter = new ListTransferenciasAdapter(elements, this, this::moveToDescription);
                recyclerView.setAdapter(listAdapter);
            }
        } catch (Exception e) {
            Log.e("MovimientoAlmacenRepository.GetSetTransferenciasPendientes", e.toString());
        }
    }

    private void moveToDescription(listTransferencias item) {
        //Intent intent = new Intent(this, DetalleTransferencia.class);
        //Intent intent = new Intent(this, DetalleTranferencias.class);
        Intent intent = new Intent(this, TransferenciasDetalleActivity.class);
        intent.putExtra("DetalleTransferencia", item);
        startActivity(intent);
    }
}