package com.abarrotescasavargas.operamovil.Main;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.abarrotescasavargas.operamovil.Main.BaseDatos.AdminSQLite;
import com.abarrotescasavargas.operamovil.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Registro2 extends AppCompatActivity implements View.OnClickListener {

    // region Declaración de Variables
    ResultSet ta, tc, th, tq;
    private static String imgfirma = "";

    Date date = new Date();
    private static String imgfirma2 = "";
    private String imgTran = "";
    private String imgFac = "";
    private String imgPlac = "";
    private String imgSello = "";
    private String imgFirmaTransportista = "";
    private String imgplaca = "";
    private String fotoTransporte = "";
    private String fotoFactura = "";
    private String aux = "";
    private String RFCPRO, FOLFIS, IMPORT = "";
    private String sfechaHora;
    private String subtring = "";
    private String nomSuc = "";
    private String idSuc = "";
    private String nombreClavePro = "";
    private String nombreNombre = "";
    private String NombreFoto = "";
    private String sqliteImgTrasnporte = "";
    private String imgStringMan1 = "";
    private String hentrada = "";
    private String urlPlaca = "";
    private String urlTransporte = "";
    private String rulFactura = "";
    private String urlSello = "";
    private String urlFirmatransportista = "";
    private int idBitacora = 0;
    private int countImg = 0;
    private int idDatosFac = 0;
    private int fotoPlaca = 0;
    private int fotoTrasnporteVali = 0;
    private int fotoFacturaVali = 0;
    private Button btnAcceso;
    private static ImageView firmaImagen;
    private static ImageView firmaImagenRecibido;
    // private TextView idSucNom;
    private File storageDir;
    private File FileTransporte;
    private File FilePlaca;
    private File FileFactura;
    private File FileSello;
    private File FileFirmaTransportista;
    private Uri imageUriRLR;
    private String rutaFinalRLR;
    /*
    Handler handler=new Handler();
    boolean isActivo=false;
    int i=0;
     */
    private SQLiteDatabase dbs;
    /*EditText fecha;
    EditText time_entrada;*/

    TextView fecha;
    TextView time_entrada;
    TextView numProveedor, proveedor;


    Date fecha_hoy = null;
    Button btncamara, btncamaraTransporte, btncamaraFactura, btnGuardar, btnPreGuardar, btncamaraSello;
    ImageView imagen, imgTransporte, imgFactura, fotoSello;
    EditText nom_trans, nom_recibio, num_factura, num_placa, idObservacionesRecivido;
    Button btnRegistro;
    Button btnFirma;
    public Bitmap imagenFirma;
    String estatus;
    String traeNombrePro = "";
    String traerfecha = "";
    String traerHoraEntre = "";
    String traerclavePro = "";
    String traernumFactura = "";
    String traerplacas = "";
    String traernomTransportista = "";
    String traernomRecibido = "";
    String traerobservaciones = "";
    Button btnFirma_recibido;
    String traersqlRFCPRO;

    {
        traersqlRFCPRO = "";
    }

    String traersqlFOLFIS = "";
    String traersqlIMPORT = "";

    ProgressDialog progressDialog;

    // endregion

   /* Handler obHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Enviar();

            AdminSQLite admin = new AdminSQLite(getApplicationContext(), "prueba", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();

            bd.delete("prueba", "proveedor ='" + traeNombrePro + "' ", null);

            Toast.makeText(getApplicationContext(), "ENVIADO CON EXITO", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), listaBitacora.class);
            startActivity(i);
        }
    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);
        storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        dbs = openOrCreateDatabase("Servidor.db", MODE_PRIVATE, null);
        //final Servidor Srv = new Servidor(getApplicationContext());
        //final ServidorIMG Srv_IMG = new ServidorIMG(getApplicationContext(), true);
        setup();
        events();
    }


    private void setup() {

        btnFirma_recibido = (Button) findViewById(R.id.firmaRecibo);
        btncamaraTransporte = findViewById(R.id.btncamaraTransporte);
        btnRegistro = findViewById(R.id.btnRegistro);
        num_factura = findViewById(R.id.numero_factura);
        num_placa = findViewById(R.id.numero_placa);
        proveedor = findViewById(R.id.nom_provedor);
        numProveedor = findViewById(R.id.numero_provedor);
        nom_trans = findViewById(R.id.nom_trans);
        nom_recibio = findViewById(R.id.nom_recibio);
        fecha = findViewById(R.id.txtFecha);
        time_entrada = findViewById(R.id.txtHora);
        idObservacionesRecivido = findViewById(R.id.idObservacionesRecivido);
        btncamara = findViewById(R.id.btncamara);
        btncamara.setOnClickListener(this);
        btncamaraFactura = findViewById(R.id.btncamaraFactura);
        btncamaraFactura.setOnClickListener(this);
        btncamaraSello = findViewById(R.id.btncamaraSello);
        btncamaraSello.setOnClickListener(this);
        btnGuardar = findViewById(R.id.btnGuardar);
        imagen = findViewById(R.id.placa);
        imgTransporte = findViewById(R.id.fotoTransporte);
        imgFactura = findViewById(R.id.fotoFactura);
        fotoSello = findViewById(R.id.fotoSello);
        firmaImagen = findViewById(R.id.imagen_firma);
        firmaImagenRecibido = findViewById(R.id.imageFirmaRecibido);
        // idSucNom = findViewById(R.id.idSucNom);
        btnPreGuardar = findViewById(R.id.btnPreGuardar);
        Button btnFirma = (Button) findViewById(R.id.btn_firma_transportista);


        /*Primero asignar el tipo de elemento visual, despues asignar valores*/


        countImg = 0;

        traeNombrePro = getIntent().getStringExtra("nombrePro");


        traerfecha = getIntent().getStringExtra("fecha");
        traerHoraEntre = getIntent().getStringExtra("HoraEntre");
        traerclavePro = getIntent().getStringExtra("clavePro");
        traernumFactura = getIntent().getStringExtra("numFactura");
        traerplacas = getIntent().getStringExtra("placas");
        traernomTransportista = getIntent().getStringExtra("nomTransportista");
        traernomRecibido = getIntent().getStringExtra("nomRecibido");
        traerobservaciones = getIntent().getStringExtra("observaciones");
        traersqlRFCPRO = getIntent().getStringExtra("sqlRFCPRO");
        traersqlFOLFIS = getIntent().getStringExtra("sqlFOLFIS");
        traersqlIMPORT = getIntent().getStringExtra("sqlIMPORT");
        estatus = getIntent().getStringExtra("estatus");


        numProveedor.setText(getIntent().getStringExtra("clavePro")==null? "":getIntent().getStringExtra("clavePro") );
        proveedor.setText(getIntent().getStringExtra("nombrePro")==null?"":getIntent().getStringExtra("nombrePro") );
        num_factura.setText(getIntent().getStringExtra("numFactura")==null?"": getIntent().getStringExtra("numFactura"));
        num_placa.setText(getIntent().getStringExtra("placas")==null? "":getIntent().getStringExtra("placas") );
        nom_trans.setText(getIntent().getStringExtra("nomTransportista")==null? "":getIntent().getStringExtra("nomTransportista") );
        nom_recibio.setText(getIntent().getStringExtra("nomRecibido")==null? "":getIntent().getStringExtra("nomRecibido") );
        idObservacionesRecivido.setText(getIntent().getStringExtra("observaciones")==null? "": getIntent().getStringExtra("observaciones"));


        if (traerfecha == null) {
            //if (getIntent().getStringExtra("fecha") == null) {
            final SimpleDateFormat fechacompleta = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            sfechaHora = fechacompleta.format(date);
            fecha.setText(sfechaHora);
            fecha.setEnabled(Boolean.parseBoolean(sfechaHora));
            SimpleDateFormat hora = new SimpleDateFormat("h:mm a");
            hentrada = hora.format(date);
            time_entrada.setText(hentrada);
            time_entrada.setEnabled(Boolean.parseBoolean(hentrada));
        } else {
            fecha.setText(traerfecha);
            time_entrada.setText(traerHoraEntre);
            sfechaHora = traerfecha;
            fecha.setEnabled(Boolean.parseBoolean(sfechaHora));
            time_entrada.setEnabled(Boolean.parseBoolean(hentrada));
        }





    }

    private void events() {


         /* tc = Srv.tabla(" select nombre,id_sucursal from sucursal where id_sucursal=(select top(1) sucursal from anaquel) ", true);
        try {
            nomSuc = tc.getString(1);
            idSuc = tc.getString(2);
        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }
       idSucNom.setText(nomSuc);*/

        btnRegistro.setOnClickListener(v -> {
            EscanearCodigo();
        });

        /*btncamaraTransporte.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri imagePath = createImage();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
            imgTransportResultLauncher.launch(takePictureIntent);
        });
*/
        btnFirma.setOnClickListener(view -> {
            Intent intent = new Intent(Registro2.this, Segundo.class);
            startActivity(intent);
        });

        btnFirma_recibido.setOnClickListener(view -> {
            Intent intent = new Intent(Registro2.this, Tercero.class);
            startActivity(intent);
        });

        btnGuardar.setOnClickListener(v -> {
            EnviaFoto();

            /*if (fecha.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA CAMPO FECHA", Toast.LENGTH_LONG).show();

            } else if (time_entrada.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA CAMPO HORA ENTRADA", Toast.LENGTH_LONG).show();

            } else if (proveedor.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA CAMPO PROVEEDOR", Toast.LENGTH_LONG).show();

            } else if (numProveedor.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA NUMERO PROVEEDOR", Toast.LENGTH_LONG).show();

            } else if (nom_trans.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA CAMPO TRANSPORTISTA", Toast.LENGTH_LONG).show();

            } else if (num_placa.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA CAMPO NUMERO PLACA", Toast.LENGTH_LONG).show();

            } else if (num_factura.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA CAMPO NUMERO FACTURA", Toast.LENGTH_LONG).show();

            }*/


            /*else if (fotoTrasnporte.equals("")){
                Toast.makeText(getApplicationContext(), "LLENA IMAGEN TRANSPORTE", Toast.LENGTH_LONG).show();

            }else if (imgplaca.equals("")){
                Toast.makeText(getApplicationContext(), "LLENA IMAGEN PLACA", Toast.LENGTH_LONG).show();

            } else if (fotoFactura.equals("")){
                Toast.makeText(getApplicationContext(), "LLENA IMAGEN FACTURA", Toast.LENGTH_LONG).show();
             */


            /*else if (imgfirma.equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA FIRMA_TRANSPORTISTA", Toast.LENGTH_LONG).show();

            } else if (imgfirma2.equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA CAMPO FIRMA", Toast.LENGTH_LONG).show();

            } else if (nom_recibio.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA CAMPO RECIBIRDO", Toast.LENGTH_LONG).show();

            } else if (idObservacionesRecivido.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "LLENA CAMPO OBSERVACIONES", Toast.LENGTH_LONG).show();

            } else {
                Runnable objRunnable = new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1000);

                        } catch (Exception ex) {
                            ex.printStackTrace();

                        }
                        obHandler.sendEmptyMessage(0);

                    }
                };

                Thread objBgThread = new Thread(objRunnable);
                objBgThread.start();
                btnGuardar.setEnabled(false);
                progressDialog = new ProgressDialog(Registro2.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
            }*/
        });
 /* btncamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fotoPlaca=1;
                abrirCamara(fotoPlaca);

            }
        });

        */

       /* btncamaraTrasnporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fotoTrasnporteVali=2;
                abrirCamarafotoTrasnporte(fotoTrasnporteVali);

            }
        });
        */

       /* btncamaraFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fotoFacturaVali=3;
                abrirCamarafotoFactura(fotoFacturaVali);

            }
        });
        */

        btnPreGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();
            }
        });

    }


    public static Bitmap decodeFile(String path) {

        try {
            int m_inSampleSize = 0;
            int m_compress = 100;

            BitmapFactory.Options bounds = new BitmapFactory.Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bounds);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            final int REQUIRED_SIZE = 1000;
            int scale = 1;
            while (opts.outWidth / scale / 2 >= REQUIRED_SIZE && opts.outHeight / scale / 2 >= REQUIRED_SIZE) {
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


    private void takePicture(String foto) {

        Uri photo = null;
        String uri = "com.example.colaboradores.fileprovider";

        NombreFoto = foto;
        File FileFoto1 = null;
        try {
            FileFoto1 = createFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imgStringMan1 = FileFoto1.getAbsolutePath();
        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileFoto1);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);

    }

    private File createFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + countImg;
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private Uri createImage() {
        Uri uri = null;
        ContentResolver resolver = getContentResolver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        String imgName = String.valueOf(System.currentTimeMillis());

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imgName + ".jpg");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "My Images/");
        Uri finalUri = resolver.insert(uri, contentValues);

        rutaFinalRLR = uri + "/Pictures/My Images/" + imgName + ".jpg";
        imageUriRLR = finalUri;
        return finalUri;
    }


    private void EnviaFoto() {


        FTPClient firmaTransportista = new FTPClient();
        FileInputStream fisfirmaTrans = null;
        try {

            firmaTransportista.connect("ftp.abarrotescasavargas.mx", 21);
            firmaTransportista.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
            firmaTransportista.setFileType(FTP.BINARY_FILE_TYPE);
            firmaTransportista.enterLocalPassiveMode();
            firmaTransportista.sendCommand("OPTS UTF8 ON");
            File file;
            file = new File(rutaFinalRLR);


            fisfirmaTrans = new FileInputStream(rutaFinalRLR);

            firmaTransportista.storeFile("/a/m.txt", fisfirmaTrans);
            urlFirmatransportista = "/" + aux + "/revision/" + idBitacora + "firt.jpg";
            firmaTransportista.storeFile(urlFirmatransportista, fisfirmaTrans);
            fisfirmaTrans.close();
            firmaTransportista.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveImage(Bitmap bitmap) {
        OutputStream fos;
        Uri uri = null;
        try {
            ContentResolver resolver = getContentResolver();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            } else {
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            }
            String imgName = String.valueOf(System.currentTimeMillis());
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imgName + ".jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "My Images/");
            Uri finalUri = resolver.insert(uri, contentValues);
            fos = resolver.openOutputStream(Objects.requireNonNull(finalUri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Objects.requireNonNull(fos);
        } catch (Exception e) {
            Log.d("error foto", "saveImage: " + e.toString());
        }
    }

    private final ActivityResultLauncher<Intent> imgTransportResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
               /*if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    saveImage(bitmap); // podria funcionar
                    imgTransporte.setImageBitmap(bitmap);*/


                    //saveBitmapToFile(new File(String.valueOf(imageUriRLR)));

                    imgTransporte.setImageURI(imageUriRLR); // Metodo funcionando , pero no debe de ir la ruta ahí



                    /*Uri uri = null;
                    ContentResolver resolver = getContentResolver();
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
                        uri= MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                    }
                    else{
                        uri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    }
                    String imgName= String.valueOf(System.currentTimeMillis());
                    ContentValues contentValues= new ContentValues();
                    contentValues.put(MediaStore.Images.Media.DISPLAY_NAME,imgName+"comprimida.jpg" );
                    contentValues.put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/"+"My Images/" );
                    contentValues.put((MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), selectedBitmap, "imgTransporte", null)));
                    Uri finalUri= resolver.insert(uri, contentValues);*/





                    /*Bitmap finalBit = BitmapFactory.decodeFile(imgStringMan1);
                    imgTransporte.setImageBitmap(finalBit);*/




                    /* Imagen normal*/
                   /* ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "imgTransporte", null);
                    Uri uri = Uri.parse(path);
                    imgTransporte.setImageURI(uri);*/


                } else if (result.getData() == null) {
                    Toast.makeText(getApplicationContext(), "Se cancelo la captura", Toast.LENGTH_SHORT).show();
                }
            }
    );


    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Toast.makeText(getApplicationContext(), "NO RECONOCE QR", Toast.LENGTH_SHORT).show();
            //new Handler().postDelayed(() -> binding.pgrBar.setVisibility(View.GONE), 1000);
        } else {

            //Toast.makeText(this, "lo logra!!", Toast.LENGTH_SHORT).show();

            try {

                //super.onActivityResult(requestCode, resultCode, data);
                proveedor = findViewById(R.id.nom_provedor);
                numProveedor = findViewById(R.id.numero_provedor);
                Servidor Srv = new Servidor(getApplicationContext());

                if (result.getContents().contains("verificacfdi.facturaelectronica.sat.gob.mx") || result.getContents().contains("?re") || result.getContents().contains("&id") || result.getContents().contains("&re") || result.getContents().contains("&tt")) {
                    Uri url = Uri.parse(result.getContents().toString());
                    String proveedore = url.getQueryParameter("re");
                    String rfcpro = url.getQueryParameter("tt");
                    String foliofiscal = url.getQueryParameter("id");
                    FOLFIS = foliofiscal;
                    IMPORT = rfcpro;
                    RFCPRO = proveedore;

                    th = Srv.tabla(" select id_datos_facturacion from datos_facturacion where rfc= '" + RFCPRO + "' ", true);

                    if (!th.wasNull()) {
                        idDatosFac = th.getInt(1);
                    } else {
                        Toast.makeText(getApplicationContext(), "No hay datos del proveedor: ", Toast.LENGTH_SHORT).show();
                    }

                    tq = Srv.tabla(" select clave,nombre from proveedor where id_datos_facturacion = " + idDatosFac + "  ", true);
                    if (!tq.wasNull()) {
                        nombreClavePro = tq.getString(1);
                        nombreNombre = tq.getString(2);
                        numProveedor.setText(nombreClavePro);
                        proveedor.setText(nombreNombre);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sin Razón Social ", Toast.LENGTH_SHORT).show();
                    }

                        /*
                        String clav = proveedore.substring(0, 4);
                        Proovedor prov = new Proovedor();
                        List<String[]> listaPro = prov.getarray();
                        String clave = "";
                        String Nombre = "";
                        String soloClave="";
                        for (int x = 0; x < listaPro.size(); x++) {
                            if (listaPro.get(x)[0].contains(clav)) {
                                clave = listaPro.get(x)[0];
                                Nombre = listaPro.get(x)[1];
                            }
                        }
                        subtring = clave.substring(5,11);
                         */
                } else {
                    Log.e("Error:", "Otro error");
                }
            } catch (Exception e) {
                Log.e("Error:", e.getMessage());
            }


        }
    });

    private void EscanearCodigo() {
        //binding.pgrBar.setVisibility(View.VISIBLE);
        //new Handler().postDelayed(() -> binding.pgrBar.setVisibility(View.GONE), 6000);
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
        options.setPrompt("Escanear Código QR");
        options.setCameraId(0);
        options.setOrientationLocked(false);
        options.setBeepEnabled(true);
        options.setCaptureActivity(CaptureActivityPortrait.class);
        options.setBarcodeImageEnabled(false);
        barcodeLauncher.launch(options);
    }


    public void lista() {

        if (proveedor.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "LLENA CAMPO PROVEEDOR", Toast.LENGTH_LONG).show();
        } else {
            AdminSQLite admin = new AdminSQLite(getApplicationContext(), "prueba", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("proveedor", proveedor.getText().toString());
            registro.put("fecha", fecha.getText().toString());
            registro.put("HoraEntre", time_entrada.getText().toString());
            registro.put("clavePro", numProveedor.getText().toString());
            registro.put("numFactura", num_factura.getText().toString());
            registro.put("placas", num_placa.getText().toString());
            registro.put("nomTransportista", nom_trans.getText().toString());
            registro.put("nomRecibido", nom_recibio.getText().toString());
            registro.put("observaciones", idObservacionesRecivido.getText().toString());
            registro.put("sqlRFCPRO", RFCPRO.toString());
            registro.put("sqlFOLFIS", FOLFIS.replace("-", ""));
            registro.put("sqlIMPORT", IMPORT.toString());
            db.insert("prueba", null, registro);
            db.close();
            Intent intent = new Intent(this, listaBitacora.class);
            startActivity(intent);
            overridePendingTransition(R.transition.in_left, R.transition.out_left);
        }
    }

    private void Enviar() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String currentDateandTime = simpleDateFormat.format(new Date());

        Servidor Srv = new Servidor(getApplicationContext());
        ServidorIMG Srv_IMG = new ServidorIMG(getApplicationContext(), true);
        ResultSet tb;
        String Id = "";
        int restl = 0;
        int restl1 = 0;
        int restl2 = 0;


        restl1 = Srv.ejecuta("INSERT INTO bitacora_proveedor (Fecha_entrada,Fecha_Guardado,Nombre_porveedor,Clave_proveedor,Nom_transportista,Firma_transportista,Nom_recibido,Firma_recibido,Placa,Numero_Factura,Numero_Placa,Observaciones)" +
                " VALUES ('" + sfechaHora + "','" + currentDateandTime + "','" + proveedor.getText().toString() + "','" + numProveedor.getText().toString() + "','" + nom_trans.getText().toString() + "','','" + nom_recibio.getText().toString() + "','','','" + num_factura.getText().toString() + "','" + num_placa.getText().toString() + "','" + idObservacionesRecivido.getText().toString() + "')");

        if (RFCPRO == null) {
            restl = Srv.ejecuta("INSERT INTO REGFACTCOMPRAS (RF_FECREG,RF_RFCPRO,RF_FOLFIS,RF_IMPORT,RF_Registrada)" +
                    " VALUES ('" + currentDateandTime + "','" + traersqlRFCPRO.toString() + "','" + traersqlFOLFIS.replace("-", "") + "','" + traersqlIMPORT.toString() + "', 0)");

        } else {
            restl = Srv.ejecuta("INSERT INTO REGFACTCOMPRAS (RF_FECREG,RF_RFCPRO,RF_FOLFIS,RF_IMPORT,RF_Registrada)" +
                    " VALUES ('" + currentDateandTime + "','" + RFCPRO.toString() + "','" + FOLFIS.replace("-", "") + "','" + IMPORT.toString() + "', 0)");

        }
        ta = Srv.tabla(" SELECT ISNULL(MAX(id), 0)+1 FROM bitacora_proveedor_url ", true);

        try {
            idBitacora = ta.getInt(1);

        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }


        String Cadena1 = "";
        ArrayList<File> lst = new ArrayList();

        try {
            if (FilePlaca != null) {
                Cadena1 += " Img_Placa = ? ";
                lst.add(FilePlaca);
            }
            if (FileTransporte != null) {
                Cadena1 += ", Img_Trasnporte = ? ";
                lst.add(FileTransporte);
            }
            if (FileFactura != null) {
                Cadena1 += ", Img_Factura = ? ";
                lst.add(FileFactura);
            }
            if (FileSello != null) {
                Cadena1 += ", Img_Sello = ? ";
                lst.add(FileSello);
            }
            aux = Integer.parseInt(idSuc) < 10 ? "0" + idSuc : idSuc;
            ///////////////////////////////// enviar foto firmatransportista ////////////////////////
            FTPClient firmaTransportista = new FTPClient();
            FileInputStream fisfirmaTrans = null;
            try {

                firmaTransportista.connect("ftp.abarrotescasavargas.mx", 21);
                firmaTransportista.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                firmaTransportista.setFileType(FTP.BINARY_FILE_TYPE);
                firmaTransportista.enterLocalPassiveMode();
                firmaTransportista.sendCommand("OPTS UTF8 ON");
                String filenamefirmatra = imgFirmaTransportista;
                fisfirmaTrans = new FileInputStream(filenamefirmatra);
                firmaTransportista.storeFile("/a/m.txt", fisfirmaTrans);

                urlFirmatransportista = "/" + aux + "/revision/" + idBitacora + "firt.jpg";

                firmaTransportista.storeFile(urlFirmatransportista, fisfirmaTrans);
                fisfirmaTrans.close();

                firmaTransportista.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }


            ///////////////////////////////// enviar foto transporte ////////////////////////
            FTPClient transporte = new FTPClient();
            FileInputStream fistransporte = null;
            try {

                transporte.connect("ftp.abarrotescasavargas.mx", 21);
                transporte.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                transporte.setFileType(FTP.BINARY_FILE_TYPE);
                transporte.enterLocalPassiveMode();
                transporte.sendCommand("OPTS UTF8 ON");
                String filenametrans = imgTran;
                fistransporte = new FileInputStream(filenametrans);
                transporte.storeFile("/a/m.txt", fistransporte);

                urlTransporte = "/" + aux + "/revision/" + idBitacora + "imgtrans.jpg";

                transporte.storeFile(urlTransporte, fistransporte);
                fistransporte.close();

                transporte.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ///////////////////////////////// enviar foto placa ////////////////////////
            //'ftp://208.109.49.246/01/revision/1-01-imgpla.jpg', (imagen placa)
            FTPClient placas = new FTPClient();
            FileInputStream fisplacas = null;
            try {

                placas.connect("ftp.abarrotescasavargas.mx", 21);
                placas.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                placas.setFileType(FTP.BINARY_FILE_TYPE);
                placas.enterLocalPassiveMode();
                placas.sendCommand("OPTS UTF8 ON");

                String filenamepla = imgPlac;
                fisplacas = new FileInputStream(filenamepla);
                placas.storeFile("/a/m.txt", fisplacas);

                urlPlaca = "/" + aux + "/revision/" + idBitacora + "imgpla.jpg";

                placas.storeFile(urlPlaca, fisplacas);
                fisplacas.close();

                placas.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }


            ///////////////////////////////// enviar foto factura ////////////////////////
            FTPClient facturas = new FTPClient();
            FileInputStream fisFacturas = null;
            try {

                facturas.connect("ftp.abarrotescasavargas.mx", 21);
                facturas.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                facturas.setFileType(FTP.BINARY_FILE_TYPE);
                facturas.enterLocalPassiveMode();
                facturas.sendCommand("OPTS UTF8 ON");
                String filenamefac = imgFac;
                fisFacturas = new FileInputStream(filenamefac);
                facturas.storeFile("/a/m.txt", fisFacturas);

                rulFactura = "/" + aux + "/revision/" + idBitacora + "imgfact.jpg";

                facturas.storeFile(rulFactura, fisFacturas);
                fisFacturas.close();

                facturas.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ///////////////////////////////// enviar foto sello ////////////////////////
            FTPClient sellos = new FTPClient();
            FileInputStream fisSello = null;
            try {

                sellos.connect("ftp.abarrotescasavargas.mx", 21);
                sellos.login("sucursalesftp@abarrotescasavargas.mx", "Fp@ubSE3~^{P");
                sellos.setFileType(FTP.BINARY_FILE_TYPE);
                sellos.enterLocalPassiveMode();
                sellos.sendCommand("OPTS UTF8 ON");
                String filenameSello = imgSello;
                fisSello = new FileInputStream(filenameSello);
                sellos.storeFile("/a/m.txt", fisSello);

                urlSello = "/" + aux + "/revision/" + idBitacora + "imgsello.jpg";

                sellos.storeFile(urlSello, fisSello);
                fisSello.close();

                sellos.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }


            restl2 = Srv.ejecuta("INSERT INTO bitacora_proveedor_url (Fecha_entrada,Fecha_guardado,Nombre_proveedor,Clave_proveedor,Nom_transportista,Nom_recibe,Numero_factura,Numero_placa,Observaciones,url_firm_transportista,url_firm_recibe,url_img_placa,url_img_transporte,url_img_factura,url_img_sello)" +
                    " VALUES ('" + sfechaHora + "','" + currentDateandTime + "','" + proveedor.getText().toString() + "','" + numProveedor.getText().toString() + "',rtrim(upper('" + nom_trans.getText().toString() + "')), rtrim(upper('" + nom_recibio.getText().toString() + "')),'" + num_factura.getText().toString() + "','" + num_placa.getText().toString() + "','" + idObservacionesRecivido.getText().toString() + "','" + imgfirma + "','" + imgfirma2 + " ', '" + urlPlaca + "','" + urlTransporte + "','" + rulFactura + "','" + urlSello + "' ) ");


            RequestQueue requestQueue = Volley.newRequestQueue(Registro2.this);


            // String url="https://abarrotescasavargas.mx/api/mobile/rh/bitacora_proveedor.php";
            String url = "https://abarrotescasavargas.mx/api/mobile/finanzas/bitacora_proveedor.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //Toast.makeText(getApplicationContext(),"funciona "+response, Toast.LENGTH_SHORT).show();

                    Gson gson = new Gson();
                    Respuesta post = gson.fromJson(response, Respuesta.class);

                    if (post.getStatus().equals(true)) {

                        Toast.makeText(getApplicationContext(), "FUNCIONA ", Toast.LENGTH_SHORT).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "no funciona " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @NonNull
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("opcion", "1");
                    params.put("cve_sucursal", idSuc);
                    params.put("cve_proveedor", numProveedor.getText().toString());
                    params.put("nombre_proveedor", proveedor.getText().toString());
                    params.put("firma_transportista", imgfirma);
                    params.put("nombre_transportista", nom_trans.getText().toString());
                    params.put("nombre_recibe", nom_recibio.getText().toString());
                    params.put("firma_recibe", imgfirma2);
                    params.put("numero_factura", num_factura.getText().toString());
                    params.put("numero_placa", num_placa.getText().toString());
                    params.put("observaciones", idObservacionesRecivido.getText().toString());
                    params.put("url_img_placa", urlPlaca);
                    params.put("url_img_transporte", urlTransporte);
                    params.put("url_img_factura", rulFactura);
                    params.put("url_img_sello", urlSello);

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




            /*restl2= Srv_IMG.ejecuta("INSERT INTO EVIDENCIA_BITACORA_PROVEEDOR (id_Bitacora,Img_Firma_Transportista,Img_Firma_Recibido)" +
                    " VALUES ( " + idBitacora + ",'" + imgfirma + "','" + imgfirma2 + " ') ");

            PreparedStatement psql = Srv_IMG.PSte("update EVIDENCIA_BITACORA_PROVEEDOR set "+ Cadena1 +" where id_Bitacora = " + idBitacora + " ");

            //PreparedStatement psql = Srv_IMG.PSte("update EVIDENCIA_BITACORA_PROVEEDOR set id_Bitacora =" + idBitacora + ", Img_Firma_Transportista ='"+ imgfirma +"', Img_Firma_Recibido ='"+ imgfirma2 +"', " + Cadena + " ");

            for (Integer i=0; i<lst.size(); i++)
            {
                psql.setBinaryStream(i+1, new FileInputStream(lst.get(i)), (int) lst.get(i).length());
            }
            psql.executeUpdate();

             */

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }

        // restl1= Srv_IMG.ejecuta("INSERT INTO EVIDENCIA_bitacora_proveedor (id_Bitacora,Img_Firma_Transportista,Img_Firma_Recibido,Img_Placa,Img_Trasnporte,Img_Factura)" +
        //" VALUES ("+idBitacora+",'"+ imgfirma +"','"+ imgfirma2 +"','"+ imgplaca +"','"+ fotoTrasnporte +"','"+ fotoFactura +"' )");
    }


    public class Respuesta {
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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btncamaraTransporte:
                tomafoto("fotoTransporte");
                break;
            case R.id.btncamara:
                tomafoto("fotoPlaca");
                break;
            case R.id.btncamaraFactura:
                tomafoto("fotoFactura");
                break;
            case R.id.btncamaraSello:
                tomafoto("fotoSello");
                break;
        }
    }


    private View.OnClickListener mOnClic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnRegistro:
                    new IntentIntegrator(Registro2.this).initiateScan();
                    break;
            }
        }
    };


    private File createFilefirmas() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }


    private void tomafoto(String foto) {
        try {
            Uri photo = null;
            String uri = "com.casavargas.bitacora.fileprovider";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                NombreFoto = foto;
                countImg++;
                switch (foto) {
                    case "fotoTransporte":
                        FileTransporte = createFile();
                        imgTran = FileTransporte.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileTransporte);
                        imgTransporte.setImageBitmap(decodeFile(imgTran));
                        //imgTrasnporte.setImageBitmap(decodeFile(imgTran));
                        break;
                    case "fotoPlaca":
                        FilePlaca = createFile();
                        imgPlac = FilePlaca.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FilePlaca);
                        break;
                    case "fotoFactura":
                        FileFactura = createFile();
                        imgFac = FileFactura.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileFactura);
                        break;
                    case "fotoSello":
                        FileSello = createFile();
                        imgSello = FileSello.getAbsolutePath();
                        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileSello);
                        break;

                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);
                startActivityForResult(intent, 1);

            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "ERROR  " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    // @Override
   /*protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        proveedor = findViewById(R.id.nom_provedor);
        numProveedor = findViewById(R.id.numero_provedor);
        Servidor Srv = new Servidor(getApplicationContext());

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        switch (NombreFoto) {
            case "fotoTransporte":
                imgTrasnporte.setImageBitmap(decodeFile(imgTran));
                break;
            case "fotoPlaca":
                imagen.setImageBitmap(decodeFile(imgPlac));
                break;
            case "fotoFactura":
                imgFactura.setImageBitmap(decodeFile(imgFac));
                break;
            case "fotoSello":
                fotoSello.setImageBitmap(decodeFile(imgSello));
                break;
        }
    }*/

    public void archivarFirmas() throws IOException {

        Uri photo = null;
        String uri = "com.casavargas.bitacora.fileprovider";
        FileFirmaTransportista = createFilefirmas();
        imgFirmaTransportista = FileFirmaTransportista.getAbsolutePath();
        photo = FileProvider.getUriForFile(getApplicationContext(), uri, FileFirmaTransportista);

    }
    ///////////////////////// tomar fotos viejitas /////////////////////


   /* private void abrirCamara(int fotoPlaca) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    private void abrirCamarafotoTrasnporte(int fotoTrasnporteVali) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    private void abrirCamarafotoFactura(int fotoFacturaVali) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    */
    ///////////////////////// tomar fotos viejitas /////////////////////

    /*private View.OnClickListener mOnClic = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnRegistro:
                    new IntentIntegrator(Registro2.this).initiateScan();
                    break;
            }
        }
    };*/

    private void showTimeDialog(final EditText time_entrada) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                time_entrada.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(Registro2.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    private void showDateDialog(final EditText fecha) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                fecha.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        new DatePickerDialog(Registro2.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();
            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static void pintarFirma(Bitmap bitmap) throws IOException {
        firmaImagen.setImageBitmap(bitmap);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] mimagen = stream.toByteArray();
        imgfirma = Base64.encodeToString(mimagen, Base64.DEFAULT);

    }

    public static void pintarFirmaRecibido(Bitmap bitmap) {

        firmaImagenRecibido.setImageBitmap(bitmap);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] mimagen = stream.toByteArray();

        imgfirma2 = Base64.encodeToString(mimagen, Base64.DEFAULT);
    }
}
