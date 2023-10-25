package com.abarrotescasavargas.operamovil.Main;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abarrotescasavargas.operamovil.R;

public class Tercero extends AppCompatActivity {

    private Firma guardarFirma_recibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercero);

        guardarFirma_recibido=(Firma)findViewById(R.id.lienzo);
        Button guardar_recibido = findViewById(R.id.btn_guardar_firma_recibido);
        Button limpiar_recibido = findViewById(R.id.btn_limpiar_firma_recibido);
        Button salir_recibido = findViewById(R.id.btn_salir_firma_recibido);

        guardar_recibido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guardarFirma_recibido.comprovarCanvas()){
                    Bitmap bitmap= guardarFirma_recibido.getBitmap();
                   // Registro2.pintarFirmaRecibido(bitmap);
                    RegistroBitacora.pintarFirmaRecibido(bitmap);
                    Tercero.super.onBackPressed();

                }else {
                    Toast.makeText(getApplicationContext(),"Se necesita Firmar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        limpiar_recibido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { guardarFirma_recibido.clearCanvas();
            }
        });

        salir_recibido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tercero.super.onBackPressed();

            }
        });
    }
}
