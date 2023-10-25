package com.abarrotescasavargas.operamovil.Main;

import static java.lang.Integer.parseInt;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.abarrotescasavargas.operamovil.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VerReporte extends AppCompatActivity {

    ListView listaGuardados;

    java.sql.ResultSet tm,th,tz,tn,tq,tg,tk,ta,tj,tp,tb,tx,ty,te,tf,ff,cp;

    String datoFolio="";
    String idProve="";

    String idProveCombo="";

    Button btnGuardarCompra;
    EditText folioFiscal;
    ArrayList<Float>listArtMov;
    String idArti;
    String subtring ="";
    String claveUniProvedor="";
    String claveUniProvedorCombo="";

    EditText observaciones;

    String pro1;

    String compararFolioFactura="";

    ArrayList listaArtContado = new ArrayList();

    Spinner spinnerFolioFactura;

    ProgressDialog progressDialog;


    String consulta="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ver_reporte);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        final SQLiteDatabase dbs = openOrCreateDatabase("Servidor.db",MODE_PRIVATE,null);

        final Servidor SrvSuc = new Servidor(getApplicationContext(),true);
        final Servidor SrvPv = new Servidor(getApplicationContext());

        btnGuardarCompra = findViewById(R.id.btnGuardarCompra);
        listaGuardados = findViewById(R.id.listaGuardados);

        spinnerFolioFactura = findViewById(R.id.spinnerFolioFactura);

        datoFolio = getIntent().getStringExtra("folio");

        final Integer[] restl8 = {null};
        final Integer[] restl9 = {null};

        final int[] id_articuloRepo = {0};

        //progressBar3=findViewById(R.id.progressBar3);
       // progressBar3.setVisibility(View.GONE);

        //////////////////////////////LLENA LA LISTA /////////////////////////////////////
        final ArrayList listaArtContado = new ArrayList();

        tm = SrvPv.tabla("select CONCAT (clave_art,'                  ',nombre_articulo,'                    ',contado_Real) as 'Contados' from reporteCompras where folio_Compra = '"+ datoFolio +"' order by 1 ", true);

        try {
            if(tm.getRow()==0){
                Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
            }else{
                do{
                    listaArtContado.add(tm.getString(1));
                }while(tm.next());
            }

        }catch (Exception ex){ }

        final ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaArtContado);
        listaGuardados.setAdapter(adapter);

        //////////////////////////////LLENA LA LISTA /////////////////////////////////////


//////////////////////////////////////eliminar de todos ///////////////////////////////////////////////////
        listaGuardados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int which_item = position;
                String elementodeLista = (String) listaGuardados.getItemAtPosition(position);

                int aux = 0;
                idArti = listaGuardados.getItemAtPosition(position).toString();
                aux = idArti.indexOf(' ');
                subtring = idArti.substring(0,aux);

                new AlertDialog.Builder(VerReporte.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("articulo a eliminar de la lista")
                        .setMessage("te equivocaste al contar? / no tuviste que contarlo? ")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                  SrvPv.ejecuta("delete from reporteCompras where folio_Compra = '"+ datoFolio +"' and clave_art= '"+subtring+"'  ");

                                listaArtContado.remove(which_item);
                                adapter.notifyDataSetChanged();
                            }
                        })
                       .setNegativeButton("NO",null)
                       .show();

            }
        });

        //////////////////////////////////////eliminar de todos ///////////////////////////////////////////////////


        btnGuardarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //hiloEnviar();

              new AlertDialog.Builder(VerReporte.this)
                .setTitle("                         GUARDAR COMPRA")
                .setMessage("¿Deseas guardar la compra?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressDialog = new ProgressDialog(VerReporte.this);
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                guardarCompra(listaArtContado);
                                progressDialog.cancel();
                            }
                        },5000);

                    }

                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Mensaje", "se cancelo envio");
                    }
                })
                .show();

            }
        });


        idProveCombo = getIntent().getStringExtra("idProveedor");

        cp = SrvPv.tabla("select * from proveedor where id_proveedor = '"+idProveCombo+"' ",true);

        try {

            claveUniProvedorCombo= cp.getString(2);

        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }


        /////////////////////////////COMBO LLENADO folio factura////////////////////////////////////////////

        ArrayList folioFactura = new ArrayList();
        folioFactura.add("  - Folios Facturas -");

        ff = SrvPv.tabla("SELECT  \n" +
                "RF_IDREGI AS ID, \n" +
                "RF_FOLFIS AS 'Folio Fiscal', \n" +
                "RF_IMPORT AS 'Importe', \n" +
                "RF_Registrada AS Activo, \n" +
                "max(p.id_proveedor) as id_proveedor  \n" +
                "FROM REGFACTCOMPRAS rf  \n" +
                "Left Join datos_facturacion df on df.rfc = rf.RF_RFCPRO  \n" +
                "Left Join proveedor p on p.id_datos_facturacion = df.id_datos_facturacion  \n" +
                "WHERE (p.clave like '"+  claveUniProvedorCombo +"') AND RF_Registrada = 0  \n" +
                "group by RF_IDREGI, RF_FOLFIS, RF_IMPORT, RF_Registrada ORDER BY RF_FOLFIS ASC;  ", true);

        try {
            if(ff.getRow()==0){
                Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
            }else{
                do{
                    folioFactura.add(ff.getString(2));
                }while(ff.next());
            }

        }catch (Exception ex){ }

        spinnerFolioFactura.setAdapter(new ArrayAdapter(this, R.layout.items_spinnerventas,folioFactura ));
        spinnerFolioFactura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int auxili = 0;
                pro1 = spinnerFolioFactura.getItemAtPosition(pos).toString();

                //auxili =pro1.indexOf('-');
                //subIDLiena = pro1.substring(0,auxili);

            }
            public void onNothingSelected(AdapterView<?> parent) {    }
        });

        /////////////////////////////COMBO LLENADO FOLIO FACTURA  ////////////////////////////////////////////




    }

    public void guardarCompra(final ArrayList listaArtContado) {


        /*new AlertDialog.Builder(this)
                .setTitle("                         GUARDAR COMPRA")
                .setMessage("¿Deseas guardar la compra?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }

                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Mensaje", "se cancelo envio");
                    }
                })
                .show();

         */

        //////////////////////////////////////////// solo para guardar ///////////////////////////

        folioFiscal = findViewById(R.id.folioFiscal);
        observaciones = findViewById(R.id.observaciones);

        listArtMov = new ArrayList<>();

        folioFiscal.getText().toString();
        observaciones.getText().toString();

        listaArtContado.size();

        if(listaArtContado.size()==0){

            Toast.makeText(getApplicationContext(), "no existen articulos a Guardar", Toast.LENGTH_LONG).show();

        }else if(folioFiscal.getText().toString().equals("")){

            Toast.makeText(getApplicationContext(), "llena campo folio fiscal", Toast.LENGTH_LONG).show();

        }else if(observaciones.getText().toString().equals("")){

            Toast.makeText(getApplicationContext(), "llena campo Observaciones", Toast.LENGTH_LONG).show();
        }else {

            final SQLiteDatabase dbs = openOrCreateDatabase("Servidor.db", MODE_PRIVATE, null);

            final Servidor SrvSuc = new Servidor(getApplicationContext(),true);
            final Servidor SrvPv = new Servidor(getApplicationContext());

            Date date = new Date();

            int idMovimientoAlmacenTipo = 1;
            String idSucursal = getIntent().getStringExtra("idSucursal");
            int IDSucursal = parseInt(idSucursal);
            int idAlmacen = 2;
            int idCaja = 2;
            String folio = "";
            //String observaciones = "Ingresado desde la APP";


            String descuento = getIntent().getStringExtra("descuentoOrden");

            //int descuento = parseInt(DescuentoOrden) ;

            final SimpleDateFormat fechacompleta = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String fehaRegistro = fechacompleta.format(date);
            int idUsuario = 1;
            int idUsuarioAutorizo = 1;

            int idReferecia = 0;
            int idOrdenCompra = 0;
            Float contadoReal = null;
            int IDarticulo = 0;

            datoFolio = getIntent().getStringExtra("folio");

            th = SrvPv.tabla(" select * from orden_compra where folio = '" + datoFolio + "' ", true);

            try {
                idReferecia = th.getInt(1);
                idOrdenCompra = th.getInt(1);


            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }

            int idSucursalDestino;
            String idAlmacenOrigen = "";
            idProve = getIntent().getStringExtra("idProveedor");

            te = SrvPv.tabla("select * from proveedor where id_proveedor = '"+idProve+"' ",true);

            try {

                claveUniProvedor= te.getString(2);

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }


            String idCliente = "";
            int idConsolida;
            String idSucursalOrigen = "";
            int consecutivoMov = 0;
            String folioCreado = "";

            Float totalIVA = null;
            Float totalIEPS = null;

            Float subtotalREAL = null;
            Float totalNetoREAL = null;

            int idMovimientoAlmacen = 0;
            int almacenDetallesConsecutivo = 0;


            tz = SrvPv.tabla("select ISNULL((MAX (consecutivo))+1,0) from movimiento_almacen where id_movimiento_almacen_tipo=1 ", true);

            try {
                consecutivoMov = tz.getInt(1);

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }

            // String subtotalREAL = getIntent().getStringExtra("subtotal");
            //String totalNetoREAL = getIntent().getStringExtra("totalNeto");


            tg = SrvPv.tabla("select " +
                    "SUM (CONVERT (decimal(12,4), (repcom.contado_real * ord.costo_unitario) *(impu.tasa /100))) as'IEPS total', " +
                    "SUM (CONVERT (decimal(12,4),(ord.costo_unitario *(impu.tasa /100)+ord.costo_unitario)*imp.tasa/100)) as'IVA total' " +
                    "from reporteCompras repcom " +
                    "inner join articulo art on art.clave = repcom.clave_art " +
                    "inner join impuesto imp on imp.id_impuesto = art.id_iva_venta  " +
                    "inner join impuesto impu on impu.id_impuesto = art.id_ieps_venta " +
                    "inner join orden_compra_detalle ord on ord.id_articulo = repcom.id_articulo " +
                    "where folio_compra ='" + datoFolio + "' and ord.id_orden_compra = " + idOrdenCompra + " ", true);

            try {
                totalIEPS = tg.getFloat(1);
                totalIVA = tg.getFloat(2);

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }

            tk = SrvPv.tabla("select " +
                    "sum (convert (decimal(12,4), repcom.contado_real * ord.costo_unitario)) as 'subtotal', " +
                    "sum (convert (decimal(12,4),(repcom.contado_real * ord.costo_unitario)+(CONVERT (decimal(12,4), (repcom.contado_real * ord.costo_unitario) *(impu.tasa /100))+convert(decimal(12,4),(ord.costo_unitario *(impu.tasa /100)+ord.costo_unitario)*imp.tasa/100)))) as 'total neto' " +
                    "from reporteCompras repcom " +
                    "inner join articulo art on art.clave = repcom.clave_art " +
                    "inner join impuesto imp on imp.id_impuesto = art.id_iva_venta " +
                    "inner join impuesto impu on impu.id_impuesto = art.id_ieps_venta " +
                    "inner join orden_compra_detalle ord on ord.id_articulo = repcom.id_articulo " +
                    "where folio_compra ='" + datoFolio + "' and ord.id_orden_compra = " + idOrdenCompra + " ", true);

            try {
                subtotalREAL = tk.getFloat(1);
                totalNetoREAL = tk.getFloat(2);

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }




            //tn = SrvPv.tabla("EXEC	 [dbo].[spGuardarMovimientoAlmacenAPP] " + idMovimientoAlmacenTipo + ", 'dos', " + IDSucursal + ", " + idAlmacen + ", " + idCaja + ", 5, '" + observaciones.getText().toString() + " app"+ "'," + subtotalREAL + ",0, " + totalNetoREAL + ", " + idUsuario + ", " + idUsuarioAutorizo + ", " + idReferecia + ", '" + datoFolio + "', '" + folioFiscal.getText().toString() + "', '" + folioFiscal.getText().toString() + "', '" + idSucursalOrigen + "', '" + idSucursalOrigen + "', '" + idAlmacenOrigen + "', " + idProve + ", '" + idCliente + "'," + totalIVA + "," + totalIEPS + ", '"+claveUniProvedor+"' ", true);


            tn = SrvPv.tabla("EXEC  [dbo].[spGuardarMovimientoAlmacenAPP] " + idMovimientoAlmacenTipo + ",'Compra'," + IDSucursal + ",2 ,2 , '', '" + observaciones.getText().toString() + " app"+ "', " + subtotalREAL + ", 0, " + totalNetoREAL + ", " + idUsuario + ", " + idUsuarioAutorizo + ", '" + idReferecia + "','" + datoFolio + "','"+folioFiscal.getText().toString()+"', '" + pro1.toString() + "' , 0,0,0, " + idProve + ", 0 ," + totalIVA + " , " + totalIEPS + ", '"+claveUniProvedor+"' ", true);

            //consulta = "EXEC  [dbo].[spGuardarMovimientoAlmacenAPP] " + idMovimientoAlmacenTipo + ",'Compra'," + IDSucursal + ",2 ,2 , '', '" + observaciones.getText().toString() + " app"+ "', " + subtotalREAL + ", 0, " + totalNetoREAL + ", " + idUsuario + ", " + idUsuarioAutorizo + ", '" + idReferecia + "','" + datoFolio + "','', '" + folioFiscal.getText().toString() + "' , '" + idSucursalOrigen + "' , 0,0,0, " + idProve + ", 0 ," + totalIVA + " , " + totalIEPS + ", '"+claveUniProvedor+"' ";

            tq = SrvPv.tabla("select * from movimiento_almacen where id_movimiento_almacen_tipo=1 and referencia= '" + datoFolio + "'  order  by 1 desc", true);

            try {
                idMovimientoAlmacen = tq.getInt(1);

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }

            ta = SrvPv.tabla("select ISNULL((MAX (consecutivo)),0)+1 from movimiento_almacen_detalle ", true);

            try {
                almacenDetallesConsecutivo = tq.getInt(1);

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }

            int idArticuloMovDetalle = 0;
            Float cantidadRealMovDetalle = null;
            int cantidadExistencia = 0;
            Float costoUnitarioMovDetalle = null;
            int descuentoMovDetalle = 0;
            int entradaArticuloExistencia = 1;
            int salidaArticuloExistencia = 0;
            int entradaArticulo = 1;
            int salidaArticulo = 1;
            int afectaCostoUltimo = 1;
            int afectaCostoPromedio = 1;
            int vendeSinExistencia = 0;
            Float subtotalMovDetalle = null;
            Float ivaMovDetalle = null;
            Float iepsMovDetalle = null;
            Float importeMovDetalle = null;
            int unidadMedida = 0;
            int idMovAlmaDetTipo = 1;

            int idAlmacenMov = 0;
            int descuentoMov = 0;
            int entradaArtExisMov = 0;
            int idMovAlmacen = 0;

            String claveArt = "";

            Float PrecioUnitario = null;
            int relacionVenta = 0;

            tj = SrvPv.tabla("select repcom.folio_compra,repcom.nombre_articulo,art.id_articulo,art.clave,uni.id_unidad_medida,repcom.contado_real,ord.costo_unitario,imp.tasa as 'tasa IVA', " +
                    "impu.tasa as 'tasa IEPS', " +
                    "convert (decimal(12,4), (repcom.contado_real * ord.costo_unitario) *(impu.tasa /100)) as'IEPS', " +
                    "convert(decimal(12,4),((convert (decimal(12,4), repcom.contado_real * ord.costo_unitario)) *(impu.tasa /100)+(convert (decimal(12,4), repcom.contado_real * ord.costo_unitario)))*imp.tasa/100)as'IVA correcto', " +
                    "convert (decimal(12,4), repcom.contado_real * ord.costo_unitario) as 'subtotal', " +
                    "convert(decimal(12,4),((convert (decimal(12,4), repcom.contado_real * ord.costo_unitario)) *(impu.tasa /100)+(convert (decimal(12,4), repcom.contado_real * ord.costo_unitario)))*imp.tasa/100) +convert (decimal(12,4), repcom.contado_real * ord.costo_unitario)+convert (decimal(12,4), (repcom.contado_real * ord.costo_unitario) *(impu.tasa /100)) as 'total neto' , " +
                    "mov.id_movimiento_almacen as'id_Movimiento_Almacen', " +
                    "2 as 'id_alamcen', " +
                    "0 as 'descuento', " +
                    "1 as 'entrada_Articulo_Existencia', " +
                    "0 as 'salida_Articulo_Existencia', " +
                    "1 as 'afecta_CostoUltimo', " +
                    "1 as 'afecta_CostoPromedio', " +
                    "1 as 'idMovimiento_Tipo' " +
                    "from reporteCompras repcom " +
                    "inner join articulo art on art.clave = repcom.clave_art " +
                    "inner join impuesto imp on imp.id_impuesto = art.id_iva_venta " +
                    "inner join impuesto impu on impu.id_impuesto = art.id_ieps_venta " +
                    "inner join orden_compra_detalle ord on ord.id_articulo = repcom.id_articulo " +
                    "inner join unidad_medida uni on uni.id_unidad_medida = art.id_unidad_venta " +
                    "inner join movimiento_almacen mov on mov.referencia = repcom.folio_Compra  " +
                    "where folio_compra ='" + datoFolio + "' and ord.id_orden_compra = " + idOrdenCompra + " ", true);

            try {

                do {

                    idAlmacenMov = tj.getInt(15);
                    idArticuloMovDetalle = tj.getInt(3);
                    cantidadRealMovDetalle = tj.getFloat(6);
                    claveArt = tj.getString(4);
                    //costoUnitarioMovDetalle = tj.getFloat(7);
                    unidadMedida = tj.getInt(5);
                    descuentoMov = tj.getInt(16);
                    importeMovDetalle = tj.getFloat(13);
                    entradaArtExisMov = tj.getInt(17);
                    subtotalMovDetalle = tj.getFloat(12);
                    ivaMovDetalle = tj.getFloat(11);
                    iepsMovDetalle = tj.getFloat(10);
                    idMovAlmacen = tj.getInt(14);

                    int consecutivoMoviAlDetalle = 0;

                    tp = SrvPv.tabla("select ISNULL((MAX (consecutivo)),0)+1 from movimiento_almacen_detalle where id_movimiento_almacen = " + idMovimientoAlmacen + " ", true);

                    try {
                        consecutivoMoviAlDetalle = tp.getInt(1);


                    } catch (SQLException | java.sql.SQLException exception) {
                        exception.printStackTrace();
                    }


                    tq = SrvPv.tabla("select * from articulo where clave ='" + claveArt + "' ", true);

                    try {
                        relacionVenta = tq.getInt(16);

                    } catch (SQLException | java.sql.SQLException exception) {
                        exception.printStackTrace();
                    }


                    tx = SrvSuc.tabla("select (DO_CLISTA)-((DO_DESCTO /100) *DO_CLISTA) from DORDEN where DO_NUMORD= '" + datoFolio + "' and DO_CVEART = '" + claveArt + "' ", true);

                    try {
                        PrecioUnitario = tx.getFloat(1);

                    } catch (SQLException | java.sql.SQLException exception) {
                        exception.printStackTrace();
                    }

                    Float preciUnitario = null;

                    preciUnitario = PrecioUnitario / relacionVenta;


                    tb = SrvPv.tabla("EXEC    [dbo].[spGuardarMADApp] "+idMovimientoAlmacen+"," + idAlmacenMov + "," + idArticuloMovDetalle + "," + consecutivoMoviAlDetalle + "," + cantidadRealMovDetalle + "," + preciUnitario + "," + unidadMedida + "," + descuentoMov + "," + importeMovDetalle + "," + entradaArtExisMov + ",11,12,13," + subtotalMovDetalle + "," + ivaMovDetalle + "," + iepsMovDetalle + " ", true);

                    consulta = "EXEC [dbo].[spGuardarMADApp] "+idMovimientoAlmacen+"," + idAlmacenMov + "," + idArticuloMovDetalle + "," + consecutivoMoviAlDetalle + "," + cantidadRealMovDetalle + "," + preciUnitario + "," + unidadMedida + "," + descuentoMov + "," + importeMovDetalle + "," + entradaArtExisMov + ",11,0,0," + subtotalMovDetalle + "," + ivaMovDetalle + "," + iepsMovDetalle + " " ;



                } while (tj.next());

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }

                            /*tf=Srv.tabla("select folio_factura from reporteCompras where folio_compra = '" + datoFolio + "'",true);

                            try {
                                compararFolioFactura = tf.getString(6);

                            } catch (SQLException | java.sql.SQLException exception) {
                                exception.printStackTrace();
                            }

                             */

                            /*if(compararFolioFactura.equals("")){

                                Integer restl4 = null;

                                restl4 = Srv.ejecuta("update reporteCompras set folio_fiscal = '" + folioFiscal.getText().toString() + "' where folio_compra = '" + datoFolio + "' ");

                            }else if (compararFolioFactura.equals(folioFiscal.getText().toString())){




                            }

                             */

            Float sumImporte = null;
            Float sumSubtotal = null;
            Float sumIVA = null;
            Float sumIEPS = null;

            Integer restl55 = null;
            Integer restl5 = null;
            Integer restl6 = null;
            Integer restl7 = null;
            Integer restl100 = null;

            ty = SrvPv.tabla("select SUM(importe) as 'importe',SUM(subtotal) as 'subtotal',SUM(iva)as 'IVA',SUM(ieps)as 'IEPS' from movimiento_almacen_detalle where id_movimiento_almacen = " + idMovimientoAlmacen + " ", true);

            try {
                sumImporte = ty.getFloat(1);
                sumSubtotal = ty.getFloat(2);
                sumIVA = ty.getFloat(3);
                sumIEPS = ty.getFloat(4);

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }

            restl100 = SrvPv.ejecuta("update movimiento_almacen set total_neto = " + sumImporte + " where id_movimiento_almacen = " + idMovimientoAlmacen + " ");

            restl5 = SrvPv.ejecuta("update movimiento_almacen set subtotal = " + sumSubtotal + " where id_movimiento_almacen = " + idMovimientoAlmacen + " ");

            restl6 = SrvPv.ejecuta("update movimiento_almacen set total_iva = " + sumIVA + " where id_movimiento_almacen = " + idMovimientoAlmacen + " ");

            restl7 = SrvPv.ejecuta("update movimiento_almacen set total_ieps = " + sumIEPS + " where id_movimiento_almacen = " + idMovimientoAlmacen + " ");

            //mensajeGuardar();
            mensajeExito();
        }
        //////////////////////////////////////////solo para guardar //////////////////////////////




    }


            private void mensajeGuardar() {

               // progressBar3.setVisibility(View.VISIBLE);

                new AlertDialog.Builder(this)
                        .setTitle("                         ORDEN COMPRA")
                        .setMessage("EXITO AL GUARDAR")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                terminoVentana();
                            }
                        })
                        .show();
            }

        private void terminoVentana() {

            Intent incant = new Intent(this, menuAplicaciones.class);
            startActivity(incant);
        }


    public void hiloEnviar(){

        new AlertDialog.Builder(this)
                .setTitle("                         GUARDAR COMPRA")
                .setMessage("¿Deseas guardar la compra?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Runnable objRunnable = new Runnable() {
                            @Override
                            public void run() {
                                try{

                                    Thread.sleep(1000);

                                }catch (Exception ex){
                                    ex.printStackTrace();

                                }
                                //obHandler.sendEmptyMessage(0);
                            }
                        };

                        Thread objBgThread = new Thread(objRunnable);
                        objBgThread.start();


                      /*progressDialog = new ProgressDialog(VerReporte.this);
                      progressDialog.show();
                      progressDialog.setContentView(R.layout.progress_dialog);

                      */



                    }

                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Mensaje","se cancelo envio");
                    }
                })
                .show();



    }


    public void mensajeExito(){

        progressDialog.cancel();

        Dialog enviadoExito = new Dialog(this);
        enviadoExito.setContentView(R.layout.mesajexito);
        enviadoExito.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView cerrar=enviadoExito.findViewById(R.id.imgCLose);
        Button btnOk=enviadoExito.findViewById(R.id.btnReintentar);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviadoExito.dismiss();
                Toast.makeText(getApplicationContext(),"CERRADO", Toast.LENGTH_SHORT).show();

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviadoExito.dismiss();

                Toast.makeText(getApplicationContext(),"PERFECTO", Toast.LENGTH_SHORT).show();

                limpiarRegresar();
            }
        });

        enviadoExito.show();

    }

    public void limpiarRegresar(){

        Intent incant = new Intent(this, menuAplicaciones.class);
        startActivity(incant);
    }

    Handler obHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg){
            super.handleMessage(msg);

            //guardarCompra(listaArtContado);

           //mensajeExito();

            //progressDialog.cancel();

        }
    };


}