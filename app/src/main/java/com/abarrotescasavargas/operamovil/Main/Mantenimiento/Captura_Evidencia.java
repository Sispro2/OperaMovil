package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.abarrotescasavargas.operamovil.Main.ActivityEvidencia;
import com.abarrotescasavargas.operamovil.Main.FunGenerales.Funciones;
import com.abarrotescasavargas.operamovil.Main.Modelos.RespuestaWS;
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;
import com.abarrotescasavargas.operamovil.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Captura_Evidencia extends AppCompatActivity {
    private ImageView imgvDocumento3;
    private Button btnEnviar3;
    private TextView txtvInstruccion2;
    ProgressDialog progressDialog;
    private  String  UrlDocumento="";
    private int idReporte;
    private static final String uriFileProvider = "com.abarrotescasavargas.operamovil.fileprovider";

    Retrofit retrofit = new RetrofitMantto().getRuta();
    PeticionMtto peticionMtto = retrofit.create(PeticionMtto.class);
    private final Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        idReporte = getIntent().getIntExtra("idReporte",0);
        Bundle extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_evidencia);
        TomaFoto();//Al presionar el boton abre automaticamente la camara para ahorrarle un paso al usuario
        Setup();
        Events();
    }
    private void Setup(){

        imgvDocumento3 = findViewById(R.id.imgvDocumento3);
        btnEnviar3 = findViewById(R.id.btnEnviar3);
        txtvInstruccion2 = findViewById(R.id.txtvInstruccion2);
        progressDialog = new ProgressDialog(this);



    }
    private void Events(){
        imgvDocumento3.setOnClickListener(v -> {
            TomaFoto();
        });
        btnEnviar3.setOnClickListener(v -> {
            if (!Objects.equals(UrlDocumento, ""))
                Envia();
        });
    }
    private void TomaFoto(){
        try {
            Uri uriPhoto = null;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (intent.resolveActivity(getPackageManager()) != null) {
                //NombreFoto = "fotoTransporte";
                File file = createFile();
                UrlDocumento = file.getAbsolutePath();
                uriPhoto = FileProvider.getUriForFile(getApplicationContext(), uriFileProvider, file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
                startActivityForResult(intent, 10);
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "ERROR  " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private File createFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String imageFileName = "JPEG_DOCUMENTO" ;
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            imgvDocumento3.setImageBitmap(Funciones.decodeFile(UrlDocumento));
            txtvInstruccion2.setText(R.string.cp_otrafoto);

        }
    }

    /*INICIA EL APARTADO PARA ENVIAR LAS FOTOGRAFIAS */
    private void Envia() {
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.enviando_dialog);
        if (Funciones.ValidaWifi(this)) {
            GuardaInfo();
        } else if (Funciones.ValidaDatos(this)) {
            GuardaInfo();
        } else {
            progressDialog.cancel();
            Toast.makeText(this, R.string.msj_sinconexiones, Toast.LENGTH_SHORT).show();
        }
    }
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MultipartBody.FORM, descriptionString);
    }

    private MultipartBody.Part prepareFile() {
        File file = new File(UrlDocumento);
        file= Funciones.ComprimeBitmapToFile(file) ;
        File file2 = new File(file.getAbsolutePath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        return MultipartBody.Part.createFormData("imgReporteM", file.getName(), requestFile);
    }
    private void GuardaInfo() {

        getIntent().getExtras();
        SucursalRepository sucursalRepository = new SucursalRepository(this);
        Call<RespuestaWS> call =  peticionMtto.subeEvidencia(
                createPartFromString(Integer.toString(idReporte))
                , createPartFromString(sucursalRepository.GetDetalleSucursal().getKS_CVESUC())
                , createPartFromString(getIntent().getStringExtra("evidenciasList"))
                , prepareFile()
        );
        call.enqueue(new Callback<RespuestaWS>() {
            @Override
            public void onResponse(@NonNull Call<RespuestaWS> call, @NonNull Response<RespuestaWS> response) {
                RespuestaWS respuesta = response.body();
                progressDialog.cancel();
                Toast.makeText(Captura_Evidencia.this, respuesta.getMessage(), Toast.LENGTH_SHORT).show();
                if (respuesta.isStatus()) {
                    ExampleRunnable runnable = new ExampleRunnable(sucursalRepository.GetDetalleSucursal().getKS_CVESUC(), Integer.toString(idReporte));
                    new Thread(runnable).start();
                }
            }
            @Override
            public void onFailure(@NonNull Call<RespuestaWS> call, @NonNull Throwable t) {
                progressDialog.cancel();
                Toast.makeText(Captura_Evidencia.this, "Intermitencia de Internet, Intente Nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LlenaDatos(ArrayList<Evidencia> documentList, String cveSucursal, String idReclutamiento) {
        Intent intent = new Intent(this, ActivityEvidencia.class);
        intent.putExtra("cveSucursal", cveSucursal);
        intent.putExtra("idReclutamiento", idReclutamiento);
        intent.putExtra("documentList", documentList);
        intent.putExtra("nombre", "nombre");
        intent.putExtra("puesto", "puesto");
        intent.putExtra("evidenciasList", getIntent().getStringExtra("evidenciasListTotal"));
        startActivity(intent);
        finish();
    }
    class ExampleRunnable implements Runnable {
        String cveSucursal, idReclutamiento;

        // Constructor de ExampleRunnable que recibe dos parámetros: cveSucursal e idReclutamiento.
        ExampleRunnable(String cveSucursal, String idReclutamiento) {
            // Asigna los valores recibidos a las variables de instancia de la clase.
            this.cveSucursal = cveSucursal;
            this.idReclutamiento = idReclutamiento;
        }

        // Método run() que se ejecuta cuando se inicia un hilo que contiene una instancia de ExampleRunnable.
        @Override
        public void run() {
            // Crea una llamada (Call) para realizar una solicitud HTTP utilizando Retrofit y OkHttp.
            Call<ResponseEvidencias> call = peticionMtto.obtieneListado(idReporte, cveSucursal);

            // Realiza la solicitud de manera asincrónica y maneja la respuesta en los métodos onResponse() y onFailure() del Callback asociado.
            call.enqueue(new Callback<ResponseEvidencias>() {
                // Método onResponse() se ejecuta cuando se recibe una respuesta exitosa del servidor.
                public void onResponse(@NonNull Call<ResponseEvidencias> call, @NonNull Response<ResponseEvidencias> response) {
                    // Verifica si la respuesta del servidor indica que la solicitud fue exitosa (status=true).
                    if (response.body().isStatus()) {
                        // Obtiene una lista de documentos de la respuesta.
                        ArrayList<Evidencia> documentList = response.body().getEvidencias();

                        try {
                            // Introduce un retraso de 100 milisegundos (0.1 segundos) en el hilo actual.
                            Thread.sleep(100);

                            // Utiliza mainHandler.post() para ejecutar una tarea en el hilo principal (UI thread).
                            mainHandler.post(() -> {
                                // Llama a la función LlenaDatos() y pasa la lista de documentos, cveSucursal e idReclutamiento como argumentos.
                                LlenaDatos(documentList, cveSucursal, idReclutamiento);
                            });
                        } catch (InterruptedException e) {
                            // Captura y lanza una excepción en caso de interrupción durante el retraso.
                            throw new RuntimeException(e);
                        }
                    }
                }

                // Método onFailure() se ejecuta cuando la solicitud HTTP experimenta un fallo.
                public void onFailure(@NonNull Call<ResponseEvidencias> call, @NonNull Throwable t) {
                    // Registra un mensaje de error en el log.
                    Log.i("ERROR en ", "onFailure: " + t.toString());
                }
            });
        }
    }
}