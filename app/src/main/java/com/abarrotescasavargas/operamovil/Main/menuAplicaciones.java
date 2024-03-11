package com.abarrotescasavargas.operamovil.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abarrotescasavargas.operamovil.Main.Mantenimiento.ActivityMantenimiento;
import com.abarrotescasavargas.operamovil.Main.Transferencias.TransferenciasActivity;
import com.abarrotescasavargas.operamovil.R;


public class menuAplicaciones extends AppCompatActivity {

    ImageButton btnproveedor,btnreclutamiento,btnrezagado,btnConcursoVentas,btnCompraTransfer,btnVerificador,btnNuevoArticulo,btnMantenimiento,btnTransferencia;
    TextView textView9prove,textView10Reclu,textView11Reza,textView14ventas,textView26compra,textView37Veri,textView49,textView77,textView80,
            versionProveedor,versionRezagados,versionReclutamiento,versionVentas,versionCompras,versionVerificador,versionNuevo;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);

        setContentView(R.layout.activity_menu_aplicaciones);

        btnproveedor = findViewById(R.id.btnproveedor);
        textView9prove = findViewById(R.id.textView9prove);
        //versionProveedor = findViewById(R.id.versionProveedor);

        btnreclutamiento = findViewById(R.id.btnreclutamiento);
        textView10Reclu=findViewById(R.id.textView10Reclu);
        //versionReclutamiento= findViewById(R.id.versionReclutamiento);

        btnrezagado = findViewById(R.id.btnrezagado);
        textView11Reza= findViewById(R.id.textView11Reza);
        //versionRezagados=findViewById(R.id.versionRezagados);

        btnConcursoVentas = findViewById(R.id.btnConcursoVentas);
        textView14ventas = findViewById(R.id.textView14ventas);
        //versionVentas = findViewById(R.id.versionVentas);

        btnCompraTransfer = findViewById(R.id.btnCompraTransfer);
        textView26compra=findViewById(R.id.textView26compra);
        //versionCompras = findViewById(R.id.versionCompras);

        btnVerificador = findViewById(R.id.btnVerificador);
        textView37Veri =findViewById(R.id.textView37Veri);
        //versionVerificador = findViewById(R.id.versionVerificador);

        btnNuevoArticulo = findViewById(R.id.btnNuevoArticulo);
        textView49 = findViewById(R.id.textView49);
        //versionNuevo = findViewById(R.id.versionNuevo);

        btnMantenimiento = findViewById(R.id.btnMantenimiento);
        textView77 = findViewById(R.id.textView77);

        btnTransferencia = findViewById(R.id.btnTransferencia);
        textView80 =findViewById(R.id.textView80);


        btnproveedor.setAnimation(animacion1);
        textView9prove.setAnimation(animacion1);
        //versionProveedor.setAnimation(animacion1);

        btnreclutamiento.setAnimation(animacion1);
        textView10Reclu.setAnimation(animacion1);
        //versionReclutamiento.setAnimation(animacion1);

        btnrezagado.setAnimation(animacion1);
        textView11Reza.setAnimation(animacion1);
        //versionRezagados.setAnimation(animacion1);

        btnConcursoVentas.setAnimation(animacion1);
        textView14ventas.setAnimation(animacion1);
        //versionVentas.setAnimation(animacion1);

        btnCompraTransfer.setAnimation(animacion1);
        textView26compra.setAnimation(animacion1);
        //versionCompras.setAnimation(animacion1);

        btnVerificador.setAnimation(animacion1);
        textView37Veri.setAnimation(animacion1);
        //versionVerificador.setAnimation(animacion1);

        btnNuevoArticulo.setAnimation(animacion1);
        textView49.setAnimation(animacion1);
        //versionNuevo.setAnimation(animacion1);

        btnMantenimiento.setAnimation(animacion1);
        textView77.setAnimation(animacion1);

        btnTransferencia.setAnimation(animacion1);
        textView80.setAnimation(animacion1);

        btnproveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent (v.getContext(), Registro2.class);
                Intent intent = new Intent (v.getContext(), listaBitacora.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.transition.in_left, R.transition.out_left);
            }
        });

        btnreclutamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent (v.getContext(),Reclutamiento.class);
                //startActivityForResult(intent, 0);
                overridePendingTransition(R.transition.in_left, R.transition.out_left);
            }
        });


        btnrezagado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (v.getContext(), Rezagado.class);
                startActivityForResult(i, 0);
                overridePendingTransition(R.transition.in_left, R.transition.out_left);
            }
        });

        btnConcursoVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (v.getContext(), concursoVentas.class);
                startActivityForResult(i, 0);
                overridePendingTransition(R.transition.in_left, R.transition.out_left);
            }
        });

        btnCompraTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (v.getContext(), comprasTransfer.class);

                startActivityForResult(i, 0);
                overridePendingTransition(R.transition.in_left, R.transition.out_left);
            }
        });

        btnVerificador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (v.getContext(), VerificadorProductos.class);
                startActivityForResult(i, 0);
                overridePendingTransition(R.transition.in_left, R.transition.out_left);
            }
        });

        btnNuevoArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), NuevoArticulo.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.transition.in_left, R.transition.out_left);
            }
        });

        btnMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (v.getContext(), ActivityMantenimiento.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.transition.in_left, R.transition.out_left);
            }
        });

        btnTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent (v.getContext(), Transferencias.class);
                Intent i = new Intent (v.getContext(), TransferenciasActivity.class);
                startActivityForResult(i, 0);
                overridePendingTransition(R.transition.in_left, R.transition.out_left);
            }
        });

    }
}