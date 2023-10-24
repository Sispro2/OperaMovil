package com.abarrotescasavargas.operamovil.Main.Documentos;

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


import com.abarrotescasavargas.operamovil.Main.FunGenerales.Funciones;
import com.abarrotescasavargas.operamovil.Main.Modelos.RespuestaWS;
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;
import com.abarrotescasavargas.operamovil.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CapturaDocumento extends AppCompatActivity {
    private ImageView imgvDocumento;
    private String UrlDocumento = "", nombre, puesto, idReclutamiento, nombreCorto;
    private Button btnEnviar;
    Retrofit retrofit = new RetrofitRRHH().getRuta();
    PeticionRRHH peticionRRHH = retrofit.create(PeticionRRHH.class);
    ProgressDialog progressDialog;
    private final Handler mainHandler = new Handler();
    private static final String uriFileProvider = "com.abarrotescasavargas.operamovil.fileprovider";
    private TextView txtvInstruccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_documento);
        SetUp();
        Events();
        TomaFoto();
    }

    private void SetUp() {
        imgvDocumento = findViewById(R.id.imgvDocumento);
        btnEnviar = findViewById(R.id.btnEnviar);
        txtvInstruccion = findViewById(R.id.txtvInstruccion);
        progressDialog = new ProgressDialog(this);
       // cveSucursal = getIntent().getStringExtra("cveSucursal");
        idReclutamiento = getIntent().getStringExtra("idReporte");
        nombre = getIntent().getStringExtra("nombre");
        puesto = getIntent().getStringExtra("puesto");
        nombreCorto = getIntent().getStringExtra("nombreCorto");
    }
    private void Events() {
        imgvDocumento.setOnClickListener(v -> {
            TomaFoto();
        });
        btnEnviar.setOnClickListener(v -> {

            if (!Objects.equals(UrlDocumento, ""))
                Envia();
        });
    }
    private void Envia() {
        // Establece el fondo del ProgressDialog como transparente para personalizar su apariencia.
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Muestra el ProgressDialog.
        progressDialog.show();
        // Establece el contenido del ProgressDialog como un diseño personalizado llamado "enviando_dialog".
        progressDialog.setContentView(R.layout.enviando_dialog);

        // Verifica si hay conexión WiFi disponible utilizando la función ValidaWifi().
        if (Funciones.ValidaWifi(this)) {
            // Si hay conexión WiFi, llama a la función GuardaInfo().
            GuardaInfo();
        } else if (Funciones.ValidaDatos(this)) {
            // Si no hay conexión WiFi pero hay conexión de datos móviles, también llama a la función GuardaInfo().
            GuardaInfo();
        } else {
            // Si no hay conexiones disponibles (ni WiFi ni datos móviles), cancela el ProgressDialog.
            progressDialog.cancel();
            // Muestra un mensaje de Toast indicando que no hay conexiones disponibles.
            Toast.makeText(this, R.string.msj_sinconexiones, Toast.LENGTH_SHORT).show();
        }
    }

    private void LlenaDatos(ArrayList<Documentos> documentList, String cveSucursal, String idReclutamiento) {
        Intent intent = new Intent(this, ActivityDocumentos.class);
        intent.putExtra("cveSucursal", cveSucursal);
        intent.putExtra("idReclutamiento", idReclutamiento);
        intent.putExtra("documentList", documentList);
        intent.putExtra("nombre", nombre);
        intent.putExtra("puesto", puesto);
        startActivity(intent);
        finish();
    }

    private void GuardaInfo() {
        // Crea una instancia del repositorio de la sucursal almacena información sobre la sucursal actual.
        SucursalRepository sucursalRepository = new SucursalRepository(this);

        // Crea una llamada (Call) para enviar un documento utilizando la interfaz peticionRRHH.
        Call<RespuestaWS> call = peticionRRHH.subeDocumento(
                createPartFromString(idReclutamiento),  // Convierte idReclutamiento a una parte de la solicitud HTTP.
                createPartFromString(sucursalRepository.GetDetalleSucursal().getKS_CVESUC()), // Obtiene el código de sucursal y lo convierte a una parte de la solicitud HTTP.
                createPartFromString(nombreCorto), // Convierte nombreCorto a una parte de la solicitud HTTP.
                prepareFile() // Prepara la foto para ser enviada como parte de la solicitud HTTP.
        );

        // Establece un Callback para manejar la respuesta de la solicitud HTTP.
        call.enqueue(new Callback<RespuestaWS>() {
            @Override
            public void onResponse(@NonNull Call<RespuestaWS> call, @NonNull Response<RespuestaWS> response) {
                // Obtiene la respuesta del servidor en forma de objeto RespuestaWS.
                RespuestaWS respuesta = response.body();
                // Cancela el ProgressDialog que se muestra durante el envío.
                progressDialog.cancel();
                // Muestra un Toast con el mensaje de respuesta del servidor.
                Toast.makeText(CapturaDocumento.this, respuesta.getMessage(), Toast.LENGTH_SHORT).show();

                // Verifica si la respuesta del servidor indica un estado exitoso (status=true).
                if (respuesta.isStatus()) {
                    // Si la respuesta es exitosa, crea una instancia de ExampleRunnable y la ejecuta en un nuevo hilo.
                    ExampleRunnable runnable = new ExampleRunnable(sucursalRepository.GetDetalleSucursal().getKS_CVESUC(), idReclutamiento);
                    new Thread(runnable).start();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RespuestaWS> call, @NonNull Throwable t) {
                // En caso de fallo en la solicitud HTTP, cancela el ProgressDialog y muestra un mensaje de error en un Toast.
                progressDialog.cancel();
                Toast.makeText(CapturaDocumento.this, "Intermitencia de Internet, Intente Nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MultipartBody.FORM, descriptionString);
    }

    private MultipartBody.Part prepareFile() {
        File file = new File(UrlDocumento);
        file= Funciones.ComprimeBitmapToFile(file) ;
        File file2 = new File(file.getAbsolutePath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        return MultipartBody.Part.createFormData("img_documento", file.getName(), requestFile);
    }
    private void TomaFoto() {
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
        //String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HHmm").format(new Date());
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd_MM_yyyy_HHmm", Locale.getDefault());
        String imageFileName = "JPEG_" + sdfDate;
        return File.createTempFile(imageFileName, ".jpg", storageDir);
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
            Call<ResponseDocumentos> call = peticionRRHH.obtieneListado(idReclutamiento, cveSucursal);

            // Realiza la solicitud de manera asincrónica y maneja la respuesta en los métodos onResponse() y onFailure() del Callback asociado.
            call.enqueue(new Callback<ResponseDocumentos>() {
                // Método onResponse() se ejecuta cuando se recibe una respuesta exitosa del servidor.
                public void onResponse(@NonNull Call<ResponseDocumentos> call, @NonNull Response<ResponseDocumentos> response) {
                    // Verifica si la respuesta del servidor indica que la solicitud fue exitosa (status=true).
                    if (response.body().isStatus()) {
                        // Obtiene una lista de documentos de la respuesta.
                        ArrayList<Documentos> documentList = response.body().getDocumentos();

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
                public void onFailure(@NonNull Call<ResponseDocumentos> call, @NonNull Throwable t) {
                    // Registra un mensaje de error en el log.
                    Log.i("ERROR en ", "onFailure: " + t.toString());
                }
            });
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            imgvDocumento.setImageBitmap(Funciones.decodeFile(UrlDocumento));
            txtvInstruccion.setText(R.string.cp_otrafoto);

        }
    }



}