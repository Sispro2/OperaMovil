package com.abarrotescasavargas.operamovil.Main.Transferencias;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;


public class TransferenciasActivity extends AppCompatActivity {
//    private List<listTransferencias> elements;
//    private RecyclerView recyclerView;
//    private ListTransferenciasAdapter listAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_transferencias);
//        setup();
//        TraeTransferencias();
//    }
//
//    private void setup(){
//        recyclerView = findViewById(R.id.listRecyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        //movimientoAlmacenRepository = new MovimientoAlmacenRepository(this);
//        //sucursalRepository = new SucursalRepository(getApplicationContext());
//    }
//
//    private void TraeTransferencias() {
//
//        int id_premovimiento_almacen ;
//        String folio = "";
//        String observaciones = "";
//        String origen = "";
//        elements = new ArrayList<>();
//        Cursor cursor = MovimientoAlmacenRepository.GetSetTransferenciasPendientes(getApplicationContext());
//        try {
//            if (cursor != null) {
//                if (cursor.getCount() > 0) {
//                    cursor.moveToFirst();
//                    while (!cursor.isAfterLast()) {
//                        id_premovimiento_almacen = cursor.getInt(0);
//                        folio = cursor.getString(1);
//                        observaciones = cursor.getString(2).replace("\n", " ");
//                        origen = cursor.getString(3);
//                        elements.add(new listTransferencias(folio, observaciones, origen, id_premovimiento_almacen));
//                        cursor.moveToNext();
//                    }
//                }
//                cursor.close();
//                listAdapter = new ListTransferenciasAdapter(elements, this, this::moveToDescription);
//                recyclerView.setAdapter(listAdapter);
//            }
//        } catch (Exception e) {
//            Log.e("MovimientoAlmacenRepository.GetSetTransferenciasPendientes", e.toString());
//        }
//    }
//
//    private void moveToDescription(listTransferencias item) {
//        //Intent intent = new Intent(this, DetalleTransferencia.class);
//        Intent intent = new Intent(this, DetalleTranferencias.class);
//        intent.putExtra("DetalleTransferencia", item);
//        startActivity(intent);
//    }
}