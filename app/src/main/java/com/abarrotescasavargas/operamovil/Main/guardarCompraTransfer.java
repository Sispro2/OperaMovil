package com.abarrotescasavargas.operamovil.Main;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.abarrotescasavargas.operamovil.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class guardarCompraTransfer extends AppCompatActivity {

    java.sql.ResultSet tr,tq,tj,tn,ta,tx,tk,tp,tc;

    TextView idcodigobarras,idclaveart,idnombreArt,idItemArt,unidadMedida,unidadMedidaCompra;
    ImageButton btnGuardarArticulo;
    EditText cantidadContada,cantidadOrdenCompra;

    String datoClaveArt,datoidnombreArt,datoFolio="";

    String idOrdenCompra = "";
    double costoUnitario =0;
    String idUnidadMedida = "";
    int idArticulo=0;
    int relacionVenta=0;

    int idFactorCompra=0;
    int idFactorVenta=0;

    String existeFolio="";

    RadioButton rbCajas,rbPiezas,rbbulto,rbkilos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_compra_transfer);

        final SQLiteDatabase dbs = openOrCreateDatabase("Servidor.db", MODE_PRIVATE, null);
        final Servidor Srv = new Servidor(getApplicationContext());

        final ServidorSuc SrvSUC = new ServidorSuc(getApplicationContext(),true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        datoFolio = getIntent().getStringExtra("folio");

        idcodigobarras = findViewById(R.id.codigobarras);
        String datoCodigoBarras = getIntent().getStringExtra("codigoBarras");
        idcodigobarras.setText(datoCodigoBarras);

        idclaveart = findViewById(R.id.idclaveart);
        datoClaveArt = getIntent().getStringExtra("claveArt");
        idclaveart.setText(datoClaveArt);

        idnombreArt = findViewById(R.id.idnombreArt);
        String datoidnombreArt = getIntent().getStringExtra("nombreArt");
        idnombreArt.setText(datoidnombreArt);

        unidadMedida = findViewById(R.id.unidadMedida);
        String datounidadMedida = getIntent().getStringExtra("unidadMedida");
        unidadMedida.setText(datounidadMedida);

        unidadMedidaCompra =findViewById(R.id.unidadMedidaCompra);
        String datounidadMedidaCompra = getIntent().getStringExtra("unidadMedida");
        unidadMedidaCompra.setText(datounidadMedidaCompra);

        cantidadOrdenCompra =findViewById(R.id.cantidadOrdenCompra);
        String datocantidadOrdenCompra = getIntent().getStringExtra("UnidadOrdenCompra");
        cantidadOrdenCompra.setText(datocantidadOrdenCompra);

        cantidadOrdenCompra.setEnabled(Boolean.parseBoolean(datocantidadOrdenCompra));

        cantidadContada = findViewById(R.id.cantidadContada);

        btnGuardarArticulo = findViewById(R.id.btnGuardarArticulo);

        tp = Srv.tabla("select id_unidad_compra,id_unidad_venta,unicompra.descripcion,univenta.descripcion  " +
                "from articulo art " +
                "inner join unidad_medida unicompra on unicompra.id_unidad_medida = art.id_unidad_compra " +
                "inner join unidad_medida univenta on univenta.id_unidad_medida = art.id_unidad_venta " +
                "where clave= '"+datoClaveArt+ "' ",true);

        try {
            idFactorCompra = tp.getInt(1);
            idFactorVenta = tp.getInt(2);

        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }

        rbCajas  = findViewById(R.id.rb_caja);
        rbPiezas = findViewById(R.id.rb_piezas);

        rbbulto = findViewById(R.id.rb_bulto);
        rbkilos = findViewById(R.id.rb_kilos);

        if(idFactorCompra ==1){

            rbCajas  = findViewById(R.id.rb_caja);
            rbCajas.setVisibility(View.INVISIBLE);

            rbPiezas = findViewById(R.id.rb_piezas);
            rbPiezas.setVisibility(View.INVISIBLE);

        }else if(idFactorCompra ==2){

            rbbulto = findViewById(R.id.rb_bulto);
            rbbulto.setVisibility(View.INVISIBLE);

            rbkilos = findViewById(R.id.rb_kilos);
            rbkilos.setVisibility(View.INVISIBLE);

        }


        btnGuardarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                guardar();
            }
        });
    }

    private void guardar() {


        rbCajas  = findViewById(R.id.rb_caja);
        rbPiezas = findViewById(R.id.rb_piezas);

        rbbulto = findViewById(R.id.rb_bulto);
        rbkilos = findViewById(R.id.rb_kilos);


        AlertDialog.Builder builder = new AlertDialog.Builder(guardarCompraTransfer.this);
        builder.setTitle("Importante");
        builder.setMessage("Recuerda seleccionar UNIDAD DE MEDIDA y llenar el campo de CANTIDAD");
        builder.setPositiveButton("OK",null);
        builder.create();
        builder.show();

        if(!cantidadContada.getText().toString().equals("")) {

            datoFolio = getIntent().getStringExtra("folio");

            final SQLiteDatabase dbs = openOrCreateDatabase("Servidor.db", MODE_PRIVATE, null);
            final Servidor Srv = new Servidor(getApplicationContext());

            final ServidorSuc SrvSUC = new ServidorSuc(getApplicationContext(),true);


            String datoidnombreArt = getIntent().getStringExtra("nombreArt");
            datoClaveArt = getIntent().getStringExtra("claveArt");
            String cantidad = cantidadContada.getText().toString();

            String nombreArt = datoidnombreArt.toString();
            String clave = datoClaveArt.toString();
            Float contado = parseFloat(cantidad);


            String idSucursal = getIntent().getStringExtra("idSucursal");
            int IDSucursal = parseInt(idSucursal);

            String fechaRegistro = getIntent().getStringExtra("fechaRegistro");
            String fechaFinVigencia = getIntent().getStringExtra("fechaFinVigencia");


            // el que parsea
           SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


            // el que formatea
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");

            Date fechRegis = null;
            try {
                fechRegis = parseador.parse(fechaRegistro);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            // el que parsea
            SimpleDateFormat parseador1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            // el que formatea
            SimpleDateFormat formateador1 = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");

            Date fechVigencia = null;
            try {
                fechVigencia = parseador1.parse(fechaFinVigencia);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            String observaciones = "";

            String subtotal = getIntent().getStringExtra("subtotal");

            String descuento = getIntent().getStringExtra("descuentoOrden");

            String totalNeto = getIntent().getStringExtra("totalNeto");

            String idProveedor = getIntent().getStringExtra("idProveedor");

            int IDProveedor = parseInt(idProveedor);

            String datocantidadOrdenCompra = getIntent().getStringExtra("UnidadOrdenCompra");
           // int catidadOrdenCompra =0;

           // catidadOrdenCompra = Integer.parseInt(datocantidadOrdenCompra);

            double importe=0;
            //String fechaCancelado = "NULL";

            Float cantidadPiezas = null;


            ////////////// chec´s cajas bultos kilos piezas ///////////////////////////////

            if(rbCajas.isChecked() == true){

                tn =Srv.tabla("select (relacion_venta * "+contado+") as contado_Real from articulo where clave= '"+ clave +"' ",true);

                try {
                    cantidadPiezas= tn.getFloat(1);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }

            }else if (rbPiezas.isChecked() == true){

                cantidadPiezas= contado;

            }


           if (rbbulto.isChecked() == true){

                tn =Srv.tabla("select (relacion_venta * "+contado+") as contado_Real from articulo where clave= '"+ clave +"' ",true);

                try {
                    cantidadPiezas= tn.getFloat(1);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }


            }else if ( rbkilos.isChecked()==true){

                cantidadPiezas= contado;

            }


            ////////////// chec´s cajas bultos kilos piezas ///////////////////////////////

            int idPromovimientoAlmacenEstado = 1;
            int idCompra = 0;

            int restl = 0;
            int restl1 = 0;
            int restl2 = 0;
            Integer restl3 = null;
            Integer restl4 = null;
            int consecutivo=0;
            Float PrecioUnitario= null;

            tq = Srv.tabla("select * from articulo where clave ='"+ clave +"' ", true);

            try {
                idArticulo = tq.getInt(1);
                idUnidadMedida= tq.getString(14);
                relacionVenta = tq.getInt(16);


            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }


            tc = Srv.tabla("select folio from orden_compra where folio = '"+datoFolio+"'   ",true);

            try {

                existeFolio = tc.getString(1);

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }

           // if(existeFolio.equals("")){

                /*restl = Srv.ejecuta("INSERT INTO orden_compra (id_sucursal,folio,observaciones,subtotal,descuento,total_neto,fecha_registro,id_proveedor,fecha_fin_vigencia,id_promovimiento_almacen_estado)" +
                        " VALUES ("+ IDSucursal + ",'" + datoFolio + "','" + observaciones.toString() + "','" + subtotal.toString() + "','" + descuento + "' " +
                        ",'" + totalNeto.toString() + "','" + formateador.format(fechRegis) + "'," + IDProveedor + " " +
                        ",'" + formateador1.format(fechVigencia) + "'," + idPromovimientoAlmacenEstado + ")");

                 */


                tr = Srv.tabla("select id_orden_compra from orden_compra where folio = '"+ datoFolio +"' ", true);

                try {
                    idOrdenCompra = tr.getString(1);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }

                int cantidadOrdenCompraPiezas=0;

                ta = Srv.tabla("select (relacion_venta * "+datocantidadOrdenCompra+") as contado_Real from articulo where id_articulo= "+idArticulo+" ",true);

                try {
                    cantidadOrdenCompraPiezas= ta.getInt(1);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }


                //sacar costo unitario directo del SUC  ORDEN DE COMPRA//

                tx = SrvSUC.tabla("select (DO_CLISTA)-((DO_DESCTO /100) *DO_CLISTA) from DORDEN where DO_NUMORD= '"+ datoFolio +"' and DO_CVEART = '"+ clave +"' ",true);

                try {
                    PrecioUnitario = tx.getFloat(1);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }

                Float preciUnitario =null;

                preciUnitario = PrecioUnitario / relacionVenta;

                importe = (PrecioUnitario / relacionVenta)* cantidadOrdenCompraPiezas;
                int ingresado=0;
                int mayoreo=1;

                Float sumaImportes = null;

                tj = Srv.tabla("select ISNULL((MAX (consecutivo))+1,0) from orden_compra_detalle where id_orden_compra='"+ idOrdenCompra +"' ", true);

                try {
                    consecutivo = tj.getInt(1);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }

               // restl1 = Srv.ejecuta("INSERT INTO orden_compra_detalle (id_orden_compra,id_articulo,consecutivo,cantidad,costo_unitario,id_unidad_medida,descuento,importe,ingresado,es_mayoreo)" +
                       // " VALUES ('"+ idOrdenCompra +"',"+ idArticulo +","+consecutivo+","+cantidadOrdenCompraPiezas+",'"+preciUnitario+"','"+idUnidadMedida+"',"+descuento+","+importe+","+ingresado+","+mayoreo+")");


                restl2 = Srv.ejecuta("INSERT INTO reporteCompras (folio_Compra,nombre_articulo,clave_art,contado_Real,id_articulo)" +
                        " VALUES ('" + datoFolio + "','"+datoidnombreArt+"','"+ clave +"',"+cantidadPiezas+","+idArticulo+")");


                tk = Srv.tabla("select SUM(importe) from orden_compra_detalle where id_orden_compra= '"+ idOrdenCompra +"' ",true);

                try {
                    sumaImportes = tk.getFloat(1);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }

               // restl3 = Srv.ejecuta("update orden_compra set subtotal = "+sumaImportes+" where folio = '" + datoFolio + "' ");
               // restl4 = Srv.ejecuta("update orden_compra set total_neto = "+sumaImportes+" where folio = '" + datoFolio + "' ");



            /*restl=Srv.ejecuta("INSERT INTO reporteCompras (folio_Compra,nombre_articulo,clave_art,contado)" +
                    " VALUES ('"+ datoFolio +"','"+ nombreArt.toString() +"','"+ clave.toString() +"','"+ contado.toString() +"')");
             */


            /* restl = Srv.ejecuta("INSERT INTO reporteCompras (folio_Compra,nombre_articulo,clave_art,contado_Real,id_sucursal,observaciones,subtotal,descuento,total_neto,fecha_registro,id_proveedor,fecha_fin_vigencia,id_promovimiento_almacen_estado)" +
                    " VALUES ('" + datoFolio + "','" + nombreArt.toString() + "','" + clave.toString() + "', " + contado + " ," + IDSucursal + ",'" + observaciones.toString() + "'" +
                    ",'" + subtotal.toString() + "','" + descuento + "','" + totalNeto.toString() + "','" + formateador.format(fechRegis) + "'," + IDProveedor + " " +
                    ",'" + formateador1.format(fechVigencia) + "'," + idPromovimientoAlmacenEstado + ")");

             */

                Toast.makeText(getApplicationContext(), "ENVIADO CON EXITO", Toast.LENGTH_LONG).show();
                Intent incant = new Intent(this, checarCompraTransfer.class);
                finish();


               /* new AlertDialog.Builder(this)
                        .setTitle("                 !!!!! ERROR !!!!!       ")
                        .setMessage("No se guardo en la Base")
                        .show();

                */

            //}else{

                //Toast.makeText(getApplicationContext(), "ya existe un folio asi", Toast.LENGTH_LONG).show();

          //  }


        }else{
            //Toast.makeText(getApplicationContext(), "LLena el campo CANTIDAD / Unidad de Medida", Toast.LENGTH_LONG).show();
        }
    }
}