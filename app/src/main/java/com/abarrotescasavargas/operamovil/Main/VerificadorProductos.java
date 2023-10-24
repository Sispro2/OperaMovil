package com.abarrotescasavargas.operamovil.Main;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.abarrotescasavargas.operamovil.Main.BaseDatos.AdminSQLite;
import com.abarrotescasavargas.operamovil.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class VerificadorProductos extends AppCompatActivity {

    Button btnEscanerVerificar;
    java.sql.ResultSet tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificador_productos);

        final SQLiteDatabase dbs = openOrCreateDatabase("Servidor.db",MODE_PRIVATE,null);
        final Servidor Srv = new Servidor(getApplicationContext());

        btnEscanerVerificar = findViewById(R.id.btnEscanerVerificar);

        AdminSQLite admin = new AdminSQLite(this,"articulos",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();


        //Consulta a una tabla articulos

        try {

            Cursor fila = db.rawQuery("select clave from Articulos ",null);

            int contador=0;
            contador = fila.getCount();
            if(fila.getCount()>0){

                Toast.makeText(getApplicationContext(), "Existen registros en la tabla  " + contador, Toast.LENGTH_SHORT).show();

            }else{

               tv = Srv.tabla("select clave,codigo_barras1,codigo_barras2,desc_mayoreo from articulo", true);

                //For recorre todos los resultados
                //{
                try {
                    if(tv.getRow()==0){
                        Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
                    }else{
                        do{
                            registro.put("clave", tv.getString(1));
                            registro.put("codigoBarras1", tv.getString(2));
                            registro.put("codigoBarras2", tv.getString(3));
                            registro.put("descripcion", tv.getString(4));
                            db.insert("Articulos", null, registro);

                        }while(tv.next());
                        db.close();

                    }

                }catch (Exception ex){ }

            }

        }catch (Exception exception2){}

        //}

        Button btnEscanerVerificar = (Button)findViewById(R.id.btnEscanerVerificar);
        btnEscanerVerificar.setOnClickListener(mOnClic);

    }

    private View.OnClickListener mOnClic = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnEscanerVerificar:
                    new IntentIntegrator(VerificadorProductos.this).initiateScan();
                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        //final SQLiteDatabase dbs = openOrCreateDatabase("Servidor.db",MODE_PRIVATE,null);
        //final Servidor Srv = new Servidor(getApplicationContext(),true);

        String CodigoBaras="";
        String  tipoMovimineto="";
        String idTipoMovimiento="";
        String proveedor="";
        String claveproveedor="";
        String nomproveedor="";
        String idMovAlmacen="";

        if (result !=null)
            try{
                if (result.getContents() !=null) {
                    CodigoBaras = result.getContents();

                    AdminSQLite admin = new AdminSQLite(this,"articulos",null,1);
                    SQLiteDatabase db = admin.getWritableDatabase();
                    Cursor fila = db.rawQuery("select clave from Articulos where codigoBarras1= '"+ CodigoBaras +"'",null);
                    if(fila.moveToFirst()){

                        //Toast.makeText(getApplicationContext(), "Existe todosssss chiringuito", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(this, ArticuloExiste.class);
                        startActivity(intent);
                    }
                    else{

                        Intent intent = new Intent(this, ArticuloNoExiste.class);
                        startActivity(intent);

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "NO RECONOCE QR" , Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
                Log.e("Error:",e.getMessage());
            }
    }
}