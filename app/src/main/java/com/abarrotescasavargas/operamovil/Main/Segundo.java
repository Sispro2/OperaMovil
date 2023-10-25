package com.abarrotescasavargas.operamovil.Main;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abarrotescasavargas.operamovil.R;

import java.io.IOException;

public class Segundo extends AppCompatActivity {

    private Firma guardarFirma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);

        guardarFirma=(Firma)findViewById(R.id.lienzo);
        Button guardar = findViewById(R.id.btn_guardar_firma);
        Button limpiar = findViewById(R.id.btn_limpiar_firma);
        Button salir = findViewById(R.id.btn_salir_firma);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guardarFirma.comprovarCanvas()){
                    Bitmap bitmap= guardarFirma.getBitmap();
                    try {
                        //Registro2.pintarFirma(bitmap);
                        RegistroBitacora.pintarFirma(bitmap);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    Segundo.super.onBackPressed();

                }else {
                    Toast.makeText(getApplicationContext(),"Se necesita Firmar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarFirma.clearCanvas();
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Segundo.super.onBackPressed();

            }
        });
    }
}
    /*class  Vista extends View{

        float x = 10;
        float y = 10;
        String accion = "accion";
        Path path = new Path();

        public Vista(Context context) {
            super(context);
        }


        public void  onDraw(Canvas canvas){

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(Color.BLACK);

            int ancho = canvas.getWidth();
            int alto = canvas.getHeight();

            if (accion=="down")
                path.moveTo(x,y);
            if (accion=="move")
                path.lineTo(x,y);

            canvas.drawPath(path,paint);
        }
        public boolean onTouchEvent(MotionEvent e){
            x= e.getX();
            y= e.getY();

            if (e.getAction()==MotionEvent.ACTION_DOWN)
                accion = "down";
            if (e.getAction()==MotionEvent.ACTION_MOVE)
                accion = "move";

            return true;
        }
    }
    */

