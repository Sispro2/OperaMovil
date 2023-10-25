package com.abarrotescasavargas.operamovil.Main;



import static com.abarrotescasavargas.operamovil.Main.FunGenerales.Variables._DICCIONARIO_SUCURSAL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.abarrotescasavargas.operamovil.Main.FunGenerales.Funciones;
import com.abarrotescasavargas.operamovil.Main.FunGenerales.Preloaders.EnviandoDialog;
import com.abarrotescasavargas.operamovil.Main.FunGenerales.Variables;
import com.abarrotescasavargas.operamovil.Main.Modelos.RespuestaWS;
import com.abarrotescasavargas.operamovil.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistroBitacora extends AppCompatActivity {

    private final String uriFileProvider = "com.casavargas.bitacora.fileprovider";
    private ResultSet TD;
    private Button btnScaner, btnGuardar, btnFirmaTransportista, btnFirmaRecibe;
    private TextView txtHora, txtFecha, txtRazonSocial, txtNumeroProveedor;
    private ImageView ivTransporte,  ivPlaca, ivFactura, ivSello;

    private File FileTransporte, FilePlacas, FileSello, FileFactura;
    private String UrlTransporte = "", UrlPlaca = "", UrlSello = "", UrlFactura = "", NombreFoto = "", FOLFIS = "", IMPORT = "", RFCPRO = "";
    private static String sFirmaTransportista = "", sFirmaRecibe = "";
    private static ImageView ivFirmaTransportista,  ivFirmaRecibe;
    private EditText num_factura, num_placa, observaciones, nom_transportista, nom_recibe;
    private EnviandoDialog enviandoDialog;
    private int idBitacora = 0;
    Retrofit retrofit = new RetrofitClient().getRetrofit();
    RealizaPeticion realizaPeticion = retrofit.create(RealizaPeticion.class);

    public RegistroBitacora() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_bitacora);
        setup();
        events();
        enviandoDialog = new EnviandoDialog(this);
    }

    private void setup() {
        btnScaner = findViewById(R.id.btn_scanear_qr);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnFirmaTransportista = findViewById(R.id.btn_firma_transportista);
        btnFirmaRecibe = findViewById(R.id.btn_firma_recibe);
        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtNumeroProveedor = findViewById(R.id.txtNumeroProveedor);
        ivTransporte = findViewById(R.id.img_transporte);
        ivPlaca = findViewById(R.id.ivPlacas);
        ivSello = findViewById(R.id.img_sello);
        ivFactura = findViewById(R.id.img_factura);
        ivFirmaTransportista = findViewById(R.id.img_firma_transportista);
        ivFirmaRecibe = findViewById(R.id.img_firma_recibe);
        num_factura = findViewById(R.id.edit_numero_factura);
        num_placa = findViewById(R.id.edit_Placas);
        observaciones = findViewById(R.id.edit_observaciones);
        nom_transportista = findViewById(R.id.edit_nombre_transportista);
        nom_recibe = findViewById(R.id.edit_nombre_recibe);
    }


    private void events() {
        btnScaner.setOnClickListener(v -> {
            EscanearCodigo();
        });
        ivTransporte.setOnClickListener(v -> {
            TomaFoto("fotoTransporte");
        });
        ivPlaca.setOnClickListener(v -> {
            TomaFoto("fotoPlacas");
        });

        ivFactura.setOnClickListener(v -> {
            TomaFoto("fotoFactura");
        });
        ivSello.setOnClickListener(v -> {
            TomaFoto("fotoSello");
        });

        btnGuardar.setOnClickListener(v -> {
            Envia();
        });
        btnFirmaTransportista.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroBitacora.this, Segundo.class);
            startActivity(intent);
        });
        btnFirmaRecibe.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroBitacora.this, Tercero.class);
            startActivity(intent);
        });


    }

    private void EscanearCodigo() {
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

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Toast.makeText(getApplicationContext(), R.string.no_codigo_qr, Toast.LENGTH_SHORT).show();
        } else {
            try {
                int idDatosFac = 0;

               /* if (Funciones.ValidaConexionServidorLocal(_DICCIONARIO_SUCURSAL.get("ip")))
                {
                    Servidor Srv = new Servidor(getApplicationContext());
                    if (result.getContents().contains("verificacfdi.facturaelectronica.sat.gob.mx") || result.getContents().contains("?re") || result.getContents().contains("&id")
                            || result.getContents().contains("&re") || result.getContents().contains("&tt")) {
                        Uri url = Uri.parse(result.getContents().toString());
                        String proveedore = url.getQueryParameter("re");
                        String rfcpro = url.getQueryParameter("tt");
                        String foliofiscal = url.getQueryParameter("id");
                        FOLFIS = foliofiscal;
                        IMPORT = rfcpro;
                        RFCPRO = proveedore;
                        TD = Srv.tabla(" select id_datos_facturacion from datos_facturacion where rfc= '" + RFCPRO + "' ", true);
                        if (!TD.wasNull()) {
                            idDatosFac = TD.getInt(1);
                        } else {
                            Toast.makeText(getApplicationContext(), "Sin Datos del Proveedor", Toast.LENGTH_SHORT).show();
                        }
                        TD = Srv.tabla(" select clave,nombre from proveedor where id_datos_facturacion = " + idDatosFac + "  ", true);
                        if (!TD.wasNull()) {
                            txtNumeroProveedor.setText(TD.getString(1));
                            txtRazonSocial.setText(TD.getString(2));
                            // PonHora();
                        } else {
                            Toast.makeText(getApplicationContext(), "Sin Razón Social", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.no_leer_informacion, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "El Servidor no responde, verifique o acerquese el Wifi", Toast.LENGTH_SHORT).show();
                }*/
            } catch (Exception e) {
                Log.e("Error:", e.getMessage());
            }
        }
    });


    private void TomaFoto(String foto) {
        try {
            Uri uriPhoto = null;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                switch (foto) {
                    case "fotoTransporte":
                        NombreFoto = "fotoTransporte";
                        FileTransporte = createFile();
                        UrlTransporte = FileTransporte.getAbsolutePath();
                        uriPhoto = FileProvider.getUriForFile(getApplicationContext(), uriFileProvider, FileTransporte);
                        break;
                    case "fotoPlacas":
                        NombreFoto = "fotoPlaca";
                        FilePlacas = createFile();
                        UrlPlaca = FilePlacas.getAbsolutePath();
                        uriPhoto = FileProvider.getUriForFile(getApplicationContext(), uriFileProvider, FilePlacas);
                        break;
                    case "fotoFactura":
                        NombreFoto = "fotoFactura";
                        FileFactura = createFile();
                        UrlFactura = FileFactura.getAbsolutePath();
                        uriPhoto = FileProvider.getUriForFile(getApplicationContext(), uriFileProvider, FileFactura);
                        break;
                    case "fotoSello":
                        NombreFoto = "fotoSello";
                        FileSello = createFile();
                        UrlSello = FileSello.getAbsolutePath();
                        uriPhoto = FileProvider.getUriForFile(getApplicationContext(), uriFileProvider, FileSello);
                        break;
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
                startActivityForResult(intent, 10);
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "ERROR  " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            switch (NombreFoto) {
                case "fotoTransporte":
                    ivTransporte.setImageBitmap(decodeFile(UrlTransporte));
                    break;
                case "fotoPlaca":
                    ivPlaca.setImageBitmap(decodeFile(UrlPlaca));
                    break;
                case "fotoFactura":
                    ivFactura.setImageBitmap(decodeFile(UrlFactura));
                    break;
                case "fotoSello":
                    ivSello.setImageBitmap(decodeFile(UrlSello));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + NombreFoto);
                    // if(requestCode==10 && requestCode== Activity.RESULT_OK)
            }
        }
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

    private File createFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HHmm").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    public File saveBitmapToFile(File file) {
        try {


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
    //private MultipartBody.Part prepareFileTransporte(String partName) {
    private MultipartBody.Part prepareFileTransporte() {
        File file = new File(UrlTransporte);
        //File file = new File(partName);
        saveBitmapToFile(file);
        File file2 = new File(file.getAbsolutePath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        return MultipartBody.Part.createFormData("img_transporte", file.getName(), requestFile);
    }

    private MultipartBody.Part prepareFilePlaca() {
        File file = new File(UrlPlaca);
        saveBitmapToFile(file);
        File file2 = new File(file.getAbsolutePath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        return MultipartBody.Part.createFormData("img_placa", file.getName(), requestFile);
    }

    private MultipartBody.Part prepareFileSello() {
        File file = new File(UrlSello);
        saveBitmapToFile(file);
        File file2 = new File(file.getAbsolutePath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        return MultipartBody.Part.createFormData("img_sello", file.getName(), requestFile);
    }

    private MultipartBody.Part prepareFileFactura() {
        File file = new File(UrlFactura);
        saveBitmapToFile(file);
        File file2 = new File(file.getAbsolutePath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        return MultipartBody.Part.createFormData("img_factura", file.getName(), requestFile);
    }

    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MultipartBody.FORM, descriptionString);
    }

    private void Envia() {
        enviandoDialog.show();
        if(GuardaRecepcionLocal()){
            if (checkConnectionWifi()) {
                if (Funciones.ValidaConexionServidorWeb())
                    GuardaBD();
                else
                    Toast.makeText(this, "Sin Internet Hacia: " + Variables._IPGODADDY, Toast.LENGTH_SHORT).show();
            } else if (checkConnectionMobile()) {
                if (Funciones.ValidaConexionServidorWeb())
                    GuardaBD();
                else
                    Toast.makeText(this, "Sin Internet Hacia: " + Variables._IPGODADDY, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sin Conexiones Disponibles", Toast.LENGTH_SHORT).show();
            }
        }
        //enviandoDialog.dismiss();
    }

    private void GuardaBD() {
        Call<RespuestaWS> call = realizaPeticion.subeFoto(
                createPartFromString(num_factura.getText().toString()),
                createPartFromString(num_placa.getText().toString())
                , createPartFromString(_DICCIONARIO_SUCURSAL.get("cvesuc"))
                , createPartFromString(txtRazonSocial.getText().toString())
                , createPartFromString(String.valueOf(idBitacora))
                , createPartFromString(txtNumeroProveedor.getText().toString())
                , createPartFromString(observaciones.getText().toString())
                , prepareFileTransporte()
                , prepareFilePlaca()
                , prepareFileSello()
                , prepareFileFactura()
                , createPartFromString(nom_transportista.getText().toString())
                , createPartFromString(sFirmaTransportista)
                , createPartFromString(nom_recibe.getText().toString())
                , createPartFromString(sFirmaRecibe)
        );
        call.enqueue(new Callback<RespuestaWS>() {
            @Override
            public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {
                RespuestaWS respuesta = response.body();
                boolean status = respuesta.isStatus();
                //String header = respuesta.getHeader();
                String mensaje = respuesta.getMessage();
                if (status) {
                    enviandoDialog.dismiss();
                    Toast.makeText(RegistroBitacora.this, mensaje, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), menuAplicaciones.class);
                    startActivity(i);
                }
            }
            @Override
            public void onFailure(Call<RespuestaWS> call, Throwable t) {
                Toast.makeText(RegistroBitacora.this, "Intermitencia de Internet, Intente Nuevamente", Toast.LENGTH_SHORT).show();
                enviandoDialog.dismiss();
            }
        });
    }

    private boolean GuardaRecepcionLocal() {
        boolean bandera = false;
        Servidor Srv = new Servidor(getApplicationContext());
        ResultSet tb;
        /*if (Funciones.ValidaConexionServidorLocal(_DICCIONARIO_SUCURSAL.get("ip"))) {
            Srv.ejecuta("INSERT INTO bitacora_proveedor (Fecha_entrada,Fecha_Guardado,Nombre_porveedor,Clave_proveedor,Nom_transportista,Nom_recibido,Numero_Factura,Numero_Placa,Observaciones) " +
                    "values" +
                    " (GETDATE(), GETDATE(), UPPER('" + txtRazonSocial.getText().toString() + "'), UPPER('" + txtNumeroProveedor.getText().toString() + "'), UPPER('" + nom_transportista.getText().toString() + "'), UPPER('" + nom_recibe.getText().toString() + "')," +
                    "  UPPER('" + num_factura.getText().toString() + "'), UPPER('" + num_placa.getText().toString() + "'), UPPER('" + observaciones.getText().toString() + "')); ");
            Srv.ejecuta("INSERT INTO REGFACTCOMPRAS (RF_FECREG,RF_RFCPRO,RF_FOLFIS,RF_IMPORT,RF_Registrada) values(GETDATE(), '" + RFCPRO + "', '" + FOLFIS.replace("-", "") + "', '" + String.format("%02d",IMPORT) + " ', '0');");
            tb = Srv.tabla(" SELECT ISNULL(MAX(id), 0)+1 FROM bitacora_proveedor_url ", true);
            try {
                idBitacora = tb.getInt(1);
            } catch (SQLException | java.sql.SQLException exception) {
                exception.printStackTrace();
            }
            bandera = true;
        } else {

            Toast.makeText(getApplicationContext(), "El Servidor no responde, verifique o acerquese el Wifi", Toast.LENGTH_SHORT).show();
        }*/
        return bandera;
    }

    public boolean checkConnectionWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean WifiConectado = false;
        if (networkInfo != null)
            WifiConectado = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        return WifiConectado;
    }

    public boolean checkConnectionMobile() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean MobileConectado = false;
        if (networkInfo != null)
            MobileConectado = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        return MobileConectado;
    }

    public static void pintarFirma(Bitmap bitmap) throws IOException {
        ivFirmaTransportista.setImageBitmap(bitmap);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] mimagen = stream.toByteArray();
        sFirmaTransportista = Base64.encodeToString(mimagen, Base64.DEFAULT);
    }
    public static void pintarFirmaRecibido(Bitmap bitmap) { // tenia Static
        ivFirmaRecibe.setImageBitmap(bitmap);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] mimagen = stream.toByteArray();
        sFirmaRecibe = Base64.encodeToString(mimagen, Base64.DEFAULT);
    }


}