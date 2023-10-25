package com.abarrotescasavargas.operamovil.Main;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.abarrotescasavargas.operamovil.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Reclutamiento extends AppCompatActivity implements View.OnClickListener{
    //AppCompatActivity

    RequestQueue requestQueue;
    String pro;
    String subID ="";
    String NombreFoto = "";
    String auxiliar ="";
    private int countImg;
    private File FileRostro;
    private File FileSolEmpleo;
    private File FileactaNacimineto;
    private File Filecurp;
    private File FileRFC;
    private File FileIMSS;
    private File FileINE;
    private File FileDOMI;
    private File FileESTUDIOS;
    private File FileLABORALES;
    private File FileFotos;
    private File FileInfonavit;
    private File FileRenuncia;
    private File FileSolDatos;
    private File FileHuella;
    private File FileNomina;
    private File FileContratacion;
    private File FileFianza;
    private File FileResponsiva;
    private File FileElectronico;
    private File FileAviso;
    private File FileVestimenta;
    private File FileCajera;
    private File storageDir;
    private Uri photoLaborales2;

    String Prueba;
    String subtring ="";
    int ids=0;
    Spinner cmbCandidatos;
    java.sql.ResultSet tb,tf,tc;


    String imgRostro="";
    Button btnRostro;
    ImageView imgrostro;

    String imgSoliEmpleo="";
    Button btnSolicitudEmpleo;
    ImageView imgSolicitudEmpleo,solicitudEvidencia;


    String imgSSActaNacimineto="";
    Button btnActaNacimineto;
    ImageView imgActaNacimiento;

    String imgCurp="";
    Button btnCurp;
    ImageView imgCurps;

    String imgrfc="";
    Button btnRfc;
    ImageView imgRfc;

    String IMSS="";
    Button btnIMSS;
    ImageView imgIMSS;

    String elector="";
    Button btnElector;
    ImageView imgElector;

    String comprobanteDomicilio="";
    Button btncomproDomicilio;
    ImageView imgcomprobanteDomicilio;

    String estudioss="";
    String estudios ="";
    Button btnestudios;
    ImageView imgestudios;

    String laborales ="";
    Button btnlaborales;
    ImageView imglaborales;

    String fotografiass="";
    String fotografias ="";
    Button btnfotografias;
    ImageView imgfotografias;

    String fotosinfonavit ="";
    Button btninfonavit;
    ImageView imginfonavit;

    /////////////////////////////////     SEGUNDA ETAPA    /////////////////////////////////////////////

    String fotoscarta ="";
    Button btncarta;
    ImageView imgcarta;

    String fotossolicitud ="";
    Button btnSolicitud;
    ImageView imgsolicitud;

    String fotoshuellas ="";
    Button btnHuellas;
    ImageView imgHuella;

    String fotosPoliticas ="";
    Button btnPoliticas;
    ImageView imgPoliticas;

    String fotosRequisitos ="";
    Button btnRequisitos;
    ImageView imgRequisito;

    String fotosFianza ="";
    Button btnFianza;
    ImageView imgFianza;

    String fotosCelulares ="";
    Button btnCelulares;
    ImageView imgCelulares;

    String fotoselectronicos ="";
    Button btnelectronicos;
    ImageView imgelectronicos;

    String fotoAviso ="";
    Button btnAviso;
    ImageView imgAviso;

    String fotoVestimenta ="";
    Button btnVestimenta;
    ImageView imgVestimenta;

    String fotoCajera ="";
    Button btnCajera;
    ImageView imgCajera;

    Button btnGuardar;

    EditText observacioness;
    TextView textView9prove;
    TextView numEtapa;

    ScrollView scrollFotos;


   ////////////// //validacion de fotos /////////////////////

     //int id_reclutamiento = 0;
     String puestos="";
     String  Empleo_veri="";
     String ACTA_veri ="";
     String CURP_veri="";
     String RFC_veri="";
     String IMSS_veri="";
     String INE_veri="";
     String DOMICILIO_veri="";
     String ESTUDIOS_veri="";
     String CONSTANCIA1_veri="";
     String CONSTANCIA2_veri="";
     String FOTOGRAFIAS_veri="";
     String INFONAVIT_veri="";

     /////////// SEGUNDA ETAPA  ////////////////////

     String RENUNCIA_veri="";
     String SOL_DATOS_veri="";
     String HUELLAS_veri="";
     String NOMINA_veri="";
     String CONTRATACION_veri="";
     String FIANZA_veri="";
     String RESPONSIVA_veri="";
     String RECIBO_veri="";
     String AVISO_veri="";
     String CODIGO_veri="";
     String CAJERA_veri="";

    String obser="";
    String estatuss="";
    String nomSuc="";

    TextView textPuesto,idSucNomRe;
    String idSuc="";


    ArrayList<String> listNomCandidatos;
    ////////////// //validacion de fotos /////////////////////


    String id_reclutamiento="";
    String nombre ="";
    String puesto="";
    String estatus="";
    String rostro ="";
    String sol_empleo = "";
    String acta ="";
    String curp = "";
    String rfc  = "";
    String imss  = "";
    String ine   ="";
    String domicilio ="";
    //String estudios   ="";
    String constancia1  ="";
    String constancia2 ="";
    //String fotografias ="";
    String infonavit  = "";


    String renuncia  ="";
    String sol_datos ="";
    String huellas ="";
    String nomina="";
    String contratacion ="";
    String fianza="";
    String responsiva="";
    String recibo="";
    String aviso="";
    String codigo="";
    String cajera="";
    String observaciones="";




    ///////////// URL´S /////primera etapa//////
    String urlRostro="";
    String urlSolEmpleo="";
    String urlActa ="";
    String urlCurp="";
    String urlRfc="";
    String urlImss="";
    String urlIne="";
    String urlDomicilio="";
    String urlestudios="";
    String urlConstancia1="";
    String urlConstancia2="";
    String urlFotografias="";
    String urlInfonavit="";

    ///////////// URL´S /////segunda etapa//////
    String urlRenuncia="";
    String urlSolicitud="";
    String urlHuellas="";
    String urlPoliticas="";
    String urlRequisitos="";
    String urlFianza="";
    String urlCelulares="";
    String urlElectronicos="";
    String urlAviso="";
    String urlVestimenta="";
    String urlCajera="";


    ProgressDialog progressDialog;

    Handler obHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg){
            super.handleMessage(msg);

            automaticoEnviar();

            mensajeExito();

            progressDialog.cancel();

        }
    };

    Handler obsevacionesHilo = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg){
            super.handleMessage(msg);

            enviarObservacion();

            //Toast.makeText(getApplicationContext(), "ENVIADO CON EXITO las observaciones", Toast.LENGTH_LONG).show();

            mensajeExito();

            progressDialog.cancel();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclutamiento);
        final Servidor Srv = new Servidor(getApplicationContext());
        ServidorIMG Srv_IMG = new ServidorIMG(getApplicationContext(), true);

        storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        cmbCandidatos = findViewById(R.id.cmbCandidatos);
       // cmbPuesto = findViewById(R.id.cmbPuesto);

        requestQueue = Volley.newRequestQueue(this);

        countImg = 0;

        textView9prove = findViewById(R.id.textView9prove);
        textView9prove.setVisibility(View.INVISIBLE);

        scrollFotos=findViewById(R.id.scrollFotos);
        scrollFotos.setVisibility(View.INVISIBLE);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setVisibility(View.INVISIBLE);

        observacioness = findViewById(R.id.editObservaciones);
        observacioness.setVisibility(View.INVISIBLE);


        imgrostro = findViewById(R.id.rostro);
        btnRostro = findViewById(R.id.btnRostro);
        btnRostro.setOnClickListener(this);

        imgSolicitudEmpleo = findViewById(R.id.solicitud);
        btnSolicitudEmpleo = findViewById(R.id.btnSolicitudEmpleo);
        btnSolicitudEmpleo.setOnClickListener(this);
        //solicitudEvidencia = findViewById(R.id.solicitudEvidencia);


        imgActaNacimiento = findViewById(R.id.acta);
        btnActaNacimineto = findViewById(R.id.btnacta);
        btnActaNacimineto.setOnClickListener(this);

        imgCurps = findViewById(R.id.curp);
        btnCurp = findViewById(R.id.btncurp);
        btnCurp.setOnClickListener(this);

        imgRfc = findViewById(R.id.imgRFC);
        btnRfc = findViewById(R.id.btnRFC);
        btnRfc.setOnClickListener(this);

        imgIMSS = findViewById(R.id.imgIMSS);
        btnIMSS = findViewById(R.id.btnIMSS);
        btnIMSS.setOnClickListener(this);

        imgElector = findViewById(R.id.imgelector);
        btnElector = findViewById(R.id.btnelector);
        btnElector.setOnClickListener(this);

        imgcomprobanteDomicilio = findViewById(R.id.domicilio);
        btncomproDomicilio = findViewById(R.id.btndomicilio);
        btncomproDomicilio.setOnClickListener(this);

        imgestudios = findViewById(R.id.estudios);
        btnestudios = findViewById(R.id.btnestudios);
        btnestudios.setOnClickListener(this);

        imglaborales = findViewById(R.id.laborales);
        btnlaborales = findViewById(R.id.btnLaboral);
        btnlaborales.setOnClickListener(this);

        imgfotografias = findViewById(R.id.fotografias);
        btnfotografias = findViewById(R.id.btnfotografias);
        btnfotografias.setOnClickListener(this);

        imginfonavit = findViewById(R.id.infonavit);
        btninfonavit = findViewById(R.id.btninfonavit);
        btninfonavit.setOnClickListener(this);

        textPuesto = findViewById(R.id.textPuesto);
        numEtapa = findViewById(R.id.numEtapa);

        numEtapa.setText("0");


        //////////////////////////      SEGUNDA ETAPA   /////////////////////////

        imgcarta = findViewById(R.id.imgcarta);
        btncarta = findViewById(R.id.btncarta);
        btncarta.setOnClickListener(this);

        imgsolicitud = findViewById(R.id.imgsolicitud);
        btnSolicitud = findViewById(R.id.btnSolicitud);
        btnSolicitud.setOnClickListener(this);

        imgHuella = findViewById(R.id.imgHuella);
        btnHuellas = findViewById(R.id.btnHuellas);
        btnHuellas.setOnClickListener(this);

        imgPoliticas = findViewById(R.id.imgPoliticas);
        btnPoliticas = findViewById(R.id.btnPoliticas);
        btnPoliticas.setOnClickListener(this);

        imgRequisito = findViewById(R.id.imgRequisito);
        btnRequisitos = findViewById(R.id.btnRequisitos);
        btnRequisitos.setOnClickListener(this);

        imgFianza = findViewById(R.id.imgFianza);
        btnFianza = findViewById(R.id.btnFianza);
        btnFianza.setOnClickListener(this);

        imgCelulares = findViewById(R.id.imgCelulares);
        btnCelulares = findViewById(R.id.btnCelulares);
        btnCelulares.setOnClickListener(this);

        imgelectronicos = findViewById(R.id.imgelectronicos);
        btnelectronicos = findViewById(R.id.btnelectronicos);
        btnelectronicos.setOnClickListener(this);

        imgAviso = findViewById(R.id.imgAviso);
        btnAviso = findViewById(R.id.btnAviso);
        btnAviso.setOnClickListener(this);

        imgVestimenta = findViewById(R.id.imgVestimenta);
        btnVestimenta = findViewById(R.id.btnVestimenta);
        btnVestimenta.setOnClickListener(this);

        imgCajera = findViewById(R.id.imgCajera);
        btnCajera = findViewById(R.id.btnCajera);
        btnCajera.setOnClickListener(this);


        idSucNomRe = findViewById(R.id.idSucNomRe);


        tc = Srv.tabla(" select nombre,id_sucursal from sucursal where id_sucursal=(select top(1) sucursal from anaquel) ",true);

        try {
            nomSuc= tc.getString(1);
            idSuc=tc.getString(2);

            if(idSuc.length()==1){
                idSuc ="0"+idSuc;
            }

        } catch (android.database.SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }

        idSucNomRe.setText(nomSuc);


        RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
        String url="https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Respuesta post = gson.fromJson(response, Respuesta.class);

                //////////////////// llenado de combo candidato////////////////////

                ArrayList Candidatos = new ArrayList();
                Candidatos.add("        - Seguimiento Candidato -");


                for (empleado susu: post.empleado) {

                     id_reclutamiento = susu.id_reclutamiento;
                     nombre           = susu.nombre;
                     puesto           = susu.puesto;
                     estatus          = susu.estatus;

                    Candidatos.add(id_reclutamiento + "-"+ nombre);

                }

//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,Candidatos);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.items_spinnerventas,Candidatos);
                cmbCandidatos.setAdapter(arrayAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"no funciona "+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @NonNull
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("opcion","1");
                params.put("cve_sucursal",idSuc);
                return params;
            }
            @NonNull
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("user","ACVprogrammer");
                params.put("pass","V=.a{Xf}e0CR");
                return params;
            }
        };

        requestQueue.add(stringRequest);


        cmbCandidatos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                int aux = 0;
                pro = cmbCandidatos.getItemAtPosition(pos).toString();
                aux =pro.indexOf('-');
                subID = pro.substring(0,aux);

                if(pos==0){

                    scrollFotos.setVisibility(View.INVISIBLE);
                    textView9prove.setVisibility(View.INVISIBLE);
                    observacioness.setVisibility(View.INVISIBLE);
                    btnGuardar.setVisibility(View.INVISIBLE);

                    textPuesto.setText("");

                }else{

                    scrollFotos.setVisibility(View.INVISIBLE);
                    textView9prove.setVisibility(View.INVISIBLE);
                    observacioness.setVisibility(View.INVISIBLE);
                    btnGuardar.setVisibility(View.INVISIBLE);

                    textPuesto.setText("");

                    RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                    String url="https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Gson gson = new Gson();
                            Respuesta post = gson.fromJson(response, Respuesta.class);

                            for (empleado susu: post.empleado) {

                                id_reclutamiento = susu.id_reclutamiento;
                                nombre           = susu.nombre;
                                puesto           = susu.puesto;
                                estatus          = susu.estatus;
                                rostro           = susu.rostro;
                                sol_empleo       = susu.sol_empleo;
                                acta             = susu.acta;
                                curp             = susu.curp;
                                rfc              = susu.rfc;
                                imss             = susu.imss;
                                ine              = susu.ine;
                                domicilio        = susu.domicilio;
                                estudios         = susu.estudios;
                                constancia1      = susu.constancia1;
                                constancia2      = susu.constancia2;
                                fotografias      = susu.fotografias;
                                infonavit        = susu.infonavit;

                                ///////////segunda etapa /////////////
                                renuncia         = susu.renuncia;
                                sol_datos        = susu.sol_datos;
                                huellas          = susu.huellas;
                                nomina           = susu.nomina;
                                contratacion     = susu.contratacion;
                                fianza           = susu.fianza;
                                responsiva       = susu.responsiva;
                                recibo           = susu.recibo;
                                aviso            = susu.aviso;
                                codigo           = susu.codigo;
                                cajera           = susu.cajera;
                                observaciones    = susu.observaciones;


                            }


                            if (estatus.equals("1")){

                                scrollFotos.setVisibility(View.VISIBLE);
                                textView9prove.setVisibility(View.VISIBLE);
                                observacioness.setVisibility(View.VISIBLE);
                                btnGuardar.setVisibility(View.VISIBLE);


                                numEtapa.setText("1");
                                textPuesto.setText(puesto);

                                if(rostro.equals("1")){
                                    imgrostro.setImageResource(R.drawable.evidencia);
                                    btnRostro.setVisibility(View.INVISIBLE);
                                }else{
                                    imgrostro.setImageResource(R.drawable.imagendocumentacion);
                                    btnRostro.setVisibility(View.VISIBLE);
                                }

                                if(sol_empleo.equals("1")){
                                    imgSolicitudEmpleo.setImageResource(R.drawable.evidencia);
                                    btnSolicitudEmpleo.setVisibility(View.INVISIBLE);

                                }else{

                                    imgSolicitudEmpleo.setImageResource(R.drawable.imagendocumentacion);
                                    btnSolicitudEmpleo.setVisibility(View.VISIBLE);

                                }

                                if(acta.equals("1")){
                                    imgActaNacimiento.setImageResource(R.drawable.evidencia);
                                    btnActaNacimineto.setVisibility(View.INVISIBLE);

                                }else{

                                    imgActaNacimiento.setImageResource(R.drawable.imagendocumentacion);
                                    btnActaNacimineto.setVisibility(View.VISIBLE);
                                }

                                if(curp.equals("1")){
                                    imgCurps.setImageResource(R.drawable.evidencia);
                                    btnCurp.setVisibility(View.INVISIBLE);

                                }else{

                                    imgCurps.setImageResource(R.drawable.imagendocumentacion);
                                    btnCurp.setVisibility(View.VISIBLE);
                                }

                                if(rfc.equals("1")){
                                    imgRfc.setImageResource(R.drawable.evidencia);
                                    btnRfc.setVisibility(View.INVISIBLE);

                                }else{

                                    imgRfc.setImageResource(R.drawable.imagendocumentacion);
                                    btnRfc.setVisibility(View.VISIBLE);
                                }

                                if(imss.equals("1")){

                                    imgIMSS.setImageResource(R.drawable.evidencia);
                                    btnIMSS.setVisibility(View.INVISIBLE);

                                }else{

                                    imgIMSS.setImageResource(R.drawable.imagendocumentacion);
                                    btnIMSS.setVisibility(View.VISIBLE);
                                }

                                if(ine.equals("1")){
                                    imgElector.setImageResource(R.drawable.evidencia);
                                    btnElector.setVisibility(View.INVISIBLE);

                                }else{

                                    imgElector.setImageResource(R.drawable.imagendocumentacion);
                                    btnElector.setVisibility(View.VISIBLE);
                                }

                                if(domicilio.equals("1")){

                                    imgcomprobanteDomicilio.setImageResource(R.drawable.evidencia);
                                    btncomproDomicilio.setVisibility(View.INVISIBLE);

                                }else{

                                    imgcomprobanteDomicilio.setImageResource(R.drawable.imagendocumentacion);
                                    btncomproDomicilio.setVisibility(View.VISIBLE);
                                }

                                if(estudios.equals("1")){
                                    imgestudios.setImageResource(R.drawable.evidencia);
                                    btnestudios.setVisibility(View.INVISIBLE);

                                }else{

                                    imgestudios.setImageResource(R.drawable.imagendocumentacion);
                                    btnestudios.setVisibility(View.VISIBLE);
                                }

                                if(constancia1.equals("1")){
                                    imglaborales.setImageResource(R.drawable.evidencia);
                                    btnlaborales.setVisibility(View.INVISIBLE);

                                }else{

                                    imglaborales.setImageResource(R.drawable.imagendocumentacion);
                                    btnlaborales.setVisibility(View.VISIBLE);
                                }

                                //CONSTANCIA2_veri

                                if(fotografias.equals("1")){
                                    imgfotografias.setImageResource(R.drawable.evidencia);
                                    btnfotografias.setVisibility(View.INVISIBLE);

                                }else{

                                    imgfotografias.setImageResource(R.drawable.imagendocumentacion);
                                    btnfotografias.setVisibility(View.VISIBLE);
                                }

                                if(infonavit.equals("1")){
                                    imginfonavit.setImageResource(R.drawable.evidencia);
                                    btninfonavit.setVisibility(View.INVISIBLE);

                                }else{

                                    imginfonavit.setImageResource(R.drawable.imagendocumentacion);
                                    btninfonavit.setVisibility(View.VISIBLE);
                                }

                                ////////////// NO VER LOS DEMAS APARTADOS /////////

                                imgcarta.setVisibility(View.INVISIBLE);
                                btncarta.setVisibility(View.INVISIBLE);

                                imgsolicitud.setVisibility(View.INVISIBLE);
                                btnSolicitud.setVisibility(View.INVISIBLE);

                                imgHuella.setVisibility(View.INVISIBLE);
                                btnHuellas.setVisibility(View.INVISIBLE);

                                imgPoliticas.setVisibility(View.INVISIBLE);
                                btnPoliticas .setVisibility(View.INVISIBLE);

                                imgRequisito.setVisibility(View.INVISIBLE);
                                btnRequisitos.setVisibility(View.INVISIBLE);

                                imgFianza.setVisibility(View.INVISIBLE);
                                btnFianza.setVisibility(View.INVISIBLE);

                                imgCelulares.setVisibility(View.INVISIBLE);
                                btnCelulares.setVisibility(View.INVISIBLE);

                                imgelectronicos.setVisibility(View.INVISIBLE);
                                btnelectronicos.setVisibility(View.INVISIBLE);

                                imgAviso.setVisibility(View.INVISIBLE);
                                btnAviso.setVisibility(View.INVISIBLE);

                                imgVestimenta.setVisibility(View.INVISIBLE);
                                btnVestimenta.setVisibility(View.INVISIBLE);

                                imgCajera .setVisibility(View.INVISIBLE);
                                btnCajera.setVisibility(View.INVISIBLE);



                            }else{


                                ///////////ETAPA 2 ///////////////////

                                scrollFotos.setVisibility(View.VISIBLE);
                                textView9prove.setVisibility(View.VISIBLE);
                                observacioness.setVisibility(View.VISIBLE);
                                btnGuardar.setVisibility(View.VISIBLE);
                                numEtapa.setText("2");
                                textPuesto.setText(puesto);


                                imgcarta.setVisibility(View.VISIBLE);

                                imgsolicitud.setVisibility(View.VISIBLE);

                                imgHuella.setVisibility(View.VISIBLE);

                                imgPoliticas.setVisibility(View.VISIBLE);

                                imgRequisito.setVisibility(View.VISIBLE);

                                imgFianza.setVisibility(View.VISIBLE);

                                imgCelulares.setVisibility(View.VISIBLE);

                                imgelectronicos.setVisibility(View.VISIBLE);

                                imgAviso.setVisibility(View.VISIBLE);

                                imgVestimenta.setVisibility(View.VISIBLE);

                                imgCajera .setVisibility(View.VISIBLE);


                                if(rostro.equals("1")){
                                    imgrostro.setImageResource(R.drawable.evidencia);
                                    btnRostro.setVisibility(View.INVISIBLE);
                                }else{
                                    imgrostro.setImageResource(R.drawable.imagendocumentacion);
                                    btnRostro.setVisibility(View.VISIBLE);
                                }

                                if(sol_empleo.equals("1")){
                                    imgSolicitudEmpleo.setImageResource(R.drawable.evidencia);
                                    btnSolicitudEmpleo.setVisibility(View.INVISIBLE);

                                }else{

                                    imgSolicitudEmpleo.setImageResource(R.drawable.imagendocumentacion);
                                    btnSolicitudEmpleo.setVisibility(View.VISIBLE);

                                }

                                if(acta.equals("1")){
                                    imgActaNacimiento.setImageResource(R.drawable.evidencia);
                                    btnActaNacimineto.setVisibility(View.INVISIBLE);

                                }else{

                                    imgActaNacimiento.setImageResource(R.drawable.imagendocumentacion);
                                    btnActaNacimineto.setVisibility(View.VISIBLE);
                                }

                                if(curp.equals("1")){
                                    imgCurps.setImageResource(R.drawable.evidencia);
                                    btnCurp.setVisibility(View.INVISIBLE);

                                }else{

                                    imgCurps.setImageResource(R.drawable.imagendocumentacion);
                                    btnCurp.setVisibility(View.VISIBLE);
                                }

                                if(rfc.equals("1")){
                                    imgRfc.setImageResource(R.drawable.evidencia);
                                    btnRfc.setVisibility(View.INVISIBLE);

                                }else{

                                    imgRfc.setImageResource(R.drawable.imagendocumentacion);
                                    btnRfc.setVisibility(View.VISIBLE);
                                }

                                if(imss.equals("1")){

                                    imgIMSS.setImageResource(R.drawable.evidencia);
                                    btnIMSS.setVisibility(View.INVISIBLE);

                                }else{

                                    imgIMSS.setImageResource(R.drawable.imagendocumentacion);
                                    btnIMSS.setVisibility(View.VISIBLE);
                                }

                                if(ine.equals("1")){
                                    imgElector.setImageResource(R.drawable.evidencia);
                                    btnElector.setVisibility(View.INVISIBLE);

                                }else{

                                    imgElector.setImageResource(R.drawable.imagendocumentacion);
                                    btnElector.setVisibility(View.VISIBLE);
                                }

                                if(domicilio.equals("1")){

                                    imgcomprobanteDomicilio.setImageResource(R.drawable.evidencia);
                                    btncomproDomicilio.setVisibility(View.INVISIBLE);

                                }else{

                                    imgcomprobanteDomicilio.setImageResource(R.drawable.imagendocumentacion);
                                    btncomproDomicilio.setVisibility(View.VISIBLE);
                                }

                                if(estudios.equals("1")){
                                    imgestudios.setImageResource(R.drawable.evidencia);
                                    btnestudios.setVisibility(View.INVISIBLE);

                                }else{

                                    imgestudios.setImageResource(R.drawable.imagendocumentacion);
                                    btnestudios.setVisibility(View.VISIBLE);
                                }

                                if(constancia1.equals("1")){
                                    imglaborales.setImageResource(R.drawable.evidencia);
                                    btnlaborales.setVisibility(View.INVISIBLE);

                                }else{

                                    imglaborales.setImageResource(R.drawable.imagendocumentacion);
                                    btnlaborales.setVisibility(View.VISIBLE);
                                }

                                if(fotografias.equals("1")){
                                    imgfotografias.setImageResource(R.drawable.evidencia);
                                    btnfotografias.setVisibility(View.INVISIBLE);

                                }else{

                                    imgfotografias.setImageResource(R.drawable.imagendocumentacion);
                                    btnfotografias.setVisibility(View.VISIBLE);
                                }

                                if(infonavit.equals("1")){
                                    imginfonavit.setImageResource(R.drawable.evidencia);
                                    btninfonavit.setVisibility(View.INVISIBLE);

                                }else{

                                    imginfonavit.setImageResource(R.drawable.imagendocumentacion);
                                    btninfonavit.setVisibility(View.VISIBLE);
                                }

                                /////////////////////////////////    SEGUNDA ETAPA      ////////////////////////////////

                                if(renuncia.equals("1")){
                                    imgcarta.setImageResource(R.drawable.evidencia);
                                    btncarta.setVisibility(View.INVISIBLE);

                                }else{

                                    imgcarta.setImageResource(R.drawable.imagendocumentacion);
                                    btncarta.setVisibility(View.VISIBLE);
                                }

                                if(sol_datos.equals("1")){
                                    imgsolicitud.setImageResource(R.drawable.evidencia);
                                    btnSolicitud.setVisibility(View.INVISIBLE);

                                }else{

                                    imgsolicitud.setImageResource(R.drawable.imagendocumentacion);
                                    btnSolicitud.setVisibility(View.VISIBLE);
                                }

                                if(huellas.equals("1")){
                                    imgHuella.setImageResource(R.drawable.evidencia);
                                    btnHuellas.setVisibility(View.INVISIBLE);

                                }else{

                                    imgHuella.setImageResource(R.drawable.imagendocumentacion);
                                    btnHuellas.setVisibility(View.VISIBLE);
                                }

                                if(nomina.equals("1")){
                                    imgPoliticas.setImageResource(R.drawable.evidencia);
                                    btnPoliticas.setVisibility(View.INVISIBLE);

                                }else{

                                    imgPoliticas.setImageResource(R.drawable.imagendocumentacion);
                                    btnPoliticas.setVisibility(View.VISIBLE);
                                }

                                if(contratacion.equals("1")){
                                    imgRequisito.setImageResource(R.drawable.evidencia);
                                    btnRequisitos.setVisibility(View.INVISIBLE);

                                }else{

                                    imgRequisito.setImageResource(R.drawable.imagendocumentacion);
                                    btnRequisitos.setVisibility(View.VISIBLE);
                                }


                                if(fianza.equals("1")){
                                    imgFianza.setImageResource(R.drawable.evidencia);
                                    btnFianza.setVisibility(View.INVISIBLE);

                                }else{

                                    imgFianza.setImageResource(R.drawable.imagendocumentacion);
                                    btnFianza.setVisibility(View.VISIBLE);
                                }

                                if(responsiva.equals("1")){
                                    imgCelulares.setImageResource(R.drawable.evidencia);
                                    btnCelulares.setVisibility(View.INVISIBLE);

                                }else{

                                    imgCelulares.setImageResource(R.drawable.imagendocumentacion);
                                    btnCelulares.setVisibility(View.VISIBLE);
                                }

                                if(recibo.equals("1")){
                                    imgelectronicos.setImageResource(R.drawable.evidencia);
                                    btnelectronicos.setVisibility(View.INVISIBLE);

                                }else{

                                    imgelectronicos.setImageResource(R.drawable.imagendocumentacion);
                                    btnelectronicos.setVisibility(View.VISIBLE);
                                }

                                if(aviso.equals("1")){
                                    imgAviso.setImageResource(R.drawable.evidencia);
                                    btnAviso.setVisibility(View.INVISIBLE);

                                }else{

                                    imgAviso.setImageResource(R.drawable.imagendocumentacion);
                                    btnAviso.setVisibility(View.VISIBLE);
                                }

                                if(codigo.equals("1")){
                                    imgVestimenta.setImageResource(R.drawable.evidencia);
                                    btnVestimenta.setVisibility(View.INVISIBLE);

                                }else{

                                    imgVestimenta.setImageResource(R.drawable.imagendocumentacion);
                                    btnVestimenta.setVisibility(View.VISIBLE);
                                }

                                if(cajera.equals("1")){
                                    imgCajera.setImageResource(R.drawable.evidencia);
                                    btnCajera.setVisibility(View.INVISIBLE);

                                }else{

                                    imgCajera.setImageResource(R.drawable.imagendocumentacion);
                                    btnCajera.setVisibility(View.VISIBLE);
                                }

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"no funciona "+error, Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @NonNull
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String,String>();
                            params.put("opcion","2");
                            params.put("cve_sucursal",idSuc);
                            params.put("id_reclutamiento",subID);
                            return params;
                        }
                        @NonNull
                        @Override
                        public Map<String,String> getHeaders()throws AuthFailureError {
                            Map<String,String> params=new HashMap<String,String>();
                            params.put("user","ACVprogrammer");
                            params.put("pass","V=.a{Xf}e0CR");
                            return params;
                        }
                    };

                    requestQueue.add(stringRequest);

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {    }
        });



        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(isConnect()){
                    //Toast.makeText(getApplicationContext(),"HAY INTERNET ", Toast.LENGTH_SHORT).show();

                    Runnable objRunnable = new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(1000);

                            }catch (Exception ex){
                                ex.printStackTrace();
                            }

                            obsevacionesHilo.sendEmptyMessage(0);
                        }
                    };

                    Thread objBgThread = new Thread(objRunnable);
                    objBgThread.start();

                    //btnGuardar.setEnabled(false);

                    progressDialog = new ProgressDialog(Reclutamiento.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);

                }else{

                    sinWifi();

                    //Toast.makeText(getApplicationContext(),"NO HAY INTERNET ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


   public void EnviarFotos(){

       /* String Cadena = "";
        ArrayList<File> lst = new ArrayList();
        if(FileRostro != null){
            Cadena+= " rostro = ?";
        }
        if (FileSolEmpleo != null) {
            Cadena += ", SOL_EMPLEO = ?";
            lst.add(FileSolEmpleo);
        }
        if (FileactaNacimineto != null) {
            Cadena += ", ACTA = ?";
            lst.add(FileactaNacimineto);
        }
        if (Filecurp != null) {
            Cadena += ", CURP = ?";
            lst.add(Filecurp);
        }
        if (FileRFC != null) {
            Cadena += ", RFC = ?";
            lst.add(FileRFC);
        }
        if (FileIMSS != null) {
            Cadena += ", IMSS = ?";
            lst.add(FileIMSS);
        }
        if (FileINE != null) {
            Cadena += ", INE = ?";
            lst.add(FileINE);
        }
        if (FileDOMI != null) {
            Cadena += ", domicilio = ?";
            lst.add(FileDOMI);
        }
        if (FileESTUDIOS != null) {
            Cadena += ", estudios = ?";
            lst.add(FileESTUDIOS);
        }
        if (FileLABORALES != null) {
            Cadena += ", CONSTANCIA1 = ?";
            lst.add(FileLABORALES);
        }
        if (FileFotos != null) {
            Cadena += ", FOTOGRAFIAS = ?";
            lst.add(FileFotos);
        }
        if (FileInfonavit != null) {
            Cadena += ", infonavit = ?";
            lst.add(FileInfonavit);
        }

        /////////////////     Segunda etapa  ////////////////////////////////////////////////////////////
        if (FileRenuncia != null) {
            Cadena += ", renuncia = ?";
            lst.add(FileRenuncia);
        }
        if (FileSolDatos != null) {
            Cadena += ", sol_datos = ?";
            lst.add(FileSolDatos);
        }
        if (FileHuella != null) {
            Cadena += ", huellas = ?";
            lst.add(FileHuella);
        }
        if (FileNomina != null) {
            Cadena += ", nomina = ?";
            lst.add(FileNomina);
        }
        if (FileContratacion != null) {
            Cadena += ", CONTRATACION = ?";
            lst.add(FileContratacion);
        }
        if (FileFianza != null) {
            Cadena += ", FIANZA = ?";
            lst.add(FileFianza);
        }
        if (FileResponsiva != null) {
            Cadena += ", RESPONSIVA = ?";
            lst.add(FileResponsiva);
        }
        if (FileElectronico != null) {
            Cadena += ", RECIBO = ?";
            lst.add(FileElectronico);
        }
        if (FileAviso != null) {
            Cadena += ", AVISO = ?";
            lst.add(FileAviso);
        }
        if (FileVestimenta != null) {
            Cadena += ", CODIGO = ?";
            lst.add(FileVestimenta);
        }
        if (FileCajera != null) {
            Cadena += ", CAJERA = ?";
            lst.add(FileCajera);
        }
        if (!observacioness.getText().toString().equals("")){
            Cadena+=", OBSERVACIONES ='"+ observacioness.getText().toString() + "' ";
        }

        */

        //auxiliar = Integer.parseInt(idSuc) <10? "0"+idSuc: idSuc;

    }

    public class Respuesta{
        private String status;
        private String message;


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getmessage() {
            return message;
        }

        public void setmessage(String message) {
            this.message = message;
        }

        public ArrayList<empleado> getnewCandidatos() {
            return empleado;
        }

        public void setnewCandidatos(ArrayList<empleado> newCandidatos) {
            this.empleado = empleado;
        }

        ArrayList<empleado> empleado =new ArrayList();

    }

    public class empleado{

        public String id_reclutamiento;         //"1087",
        public String nombre;                   //"MARTINEZ GONZALEZ JUAN OSCAR",
        public String puesto;                   //"ADUANERO"
        public String estatus;                  // "0",
        public String rostro;                   //"0",
        public String sol_empleo;               //"0",
        public String acta;                     //"0",
        public String curp;                     //"0",
        public String rfc;                      //"0",
        public String imss;                     //"0",
        public String ine;                      //"0",
        public String domicilio;                //"0",
        public String estudios;                 //"0",
        public String constancia1;              //"0",
        public String constancia2;              //"0",
        public String fotografias;              //"0",
        public String infonavit;                //"0"


        ////// segunda etapa  /////////////

        public String renuncia;
        public String sol_datos;
        public String huellas;
        public String nomina;
        public String contratacion;
        public String fianza;
        public String responsiva;
        public String recibo;
        public String aviso;
        public String codigo;
        public String cajera;
        public String observaciones;



        public String getId_reclutamiento() {
            return id_reclutamiento;
        }

        public void setId_reclutamiento(String id_reclutamiento) {
            this.id_reclutamiento = id_reclutamiento;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPuesto() {
            return puesto;
        }

        public void setPuesto(String puesto) {
            this.puesto = puesto;
        }

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }

        public String getRostro() {
            return rostro;
        }

        public void setRostro(String rostro) {
            this.rostro = rostro;
        }

        public String getSol_empleo() {
            return sol_empleo;
        }

        public void setSol_empleo(String sol_empleo) {
            this.sol_empleo = sol_empleo;
        }

        public String getActa() {
            return acta;
        }

        public void setActa(String acta) {
            this.acta = acta;
        }

        public String getCurp() {
            return curp;
        }

        public void setCurp(String curp) {
            this.curp = curp;
        }

        public String getRfc() {
            return rfc;
        }

        public void setRfc(String rfc) {
            this.rfc = rfc;
        }

        public String getImss() {
            return imss;
        }

        public void setImss(String imss) {
            this.imss = imss;
        }

        public String getIne() {
            return ine;
        }

        public void setIne(String ine) {
            this.ine = ine;
        }

        public String getDomicilio() {
            return domicilio;
        }

        public void setDomicilio(String domicilio) {
            this.domicilio = domicilio;
        }

        public String getEstudios() {
            return estudios;
        }

        public void setEstudios(String estudios) {
            this.estudios = estudios;
        }

        public String getConstancia1() {
            return constancia1;
        }

        public void setConstancia1(String constancia1) {
            this.constancia1 = constancia1;
        }

        public String getConstancia2() {
            return constancia2;
        }

        public void setConstancia2(String constancia2) {
            this.constancia2 = constancia2;
        }

        public String getFotografias() {
            return fotografias;
        }

        public void setFotografias(String fotografias) {
            this.fotografias = fotografias;
        }

        public String getInfonavit() {
            return infonavit;
        }

        public void setInfonavit(String infonavit) {
            this.infonavit = infonavit;
        }

        public String getRenuncia() {
            return renuncia;
        }

        public void setRenuncia(String renuncia) {
            this.renuncia = renuncia;
        }

        public String getSol_datos() {
            return sol_datos;
        }

        public void setSol_datos(String sol_datos) {
            this.sol_datos = sol_datos;
        }

        public String getHuellas() {
            return huellas;
        }

        public void setHuellas(String huellas) {
            this.huellas = huellas;
        }

        public String getNomina() {
            return nomina;
        }

        public void setNomina(String nomina) {
            this.nomina = nomina;
        }

        public String getContratacion() {
            return contratacion;
        }

        public void setContratacion(String contratacion) {
            this.contratacion = contratacion;
        }

        public String getFianza() {
            return fianza;
        }

        public void setFianza(String fianza) {
            this.fianza = fianza;
        }

        public String getResponsiva() {
            return responsiva;
        }

        public void setResponsiva(String responsiva) {
            this.responsiva = responsiva;
        }

        public String getRecibo() {
            return recibo;
        }

        public void setRecibo(String recibo) {
            this.recibo = recibo;
        }

        public String getAviso() {
            return aviso;
        }

        public void setAviso(String aviso) {
            this.aviso = aviso;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getCajera() {
            return cajera;
        }

        public void setCajera(String cajera) {
            this.cajera = cajera;
        }

        public String getObservaciones() {
            return observaciones;
        }

        public void setObservaciones(String observaciones) {
            this.observaciones = observaciones;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnRostro:
                tomafoto("fotoRostro");
                break;
            case R.id.btnSolicitudEmpleo:
                tomafoto("fotoEmpleo");
                break;
            case R.id.btnacta:
                tomafoto("fotoActaNacimineto");
                break;
            case R.id.btncurp:
                tomafoto("fotoCurp");
                break;
            case R.id.btnRFC:
                tomafoto("fotoRfc");
                break;
            case R.id.btnIMSS:
                tomafoto("fotoIMSS");
                break;
            case R.id.btnelector:
                tomafoto("fotoElector");
                break;
            case R.id.btndomicilio:
                tomafoto("fotoDomicilio");
                break;
            case R.id.btnestudios:
                tomafoto("fotoEstudios");
                break;
            case R.id.btnLaboral:
                tomafoto("fotoLaborales");
                break;
            case R.id.btnfotografias:
                tomafoto("fotografias");
                break;
            case R.id.btninfonavit:
                tomafoto("fotoInfonavit");
                break;

                ///////////////////////////    SEGUNDA ETAPA     ///////////////////////////////////////////
            case R.id.btncarta:
                tomafoto("fotoCarta");
                break;
            case R.id.btnSolicitud:
                tomafoto("fotoSolici");
                break;
            case R.id.btnHuellas:
                tomafoto("fotoHuella");
                break;
            case R.id.btnPoliticas:
                tomafoto("fotoPolitica");
                break;
            case R.id.btnRequisitos:
                tomafoto("fotoRequisito");
                break;
            case R.id.btnFianza:
                tomafoto("fotoFianza");
                break;
            case R.id.btnCelulares:
                tomafoto("fotoCelular");
                break;
            case R.id.btnelectronicos:
                tomafoto("fotoElectricos");
                break;
            case R.id.btnAviso:
                tomafoto("fotoAviso");
                break;
            case R.id.btnVestimenta:
                tomafoto("fotoVestimenta");
                break;
            case R.id.btnCajera:
                tomafoto("fotoCajera");
                break;
        }

    }

    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (NombreFoto) {
            case "fotoRostro":
                imgrostro.setImageBitmap(decodeFile(imgRostro));
                hiloEnviar();
                break;
            case "fotoEmpleo":
                imgSolicitudEmpleo.setImageBitmap(decodeFile(imgSoliEmpleo));
                hiloEnviar();
                break;
            case "fotoActaNacimineto":
                imgActaNacimiento.setImageBitmap(decodeFile(imgSSActaNacimineto));
                hiloEnviar();
                break;
            case "fotoCurp":
                imgCurps.setImageBitmap(decodeFile(imgCurp));
                hiloEnviar();
                break;
            case "fotoRfc":
                imgRfc.setImageBitmap(decodeFile(imgrfc));
                hiloEnviar();
                break;
            case "fotoIMSS":
                imgIMSS.setImageBitmap(decodeFile(IMSS));
                hiloEnviar();
                break;
            case "fotoElector":
                imgElector.setImageBitmap(decodeFile(elector));
                hiloEnviar();
                break;
            case "fotoDomicilio":
                imgcomprobanteDomicilio.setImageBitmap(decodeFile(comprobanteDomicilio));
                hiloEnviar();
                break;
            case "fotoEstudios":
                imgestudios.setImageBitmap(decodeFile(estudioss));
                hiloEnviar();
                break;
            case "fotoLaborales":
                imglaborales.setImageBitmap(decodeFile(laborales));
                hiloEnviar();
                break;
            case "fotografias":
                imgfotografias.setImageBitmap(decodeFile(fotografiass));
                hiloEnviar();
                break;
            case "fotoInfonavit":
                imginfonavit.setImageBitmap(decodeFile(fotosinfonavit));
                hiloEnviar();
                break;

                /////////////////////////////////       SEGUNDA ETAPA      ////////////////////////////////////////////////////
            case "fotoCarta":
                imgcarta.setImageBitmap(decodeFile(fotoscarta));
                hiloEnviar();
                break;
            case "fotoSolici":
                imgsolicitud.setImageBitmap(decodeFile(fotossolicitud));
                hiloEnviar();
                break;
            case "fotoHuella":
                imgHuella.setImageBitmap(decodeFile(fotoshuellas));
                hiloEnviar();
                break;
            case "fotoPolitica":
                imgPoliticas.setImageBitmap(decodeFile(fotosPoliticas));
                hiloEnviar();
                break;
            case "fotoRequisito":
                imgRequisito.setImageBitmap(decodeFile(fotosRequisitos));
                hiloEnviar();
                break;
            case "fotoFianza":
                imgFianza.setImageBitmap(decodeFile(fotosFianza));
                hiloEnviar();
                break;
            case "fotoCelular":
                imgCelulares.setImageBitmap(decodeFile(fotosCelulares));
                hiloEnviar();
                break;
            case "fotoElectricos":
                imgelectronicos.setImageBitmap(decodeFile(fotoselectronicos));
                hiloEnviar();
                break;
            case "fotoAviso":
                imgAviso.setImageBitmap(decodeFile(fotoAviso));
                hiloEnviar();
                break;
            case "fotoVestimenta":
                imgVestimenta.setImageBitmap(decodeFile(fotoVestimenta));
                hiloEnviar();
                break;
            case "fotoCajera":
                imgCajera.setImageBitmap(decodeFile(fotoCajera));
                hiloEnviar();
                break;
        }
    }

    public Bitmap decodeFile(String path) {
        try {
            BitmapFactory.Options bounds = new BitmapFactory.Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bounds);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            final int REQUIRED_SIZE = 70;
            int scale = 1;
            while (opts.outWidth / scale / 2 >= REQUIRED_SIZE && opts.outHeight / scale / 2 >= REQUIRED_SIZE){
                scale *= 2;
            }
            opts.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeFile(path, opts);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
            return Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private File createFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + countImg;
        return File.createTempFile(imageFileName,".jpg", storageDir);
    }

    private void tomafoto(String foto){
        try {
            Uri photo = null;
            String uri = "com.casavargas.bitacora.fileprovider";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                NombreFoto = foto;
                countImg ++;
                switch (foto){
                    case "fotoRostro":
                        FileRostro = createFile();
                        imgRostro = FileRostro.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(),uri,FileRostro);
                        break;
                    case "fotoEmpleo":
                        FileSolEmpleo = createFile();
                        imgSoliEmpleo = FileSolEmpleo.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileSolEmpleo);
                        break;
                    case "fotoActaNacimineto":
                        FileactaNacimineto = createFile();
                        imgSSActaNacimineto = FileactaNacimineto.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileactaNacimineto);
                        break;
                    case "fotoCurp":
                        Filecurp = createFile();
                        imgCurp = Filecurp.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, Filecurp);
                        break;
                    case "fotoRfc":
                        FileRFC = createFile();
                        imgrfc = FileRFC.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileRFC);
                        break;
                    case "fotoIMSS":
                        FileIMSS = createFile();
                        IMSS = FileIMSS.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileIMSS);
                        break;
                    case "fotoElector":
                        FileINE = createFile();
                        elector = FileINE.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileINE);
                        break;
                    case "fotoDomicilio":
                        FileDOMI = createFile();
                        comprobanteDomicilio = FileDOMI.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileDOMI);
                        break;
                    case "fotoEstudios":
                        FileESTUDIOS = createFile();
                        estudioss = FileESTUDIOS.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileESTUDIOS);
                        break;
                    case "fotoLaborales":
                        FileLABORALES = createFile();
                        laborales = FileLABORALES.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileLABORALES);
                        break;
                    case "fotografias":
                        FileFotos = createFile();
                        fotografiass = FileFotos.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileFotos);
                        break;
                    case "fotoInfonavit":
                        FileInfonavit = createFile();
                        fotosinfonavit = FileInfonavit.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileInfonavit);
                        break;

                        ////////////////////////    SEGUNDA ETAPA       //////////////////////////////////////////////////////

                    case "fotoCarta":
                        FileRenuncia = createFile();
                        fotoscarta = FileRenuncia.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileRenuncia);
                        break;
                    case "fotoSolici":
                        FileSolDatos = createFile();
                        fotossolicitud = FileSolDatos.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileSolDatos);
                        break;
                    case "fotoHuella":
                        FileHuella = createFile();
                        fotoshuellas = FileHuella.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileHuella);
                        break;
                    case "fotoPolitica":
                        FileNomina = createFile();
                        fotosPoliticas = FileNomina.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileNomina);
                        break;
                    case "fotoRequisito":
                        FileContratacion = createFile();
                        fotosRequisitos = FileContratacion.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileContratacion);
                        break;
                    case "fotoFianza":
                        FileFianza = createFile();
                        fotosFianza = FileFianza.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileFianza);
                        break;
                    case "fotoCelular":
                        FileResponsiva = createFile();
                        fotosCelulares = FileResponsiva.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileResponsiva);
                        break;
                    case "fotoElectricos":
                        FileElectronico = createFile();
                        fotoselectronicos = FileElectronico.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileElectronico);
                        break;
                    case "fotoAviso":
                        FileAviso = createFile();
                        fotoAviso = FileAviso.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileAviso);
                        break;
                    case "fotoVestimenta":
                        FileVestimenta = createFile();
                        fotoVestimenta = FileVestimenta.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileVestimenta);
                        break;
                    case "fotoCajera":
                        FileCajera = createFile();
                        fotoCajera = FileCajera.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileCajera);
                        break;
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);
                startActivityForResult(intent, 1);
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"ERROR  " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    public void hiloEnviar(){

        if(isConnect()){

            Runnable objRunnable = new Runnable() {
                @Override
                public void run() {
                    try{

                        Thread.sleep(1000);

                    }catch (Exception ex){
                        ex.printStackTrace();

                    }
                    obHandler.sendEmptyMessage(0);
                }
            };

            Thread objBgThread = new Thread(objRunnable);
            objBgThread.start();

            // btnGuardar.setEnabled(false);

            progressDialog = new ProgressDialog(Reclutamiento.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);

           // Toast.makeText(getApplicationContext(),"puede que funcione si ", Toast.LENGTH_SHORT).show();

        }else{
            sinWifi();
        }
    }



    public void automaticoEnviar(){

        //auxiliar = Integer.parseInt(idSuc) <10? "0"+idSuc: idSuc;

        auxiliar = idSuc;


        ///////////// evaluar si ya existe en la base de datos //////////////////

        RequestQueue requestQueuess = Volley.newRequestQueue(Reclutamiento.this);
        String urlss="https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

        StringRequest stringRequestss = new StringRequest(Request.Method.POST, urlss, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Respuesta post=null;
                Gson gson = new Gson();
                 post = gson.fromJson(response, Respuesta.class);

                for (empleado susu: post.empleado) {

                    id_reclutamiento = susu.id_reclutamiento;
                    nombre           = susu.nombre;
                    puesto           = susu.puesto;
                    estatus          = susu.estatus;
                    rostro           = susu.rostro;
                    sol_empleo       = susu.sol_empleo;
                    acta             = susu.acta;
                    curp             = susu.curp;
                    rfc              = susu.rfc;
                    imss             = susu.imss;
                    ine              = susu.ine;
                    domicilio        = susu.domicilio;
                    estudios         = susu.estudios;
                    constancia1      = susu.constancia1;
                    constancia2      = susu.constancia2;
                    fotografias      = susu.fotografias;
                    infonavit        = susu.infonavit;

                    ///////////segunda etapa /////////////

                    renuncia         = susu.renuncia;
                    sol_datos        = susu.sol_datos;
                    huellas          = susu.huellas;
                    nomina           = susu.nomina;
                    contratacion     = susu.contratacion;
                    fianza           = susu.fianza;
                    responsiva       = susu.responsiva;
                    recibo           = susu.recibo;
                    aviso            = susu.aviso;
                    codigo           = susu.codigo;
                    cajera           = susu.cajera;
                    observaciones    = susu.observaciones;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"no funciona "+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @NonNull
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("opcion","2");
                params.put("cve_sucursal",idSuc);
                params.put("id_reclutamiento",subID);
                return params;
            }
            @NonNull
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("user","ACVprogrammer");
                params.put("pass","V=.a{Xf}e0CR");
                return params;
            }
        };


            if (!imgRostro.equals("") && rostro.equals("0")) {

                FTPClient EnvFotoRostro = new FTPClient();
                FileInputStream envFotoRostro = null;
                String filenameRostro = imgRostro;

                if (!filenameRostro.equals("")) {
                    try {
                        EnvFotoRostro.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoRostro.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoRostro.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoRostro.enterLocalPassiveMode();
                        EnvFotoRostro.sendCommand("OPTS UTF8 ON");
                        // String filenameRostro = imgRostro;
                        envFotoRostro = new FileInputStream(filenameRostro);
                        EnvFotoRostro.storeFile("/a/m.txt", envFotoRostro);

                        urlRostro = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoROSTRO.jpg";

                        EnvFotoRostro.storeFile(urlRostro, envFotoRostro);
                        envFotoRostro.close();

                        EnvFotoRostro.logout();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                        // {"status":true,"message":"Datos Actualizados Correctamente "}


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        params.put("rostro", urlRostro);

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            }

            if (!imgSoliEmpleo.equals("") && sol_empleo.equals("0")) {

                ///////////////////////////////// enviar foto SOL_EMPLEO ////////////////////////

                FTPClient EnvFotoSolEmpleo = new FTPClient();
                FileInputStream envFotoSolEmpleo = null;

                String filenameSolEmpleo = imgSoliEmpleo;

                if (!filenameSolEmpleo.equals("")) {

                    try {
                        EnvFotoSolEmpleo.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoSolEmpleo.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoSolEmpleo.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoSolEmpleo.enterLocalPassiveMode();
                        EnvFotoSolEmpleo.sendCommand("OPTS UTF8 ON");

                        envFotoSolEmpleo = new FileInputStream(filenameSolEmpleo);
                        EnvFotoSolEmpleo.storeFile("/a/m.txt", envFotoSolEmpleo);

                        urlSolEmpleo = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoSOLEMPLE.jpg";

                        EnvFotoSolEmpleo.storeFile(urlSolEmpleo, envFotoSolEmpleo);
                        envFotoSolEmpleo.close();

                        EnvFotoSolEmpleo.logout();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                        // {"status":true,"message":"Datos Actualizados Correctamente "}


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        params.put("sol_empleo", urlSolEmpleo);

                        return params;

                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            }

            if (!imgSSActaNacimineto.equals("") && acta.equals("0")) {

                ///////////////////////////////// enviar foto ACTA ////////////////////////

                FTPClient EnvFotoActa = new FTPClient();
                FileInputStream envFotoActa = null;
                String filenameACTA = imgSSActaNacimineto;

                if (!filenameACTA.equals("")) {
                    try {
                        EnvFotoActa.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoActa.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoActa.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoActa.enterLocalPassiveMode();
                        EnvFotoActa.sendCommand("OPTS UTF8 ON");

                        envFotoActa = new FileInputStream(filenameACTA);
                        EnvFotoActa.storeFile("/a/m.txt", envFotoActa);

                        urlActa = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoACTA.jpg";

                        EnvFotoActa.storeFile(urlActa, envFotoActa);
                        envFotoActa.close();

                        EnvFotoActa.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        //params.put("sol_empleo",urlSolEmpleo);
                        params.put("acta", urlActa);

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if (!imgCurp.equals("") && curp.equals("0")) {

                ///////////////////////////////// enviar foto CURP ////////////////////////

                FTPClient EnvFotoCurp = new FTPClient();
                FileInputStream envFotoCUrp = null;

                String filenameCURP = imgCurp;

                if (!filenameCURP.equals("")) {
                    try {
                        EnvFotoCurp.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoCurp.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoCurp.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoCurp.enterLocalPassiveMode();
                        EnvFotoCurp.sendCommand("OPTS UTF8 ON");

                        envFotoCUrp = new FileInputStream(filenameCURP);
                        EnvFotoCurp.storeFile("/a/m.txt", envFotoCUrp);

                        urlCurp = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoCURP.jpg";

                        EnvFotoCurp.storeFile(urlCurp, envFotoCUrp);
                        envFotoCUrp.close();

                        EnvFotoCurp.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        //params.put("sol_empleo",urlSolEmpleo);
                        //params.put("acta",urlActa);
                        params.put("curp", urlCurp);

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if (!imgrfc.equals("") && rfc.equals("0")) {

                ///////////////////////////////// enviar foto RFC ////////////////////////

                FTPClient EnvFotoRFC = new FTPClient();
                FileInputStream envFotoRFC = null;

                String filenameRFC = imgrfc;

                if (!filenameRFC.equals("")) {
                    try {
                        EnvFotoRFC.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoRFC.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoRFC.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoRFC.enterLocalPassiveMode();
                        EnvFotoRFC.sendCommand("OPTS UTF8 ON");

                        envFotoRFC = new FileInputStream(filenameRFC);
                        EnvFotoRFC.storeFile("/a/m.txt", envFotoRFC);

                        urlRfc = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoRFC.jpg";

                        EnvFotoRFC.storeFile(urlRfc, envFotoRFC);
                        envFotoRFC.close();

                        EnvFotoRFC.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        //params.put("sol_empleo",urlSolEmpleo);
                        //params.put("acta",urlActa);
                        //params.put("curp",urlCurp);
                        params.put("rfc", urlRfc);

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if (!IMSS.equals("") && imss.equals("0")) {

                ///////////////////////////////// enviar foto IMSS ////////////////////////

                FTPClient EnvFotoImss = new FTPClient();
                FileInputStream envFotoIMSS = null;

                String filenameIMSS = IMSS;

                if (!filenameIMSS.equals("")) {
                    try {
                        EnvFotoImss.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoImss.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoImss.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoImss.enterLocalPassiveMode();
                        EnvFotoImss.sendCommand("OPTS UTF8 ON");

                        envFotoIMSS = new FileInputStream(filenameIMSS);
                        EnvFotoImss.storeFile("/a/m.txt", envFotoIMSS);

                        urlImss = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoIMSS.jpg";

                        EnvFotoImss.storeFile(urlImss, envFotoIMSS);
                        envFotoIMSS.close();

                        EnvFotoImss.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        //params.put("sol_empleo",urlSolEmpleo);
                        //params.put("acta",urlActa);
                        //params.put("curp",urlCurp);
                        //params.put("rfc",urlRfc);
                        params.put("imss", urlImss);

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if (!elector.equals("") && ine.equals("0")) {

                ///////////////////////////////// enviar foto INE ////////////////////////

                FTPClient EnvFotoIne = new FTPClient();
                FileInputStream envFotoINE = null;

                String filenameIne = elector;

                if (!filenameIne.equals("")) {
                    try {
                        EnvFotoIne.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoIne.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoIne.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoIne.enterLocalPassiveMode();
                        EnvFotoIne.sendCommand("OPTS UTF8 ON");

                        envFotoINE = new FileInputStream(filenameIne);
                        EnvFotoIne.storeFile("/a/m.txt", envFotoINE);

                        urlIne = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoINE.jpg";

                        EnvFotoIne.storeFile(urlIne, envFotoINE);
                        envFotoINE.close();

                        EnvFotoIne.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        //params.put("sol_empleo",urlSolEmpleo);
                        //params.put("acta",urlActa);
                        //params.put("curp",urlCurp);
                        //params.put("rfc",urlRfc);
                        //params.put("imss",urlImss);
                        params.put("ine", urlIne);

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if (!comprobanteDomicilio.equals("") && domicilio.equals("0")) {
                ///////////////////////////////// enviar foto DOMICILIO ////////////////////////

                FTPClient EnvFotoDomicilio = new FTPClient();
                FileInputStream envFotoDOMICILIO = null;

                String filenameDomicilio = comprobanteDomicilio;

                if (!filenameDomicilio.equals("")) {
                    try {
                        EnvFotoDomicilio.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoDomicilio.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoDomicilio.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoDomicilio.enterLocalPassiveMode();
                        EnvFotoDomicilio.sendCommand("OPTS UTF8 ON");

                        envFotoDOMICILIO = new FileInputStream(filenameDomicilio);
                        EnvFotoDomicilio.storeFile("/a/m.txt", envFotoDOMICILIO);

                        urlDomicilio = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoDOMICILIO.jpg";

                        EnvFotoDomicilio.storeFile(urlDomicilio, envFotoDOMICILIO);
                        envFotoDOMICILIO.close();

                        EnvFotoDomicilio.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        //params.put("sol_empleo",urlSolEmpleo);
                        //params.put("acta",urlActa);
                        //params.put("curp",urlCurp);
                        //params.put("rfc",urlRfc);
                        //params.put("imss",urlImss);
                        //params.put("ine",urlIne);
                        params.put("domicilio", urlDomicilio);

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if (!estudioss.equals("") && estudios.equals("0")) {
                ///////////////////////////////// enviar foto ESTUDIOS ////////////////////////

                FTPClient EnvFotoEstudios = new FTPClient();
                FileInputStream envFotoESTUDIOS = null;

                String filenameEstudios = estudioss;

                if (!filenameEstudios.equals("")) {
                    try {
                        EnvFotoEstudios.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoEstudios.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoEstudios.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoEstudios.enterLocalPassiveMode();
                        EnvFotoEstudios.sendCommand("OPTS UTF8 ON");

                        envFotoESTUDIOS = new FileInputStream(filenameEstudios);
                        EnvFotoEstudios.storeFile("/a/m.txt", envFotoESTUDIOS);

                        urlestudios = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoESTUDIOS.jpg";

                        EnvFotoEstudios.storeFile(urlestudios, envFotoESTUDIOS);
                        envFotoESTUDIOS.close();

                        EnvFotoEstudios.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        //params.put("sol_empleo",urlSolEmpleo);
                        //params.put("acta",urlActa);
                        //params.put("curp",urlCurp);
                        //params.put("rfc",urlRfc);
                        //params.put("imss",urlImss);
                        //params.put("ine",urlIne);
                        //params.put("domicilio",urlDomicilio);
                        params.put("estudios", urlestudios);

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if (!laborales.equals("") && constancia1.equals("0")) {
                ///////////////////////////////// enviar foto Constancias ////////////////////////

                FTPClient EnvFotoContancia = new FTPClient();
                FileInputStream envFotoCONSTANCIA = null;

                String filenamecONATANCIA = laborales;

                if (!filenamecONATANCIA.equals("")) {
                    try {
                        EnvFotoContancia.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoContancia.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoContancia.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoContancia.enterLocalPassiveMode();
                        EnvFotoContancia.sendCommand("OPTS UTF8 ON");

                        envFotoCONSTANCIA = new FileInputStream(filenamecONATANCIA);
                        EnvFotoContancia.storeFile("/a/m.txt", envFotoCONSTANCIA);

                        urlConstancia1 = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoCONSTANCIAS.jpg";

                        EnvFotoContancia.storeFile(urlConstancia1, envFotoCONSTANCIA);
                        envFotoCONSTANCIA.close();

                        EnvFotoContancia.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        //params.put("sol_empleo",urlSolEmpleo);
                        //params.put("acta",urlActa);
                        //params.put("curp",urlCurp);
                        //params.put("rfc",urlRfc);
                        //params.put("imss",urlImss);
                        //params.put("ine",urlIne);
                        //params.put("domicilio",urlDomicilio);
                        //params.put("estudios",urlestudios);
                        params.put("constancia1", urlConstancia1);
                        //params.put("constancia2", urlConstancia1);

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if (!fotografiass.equals("") && fotografias.equals("0")) {
                ///////////////////////////////// enviar foto Fotografias ////////////////////////

                FTPClient EnvFotofOTOGRAFIAS = new FTPClient();
                FileInputStream envFotoFOTOGRAFIAS = null;

                String filenameFOTOGRAFIAS = fotografiass;

                if (!filenameFOTOGRAFIAS.equals("")) {
                    try {
                        EnvFotofOTOGRAFIAS.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotofOTOGRAFIAS.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotofOTOGRAFIAS.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotofOTOGRAFIAS.enterLocalPassiveMode();
                        EnvFotofOTOGRAFIAS.sendCommand("OPTS UTF8 ON");

                        envFotoFOTOGRAFIAS = new FileInputStream(filenameFOTOGRAFIAS);
                        EnvFotofOTOGRAFIAS.storeFile("/a/m.txt", envFotoFOTOGRAFIAS);

                        urlFotografias = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoFOTOGRAFIAS.jpg";

                        EnvFotofOTOGRAFIAS.storeFile(urlFotografias, envFotoFOTOGRAFIAS);
                        envFotoFOTOGRAFIAS.close();

                        EnvFotofOTOGRAFIAS.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        //params.put("sol_empleo",urlSolEmpleo);
                        //params.put("acta",urlActa);
                        //params.put("curp",urlCurp);
                        //params.put("rfc",urlRfc);
                        //params.put("imss",urlImss);
                        //params.put("ine",urlIne);
                        //params.put("domicilio",urlDomicilio);
                        //params.put("estudios",urlestudios);
                        //params.put("constancia1",urlConstancia1);
                        //params.put("constancia2",urlConstancia1);
                        params.put("fotografias", urlFotografias);

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if (!fotosinfonavit.equals("") && infonavit.equals("0")) {
                ///////////////////////////////// enviar foto INFONAVIT ////////////////////////

                FTPClient EnvFotoINfonavit = new FTPClient();
                FileInputStream envFotoFOTOInfonavit = null;

                String filenameINFONAVIT = fotosinfonavit;

                if (!filenameINFONAVIT.equals("")) {
                    try {

                        EnvFotoINfonavit.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoINfonavit.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoINfonavit.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoINfonavit.enterLocalPassiveMode();
                        EnvFotoINfonavit.sendCommand("OPTS UTF8 ON");

                        envFotoFOTOInfonavit = new FileInputStream(filenameINFONAVIT);
                        EnvFotoINfonavit.storeFile("/a/m.txt", envFotoFOTOInfonavit);

                        urlInfonavit = "/" + auxiliar + "/candidato/" + id_reclutamiento + "fotoINFONAVIT.jpg";

                        EnvFotoINfonavit.storeFile(urlInfonavit, envFotoFOTOInfonavit);
                        envFotoFOTOInfonavit.close();

                        EnvFotoINfonavit.logout();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "3");
                        params.put("id_reclutamiento", subID);
                        //params.put("rostro", urlRostro);
                        //params.put("sol_empleo",urlSolEmpleo);
                        //params.put("acta",urlActa);
                        //params.put("curp",urlCurp);
                        //params.put("rfc",urlRfc);
                        //params.put("imss",urlImss);
                        //params.put("ine",urlIne);
                        //params.put("domicilio",urlDomicilio);
                        //params.put("estudios",urlestudios);
                        //params.put("constancia1",urlConstancia1);
                        //params.put("constancia2",urlConstancia1);
                        //params.put("fotografias",urlFotografias);
                        params.put("infonavit", urlInfonavit);


                        /// segunda etapa ///

               /* params.put("fotoCarta",urlRenuncia);
                params.put("fotoSolici",urlSolicitud);
                params.put("fotoHuella",urlHuellas);
                params.put("fotoPolitica",urlPoliticas);
                params.put("fotoRequisito",urlRequisitos);
                params.put("fotoFianza",urlFianza);
                params.put("fotoCelular",urlCelulares);
                params.put("fotoElectricos",urlElectronicos);
                params.put("fotoAviso",urlAviso);
                params.put("fotoVestimenta",urlVestimenta);
                params.put("fotoCajera",urlCajera);

                params.put("OBSERVACIONES", observacioness.getText().toString());

                */

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }


            /////////////////////////// PASAR A LA SE GUNDA ETAPA ///////////////////////////////

            if(!fotoscarta.equals("") && renuncia.equals("0")){
                ///////////////////////////////// enviar foto  RENUNCIA  ////////////////////////
                FTPClient EnvFotoRenuncia = new FTPClient();
                FileInputStream envFotoFOTORENUNCIA= null;

                String filenameRENUNCIA= fotoscarta;

                if(!filenameRENUNCIA.equals("")){
                    try {
                        EnvFotoRenuncia.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoRenuncia.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoRenuncia.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoRenuncia.enterLocalPassiveMode();
                        EnvFotoRenuncia.sendCommand("OPTS UTF8 ON");

                        envFotoFOTORENUNCIA = new FileInputStream(filenameRENUNCIA);
                        EnvFotoRenuncia.storeFile("/a/m.txt", envFotoFOTORENUNCIA);

                        urlRenuncia ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoRENUNCIA.jpg";

                        EnvFotoRenuncia.storeFile(urlRenuncia, envFotoFOTORENUNCIA);
                        envFotoFOTORENUNCIA.close();

                        EnvFotoRenuncia.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                params.put("renuncia",urlRenuncia);
               /* params.put("fotoSolici",urlSolicitud);
                params.put("fotoHuella",urlHuellas);
                params.put("fotoPolitica",urlPoliticas);
                params.put("fotoRequisito",urlRequisitos);
                params.put("fotoFianza",urlFianza);
                params.put("fotoCelular",urlCelulares);
                params.put("fotoElectricos",urlElectronicos);
                params.put("fotoAviso",urlAviso);
                params.put("fotoVestimenta",urlVestimenta);
                params.put("fotoCajera",urlCajera);

                params.put("observaciones", observacioness.getText().toString());

                */
                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if(!fotossolicitud.equals("") && sol_datos.equals("0")){

                ///////////////////////////////// enviar foto  SOLICITUD  ////////////////////////

                FTPClient EnvFotoSolicitud = new FTPClient();
                FileInputStream envFotoFOTOSOLICITUD= null;

                String filenameSOLICITUD= fotossolicitud;

                if(!filenameSOLICITUD.equals("")){
                    try {
                        EnvFotoSolicitud.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoSolicitud.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoSolicitud.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoSolicitud.enterLocalPassiveMode();
                        EnvFotoSolicitud.sendCommand("OPTS UTF8 ON");

                        envFotoFOTOSOLICITUD = new FileInputStream(filenameSOLICITUD);
                        EnvFotoSolicitud.storeFile("/a/m.txt", envFotoFOTOSOLICITUD);

                        urlSolicitud ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoSOLICITUD.jpg";

                        EnvFotoSolicitud.storeFile(urlSolicitud, envFotoFOTOSOLICITUD);
                        envFotoFOTOSOLICITUD.close();

                        EnvFotoSolicitud.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                        //params.put("fotoCarta",urlRenuncia);
                params.put("sol_datos",urlSolicitud);
                /*params.put("fotoHuella",urlHuellas);
                params.put("fotoPolitica",urlPoliticas);
                params.put("fotoRequisito",urlRequisitos);
                params.put("fotoFianza",urlFianza);
                params.put("fotoCelular",urlCelulares);
                params.put("fotoElectricos",urlElectronicos);
                params.put("fotoAviso",urlAviso);
                params.put("fotoVestimenta",urlVestimenta);
                params.put("fotoCajera",urlCajera);

                params.put("OBSERVACIONES", observacioness.getText().toString());

                */
                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if(!fotoshuellas.equals("") && huellas.equals("0")){

                ///////////////////////////////// enviar foto HUELLAS  ////////////////////////

                FTPClient EnvFotoHuellas = new FTPClient();
                FileInputStream envFotoFOTOSHUELLAS= null;

                String filenameHUELLAS = fotoshuellas;

                if(!filenameHUELLAS.equals("")){
                    try {
                        EnvFotoHuellas.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoHuellas.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoHuellas.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoHuellas.enterLocalPassiveMode();
                        EnvFotoHuellas.sendCommand("OPTS UTF8 ON");

                        envFotoFOTOSHUELLAS = new FileInputStream(filenameHUELLAS);
                        EnvFotoHuellas.storeFile("/a/m.txt", envFotoFOTOSHUELLAS);

                        urlHuellas ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoHUELLAS.jpg";

                        EnvFotoHuellas.storeFile(urlHuellas, envFotoFOTOSHUELLAS);
                        envFotoFOTOSHUELLAS.close();

                        EnvFotoHuellas.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                        //params.put("fotoCarta",urlRenuncia);
                        //params.put("fotoSolici",urlSolicitud);
                params.put("huellas",urlHuellas);
                /*params.put("fotoPolitica",urlPoliticas);
                params.put("fotoRequisito",urlRequisitos);
                params.put("fotoFianza",urlFianza);
                params.put("fotoCelular",urlCelulares);
                params.put("fotoElectricos",urlElectronicos);
                params.put("fotoAviso",urlAviso);
                params.put("fotoVestimenta",urlVestimenta);
                params.put("fotoCajera",urlCajera);

                params.put("OBSERVACIONES", observacioness.getText().toString());

                */
                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if(!fotosPoliticas.equals("") && nomina.equals("0")) {
                ///////////////////////////////// enviar foto POLITICAS  ////////////////////////

                FTPClient EnvFotoPoliticas = new FTPClient();
                FileInputStream envFotoFOTOSPOLITICAS = null;

                String filenamePOLITICAS = fotosPoliticas;

                if(!filenamePOLITICAS.equals("")){

                    try {
                        EnvFotoPoliticas.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoPoliticas.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoPoliticas.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoPoliticas.enterLocalPassiveMode();
                        EnvFotoPoliticas.sendCommand("OPTS UTF8 ON");

                        envFotoFOTOSPOLITICAS = new FileInputStream(filenamePOLITICAS);
                        EnvFotoPoliticas.storeFile("/a/m.txt", envFotoFOTOSPOLITICAS);

                        urlPoliticas ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoPOLITICAS.jpg";

                        EnvFotoPoliticas.storeFile(urlPoliticas, envFotoFOTOSPOLITICAS);
                        envFotoFOTOSPOLITICAS.close();

                        EnvFotoPoliticas.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                        //params.put("fotoCarta",urlRenuncia);
                        //params.put("fotoSolici",urlSolicitud);
                        //params.put("fotoHuella",urlHuellas);
                params.put("nomina",urlPoliticas);
                /*params.put("fotoRequisito",urlRequisitos);
                params.put("fotoFianza",urlFianza);
                params.put("fotoCelular",urlCelulares);
                params.put("fotoElectricos",urlElectronicos);
                params.put("fotoAviso",urlAviso);
                params.put("fotoVestimenta",urlVestimenta);
                params.put("fotoCajera",urlCajera);

                params.put("OBSERVACIONES", observacioness.getText().toString());

                */
                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if(!fotosRequisitos.equals("") && contratacion.equals("0")){
                ///////////////////////////////// enviar foto REQUISITO  ////////////////////////

                FTPClient EnvFotoRequisitos = new FTPClient();
                FileInputStream envFotoFOTOSREQUISITOS = null;

                String filenameREQUISITOS = fotosRequisitos;

                if(!filenameREQUISITOS.equals("")){
                    try {
                        EnvFotoRequisitos.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoRequisitos.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoRequisitos.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoRequisitos.enterLocalPassiveMode();
                        EnvFotoRequisitos.sendCommand("OPTS UTF8 ON");

                        envFotoFOTOSREQUISITOS = new FileInputStream(filenameREQUISITOS);
                        EnvFotoRequisitos.storeFile("/a/m.txt", envFotoFOTOSREQUISITOS);

                        urlRequisitos ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoREQUISITOS.jpg";

                        EnvFotoRequisitos.storeFile(urlRequisitos, envFotoFOTOSREQUISITOS);
                        envFotoFOTOSREQUISITOS.close();

                        EnvFotoRequisitos.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                        //params.put("fotoCarta",urlRenuncia);
                        //params.put("fotoSolici",urlSolicitud);
                        //params.put("fotoHuella",urlHuellas);
                        //params.put("fotoPolitica",urlPoliticas);
                params.put("contratacion",urlRequisitos);
                /*params.put("fotoFianza",urlFianza);
                params.put("fotoCelular",urlCelulares);
                params.put("fotoElectricos",urlElectronicos);
                params.put("fotoAviso",urlAviso);
                params.put("fotoVestimenta",urlVestimenta);
                params.put("fotoCajera",urlCajera);

                params.put("OBSERVACIONES", observacioness.getText().toString());

                */
                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if(!fotosFianza.equals("") && fianza.equals("0")){
                ///////////////////////////////// enviar foto FIANZA  ////////////////////////

                FTPClient EnvFotoFianza = new FTPClient();
                FileInputStream envFotoFOTOSFIANZA = null;

                String filenameFIANZA = fotosFianza;

                if(!filenameFIANZA.equals("")){
                    try {
                        EnvFotoFianza.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoFianza.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoFianza.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoFianza.enterLocalPassiveMode();
                        EnvFotoFianza.sendCommand("OPTS UTF8 ON");

                        envFotoFOTOSFIANZA = new FileInputStream(filenameFIANZA);
                        EnvFotoFianza.storeFile("/a/m.txt", envFotoFOTOSFIANZA);

                        urlFianza ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoFIANZA.jpg";

                        EnvFotoFianza.storeFile(urlFianza, envFotoFOTOSFIANZA);
                        envFotoFOTOSFIANZA.close();

                        EnvFotoFianza.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                        //params.put("fotoCarta",urlRenuncia);
                        //params.put("fotoSolici",urlSolicitud);
                        //params.put("fotoHuella",urlHuellas);
                        //params.put("fotoPolitica",urlPoliticas);
                        //params.put("fotoRequisito",urlRequisitos);
                params.put("fianza",urlFianza);
                /*params.put("fotoCelular",urlCelulares);
                params.put("fotoElectricos",urlElectronicos);
                params.put("fotoAviso",urlAviso);
                params.put("fotoVestimenta",urlVestimenta);
                params.put("fotoCajera",urlCajera);

                params.put("OBSERVACIONES", observacioness.getText().toString());

                */
                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if(!fotosCelulares.equals("") && responsiva.equals("0")){
                ///////////////////////////////// enviar foto CELULARES  ////////////////////////

                FTPClient EnvFotoCelulares = new FTPClient();
                FileInputStream envFotoFOTOCELULARES = null;

                String filenameCELULARES = fotosCelulares;

                if(!filenameCELULARES.equals("")){
                    try {
                        EnvFotoCelulares.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoCelulares.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoCelulares.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoCelulares.enterLocalPassiveMode();
                        EnvFotoCelulares.sendCommand("OPTS UTF8 ON");

                        envFotoFOTOCELULARES = new FileInputStream(filenameCELULARES);
                        EnvFotoCelulares.storeFile("/a/m.txt", envFotoFOTOCELULARES);

                        urlCelulares ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoCELULARES.jpg";

                        EnvFotoCelulares.storeFile(urlCelulares, envFotoFOTOCELULARES);
                        envFotoFOTOCELULARES.close();

                        EnvFotoCelulares.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                        //params.put("fotoCarta",urlRenuncia);
                        //params.put("fotoSolici",urlSolicitud);
                        //params.put("fotoHuella",urlHuellas);
                        //params.put("fotoPolitica",urlPoliticas);
                        //params.put("fotoRequisito",urlRequisitos);
                        //params.put("fotoFianza",urlFianza);
                params.put("responsiva",urlCelulares);
                /*params.put("fotoElectricos",urlElectronicos);
                params.put("fotoAviso",urlAviso);
                params.put("fotoVestimenta",urlVestimenta);
                params.put("fotoCajera",urlCajera);

                params.put("OBSERVACIONES", observacioness.getText().toString());

                */
                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if(!fotoselectronicos.equals("") && recibo.equals("0")){
            ///////////////////////////////// enviar foto ELECTRONICOS  ////////////////////////

                FTPClient EnvFotoElectronico = new FTPClient();
                FileInputStream envFotoFOTOELECTRONICOS = null;

                String filenameELECTRONICOS = fotoselectronicos;

                if(!filenameELECTRONICOS.equals("")){
                    try {
                        EnvFotoElectronico.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoElectronico.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoElectronico.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoElectronico.enterLocalPassiveMode();
                        EnvFotoElectronico.sendCommand("OPTS UTF8 ON");

                        envFotoFOTOELECTRONICOS = new FileInputStream(filenameELECTRONICOS);
                        EnvFotoElectronico.storeFile("/a/m.txt", envFotoFOTOELECTRONICOS);

                        urlElectronicos ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoELECTRONICOS.jpg";

                        EnvFotoElectronico.storeFile(urlElectronicos, envFotoFOTOELECTRONICOS);
                        envFotoFOTOELECTRONICOS.close();

                        EnvFotoElectronico.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                        //params.put("fotoCarta",urlRenuncia);
                        //params.put("fotoSolici",urlSolicitud);
                        //params.put("fotoHuella",urlHuellas);
                        //params.put("fotoPolitica",urlPoliticas);
                        //params.put("fotoRequisito",urlRequisitos);
                        //params.put("fotoFianza",urlFianza);
                       // params.put("fotoCelular",urlCelulares);
                params.put("recibo",urlElectronicos);
               /* params.put("fotoAviso",urlAviso);
                params.put("fotoVestimenta",urlVestimenta);
                params.put("fotoCajera",urlCajera);

                params.put("OBSERVACIONES", observacioness.getText().toString());

                */
                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if(!fotoAviso.equals("") && aviso.equals("0")){
                ///////////////////////////////// enviar foto AVISO  ////////////////////////

                FTPClient EnvFotoAviso = new FTPClient();
                FileInputStream envFotoFOTOAVISO = null;

                String filenameAVISO = fotoAviso;

                if(!filenameAVISO.equals("")){
                    try {
                        EnvFotoAviso.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoAviso.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoAviso.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoAviso.enterLocalPassiveMode();
                        EnvFotoAviso.sendCommand("OPTS UTF8 ON");

                        envFotoFOTOAVISO = new FileInputStream(filenameAVISO);
                        EnvFotoAviso.storeFile("/a/m.txt", envFotoFOTOAVISO);

                        urlAviso ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoAVISO.jpg";

                        EnvFotoAviso.storeFile(urlAviso, envFotoFOTOAVISO);
                        envFotoFOTOAVISO.close();

                        EnvFotoAviso.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                        //params.put("fotoCarta",urlRenuncia);
                        //params.put("fotoSolici",urlSolicitud);
                        //params.put("fotoHuella",urlHuellas);
                        //params.put("fotoPolitica",urlPoliticas);
                        //params.put("fotoRequisito",urlRequisitos);
                        //params.put("fotoFianza",urlFianza);
                        // params.put("fotoCelular",urlCelulares);
                        //params.put("fotoElectricos",urlElectronicos);
                params.put("aviso",urlAviso);
                /*params.put("fotoVestimenta",urlVestimenta);
                params.put("fotoCajera",urlCajera);

                params.put("OBSERVACIONES", observacioness.getText().toString());

                */
                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if(!fotoVestimenta.equals("") && codigo.equals("0")){
                ///////////////////////////////// enviar foto VESTIMENTA  ////////////////////////

                FTPClient EnvFotoVestimenta = new FTPClient();
                FileInputStream envFotoVESTIMENTA = null;

                String filenameVESTIMENTA = fotoVestimenta;

                if(!filenameVESTIMENTA.equals("")){
                    try {
                        EnvFotoVestimenta.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoVestimenta.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoVestimenta.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoVestimenta.enterLocalPassiveMode();
                        EnvFotoVestimenta.sendCommand("OPTS UTF8 ON");

                        envFotoVESTIMENTA = new FileInputStream(filenameVESTIMENTA);
                        EnvFotoVestimenta.storeFile("/a/m.txt", envFotoVESTIMENTA);

                        urlVestimenta ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoVESTIMENTA.jpg";

                        EnvFotoVestimenta.storeFile(urlVestimenta, envFotoVESTIMENTA);
                        envFotoVESTIMENTA.close();

                        EnvFotoVestimenta.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                        //params.put("fotoCarta",urlRenuncia);
                        //params.put("fotoSolici",urlSolicitud);
                        //params.put("fotoHuella",urlHuellas);
                        //params.put("fotoPolitica",urlPoliticas);
                        //params.put("fotoRequisito",urlRequisitos);
                        //params.put("fotoFianza",urlFianza);
                        // params.put("fotoCelular",urlCelulares);
                        //params.put("fotoElectricos",urlElectronicos);
                        //params.put("fotoAviso",urlAviso);
                params.put("codigo",urlVestimenta);
                /*params.put("fotoCajera",urlCajera);

                params.put("OBSERVACIONES", observacioness.getText().toString());

                */
                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }

            if(!fotoCajera.equals("") && cajera.equals("0")){
                ///////////////////////////////// enviar foto CAJERA  ////////////////////////

                FTPClient EnvFotoCajera = new FTPClient();
                FileInputStream envFotoCAJERA = null;

                String filenameCAJERA = fotoCajera;

                if(!filenameCAJERA.equals("")){
                    try {
                        EnvFotoCajera.connect("ftp.abarrotescasavargas.mx", 21);
                        EnvFotoCajera.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                        EnvFotoCajera.setFileType(FTP.BINARY_FILE_TYPE);
                        EnvFotoCajera.enterLocalPassiveMode();
                        EnvFotoCajera.sendCommand("OPTS UTF8 ON");

                        envFotoCAJERA = new FileInputStream(filenameCAJERA);
                        EnvFotoCajera.storeFile("/a/m.txt", envFotoCAJERA);

                        urlCajera ="/"+auxiliar+"/candidato/"+id_reclutamiento+"fotoCAJERA.jpg";

                        EnvFotoCajera.storeFile(urlCajera, envFotoCAJERA);
                        envFotoCAJERA.close();

                        EnvFotoCajera.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
                String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Respuesta post = gson.fromJson(response, Respuesta.class);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opcion", "4");
                        params.put("id_reclutamiento", subID);

                        /// segunda etapa ///

                        //params.put("fotoCarta",urlRenuncia);
                        //params.put("fotoSolici",urlSolicitud);
                        //params.put("fotoHuella",urlHuellas);
                        //params.put("fotoPolitica",urlPoliticas);
                        //params.put("fotoRequisito",urlRequisitos);
                        //params.put("fotoFianza",urlFianza);
                        // params.put("fotoCelular",urlCelulares);
                        //params.put("fotoElectricos",urlElectronicos);
                        //params.put("fotoAviso",urlAviso);
                        //params.put("fotoVestimenta",urlVestimenta);
                params.put("cajera",urlCajera);

               //params.put("OBSERVACIONES", observacioness.getText().toString());

                        return params;
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", "ACVprogrammer");
                        params.put("pass", "V=.a{Xf}e0CR");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }


        requestQueuess.add(stringRequestss);

    }


    public void sinWifi(){

        Dialog sinwifi = new Dialog(this);
        sinwifi.setContentView(R.layout.sinwifi);
        sinwifi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView cerrar=sinwifi.findViewById(R.id.imgCLose);
        Button btnOk=sinwifi.findViewById(R.id.btnReintentar);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinwifi.dismiss();
                Toast.makeText(getApplicationContext(),"CERRADO", Toast.LENGTH_SHORT).show();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinwifi.dismiss();
                //Toast.makeText(getApplicationContext(),"PERFECTO", Toast.LENGTH_SHORT).show();
            }
        });



        sinwifi.show();


    }

    public void mensajeExito(){

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
            }
        });


        enviadoExito.show();

    }

    public void enviarObservacion(){
        RequestQueue requestQueue = Volley.newRequestQueue(Reclutamiento.this);
        String url = "https://abarrotescasavargas.mx/api/mobile/rh/addphoto.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Respuesta post = gson.fromJson(response, Respuesta.class);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opcion", "4");
                params.put("id_reclutamiento", subID);

                params.put("observaciones", observacioness.getText().toString());

                return params;
            }

            @NonNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", "ACVprogrammer");
                params.put("pass", "V=.a{Xf}e0CR");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public boolean isConnect(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
