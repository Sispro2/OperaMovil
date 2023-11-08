package com.abarrotescasavargas.operamovil.Main.Login;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.abarrotescasavargas.operamovil.Main.BaseDatos.BD_SQL;
import com.abarrotescasavargas.operamovil.Main.Entidades.Sucursales;
import com.abarrotescasavargas.operamovil.Main.FunGenerales.Funciones;
import com.abarrotescasavargas.operamovil.Main.FunGenerales.LocationHelper;
import com.abarrotescasavargas.operamovil.Main.FunGenerales.Variables;
import com.abarrotescasavargas.operamovil.Main.Interfaces.TareaMensajeInterface;
import com.abarrotescasavargas.operamovil.R;
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;
import com.abarrotescasavargas.operamovil.Main.activity_menu_2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class LoginActivity extends AppCompatActivity implements TareaMensajeInterface {
    private ImageView imagen;
    private Button btnAcceso;
    // private ProgressBar loadingProgressBar;
    private Handler handler = new Handler();
    private String CveSuc, NomSuc, Usuario, Nivel;
    ResultSet tb, tf, tz;
    private String Sucursal;
    private String IP = "";
    private String Base = "";
    private String PassW = "";
    private Spinner cmbSucursal;
    // private CheckBox chkCvo;
    private Context context;
    private Spinner txtUsuario;

    //notificaciones///
    int Permisos = 1;
    private String CHANNEL_ID = "canal";
    PendingIntent pendingIntent;
    //notificaciones///
    private String CountRezagados = "";
    private String CountVentas = "";
    private String CountNew = "";
    SucursalRepository sucursalRepository;
    ProgressDialog progressDialog;
    private EditText txtPassW;
    private ImageButton showHidePasswordButton;
    private boolean isPasswordVisible = false;
    private volatile boolean stopThread;
    private static final String TAG = "LoginActivity";
    private final Handler mainHandler = new Handler();
    private static final int PERMISSIONS_REQUEST = 123;
    TextView andrID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activaPermisos();
        }
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST);
        }
        //Test Estoy en rama JFCG
        //Test Estoy en rama JFCG
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();
        events();
        showHidePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePasswordVisibility();
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de ubicación concedido, ahora puedes obtener la ubicación.
                LocationHelper.getDeviceLocation(this, new LocationHelper.OnLocationResultListener() {
                    @Override
                    public void onLocationResult(double latitude, double longitude) {
                        // Imprimir la latitud y longitud en el LogCat
                        Log.v("LocationHelper----", "Latitud: " + latitude + ", Longitud: " + longitude);
                    }
                });
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

//

    private void setup(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.poco_a_poco);
        imagen = findViewById(R.id.imageView);
        imagen.setAnimation(animation);
        btnAcceso = findViewById(R.id.login);
        sucursalRepository = new SucursalRepository(getApplicationContext());
        txtUsuario = findViewById(R.id.customSpinner);
        txtPassW = findViewById(R.id.password);
        cmbSucursal = findViewById(R.id.cmbSucursal);
        context = getApplicationContext();
        cmbSucursal.setAdapter(Funciones.CargaSpinnerSucursales(getApplicationContext()));
        andrID= findViewById(R.id.androID);
        andrID.setText(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase());
        showHidePasswordButton = findViewById(R.id.toggleButtonShowPassword);

    }
    private  void events(){
        cmbSucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String aux = cmbSucursal.getItemAtPosition(pos).toString();
                String cveSucursal = aux.substring(0, aux.indexOf('-'));
                if (!aux.equals(getString(R.string.Sp_elija_sucursal))) {
                    guardaSucursal(cveSucursal);
                    Log.v(TAG,"Entre");
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.sincronizando_dialog);
                    stopThread = false;
                    loginRunnable proveedoresRunnable = new loginRunnable(cveSucursal);
                    new Thread(proveedoresRunnable).start();
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
                Log.v(TAG,"Entre onNothingSelected");
            }
        });
        txtUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = txtUsuario.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
    //Muestra/oculta contraseña
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Si la contraseña está visible, ocultarla
            txtPassW.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showHidePasswordButton.setImageResource(R.drawable.eye_password);
            showHidePasswordButton.setContentDescription("Mostrar contraseña");
        } else {
            // Si la contraseña está oculta, mostrarla
            txtPassW.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showHidePasswordButton.setImageResource(R.drawable.eye_password_closed);
            showHidePasswordButton.setContentDescription("Ocultar contraseña");
        }
        txtPassW.setSelection(txtPassW.getText().length());
        isPasswordVisible = !isPasswordVisible;
    }

    private  void guardaSucursal(String cveSucursal){
        sucursalRepository = new SucursalRepository(context);
        Sucursales sucursal = sucursalRepository.GetDetalleSelectedSucursal(cveSucursal);
        sucursalRepository.SetSucursal(context, sucursal);
    }
    private void paraTodo(boolean bandera, List<String> data)
    {
        stopThread=true;
        if (!bandera)
        {
            progressDialog.cancel();
            Toast.makeText(LoginActivity.this,
                    context.getResources().getString(R.string.msj_servidor_local_no_responde, sucursalRepository.GetDetalleSucursal().getKS_IP()),
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            Spinner spinner = findViewById(R.id.customSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(LoginActivity.this, R.layout.cmb_item, data);
            spinner.setAdapter(adapter);
            progressDialog.cancel();
        }
    }
    class ValidaCredencialesRunnable implements Runnable {
        private final String usuario;
        private final String pasword;

        public ValidaCredencialesRunnable( String usuario, String pasword) {
            this.usuario = usuario;
            this.pasword = pasword;
        }

        @Override
        public void run() {
            boolean bandera = validaCredenciales(usuario, pasword);
            mainHandler.post(() -> {
                stopValidaCredenciales(bandera);
            });
        }
        private boolean validaCredenciales(String usuario, String pasword) {
            ValidaCredencialesRepository vcr = new ValidaCredencialesRepository(context);
            return vcr.GetExisteUsuarioPasswordSql(usuario, pasword);
        }
    }

    private void stopValidaCredenciales(boolean bandera)
    {
        stopThread=true;
        progressDialog.cancel();

        if (!bandera)
        {
            Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show();
            String usuario = txtUsuario.getSelectedItem().toString();

            Intent intent = new Intent(getApplicationContext(), activity_menu_2.class);
            intent.putExtra("USUARIO_LOG", usuario);

            startActivity(intent);
            overridePendingTransition(R.transition.in_left, R.transition.out_left);
            finish();
        }

    }
    class loginRunnable implements Runnable {
        String cveSucursal;
        loginRunnable(String cveSucursal) {
            this.cveSucursal = cveSucursal;
        }
        @Override
        public void run() {
            boolean conexion=Funciones.ValidaConexionServidorLocalSinToast(context);
            List<String> data;
            if (conexion && !stopThread )
            {
                //consulta
                data=consultaDB();
                mainHandler.post(() -> {
                    // ejecutar el metodo
                    paraTodo(true, data);
                });
            }
            else if (!conexion && !stopThread )
            {
                data = null;
                mainHandler.post(() -> {
                    // ejecutar el metodo
                    paraTodo(false, null);
                });

            }

        }
    }

    public List<String> consultaDB() {
        String query = "SELECT clave \n" +
                "FROM [dbo].[usuario] \n" +
                "WHERE id_perfil IN (1, 2, 3, 4, 5, 6, 7, 11,14) \n" +
                "  AND activo = 1\n" +
                "  AND clave NOT LIKE 'TFRCORP%' \n" +
                "  AND clave NOT LIKE 'CONT%' \n" +
                "  AND clave NOT LIKE 'VPICHARDO%' \n" +
                "ORDER BY \n" +
                "  CASE \n" +
                "    WHEN clave = 'MS01' THEN 0 \n" +
                "    ELSE 1 \n" +
                "  END,\n" +
                "  clave;\n";
        ResultSet resultSet = BD_SQL.tabla(query, !Objects.equals(Variables.Ambiente, "DESARROLLO"), context);

        List<String> nombres = new ArrayList<>();
        nombres.add(context.getResources().getString(R.string.prompt_email));

        try {
            while (resultSet.next()) {
                String nombre = resultSet.getString("clave");
                nombres.add(nombre);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombres;
    }


    public void ValidaInicio(View view) {

        cerrarTeclado();
        if (txtUsuario.getSelectedItem().toString().equals("") || txtPassW.getText().toString().equals("")) {
            Toast.makeText(this, R.string.lg_mensaje, Toast.LENGTH_SHORT).show();
        } else {
            if (Funciones.ValidaConexionServidorLocal(getApplicationContext())) {
                ValidaCredencialesRunnable validaRunnable =
                        new ValidaCredencialesRunnable(txtUsuario.getSelectedItem().toString(), txtPassW.getText().toString());
                new Thread(validaRunnable).start();
            }
        }
    }


    public void activaPermisos() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setTitle("need permisos")
                    .setMessage("Activa los permisos de camara")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.CAMERA}, Permisos))
                    .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Permisos);
        }


    }


    private boolean Valida(String User, String PassW) {
        Usuario = "SISTEMAS";
        Nivel = "0";
        return true;
    }
    private  void cerrarTeclado(){
        View view = this.getCurrentFocus();
        if(view!= null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void toggleProgressBar(boolean status) {
        if (status) {
            View overlay = findViewById(R.id.bloqueo);
            overlay.setVisibility(View.VISIBLE);
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.show();
            progressDialog.setContentView(R.layout.validando_dialog);
        } else {
            View overlay = findViewById(R.id.bloqueo);
            overlay.setVisibility(View.GONE);
            progressDialog.cancel();
        }
    }


    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void realizaAccion(boolean status) {
    }

    @Override
    public void lanzarActividad(Class<?> tipoActividad) {
        Intent intent = new Intent(this, tipoActividad);
        startActivity(intent);
    }
}