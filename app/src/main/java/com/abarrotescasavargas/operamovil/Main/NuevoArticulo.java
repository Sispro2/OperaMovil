package com.abarrotescasavargas.operamovil.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abarrotescasavargas.operamovil.Main.ArticuloNuevo.DBArticuloNuevo;
import com.abarrotescasavargas.operamovil.Main.ArticuloNuevo.ArticuloNuevoAdapter;
import com.abarrotescasavargas.operamovil.R;

import java.util.ArrayList;
import java.util.List;

public class NuevoArticulo extends AppCompatActivity {
    private RecyclerView contenedor;
    private ArticuloNuevoAdapter adapter;
    private List<DBArticuloNuevo> dataList;
    private Spinner filtros;
    private TextView contador;
    private List<String> dataSpinner;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_articulo);
         setup();
         events();
    }
    private void setup ()
    {
        contenedor = findViewById(R.id.testerArt);
        contador = findViewById(R.id.textView4Art);
        filtros = findViewById(R.id.selecionaSpinnerArt);
        searchView = findViewById(R.id.searchViewBuscarArt);
        contenedor.setLayoutManager(new LinearLayoutManager(this));
        Intent data = getIntent();
        dataList = (ArrayList<DBArticuloNuevo>) data.getSerializableExtra("dataList");
        dataSpinner = (ArrayList<String>) data.getSerializableExtra("dataSpinner");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtros.setAdapter(spinnerAdapter);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        adapter = new ArticuloNuevoAdapter(dataList, this, item -> {
            // Click hace algo
        });
        contador.setText("No. de registros: " + dataList.size());
        contenedor.setAdapter(adapter);
    }
    public void events()
    {
        contenedor.setHasFixedSize(true);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        //Inicia interaccion con el Spinner de filtros
        filtros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String seleccion = filtros.getSelectedItem().toString();
                Log.d("MiApp", "El usuario seleccionó: " + seleccion);
                filterListSpinner(seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });



    }
    private void filterList(String texto) {
        List<DBArticuloNuevo> listaFiltrada = new ArrayList<>();
        for (DBArticuloNuevo elemento : dataList) {
            if (elemento.getNA_CVEART().toLowerCase().contains(texto.toLowerCase()) ||
                    elemento.getNA_NOMART().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(elemento);
            }
        }

        if (adapter != null) {
            adapter.actualizarDatos(listaFiltrada);
            int numeroRegistros = listaFiltrada.size();
            contador.setText("No. de registros: " + numeroRegistros);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, activity_menu_2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void filterListSpinner(String seleccion) {
        List<DBArticuloNuevo> listaFiltrada = new ArrayList<>();

        switch (seleccion) {
            case "SIN EVIDENCIA":
                listaFiltrada = filterByEvidencia(false);
                break;
            case "CON EVIDENCIA":
                listaFiltrada = filterByEvidencia(true);
                break;
            case "TODOS":
                listaFiltrada.addAll(dataList);
                break;
            default:
                listaFiltrada= filtraLinea(seleccion);

        }

        if (adapter != null) {
            adapter.actualizarDatos(listaFiltrada);
            int numeroRegistros = listaFiltrada.size();
            if (contador != null) {
                contador.setText("No. de registros: " + numeroRegistros);
            }
        }
    }

    private List<DBArticuloNuevo> filterByEvidencia(boolean conEvidencia) {
        List<DBArticuloNuevo> listaFiltrada = new ArrayList<>();

        for (DBArticuloNuevo artNue : dataList) {
            boolean tieneEvidencia = artNue.getNA_EVIDEN() != null;
            if ((conEvidencia && tieneEvidencia) || (!conEvidencia && !tieneEvidencia)) {
                listaFiltrada.add(artNue);
            }
        }

        return listaFiltrada;
    }
    private List<DBArticuloNuevo> filtraLinea(String seleccion) {
        List<DBArticuloNuevo> listaFiltrada = new ArrayList<>();

        for (DBArticuloNuevo artNue : dataList) {
            if (artNue.getNA_LINNEG().equals(seleccion)) {
                listaFiltrada.add(artNue);
            }
        }

        return listaFiltrada;
    }


}
