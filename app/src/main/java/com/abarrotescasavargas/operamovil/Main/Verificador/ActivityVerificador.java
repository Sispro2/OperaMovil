package com.abarrotescasavargas.operamovil.Main.Verificador;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abarrotescasavargas.operamovil.Main.BaseDatos.BD_SQL;
import com.abarrotescasavargas.operamovil.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityVerificador extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private ImageView camara;
    private List<String> dataValor;
    private ResultSet dataSet;
    private RecyclerView promocionesRecycler, preciosRecycler;
    private TextView claveTextView ,nombreTextView ,categoriaTextView ,stockTextView ;
    private LinearLayout fichaLayout ;
    private List listaPrecios,listaPromos;


    VerificadorRepository verificadorRepository = new VerificadorRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificador);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setup();
        events();
    }
    private void configurarPromocionesRecycler(List listaPrecios) {
        VerificadorPreciosAdapter promocionesAdapter = new VerificadorPreciosAdapter(listaPrecios);
        promocionesRecycler.setLayoutManager(new LinearLayoutManager(this));
        promocionesRecycler.setAdapter(promocionesAdapter);
    }
    private void configurarPreciosRecycler(List listaPrecios) {

        VerificadorPromocionesAdapter preciosAdapter = new VerificadorPromocionesAdapter(listaPrecios);
        preciosRecycler.setLayoutManager(new LinearLayoutManager(this));
        preciosRecycler.setAdapter(preciosAdapter);
    }


    private void setup()
    {
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        spinner = findViewById(R.id.spinner);
        camara=findViewById(R.id.cameraIcon);
        fichaLayout = findViewById(R.id.ficha);
        //Se obtiene desde aqui porque en el intent tiene una limitacion
        //de listas grandes entonces la app tronaba
        dataValor=verificadorRepository.getValores(this);
        adapter = new CustomAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line, dataValor);
        promocionesRecycler=findViewById(R.id.promocionesRecycler);
        preciosRecycler=findViewById(R.id.precioRecycler);
        autoCompleteTextView.setAdapter(adapter);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataValor);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        claveTextView = findViewById(R.id.ClaveVer);
        nombreTextView = findViewById(R.id.NombreVer);
        categoriaTextView = findViewById(R.id.categoriaVer);
        stockTextView = findViewById(R.id.stockVer);
    }
    private void events()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String seleccion = dataValor.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanOptions options = new ScanOptions();
                options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
                options.setPrompt("Escanear QR");
                options.setCameraId(0);
                options.setOrientationLocked(false);
                options.setBeepEnabled(true);
                options.setCaptureActivity(CaptureActivityPortrait.class);
                options.setBarcodeImageEnabled(false);
                BarcodeLauncher.launch(options);
            }
            private final ActivityResultLauncher<ScanOptions> BarcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    String [] operaEscaneoSplit=result.getContents().split(" - ");
                    String operaEscaneo= operaEscaneoSplit[0];
                    dataSet = verificadorRepository.getDataValor(getApplicationContext(), operaEscaneo);
                    try {
                        String clave=dataSet.getString("clave");
                        claveTextView.setText(clave);
                        nombreTextView.setText(dataSet.getString("desc_super"));
                        categoriaTextView.setText(BD_SQL.getLineaNegocioClave(clave, "descripcion"));
                        stockTextView.setText("Existencias: "+BD_SQL.ObtenerExistenciaTotalClave(clave)+" "+BD_SQL.ObtenerExistenciaTotalUnidad(clave));
                        listaPrecios = verificadorRepository.getTablaPrecios("spConsultaArticuloPrecioPorClave", List.of(clave));
                        listaPromos =verificadorRepository.getPromociones("spObtenerPromocionesArticulo", List.of(clave),getApplicationContext());
                        configurarPreciosRecycler(listaPromos);
                        configurarPromocionesRecycler(listaPrecios);
                    } catch (SQLException e) {
                        claveTextView.setText("Clave");
                        nombreTextView.setText("Nombre");
                        categoriaTextView.setText("Categoria");
                        stockTextView.setText("Stock");
//
                        Toast.makeText(ActivityVerificador.this, "Articulo "+operaEscaneo+" no identificado, intente con la clave. ", Toast.LENGTH_SHORT).show();
                        if (listaPrecios != null) {
                            listaPrecios.clear();
                        }

                    }
                }
            });
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cerrarTeclado();
                String seleccion=autoCompleteTextView.getText().toString();
                String [] operaEscaneoSplit=seleccion.split(" - ");
                seleccion = operaEscaneoSplit[0];
                dataSet = verificadorRepository.getDataValor(getApplicationContext(), seleccion);

                try {
                    String clave=dataSet.getString("clave");
                    claveTextView.setText(clave);
                    nombreTextView.setText(dataSet.getString("desc_super"));
                    categoriaTextView.setText(BD_SQL.getLineaNegocioClave(clave, "descripcion"));
                    stockTextView.setText("Existencias: "+BD_SQL.ObtenerExistenciaTotalClave(clave)+" "+BD_SQL.ObtenerExistenciaTotalUnidad(clave));
                    listaPrecios = verificadorRepository.getTablaPrecios("spConsultaArticuloPrecioPorClave", List.of(clave));
                    listaPromos =verificadorRepository.getPromociones("spObtenerPromocionesArticulo", List.of(clave),getApplicationContext());
                    configurarPreciosRecycler(listaPromos);
                    configurarPromocionesRecycler(listaPrecios);
                } catch (SQLException e) {
                    claveTextView.setText("Clave");
                    nombreTextView.setText("Nombre");
                    categoriaTextView.setText("Categoria");
                    stockTextView.setText("Stock");
                }


                fichaLayout.setVisibility(View.VISIBLE);

                if (seleccion != null) {
                    listaPromos =verificadorRepository.getPromociones("spObtenerPromocionesArticulo", List.of(seleccion),getApplicationContext());
                    configurarPreciosRecycler(listaPromos);
                    listaPrecios = verificadorRepository.getTablaPrecios("spConsultaArticuloPrecioPorClave", List.of(seleccion));
                    configurarPromocionesRecycler(listaPrecios);
                }
            }
        });
    }
    private  void cerrarTeclado(){
        View view = this.getCurrentFocus();
        if(view!= null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
