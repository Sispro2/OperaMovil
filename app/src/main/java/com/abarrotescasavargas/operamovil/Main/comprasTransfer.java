package com.abarrotescasavargas.operamovil.Main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.abarrotescasavargas.operamovil.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class comprasTransfer extends AppCompatActivity {

    Button btnRecapcionTransfer;
    java.sql.ResultSet tb,tt,tr;
    EditText editScaner;
    String codigoCompra="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras_transfer);

        btnRecapcionTransfer = findViewById(R.id.btnRecapcionTransfer);

        editScaner = findViewById(R.id.editScaner);


        editScaner.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    final Servidor Srv = new Servidor(getApplicationContext(),true);

                    editScaner.getText().toString();

                    codigoCompra = editScaner.getText().toString();

                    String proveedor="";
                    String claveproveedor="";
                    String nomproveedor="";

                    if (codigoCompra !=null){

                       try {

                           tb = Srv.tabla("select * from MORDEN where MO_NUMORD = '"+ codigoCompra +"' ", true);

                           proveedor=tb.getString(2);

                           tr = Srv.tabla("select * from proveedor where clave= '"+ proveedor +"' ", true);

                           claveproveedor = tr.getString(2);
                           nomproveedor = tr.getString(3);


                           if (tb.getRow()!=0){

                               Intent intent = new Intent(comprasTransfer.this, checarCompraTransfer.class);
                               intent.putExtra("folio",codigoCompra.toString());
                               //intent.putExtra("tipoMovimiento",tipoMovimineto.toString());
                               intent.putExtra("claveproveedor",claveproveedor.toString());
                               intent.putExtra("nomproveedor",nomproveedor.toString());
                               //intent.putExtra("idMovAlmacen",idMovAlmacen.toString());

                               startActivity(intent);

                           }else{
                               Toast.makeText(getApplicationContext(), " No existe el folio : "+ codigoCompra, Toast.LENGTH_SHORT).show();
                           }


                       }catch(Exception e){

                            new AlertDialog.Builder(comprasTransfer.this)
                                    .setTitle("                 !!!!! ERROR !!!!!       ")
                                    .setMessage("QR no es una orden de compra")
                                    .show();


                            // Log.e("Error:",e.getMessage());
                        }

                    }

                    return true;
                }
                return false;
            }
        });

        btnRecapcionTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnRecapcionTransfer:
                        new IntentIntegrator(comprasTransfer.this).initiateScan();
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);


        final SQLiteDatabase dbs = openOrCreateDatabase("Servidor.db",MODE_PRIVATE,null);
        final Servidor SrvSuc = new Servidor(getApplicationContext(),true);
        final Servidor SrvPv = new Servidor(getApplicationContext());




        String folio="";
        String  tipoMovimineto="";
        String idTipoMovimiento="";
        String proveedor="";
        String claveproveedor="";
        String nomproveedor="";
        String idMovAlmacen="";

        if (result !=null)
            try{
                if (result.getContents() !=null) {
                    folio= result.getContents();

                    //tb = Srv.tabla("select * from movimiento_almacen where id_movimiento_almacen_tipo=1 and folio = '"+ folio +"' ", true);
                    tb = SrvSuc.tabla("select * from MORDEN where MO_NUMORD = '"+ folio +"' ", true);

                    //idMovAlmacen=tb.getString(1);
                    //idTipoMovimiento=tb.getString(2);
                    proveedor=tb.getString(2);

                    tr = SrvPv.tabla("select * from proveedor where clave= '"+ proveedor +"' ", true);

                    claveproveedor = tr.getString(2);
                    nomproveedor = tr.getString(3);


                    /*tt = Srv.tabla("select * from movimiento_almacen_tipo where id_movimiento_almacen_tipo= '"+ idTipoMovimiento +"'", true);

                    tipoMovimineto = tt.getString(2);

                     */

                    if (tb.getRow()!=0){

                        Intent intent = new Intent(this, checarCompraTransfer.class);
                        intent.putExtra("folio",folio.toString());
                        //intent.putExtra("tipoMovimiento",tipoMovimineto.toString());
                        intent.putExtra("claveproveedor",claveproveedor.toString());
                        intent.putExtra("nomproveedor",nomproveedor.toString());
                        //intent.putExtra("idMovAlmacen",idMovAlmacen.toString());

                        startActivity(intent);

                    }else{
                        Toast.makeText(getApplicationContext(), " No existe el folio : "+ folio, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "NO RECONOCE QR" , Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){

                new AlertDialog.Builder(this)
                        .setTitle("                 !!!!! ERROR !!!!!       ")
                        .setMessage("QR no es una orden de compra")
                        .show();


               // Log.e("Error:",e.getMessage());
            }
    }

}