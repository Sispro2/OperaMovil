package com.abarrotescasavargas.operamovil.Main;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.abarrotescasavargas.operamovil.Main.ArticuloNuevo.DBArticuloNuevo;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.BD_SQL;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.DbHelper;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.OperaMovilContract;
import com.abarrotescasavargas.operamovil.Main.Candidatos.ActivityCandidatos;
import com.abarrotescasavargas.operamovil.Main.Candidatos.Candidatos;
import com.abarrotescasavargas.operamovil.Main.Candidatos.ResponseCandidatos;
import com.abarrotescasavargas.operamovil.Main.ConcursoVentas.DBConcursoVentas;
import com.abarrotescasavargas.operamovil.Main.Documentos.PeticionRRHH;
import com.abarrotescasavargas.operamovil.Main.Documentos.RetrofitRRHH;
import com.abarrotescasavargas.operamovil.Main.Mantenimiento.ActivityMantenimiento;
import com.abarrotescasavargas.operamovil.Main.Mantenimiento.PeticionMtto;
import com.abarrotescasavargas.operamovil.Main.Mantenimiento.ReporteMantenimiento;
import com.abarrotescasavargas.operamovil.Main.Mantenimiento.ResponseReporteMantto;
import com.abarrotescasavargas.operamovil.Main.Mantenimiento.RetrofitMantto;
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;
import com.abarrotescasavargas.operamovil.Main.Transferencias.TransferenciasActivity;
import com.abarrotescasavargas.operamovil.Main.Verificador.ActivityVerificador;
import com.abarrotescasavargas.operamovil.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SincronizandoActivity extends AppCompatActivity {
    private final Handler mainHandler = new Handler();
    private volatile boolean stopThread;
    SucursalRepository sucursalRepository;
    /* instancias para Recursos humanos*/
    String usuario;
    /* instancias para Recursos humanos*/
    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    Context context;

    private static final String TAG = "SincronizandoActivity";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizando);
        Setup();
    }

    private void Setup() {
        progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.sincronizando_dialog);
        sucursalRepository = new SucursalRepository(this);
        String origen = getIntent().getStringExtra("origen");
        usuario = getIntent().getStringExtra("USUARIO_LOG");
        switch (origen) {
            case "PROVEEDORES":
                stopThread = false;
                ProveedoresRunnable proveedoresRunnable = new ProveedoresRunnable("cveSucursal", "idReclutamiento");
                new Thread(proveedoresRunnable).start();
                break;
            case "RECLUTAMIENTO":
                stopThread = false;
                ReclutamientoRunnable reclutamientoRunnable = new ReclutamientoRunnable(sucursalRepository.GetDetalleSucursal().getKS_CVESUC());
                new Thread(reclutamientoRunnable).start();
                break;
            case "REZAGADO":
                stopThread = false;
                RezagadoRunnable rezagadoRunnable = new RezagadoRunnable("cveSucursal", "idReclutamiento", this);
                new Thread(rezagadoRunnable).start();
                break;
            case "VERIFICADOR":
                stopThread = false;
                VerificadorRunnable verificadorRunnable = new VerificadorRunnable("cveSucursal", "idReclutamiento");
                new Thread(verificadorRunnable).start();
                break;
            case "CONCURSO":
                stopThread = false;
                dbHelper = new DbHelper(this);
                db = dbHelper.getWritableDatabase();
                db.execSQL("DELETE FROM " + OperaMovilContract.CONCURSO_VENTAS.Table);
                db.close();
                ConcursoRunnable concursoRunnable = new ConcursoRunnable("cveSucursal", "idReclutamiento");
                new Thread(concursoRunnable).start();
                break;
            case "NUEVO":
                stopThread = false;
                dbHelper = new DbHelper(this);
                db = dbHelper.getWritableDatabase();
                db.execSQL("DELETE FROM " + OperaMovilContract.ARTICULO.Table);
                db.close();
                NuevoRunnable nuevoRunnable = new NuevoRunnable();
                new Thread(nuevoRunnable).start();
                break;
            case "MANTENIMIENTO":
                stopThread = false;
                MantenimientoRunnable mantenimientoRunnable = new MantenimientoRunnable(sucursalRepository.GetDetalleSucursal().getKS_CVESUC());
                new Thread(mantenimientoRunnable).start();
                break;
            case "TRANSFERENCIA":
                stopThread = false;
                TransferenciaRunnable transferenciaRunnable = new TransferenciaRunnable("cveSucursal", "idReclutamiento");
                new Thread(transferenciaRunnable).start();
                break;
        }
    }

    private void CargaProveedores(String parametro1, String parametro2) {
        progressDialog.cancel();
//        Intent intent = new Intent(getApplicationContext(), listaBitacora.class);
//        intent.putExtra("Parametro1", parametro1);
//        intent.putExtra("Parametro2", parametro2);
//        startActivity(intent);
        overridePendingTransition(R.transition.in_left, R.transition.out_left);
        sucursalRepository.insertLog("Proveedores","Entrada al modulo");
        finish();
    }

    private void CargaReclutamiento(ArrayList<Candidatos> candidatosList) {
        progressDialog.cancel();
        Intent intent = new Intent(getApplicationContext(), ActivityCandidatos.class);
        intent.putExtra("candidatosList", candidatosList);
        startActivity(intent);
        overridePendingTransition(R.transition.in_left, R.transition.out_left);
        sucursalRepository.insertLog("Reclutamiento","Entrada al modulo");
        finish();
    }

    private void CargaRezagado(String parametro1, String parametro2) {
        progressDialog.cancel();
        Intent intent = new Intent(getApplicationContext(), Rezagado.class);
        intent.putExtra("Parametro1", parametro1);
        intent.putExtra("Parametro2", parametro2);
        startActivity(intent);
        overridePendingTransition(R.transition.in_left, R.transition.out_left);
        sucursalRepository.insertLog("Lento desplazamiento","Entrada al modulo");
        finish();
    }

    private void CargaVerificador(String parametro1, String parametro2) {
        progressDialog.cancel();
        Intent intent = new Intent(getApplicationContext(), ActivityVerificador.class);
        intent.putExtra("Parametro1", parametro1);
        intent.putExtra("Parametro2", parametro2);
        startActivity(intent);
        overridePendingTransition(R.transition.in_left, R.transition.out_left);
        sucursalRepository.insertLog("Verificador","Entrada al modulo");
        finish();
    }

    private void CargaNuevo(ArrayList<DBArticuloNuevo> dataList, ArrayList<String> dataSpinner) {
        progressDialog.cancel();
        Intent intent = new Intent(getApplicationContext(), NuevoArticulo.class);
        intent.putExtra("dataList", dataList);
        intent.putExtra("dataSpinner", dataSpinner);
        startActivity(intent);
        overridePendingTransition(R.transition.in_left, R.transition.out_left);
        sucursalRepository.insertLog("Articulo nuevo","Entrada al modulo");
        finish();
    }
    private void CargaConcurso(ArrayList<DBConcursoVentas> dataList, ArrayList<String> dataSpinner) {
        progressDialog.cancel();
        Intent intent = new Intent(getApplicationContext(), concursoVentas.class);
        intent.putExtra("dataList", dataList);
        intent.putExtra("dataSpinner", dataSpinner);
        try{
            startActivity(intent);
        }
        catch (Exception e)
        {
            Log.v("Trone compa",e.toString());
        }
        overridePendingTransition(R.transition.in_left, R.transition.out_left);
        sucursalRepository.insertLog("Concurso de ventas","Entrada al modulo");
        finish();
    }

    private void CargaMantenimiento(ArrayList<ReporteMantenimiento> reportesMantenimientoList) {

        progressDialog.cancel();
        Intent intent = new Intent(getApplicationContext(), ActivityMantenimiento.class);
        intent.putExtra("reportesMantenimientoList", reportesMantenimientoList);
        startActivity(intent);
        overridePendingTransition(R.transition.in_left, R.transition.out_left);
        sucursalRepository.insertLog("Mantenimiento","Entrada al modulo");
        finish();
    }

    private void CargaTransferencia(String parametro1, String parametro2) {
//        progressDialog.cancel();
//        Intent intent = new Intent(getApplicationContext(), TransferenciasActivity.class);
//        intent.putExtra("Parametro1", parametro1);
//        intent.putExtra("Parametro2", parametro2);
//        startActivity(intent);
//        overridePendingTransition(R.transition.in_left, R.transition.out_left);
//        sucursalRepository.insertLog("Transferencias","Entrada al modulo");
//        finish();
    }

    @Override
    public void onBackPressed() {
        stopThread = true;
        Intent intent = new Intent(getApplicationContext(), activity_menu_2.class);
        overridePendingTransition(R.transition.in_left, R.transition.out_left);
        startActivity(intent);
        finish();
    }

    class ProveedoresRunnable implements Runnable {
        // declaracion de variables
        String cveSucursal, idReclutamiento;

        // constructor por si deseamos pasarle variables a nuestro metodo
        ProveedoresRunnable(String cveSucursal, String idReclutamiento) {
            this.cveSucursal = cveSucursal;
            this.idReclutamiento = idReclutamiento;
        }

        @Override
        public void run() {
            for (int i = 0; i <= 20; i++) {
                if (!stopThread) {
                    Log.d(TAG, "ProveedoresThread: " + i);
                    try {
                        Thread.sleep(1000); // solo lo puse para validar como se detiene un poco el proceso
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (i == 3) {
                        // al finalizar la condicion deseada, se ejecuta el metodo
                        mainHandler.post(() -> {
                            // ejecutar el metodo
                            CargaProveedores(cveSucursal, idReclutamiento);
                        });
                        // Finaliza el hilo
                    }
                }

            }
        }
    }

    class ReclutamientoRunnable implements Runnable {
        String cveSucursal;
        ReclutamientoRunnable(String cveSucursal) {
            this.cveSucursal = cveSucursal;
        }

        @Override
        public void run() {

            Retrofit retrofit = new RetrofitRRHH().getRuta();
            PeticionRRHH peticionRRHH = retrofit.create(PeticionRRHH.class);
            Call<ResponseCandidatos> call = peticionRRHH.obtieneCandidatos(cveSucursal);
            call.enqueue(new Callback<ResponseCandidatos>() {
                @Override
                public void onResponse(@NonNull Call<ResponseCandidatos> call, @NonNull Response<ResponseCandidatos> response) {
                    if (response.body().isStatus()) {
                        ArrayList<Candidatos> candidatosList = response.body().getCandidatos();
                        try {
                            Thread.sleep(100); // mil es un segundo
                            mainHandler.post(() -> {
                                CargaReclutamiento(candidatosList);
                            });
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseCandidatos> call, Throwable t) {
                    Log.i("ERROR en ", "onFailure: " + t.toString());
                }
            });
        }
    }

    class RezagadoRunnable implements Runnable {
        String cveSucursal, idReclutamiento;
        Context context; // Declaración de la variable de contexto
        RezagadoRunnable(String cveSucursal, String idReclutamiento, Context context) {
            this.cveSucursal = cveSucursal;
            this.idReclutamiento = idReclutamiento;
            this.context = context; // Almacena el contexto proporcionado en la variable de instancia
        }
        @Override
        public void run() {
            for (int i = 0; i <= 20; i++) {
                if (!stopThread) {
                    Log.d(TAG, "RezagadoThread: " + i);
                    if (i == 15) {
                        mainHandler.post(() -> {
                            CargaRezagado(cveSucursal, idReclutamiento);
                            ActualizaDBs();
                        });
                        return;
                    }
                }
            }
        }
    }


    private void ActualizaDBs(){
    }
    class VerificadorRunnable implements Runnable {
        String cveSucursal, idReclutamiento;

        VerificadorRunnable(String cveSucursal, String idReclutamiento) {
            this.cveSucursal = cveSucursal;
            this.idReclutamiento = idReclutamiento;
        }

        @Override
        public void run() {
            for (int i = 0; i <= 20; i++) {
                if (!stopThread) {
                    Log.d(TAG, "VerificadorThread: " + i);
                    try {
                        Thread.sleep(100); // solo lo puse para validar como se detiene un poco el proceso
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (i == 15) {
                        mainHandler.post(() -> {
                            CargaVerificador(cveSucursal, idReclutamiento);
                        });
                        return;
                    }
                }
            }
        }
    }

    class ConcursoRunnable implements Runnable {
        String cveSucursal, idReclutamiento;
        Boolean bandera;

        ConcursoRunnable(String cveSucursal, String idReclutamiento) {
            this.cveSucursal = cveSucursal;
            this.idReclutamiento = idReclutamiento;
        }

        @Override
        public void run() {
            String query="SELECT * FROM CONCURSOVTA WHERE GETDATE() BETWEEN CV_FECINI AND CV_FECFIN ORDER BY CV_FECINI DESC";
            ResultSet data = BD_SQL.tabla(query, false,getApplicationContext());
            ArrayList<DBConcursoVentas> dataList = new ArrayList<>();
            try {
                while (data.next())
                {
                    DBConcursoVentas insertArt = new DBConcursoVentas();
                    insertArt.setCV_CONSEC(data.getString(1));
                    insertArt.setCV_TIPOCO(data.getString(2));
                    insertArt.setCV_FECINI(data.getString(3));
                    insertArt.setCV_FECFIN(data.getString(4));
                    insertArt.setCV_CVESUC(data.getString(5));
                    insertArt.setCV_CVEPRO(data.getString(6));
                    insertArt.setCV_CVEART(data.getString(7));
                    insertArt.setCV_VTACAN(data.getString(8));
                    insertArt.setCV_VTAMON(data.getString(9));
                    insertArt.setCV_VTAPTO(data.getString(10));
                    insertArt.setCV_FECEVI(data.getString(11));
                    insertArt.setCV_EVIDEN(data.getString(13));
                    bandera = setDataConcursoVtas(insertArt,getApplicationContext());
                    dataList.add(insertArt);

                }
                data.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            mainHandler.post(() -> {
                CargaConcurso(dataList,consultaProveedores());
            });
        }
    }

    class NuevoRunnable implements Runnable {
        Boolean bandera;

        @Override
        public void run() {
            String query="SELECT * FROM articulo WHERE DATEDIFF(DAY, [fecha_alta], GETDATE()) < 31;";
            ResultSet data = BD_SQL.tabla(query, false, getApplicationContext());
            ArrayList<DBArticuloNuevo> dataList = new ArrayList<>();
            int i=0;
            try {
                while (data.next())
                {
                    DBArticuloNuevo insertArt = new DBArticuloNuevo();
                    insertArt.setNA_CVEART(data.getString(2));
                    insertArt.setNA_NOMART(data.getString(10));
                    insertArt.setNA_FECALT(data.getString(24));
                    insertArt.setNA_EVIDEN(data.getString(38));
                    insertArt.setNA_LINNEG(BD_SQL.getLineaNegocioClave(data.getString(2),"clave")+" - "+BD_SQL.getLineaNegocioClave(data.getString(2),"descripcion"));
                    insertArt.setNA_EXISTE(BD_SQL.ObtenerExistenciaTotalClave(data.getString(2)));
                    bandera = setDataNueArt(insertArt,getApplicationContext());
                    dataList.add(insertArt);

                }
                data.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            mainHandler.post(() -> {
                CargaNuevo(dataList,consultaLineaNegocio());
            });

        }

    }

    public ArrayList<String> consultaLineaNegocio() {
        ArrayList<String> list = new ArrayList<>();
        list.add("TODOS");
        list.add("CON EVIDENCIA");
        list.add("SIN EVIDENCIA");
        String query = "SELECT * FROM linea_negocio;";
        ResultSet data = BD_SQL.tabla(query, false, getApplicationContext());
        try {
            while (data != null && data.next()) {
                String item =data.getString("clave")+" - "+ data.getString("descripcion");
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<String> consultaProveedores() {
        ArrayList<String> list = new ArrayList<>();
        list.add("TODOS");
        list.add("CON EVIDENCIA");
        list.add("SIN EVIDENCIA");
        return list;
    }
    private static boolean setDataConcursoVtas(DBConcursoVentas dataRez, Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        try {
            ContentValues configVal = new ContentValues();
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_CONSEC, dataRez.getCV_CONSEC());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_TIPOCO, dataRez.getCV_TIPOCO());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_FECINI, dataRez.getCV_FECINI());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_FECFIN, dataRez.getCV_FECFIN());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_CVESUC, dataRez.getCV_CVESUC());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_CVEPRO, dataRez.getCV_CVEPRO());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_CVEART, dataRez.getCV_CVEART());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_VTACAN, dataRez.getCV_VTACAN());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_VTAMON, dataRez.getCV_VTAMON());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_VTAPTO, dataRez.getCV_VTAPTO());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_FECEVI, dataRez.getCV_FECEVI());
            configVal.put(OperaMovilContract.CONCURSO_VENTAS.CV_EVIDEN, dataRez.getCV_EVIDEN());

            long res = db.insertOrThrow(OperaMovilContract.CONCURSO_VENTAS.Table, null, configVal);
            db.close();
            return res > 0;
        } catch (Exception e) {
            db.close();
            return false;
        }
    }

    private static boolean setDataNueArt(DBArticuloNuevo dataRez, Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        try {
            ContentValues configVal = new ContentValues();
            configVal.put(OperaMovilContract.ARTICULO.NA_CVEART, dataRez.getNA_CVEART());
            configVal.put(OperaMovilContract.ARTICULO.NA_NOMART, dataRez.getNA_NOMART());
            configVal.put(OperaMovilContract.ARTICULO.NA_FECALT, dataRez.getNA_FECALT());
            configVal.put(OperaMovilContract.ARTICULO.NA_EVIDEN, dataRez.getNA_EVIDEN());
            configVal.put(OperaMovilContract.ARTICULO.NA_LINNEG, dataRez.getNA_LINNEG());
            configVal.put(OperaMovilContract.ARTICULO.NA_EXISTE, dataRez.getNA_EXISTE());
            long res = db.insertOrThrow(OperaMovilContract.ARTICULO.Table, null, configVal);
            db.close();
            return res > 0;
        } catch (Exception e) {
            db.close();
            return false;
        }
    }
    class MantenimientoRunnable implements Runnable {
        String cveSucursal;

        MantenimientoRunnable(String cveSucursal) {
            this.cveSucursal = cveSucursal;

        }

        @Override
        public void run() {


            Retrofit retrofit = new RetrofitMantto().getRuta();
            PeticionMtto peticionMtto = retrofit.create(PeticionMtto.class);
            Call<ResponseReporteMantto> call = peticionMtto.obtieneReportes(cveSucursal);
            call.enqueue(new Callback<ResponseReporteMantto>() {
                @Override
                public void onResponse(@NonNull Call<ResponseReporteMantto> call, @NonNull Response<ResponseReporteMantto> response) {
                    assert response.body() != null;
                    if (response.body().isStatus()) {
                        ArrayList<ReporteMantenimiento> reportesMantenimientoList = response.body().getReportesMantenimiento();
                        try {
                            Thread.sleep(100); // mil es un segundo
                            mainHandler.post(() -> {
                                CargaMantenimiento(reportesMantenimientoList);
                            });
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseReporteMantto> call, @NonNull Throwable t) {
                    Log.i("ERROR en ", "onFailure: " + t.toString());
                }
            });

        }
    }

    class TransferenciaRunnable implements Runnable {
        String cveSucursal, idReclutamiento;

        TransferenciaRunnable(String cveSucursal, String idReclutamiento) {
            this.cveSucursal = cveSucursal;
            this.idReclutamiento = idReclutamiento;
        }

        @Override
        public void run() {
            for (int i = 0; i <= 20; i++) {
                if (!stopThread) {
                    Log.d(TAG, "TransferenciaThread: " + i);
                    try {
                        Thread.sleep(1000); // solo lo puse para validar como se detiene un poco el proceso
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (i == 15) {
                        mainHandler.post(() -> {
                            CargaTransferencia(cveSucursal, idReclutamiento);
                        });
                        return;
                    }
                }
            }
        }
    }
}