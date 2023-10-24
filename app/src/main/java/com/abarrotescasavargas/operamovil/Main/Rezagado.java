package com.abarrotescasavargas.operamovil.Main;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abarrotescasavargas.operamovil.Main.BaseDatos.BD_SQL;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.DbHelper;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.OperaMovilContract;
import com.abarrotescasavargas.operamovil.Main.Menu2.DBRezagado;
import com.abarrotescasavargas.operamovil.Main.Rezagados.RezagadoAdapter;
import com.abarrotescasavargas.operamovil.R;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Rezagado extends AppCompatActivity implements AdapterARTREZA.RecyclerItemClick {

    ResultSet tb,th,tx,tk,ta,te,ti,to,tu,tq,ty,tz,tc;

    private String sfechaHora;
    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private RecyclerView recyclerView;
    private RezagadoAdapter adapter;
    List<DBRezagado> rezagados;
    private  Spinner seleccionaSpinner;
    private SearchView searchView;
    private TextView textViewNumeroRegistros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezagados);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Setup();
        Events();

        rezagados = new ArrayList<>();
        Cursor cursor = GetSetREZAGADOS(getApplicationContext(),"SELECT * FROM REZAGADOINI");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Leer datos del Cursor y crear un objeto DBRezagado
                String RI_CVEART = cursor.getString(1);
                String RI_NOMART = cursor.getString(5);
                String RI_FECDAT = cursor.getString(4);
                String RI_DIASVT = cursor.getString(2);
                String RI_EXISTE = cursor.getString(3);
                String RI_URLWEB = cursor.getString(6);

                DBRezagado tarjeta = new DBRezagado();
                tarjeta.setRI_CVEART(RI_CVEART);
                tarjeta.setRI_NOMART(RI_NOMART);
                tarjeta.setRI_FECDAT(RI_FECDAT);
                tarjeta.setRI_DIASVT(RI_DIASVT);
                tarjeta.setRI_EXISTE(RI_EXISTE);
                tarjeta.setRI_URLWEB(RI_URLWEB);

                rezagados.add(tarjeta);
            } while (cursor.moveToNext());

            // Cerrar el Cursor después de procesarlo
            cursor.close();
            int numeroRegistros = rezagados.size();
            textViewNumeroRegistros = findViewById(R.id.textView4);
            textViewNumeroRegistros.setText("No. de registros: " + numeroRegistros);
        }

        // Configurar el adaptador con la lista de DBRezagado
        if (getApplicationContext() != null) {

            adapter = new RezagadoAdapter(rezagados, getApplicationContext(), item -> moveToDescription(item));
            recyclerView.setAdapter(adapter);
        }

    }
    private void Setup()
    {
        seleccionaSpinner = findViewById(R.id.selecionaSpinner);
        recyclerView = findViewById(R.id.tester);
        searchView = findViewById(R.id.searchViewBuscar);

    }
    private void Events()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
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
        seleccionaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtiene el elemento seleccionado en el Spinner
                String seleccion = seleccionaSpinner.getSelectedItem().toString();

                // Registra la selección en el log
                Log.d("MiApp", "El usuario seleccionó: " + seleccion);
                filterListSpinner(seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // En caso de que no se seleccione ningún elemento
            }
        });
        //Finaliza interaccion con el Spinner de filtros


    }
    private void filterList(String texto) {
        List<DBRezagado> listaFiltrada = new ArrayList<>();
        for (DBRezagado elemento : rezagados) {
            if (elemento.getRI_CVEART().toLowerCase().contains(texto.toLowerCase()) ||
                    elemento.getRI_NOMART().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(elemento);
            }
        }

        if (adapter != null) {
            adapter.actualizarDatos(listaFiltrada);
            int numeroRegistros = listaFiltrada.size();
            textViewNumeroRegistros.setText("No. de registros: " + numeroRegistros);
        }
    }
    private void filterListSpinner(String seleccion) {
        List<DBRezagado> listaFiltrada = new ArrayList<>();

        switch (seleccion) {
            case "Sin evidencia":
                listaFiltrada = filterByEvidencia(false);
                break;
            case "Mayor existencia":
                listaFiltrada = sortByExistencia(false);
                break;
            case "Menor existencia":
                listaFiltrada = sortByExistencia(true);
                break;
            case "Con evidencia":
                listaFiltrada = filterByEvidencia(true);
                break;
            case "Todos":
                listaFiltrada.addAll(rezagados);
                break;
        }

        if (adapter != null) {
            adapter.actualizarDatos(listaFiltrada);
            int numeroRegistros = listaFiltrada.size();
            if (textViewNumeroRegistros != null) {
                textViewNumeroRegistros.setText("No. de registros: " + numeroRegistros);
            }
        }
    }

    private List<DBRezagado> filterByEvidencia(boolean conEvidencia) {
        List<DBRezagado> listaFiltrada = new ArrayList<>();

        for (DBRezagado rezagado : rezagados) {
            boolean tieneEvidencia = rezagado.getRI_URLWEB() != null;
            if ((conEvidencia && tieneEvidencia) || (!conEvidencia && !tieneEvidencia)) {
                listaFiltrada.add(rezagado);
            }
        }

        return listaFiltrada;
    }

    private List<DBRezagado> sortByExistencia(boolean ascendente) {
        List<DBRezagado> listaFiltrada = new ArrayList<>(rezagados);

        Collections.sort(listaFiltrada, new Comparator<DBRezagado>() {
            @Override
            public int compare(DBRezagado dat1, DBRezagado dat2) {
                try {
                    double existe1 = Double.parseDouble(dat1.getRI_EXISTE());
                    double existe2 = Double.parseDouble(dat2.getRI_EXISTE());
                    return ascendente ? Double.compare(existe1, existe2) : Double.compare(existe2, existe1);
                } catch (NumberFormatException e) {
                    Log.v("Trone existencia", e.toString());
                    return 0;
                }
            }
        });

        return listaFiltrada;
    }





    private void moveToDescription(DBRezagado item) {
//Acciones al clickear la tarjeta
    }
    //Inicia consultas DB Local
    public static Cursor GetSetREZAGADOSLocal(Context context, String query) {
        if (getRezagadosSQLLocal(context, query)) {
            try {
                // Obtiene una instancia de la base de datos en modo lectura
                db = dbHelper.getReadableDatabase();
                // Ejecuta la consulta SQL y devuelve un Cursor con los resultados
                return db.rawQuery(query, null);
            } catch (Exception e) {
                // En caso de excepción, registra el error en el registro de errores (log)
                Log.e("GetSetREZAGADOSLOCAL ====================>>>>>>>>>>>>>", e.toString());
                return null; // Devuelve null en caso de error
            }
        } else {
            // Si la consulta no es válida, devuelve null
            return null;
        }
    }
    public static boolean getRezagadosSQLLocal(Context context, String query) {
        // Crea una instancia de DBRezagado
        DBRezagado dataRez = new DBRezagado();

        // Obtiene un ResultSet a partir de la consulta SQL usando una función llamada tabla() en BD_SQL
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
        Cursor data = db.rawQuery(query, null);

        boolean bandera = false;

        try {
            if (data != null && data.moveToFirst()) { // Verifica si hay al menos una fila
                do {
                    // Configura los campos del objeto dataRez con valores del Cursor
                    dataRez.setRI_CVEART(data.getString(1));
                    dataRez.setRI_NOMART(data.getString(2));
                    dataRez.setRI_FECDAT(data.getString(3));
                    dataRez.setRI_EXISTE(data.getString(4));
                    dataRez.setRI_DIASVT(data.getString(5));
                    dataRez.setRI_URLWEB(data.getString(6));

                    // Llama a la función setDataRez para realizar alguna acción con los datos
                    bandera = setDataRez(dataRez, context);
                } while (data.moveToNext()); // Mueve el cursor a la siguiente fila

                // Cierra el Cursor
                data.close();
            }

            return bandera;
        } catch (Exception e) {
            // En caso de excepción, registra el error en el log
            Log.e("GetTransferenciasPendientesSql", e.toString());
            return bandera;
        } finally {
            // Asegúrate de cerrar la base de datos en el bloque finally
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    //Fin consulta local

    public static Cursor GetSetREZAGADOS(Context context, String query) {
        if (getRezagadosSQL(context, query)) {
            try {
                // Obtiene una instancia de la base de datos en modo lectura
                db = dbHelper.getReadableDatabase();
                // Ejecuta la consulta SQL y devuelve un Cursor con los resultados
                return db.rawQuery(query, null);
            } catch (Exception e) {
                // En caso de excepción, registra el error en el registro de errores (log)
                Log.e("GetSetREZAGADOS ====================>>>>>>>>>>>>>", e.toString());
                return null; // Devuelve null en caso de error
            }
        } else {
            // Si la consulta no es válida, devuelve null
            return null;
        }
    }

    public static boolean getRezagadosSQL(Context context, String query) {
        // Crea una instancia de DBRezagado
        DBRezagado dataRez = new DBRezagado();

        // Obtiene un ResultSet a partir de la consulta SQL usando una función llamada tabla() en BD_SQL
        ResultSet data = BD_SQL.tabla(query, true, context);

        boolean bandera = false;

        try {
            if (data != null) {
                // Crea un dbHelper para obtener una instancia de la base de datos en modo escritura
                dbHelper = new DbHelper(context);
                db = dbHelper.getWritableDatabase();

                // Borra registros de la tabla REZAGADOS en la base de datos
                String whereClause = "";
                String[] whereArgs = {};
                db.delete(OperaMovilContract.REZAGADOS.Table, whereClause, whereArgs);

                // Cierra la base de datos
                db.close();

                // Procesa cada fila de datos en el ResultSet
                do {
                    // Configura los campos del objeto dataRez con valores del ResultSet
                    dataRez.setRI_CVEART(data.getString(5));
                    dataRez.setRI_NOMART(data.getString(6));
                    dataRez.setRI_FECDAT(data.getString(7));
                    dataRez.setRI_EXISTE(data.getString(9));
                    dataRez.setRI_DIASVT(data.getString(8));
                    dataRez.setRI_URLWEB(data.getString(15));

                    // Llama a la función setDataRez para realizar alguna acción con los datos
                    bandera = setDataRez(dataRez, context);
                } while (data.next());

                // Cierra el ResultSet
                data.close();
            }

            return bandera;
        } catch (Exception e) {
            // En caso de excepción, registra el error en el log
            Log.e("GetTransferenciasPendientesSql", e.toString());
            return bandera;
        }
    }


    private static boolean setDataRez(DBRezagado dataRez, Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        try {
            ContentValues configVal = new ContentValues();
            configVal.put(OperaMovilContract.REZAGADOS.RI_CVEART, dataRez.getRI_CVEART());
            configVal.put(OperaMovilContract.REZAGADOS.RI_DIASVT, dataRez.getRI_DIASVT());
            configVal.put(OperaMovilContract.REZAGADOS.RI_EXISTE, dataRez.getRI_EXISTE());
            configVal.put(OperaMovilContract.REZAGADOS.RI_FECDAT, dataRez.getRI_FECDAT());
            configVal.put(OperaMovilContract.REZAGADOS.RI_NOMART, dataRez.getRI_NOMART());
            configVal.put(OperaMovilContract.REZAGADOS.RI_URLWEB, dataRez.getRI_URLWEB());
            long res = db.insertOrThrow(OperaMovilContract.REZAGADOS.Table, null, configVal);
            db.close();
            return res > 0;
        } catch (Exception e) {
            db.close();
            Log.e("SetMovimientoAlmacenDetalle", e.toString());
            return false;
        }
    }

    @Override
    public void itemClick(String item) {

        final Servidor Srv = new Servidor(getApplicationContext());

        String fechEVV="";
        th =Srv.tabla("select RI_FECEVI from REZAGADOINI where RI_CVEART = '"+item+"' ",true);

        try {
            fechEVV = th.getString(1);

        } catch (SQLException | java.sql.SQLException exception) {
            exception.printStackTrace();
        }


        if(fechEVV ==null || fechEVV.equals("")){

            //new IntentIntegrator(this).initiateScan();

//            Intent intent = new Intent(this, captura_Rezagado.class);
//            intent.putExtra("item", item);
//
//            startActivity(intent);

        }else{

            Toast.makeText(getApplicationContext(), "Ya cuentas con EVIDENCIA", Toast.LENGTH_LONG).show();

        }
    }
}
