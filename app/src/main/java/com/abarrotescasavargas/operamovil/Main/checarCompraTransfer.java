package com.abarrotescasavargas.operamovil.Main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.abarrotescasavargas.operamovil.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class checarCompraTransfer extends AppCompatActivity {

    TextView idFolio, idTipoMovimiento, idClaveProveedor, nompro, Prueba;
    ListView listaArticulos;
    Button btnVerReporte;
    EditText folioFiscal;
    EditText escaArt;
    Button btnEscArt;
    ArrayList listaArtTodos;

    ArrayList idArtComBot = new ArrayList();

    java.sql.ResultSet tb, ty, tr, tp, tl, tm, tz, tg,ta,te,tf,tw,tk,tq,tc,tu,pp,pa,
    gocd,gocd1,ho,hj,hh,ha,hi,km,kl;

    String cla = "";
    String subtring = "";
    String codigoBarras = "";
    String claveArti = "";
    String nombreArt = "";
    String UnidadCompra = "";
    String desUnidad = "";
    String idArticulo = "";
    String cantidadOrdenCompra = "";
    int posicionArticulo = 0;
    String datoFolio = "";
    String descuentoOrden = "";
    Boolean inicioClick = false;
    String idFolioCompra="";
    String codART="";

    String idArtComparar="";
    int idOrdenComp=0;


    int restl = 0;
    int restl1 = 0;

    int restl3=0;
    int restl4=0;


    //////////// para llenar tabla de orden_Compra //////////////////

    // orden compara    // IDENTITY
    String idSucursal = "";
    //datoFolio//
    String observaciones = "";
    String subtotal = "";
    String descuento = "0.00";
    String totalNeto = "";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    String fechaRegistro = "";
    String idProveedor = "";
    String fechaCancelado = "NULL";
    String fechaFinVigencia = "";
    String idPromovimientoAlmacenEstado = "1";
    String idCompra = "NULL";



    String bdSucursal="";



    /// orden compra detalle PV ////

    String existe = "";
    String idOrdenCompra = "";

    /*
    String idMovimientoAlmacenTipo ="1";
    String idAlmacen ="2";
    String idCaja ="1";
    String idUsuarioLogueado ="1";
    String idUsuarioAutorizado ="1";
    String idFacturacion="NULL";
    String idReferencia="";
    String referencia="";
    String codigoAutorizacion="";
    String folioFiscal="";
    String idSucursalOrigen="NULL";
    String idSucursalDestino="NULL";
    String idAlmacenOrigen="NULL";
    String idCliente="NULL";
    String idConsolidacion="NULL";
    String devuelto="NULL";
    String consecutivo="";
    String totalIva="";
    String totalIEPS="";
    String estatus="";
     */

    //////////// para llenar tabla de premovimiento_almacen //////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checar_compra_transfer);

        final SQLiteDatabase dbs = openOrCreateDatabase("Servidor.db", MODE_PRIVATE, null);
        final Servidor SrvSuc = new Servidor(getApplicationContext(),true);
        final Servidor SrvPv = new Servidor(getApplicationContext());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        //folioFiscal = findViewById(R.id.folioFiscal);
        idFolio = findViewById(R.id.idFolio);
        datoFolio = getIntent().getStringExtra("folio");
        idFolio.setText(datoFolio);


        idTipoMovimiento = findViewById(R.id.idTipoMovimiento);
        //String datoTipoMovimiento = getIntent().getStringExtra("tipoMovimiento");
        //idTipoMovimiento.setText(datoTipoMovimiento);

        idClaveProveedor = findViewById(R.id.idClaveProveedor);
        String datoClaveProveedor = getIntent().getStringExtra("claveproveedor");
        idClaveProveedor.setText(datoClaveProveedor);

        nompro = findViewById(R.id.nompro);
        String datoNombreProvee = getIntent().getStringExtra("nomproveedor");
        nompro.setText(datoNombreProvee);

        listaArticulos = findViewById(R.id.listaArticulos);
        btnVerReporte = findViewById(R.id.btnVerReporte);
        btnEscArt = findViewById(R.id.btnEscArt);

        escaArt = findViewById(R.id.escaArt);

        escaArt.setText("");

        /*Prueba= findViewById(R.id.prueba1);
        String datoPrueba = getIntent().getStringExtra("cantidadContada");
        Prueba.setText(datoPrueba);
         */

//////////////////////////////LLENA LA LISTA /////////////////////////////////////

        ArrayList listaArt = new ArrayList();

        idFolioCompra = getIntent().getStringExtra("folio");

        pa = SrvSuc.tabla("select * from DORDEN where DO_NUMORD = '" + idFolioCompra + "' ", true);

        try {
            idSucursal = pa.getString(2);

        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }

        pp = SrvPv.tabla("select nombre_bd from sucursal where id_sucursal= "+idSucursal+" ",true);

        try {

            bdSucursal = pp.getString(1);

        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }



        /*tb = Srv.tabla("select CONCAT(art.clave,'-  ',art.desc_mayoreo) as 'nombre articulo'\n" +
                "from movimiento_almacen_detalle mad\n" +
                "inner join articulo art on art.id_articulo = mad.id_articulo\n" +
                "where id_movimiento_almacen= '"+ idFolioCompra +"' ", true);
         */

       /* tb = Srv.tabla("select CONCAT(art.clave,'-  ',art.desc_mayoreo) as 'nombre articulo' " +
                "from DORDEN dor " +
                "inner join articulo art on art.clave = dor.DO_CVEART " +
                "where DO_NUMORD = '" + idFolioCompra + "' ", true);

        */


        tb = SrvSuc.tabla("select CONCAT(art.clave,'-  ',art.desc_mayoreo) as 'nombre articulo' \n" +
                "from DORDEN dor \n" +
                "inner join "+bdSucursal+".dbo.articulo art on art.clave=dor.DO_CVEART \n" +
                "where  DO_NUMORD= '" + idFolioCompra + "' ", true);


        try {
            if (tb.getRow() == 0) {
                Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
            } else {
                do {
                    listaArt.add(tb.getString(1));
                } while (tb.next());
            }

        } catch (Exception ex) {
        }

        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, listaArt);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaArt);

        listaArticulos.setAdapter(adapter);


        listaArtTodos=listaArt;
//////////////////////////////LLENA LA LISTA /////////////////////////////////////


/////////////// toda la lista para obtener iva, ieps    GENERAL orden_Compra ////////////////////////////

        tl = SrvSuc.tabla("select * from DORDEN where DO_NUMORD = '" + idFolioCompra + "' ", true);

        try {
            idSucursal = tl.getString(2);

        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }

        String Folio = getIntent().getStringExtra("folio");


        tm = SrvSuc.tabla("select SUM( DO_CANTID*DO_CLISTA) from DORDEN where DO_NUMORD  ='" + idFolioCompra + "' ", true);

        try {
            subtotal = tm.getString(1);
            totalNeto = tm.getString(1);

        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }


        tz = SrvSuc.tabla("select * from MORDEN where MO_NUMORD = '" + idFolioCompra + "' ", true);

        try {
            fechaRegistro = tz.getString(4);
            fechaFinVigencia = tz.getString(5);

        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }


        tg = SrvPv.tabla("select * from proveedor where clave = '" + datoClaveProveedor + "' ", true);

        try {
            idProveedor = tg.getString(1);


        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }


///////////////////////////ss da click ///////////////////////////////////////////

      /*  listaArticulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int aux = 0;
                cla = listaArticulos.getItemAtPosition(position).toString();
                aux = cla.indexOf('-');
                subtring = cla.substring(0, aux);

                ty = Srv.tabla("select * from articulo where clave = '" + subtring + "'", true);

                try {
                    idArticulo = ty.getString(1);
                    claveArti = ty.getString(2);
                    codigoBarras = ty.getString(3);
                    nombreArt = ty.getString(11);
                    UnidadCompra = ty.getString(13);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }
                tr = Srv.tabla("select * from unidad_medida where id_unidad_medida = '" + UnidadCompra + "'", true);

                try {
                    desUnidad = tr.getString(2);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }

                tp = Srv.tabla("select * from DORDEN where DO_NUMORD = '" + idFolioCompra + "' and DO_CVEART = '" + claveArti + "' ", true);

                try {
                    cantidadOrdenCompra = tp.getString(4);
                    descuentoOrden = tp.getString(6);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }
                new IntentIntegrator(checarCompraTransfer.this).initiateScan();
            }
        });

       */

//////////////////////// ss da click //////////////////////////////////////////////

        btnVerReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(checarCompraTransfer.this, VerReporte.class);
                intent.putExtra("folio", datoFolio);
                intent.putExtra("idSucursal", idSucursal);
                intent.putExtra("subtotal", subtotal);
                intent.putExtra("idProveedor", idProveedor);
                intent.putExtra("idArticulo", idArticulo);
                intent.putExtra("subtotal", subtotal);
                intent.putExtra("totalNeto", totalNeto);
                intent.putExtra("descuentoOrden", descuentoOrden);
                startActivity(intent);

            }
        });


        btnEscArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new IntentIntegrator(checarCompraTransfer.this).initiateScan();

                inicioClick= true;
            }
        });


        escaArt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    final Servidor SrvSuc = new Servidor(getApplicationContext(),true);
                    final Servidor SrvPv = new Servidor(getApplicationContext());

                    ArrayList idArtCom = new ArrayList();

                    escaArt.getText().toString();

                    codART = escaArt.getText().toString();

                    tk=SrvPv.tabla("select * from articulo where codigo_barras1 = '"+codART+"' ",true);

                    try {

                        idArtComparar = tk.getString(2);

                    } catch (SQLException | java.sql.SQLException exception) {
                        exception.printStackTrace();
                    }

                    tq = SrvSuc.tabla("select * from DORDEN where DO_NUMORD= '"+datoFolio+"' ",true);

                    try {
                        if (tq.getRow() == 0) {
                            Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
                        } else {
                            do {
                                idArtCom.add(tq.getString(3));
                            } while (tq.next());
                        }

                    } catch (Exception ex) {
                    }


                    if(idArtCom.contains(idArtComparar)){
                        try {
                            te=SrvPv.tabla("select id_articulo,clave,codigo_barras1,desc_mayoreo,id_unidad_compra from articulo where codigo_barras1 = '"+codART+"' ",true);

                            try {
                                idArticulo = te.getString(1);
                                claveArti = te.getString(2);
                                codigoBarras = te.getString(3);
                                nombreArt = te.getString(4);
                                UnidadCompra = te.getString(5);

                            } catch (SQLException | java.sql.SQLException exception) {
                                exception.printStackTrace();
                            }

                            tf = SrvPv.tabla("select * from unidad_medida where id_unidad_medida = '" + UnidadCompra + "'", true);

                            try {
                                desUnidad = tf.getString(2);

                            } catch (SQLException | java.sql.SQLException exception) {
                                exception.printStackTrace();
                            }

                            tw = SrvPv.tabla("select * from DORDEN where DO_NUMORD = '" + idFolioCompra + "' and DO_CVEART = '" + claveArti + "' ", true);

                            try {
                                cantidadOrdenCompra = tw.getString(4);
                                descuentoOrden = tw.getString(6);

                            } catch (SQLException | java.sql.SQLException exception) {
                                exception.printStackTrace();
                            }

                            if (codART.equals(codigoBarras)) {

                                Intent intent = new Intent(checarCompraTransfer.this, guardarCompraTransfer.class);
                                intent.putExtra("folio", datoFolio);
                                intent.putExtra("nombreArt", nombreArt);
                                intent.putExtra("claveArt", claveArti.toString());
                                intent.putExtra("idSucursal", idSucursal);
                                intent.putExtra("subtotal", subtotal);
                                intent.putExtra("totalNeto", totalNeto);
                                intent.putExtra("fechaRegistro", fechaRegistro);
                                intent.putExtra("idProveedor", idProveedor);
                                intent.putExtra("fechaFinVigencia", fechaFinVigencia);

                                intent.putExtra("codigoBarras", codigoBarras.toString());
                                intent.putExtra("posicionItem", posicionArticulo);
                                intent.putExtra("unidadMedida", desUnidad);
                                intent.putExtra("UnidadOrdenCompra", cantidadOrdenCompra);

                                intent.putExtra("descuentoOrden", descuentoOrden);


                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "No coinciden el codigo de barras con el articulo", Toast.LENGTH_LONG).show();
                            }


                        }catch(Exception e){

                            new AlertDialog.Builder(checarCompraTransfer.this)
                                    .setTitle("                 !!!!! ERROR !!!!!       ")
                                    .setMessage("QR no es una orden de compra")
                                    .show();


                            // Log.e("Error:",e.getMessage());
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "el articulo no esta en la orden de compra", Toast.LENGTH_LONG).show();
                        escaArt.setText("");
                        return true;
                    }

                }
                return false;
            }
        });



        ///llenar la tabla en Pv Orden orden_compra //////////

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

        gocd1 = SrvPv.tabla("select id_orden_compra from orden_compra where folio = '"+ datoFolio +"' ", true);

        try {
            existe = gocd1.getString(1);

        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }

        if(existe.equals("")){

            restl = SrvPv.ejecuta("INSERT INTO orden_compra (id_sucursal,folio,observaciones,subtotal,descuento,total_neto,fecha_registro,id_proveedor,fecha_fin_vigencia,id_promovimiento_almacen_estado)" +
                    " VALUES ("+ idSucursal + ",'" + datoFolio + "','" + observaciones.toString() + "','" + subtotal.toString() + "','" + descuento + "' " +
                    ",'" + totalNeto.toString() + "','" + formateador.format(fechRegis) + "'," + idProveedor + " " +
                    ",'" + formateador1.format(fechVigencia) + "'," + idPromovimientoAlmacenEstado + ")");


            //// llenar  orden compra detalle  Pv /////


            gocd = SrvPv.tabla("select id_orden_compra from orden_compra where folio = '"+ datoFolio +"' ", true);

            try {
                idOrdenCompra = gocd.getString(1);

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }


            ArrayList<String> listaArtSuc = new ArrayList();

            ho = SrvSuc.tabla("select * from DORDEN where DO_NUMORD = '"+ datoFolio +"' ",true);

            try {
                if (ho.getRow() == 0) {
                    Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
                } else {
                    do {
                        listaArtSuc.add(ho.getString(3));
                    } while (ho.next());
                }

            } catch (Exception ex) {
            }


            String idArticulo1="";
            String claveArti1="";
            int consecutivo1=0;
            int relacionVenta=0;
            String idUnidadMedida = "";
            double importe=0;
            Float PrecioUnitario= null;

            for (String clave:listaArtSuc) {


                hj = SrvPv.tabla("select * from articulo where clave = '"+clave+"' ",true);

                try {
                    idArticulo1 = hj.getString(1);
                    claveArti1 = hj.getString(2);
                    codigoBarras = hj.getString(3);
                    nombreArt = hj.getString(4);
                    UnidadCompra = hj.getString(5);
                    idUnidadMedida = hj.getString(14);
                    relacionVenta = hj.getInt(16);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }

                hh = SrvPv.tabla("select ISNULL((MAX (consecutivo))+1,0) from orden_compra_detalle where id_orden_compra='"+ idOrdenCompra +"' ", true);

                try {
                    consecutivo1 = hh.getInt(1);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }


                ha = SrvSuc.tabla("select (DO_CLISTA)-((DO_DESCTO /100) *DO_CLISTA) from DORDEN where DO_NUMORD= '"+ datoFolio +"' and DO_CVEART = '"+ clave +"' ",true);

                try {

                    PrecioUnitario = ha.getFloat(1);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }


                kl = SrvSuc.tabla("select * from DORDEN where DO_NUMORD = '" + idFolioCompra + "' and DO_CVEART = '" + claveArti1 + "' ", true);

                try {
                    cantidadOrdenCompra = kl.getString(4);
                    descuentoOrden = kl.getString(6);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }



                int cantidadOrdenCompraPiezas=0;

                km = SrvPv.tabla("select (relacion_venta * "+cantidadOrdenCompra+") as contado_Real from articulo where id_articulo= "+idArticulo1+" ",true);

                try {
                    cantidadOrdenCompraPiezas= km.getInt(1);

                } catch (SQLException | java.sql.SQLException exception) {
                    exception.printStackTrace();
                }


                Float preciUnitario =null;

                preciUnitario = PrecioUnitario / relacionVenta;

                importe = (PrecioUnitario / relacionVenta)* cantidadOrdenCompraPiezas;
                int ingresado=0;
                int mayoreo=1;




                restl1 = SrvPv.ejecuta("INSERT INTO orden_compra_detalle (id_orden_compra,id_articulo,consecutivo,cantidad,costo_unitario,id_unidad_medida,descuento,importe,ingresado,es_mayoreo)" +
                " VALUES ('"+ idOrdenCompra +"',"+ idArticulo1 +","+consecutivo1+","+cantidadOrdenCompraPiezas+",'"+preciUnitario+"','"+idUnidadMedida+"',"+descuento+","+importe+","+ingresado+","+mayoreo+")");



            }

            Float sumaImportes = null;

            hi = SrvPv.tabla("select SUM(importe) from orden_compra_detalle where id_orden_compra= '"+ idOrdenCompra +"' ",true);

            try {
                sumaImportes = hi.getFloat(1);

            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }

            //restl3 = SrvPv.ejecuta("update orden_compra set subtotal = "+sumaImportes+" where folio = '" + datoFolio + "' ");
            //restl4 = SrvPv.ejecuta("update orden_compra set total_neto = "+sumaImportes+" where folio = '" + datoFolio + "' ");


        }else{

            Toast.makeText(getApplicationContext(), "Ya existe en la orden_compra", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        /*if (id == R.id.item_done) {

            for (int i = 0; i < listaArticulos.getCount(); i++) {

                if (listaArticulos.isItemChecked(i)) {
                    String itemSelect = "seleccionaste ; ";
                    itemSelect += listaArticulos.getItemAtPosition(i);

                }
            }
        }*/

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        final SQLiteDatabase dbs = openOrCreateDatabase("Servidor.db", MODE_PRIVATE, null);
        final Servidor SrvSuc = new Servidor(getApplicationContext(),true);
        final Servidor SrvPv = new Servidor(getApplicationContext());

        String codEscaneado="";

        if(inicioClick == true) { //codigo escaneado con control//

            if (result != null) //codigo escaneado con control//
                try {
                    if (result.getContents() != null) {

                        codEscaneado = result.getContents();

                        tc = SrvPv.tabla("select * from articulo where codigo_barras1 = '" + codEscaneado + "' ", true);

                        try {

                            idArtComparar = tc.getString(2);


                        } catch (SQLException | java.sql.SQLException exception) {
                            exception.printStackTrace();
                        }


                        tu = SrvSuc.tabla("select * from DORDEN where DO_NUMORD= '" + datoFolio + "' ", true);

                        try {
                            if (tu.getRow() == 0) {
                                Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
                            } else {
                                do {
                                    idArtComBot.add(tu.getString(3));
                                } while (tu.next());
                            }

                        } catch (Exception ex) {
                        }


                        if (idArtComBot.contains(idArtComparar)) {

                        ta = SrvPv.tabla("select id_articulo,clave,codigo_barras1,desc_mayoreo,id_unidad_compra from articulo where codigo_barras1 = '" + codEscaneado + "' ", true);

                        try {
                            idArticulo = ta.getString(1);
                            claveArti = ta.getString(2);
                            codigoBarras = ta.getString(3);
                            nombreArt = ta.getString(4);
                            UnidadCompra = ta.getString(5);

                        } catch (SQLException | java.sql.SQLException exception) {
                            exception.printStackTrace();
                        }

                           // colorear_item();

                       tr = SrvPv.tabla("select * from unidad_medida where id_unidad_medida = '" + UnidadCompra + "'", true);

                        try {
                            desUnidad = tr.getString(2);

                        } catch (SQLException | java.sql.SQLException exception) {
                            exception.printStackTrace();
                        }

                        tp = SrvSuc.tabla("select * from DORDEN where DO_NUMORD = '" + idFolioCompra + "' and DO_CVEART = '" + claveArti + "' ", true);

                        try {
                            cantidadOrdenCompra = tp.getString(4);
                            descuentoOrden = tp.getString(6);

                        } catch (SQLException | java.sql.SQLException exception) {
                            exception.printStackTrace();
                        }

                       // new IntentIntegrator(checarCompraTransfer.this).initiateScan();

                           Intent intent = new Intent(this, guardarCompraTransfer.class);
                            intent.putExtra("folio", datoFolio);
                            intent.putExtra("nombreArt", nombreArt);
                            intent.putExtra("claveArt", claveArti.toString());
                            intent.putExtra("idSucursal", idSucursal);
                            intent.putExtra("subtotal", subtotal);
                            intent.putExtra("totalNeto", totalNeto);
                            intent.putExtra("fechaRegistro", fechaRegistro);
                            intent.putExtra("idProveedor", idProveedor);
                            intent.putExtra("fechaFinVigencia", fechaFinVigencia);

                            intent.putExtra("codigoBarras", codigoBarras.toString());
                            intent.putExtra("posicionItem", posicionArticulo);
                            intent.putExtra("unidadMedida", desUnidad);
                            intent.putExtra("UnidadOrdenCompra", cantidadOrdenCompra);

                            intent.putExtra("descuentoOrden", descuentoOrden);


                            startActivity(intent);


                        }else{
                            Toast.makeText(getApplicationContext(), "el articulo no esta en la orden de compra", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("Error:", e.getMessage());
                }

        }else{
            Toast.makeText(getApplicationContext(), "No entra ", Toast.LENGTH_LONG).show();
        }



       /* String claveArt = "";

        if (result != null)
            try {
                if (result.getContents() != null) {

                    claveArt = result.getContents();

                    if (codigoBarras.equals(claveArt)) {

                        Intent intent = new Intent(this, guardarCompraTransfer.class);
                        intent.putExtra("folio", datoFolio);
                        intent.putExtra("nombreArt", nombreArt);
                        intent.putExtra("claveArt", claveArti.toString());
                        intent.putExtra("idSucursal", idSucursal);
                        intent.putExtra("subtotal", subtotal);
                        intent.putExtra("totalNeto", totalNeto);
                        intent.putExtra("fechaRegistro", fechaRegistro);
                        intent.putExtra("idProveedor", idProveedor);
                        intent.putExtra("fechaFinVigencia", fechaFinVigencia);

                        intent.putExtra("codigoBarras", codigoBarras.toString());
                        intent.putExtra("posicionItem", posicionArticulo);
                        intent.putExtra("unidadMedida", desUnidad);
                        intent.putExtra("UnidadOrdenCompra", cantidadOrdenCompra);

                        intent.putExtra("descuentoOrden", descuentoOrden);


                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "No coinciden el codigo de barras con el articulo", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No lee el codigo de barras (intente de nuevo)", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("Error:", e.getMessage());
            }

        */

    }

    /*public void colorear_item(){

        final SQLiteDatabase dbs = openOrCreateDatabase("Servidor.db", MODE_PRIVATE, null);
        final Servidor Srv = new Servidor(getApplicationContext(), true);

         ArrayList listaArt = new ArrayList();

        idFolioCompra = getIntent().getStringExtra("folio");

        tb = Srv.tabla("select art.clave as 'nombre articulo' " +
                "from DORDEN dor " +
                "inner join articulo art on art.clave = dor.DO_CVEART " +
                "where DO_NUMORD = '" + idFolioCompra + "' ", true);


        try {
            if (tb.getRow() == 0) {
                Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
            } else {
                do {
                    listaArt.add(tb.getString(1));
                } while (tb.next());
            }

        } catch (Exception ex) {
        }

        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, listaArt);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaArt){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

               View view = super.getView(position, convertView, parent);

               int index=0;

               if(listaArt.contains(claveArti)) {

                   index = listaArt.indexOf(claveArti);



               }
                return view;
            }
        };
        listaArticulos.setAdapter(adapter);

        listaArtTodos=listaArt;
    }

     */

}