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


import com.abarrotescasavargas.operamovil.Main.ConcursoVentas.ConcursoVentasAdapter;
import com.abarrotescasavargas.operamovil.Main.ConcursoVentas.DBConcursoVentas;
import com.abarrotescasavargas.operamovil.R;

import java.util.ArrayList;
import java.util.List;

public class concursoVentas extends AppCompatActivity {
    private RecyclerView contenedor;
    private ConcursoVentasAdapter adapter;
    private List<DBConcursoVentas> dataList;
    private Spinner filtros;
    private TextView contador;
    private List<String> dataSpinner;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurso_ventas);
        setup();
        events();
    }
    private void setup ()
    {
        contenedor = findViewById(R.id.testerCon);
        contador = findViewById(R.id.textView4Con);
        filtros = findViewById(R.id.selecionaSpinnerCon);
        searchView = findViewById(R.id.searchViewBuscarCon);
        contenedor.setLayoutManager(new LinearLayoutManager(this));
        Intent data = getIntent();
        dataList = (ArrayList<DBConcursoVentas>) data.getSerializableExtra("dataList");
        dataSpinner = (ArrayList<String>) data.getSerializableExtra("dataSpinner");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtros.setAdapter(spinnerAdapter);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        adapter = new ConcursoVentasAdapter(dataList, this, item -> {
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
        List<DBConcursoVentas> listaFiltrada = new ArrayList<>();
        for (DBConcursoVentas elemento : dataList) {
            if (elemento.getCV_CVEPRO().toLowerCase().contains(texto.toLowerCase()) ||
                    elemento.getCV_CVEART().toLowerCase().contains(texto.toLowerCase())) {
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
        List<DBConcursoVentas> listaFiltrada = new ArrayList<>();

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
        }

        if (adapter != null) {
            adapter.actualizarDatos(listaFiltrada);
            int numeroRegistros = listaFiltrada.size();
            if (contador != null) {
                contador.setText("No. de registros: " + numeroRegistros);
            }
        }
    }

    private List<DBConcursoVentas> filterByEvidencia(boolean conEvidencia) {
        List<DBConcursoVentas> listaFiltrada = new ArrayList<>();

        for (DBConcursoVentas artNue : dataList) {
            boolean tieneEvidencia = artNue.getCV_EVIDEN() != null;
            if ((conEvidencia && tieneEvidencia) || (!conEvidencia && !tieneEvidencia)) {
                listaFiltrada.add(artNue);
            }
        }

        return listaFiltrada;
    }


}