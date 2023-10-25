package com.abarrotescasavargas.operamovil.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.abarrotescasavargas.operamovil.R;

public class activity_menu_2 extends AppCompatActivity {
    ImageView btnproveedor,btnreclutamiento,btnrezagado,btnVerificador, btnConcursoVentas,btnNuevoArticulo,btnMantenimiento,btnTransferencia;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        setup();
        events();
    }
    private void setup(){
        btnproveedor=  findViewById(R.id.btnproveedor);
        btnreclutamiento= findViewById(R.id.btnreclutamiento);
        btnrezagado= findViewById(R.id.imvrezagado);
        btnVerificador= findViewById(R.id.btnVerificador);
        btnConcursoVentas= findViewById(R.id.btnConcursoVentas);
        btnNuevoArticulo= findViewById(R.id.btnNuevoArticulo);
        btnMantenimiento= findViewById(R.id.btnMantenimiento);
        btnTransferencia= findViewById(R.id.btnTransferencia);
        usuario = getIntent().getStringExtra("USUARIO_LOG");
    }
    private void creaPopUp()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Modulo en mantenimiento");
        alertDialogBuilder.setMessage("¡Hola! Hemos deshabilitado temporalmente este modulo para llevar a cabo mejoras. Agradecemos tu comprensión.");
        alertDialogBuilder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void events(){
        btnproveedor.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(), SincronizandoActivity.class);
//            intent.putExtra("origen","PROVEEDORES");
//            startActivity(intent);
//            overridePendingTransition(R.transition.in_left, R.transition.out_left);
            creaPopUp();
        });
        btnreclutamiento.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SincronizandoActivity.class);
            //Intent intent = new Intent (v.getContext(), ActivityCandidatos.class);
            intent.putExtra("origen","RECLUTAMIENTO");
            startActivity(intent);
            overridePendingTransition(R.transition.in_left, R.transition.out_left);
        });
        btnrezagado.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), SincronizandoActivity.class);
            intent.putExtra("origen","REZAGADO");
            intent.putExtra("USUARIO_LOG", usuario);
            startActivity(intent);
            overridePendingTransition(R.transition.in_left, R.transition.out_left);
            Log.v("El usuario que ingreso es: ",usuario);
        });

        btnVerificador.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), SincronizandoActivity.class);
            intent.putExtra("origen","VERIFICADOR");
            startActivity(intent);
            overridePendingTransition(R.transition.in_left, R.transition.out_left);
//            creaPopUp();

        });
        btnConcursoVentas.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), SincronizandoActivity.class);
            intent.putExtra("origen","CONCURSO");
            startActivity(intent);
            overridePendingTransition(R.transition.in_left, R.transition.out_left);
//            creaPopUp();

        });

        btnNuevoArticulo.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), SincronizandoActivity.class);
            intent.putExtra("origen","NUEVO");
            startActivity(intent);
            overridePendingTransition(R.transition.in_left, R.transition.out_left);
        });
        btnMantenimiento.setOnClickListener(v->{

            Intent intent = new Intent(getApplicationContext(), SincronizandoActivity.class);
            intent.putExtra("origen","MANTENIMIENTO");
            startActivity(intent);
            overridePendingTransition(R.transition.in_left, R.transition.out_left);

        });

        btnTransferencia.setOnClickListener(v->{

//            Intent intent = new Intent(getApplicationContext(), SincronizandoActivity.class);
//            intent.putExtra("origen","TRANSFERENCIA");
//            startActivity(intent);
//            overridePendingTransition(R.transition.in_left, R.transition.out_left);
            creaPopUp();
        });
    }
}