package com.abarrotescasavargas.operamovil.Main.Repository;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.abarrotescasavargas.operamovil.Main.BaseDatos.BD_SQL;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.DbHelper;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.OperaMovilContract;
import com.abarrotescasavargas.operamovil.Main.Entidades.Sucursales;
import com.abarrotescasavargas.operamovil.Main.FunGenerales.Variables;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;

public class SucursalRepository {
    Context context;
    static DbHelper dbHelper;
    static String query;
    static SQLiteDatabase db;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public SucursalRepository(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        //Log.e("tagBase", "SucursalRepository: Se creo" );
    }

    public void SetSucursal(Context context, Sucursales sucursal) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        try {
            String whereClause = "";
            String[] whereArgs = {};
            db.delete(OperaMovilContract.SUCURSAL.Table, whereClause, whereArgs);
            ContentValues configVal = new ContentValues();
            configVal.put(OperaMovilContract.SUCURSAL.CVESUC, sucursal.getKS_CVESUC());
            configVal.put(OperaMovilContract.SUCURSAL.NOMSUC, sucursal.getKS_NOMSUC());
            configVal.put(OperaMovilContract.SUCURSAL.IP, sucursal.getKS_IP());
            configVal.put(OperaMovilContract.SUCURSAL.BASEDT, sucursal.getKS_BASEDT());
            configVal.put(OperaMovilContract.SUCURSAL.PABASE, sucursal.getKS_PABASE());
            configVal.put(OperaMovilContract.SUCURSAL.CVESUCSINFONIA, sucursal.getKS_CVESUCSINFONIA());
            configVal.put(OperaMovilContract.SUCURSAL.IP_DEV, "192.168.1.14");
            configVal.put(OperaMovilContract.SUCURSAL.PABASE_DEV, "wincaja");
            long res = db.insertOrThrow(OperaMovilContract.SUCURSAL.Table, null, configVal);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e("SetSucursal", e.toString());
        }
    }

    public void SetUsuario(Context context, String CveTra, String IMEI) {
        int _idUsuario = 0;
        try {
            ResultSet resultSet = BD_SQL.tabla("select id_usuario  from usuario where upper(clave) = upper('" + CveTra + "'); ", true, context);
            if (resultSet != null) {
                do {
                    _idUsuario = resultSet.getInt(1);
                } while (resultSet.next());
                resultSet.close();
            }
        } catch (Exception e) {
            Log.e("GetIdUsuario", e.toString());
        }


        // obterner el usuario
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        try {
            String whereClause = "";
            String[] whereArgs = {};
            // db.delete(OperaMovilContract.SUCURSAL.Table, whereClause, whereArgs);
            ContentValues configVal = new ContentValues();
            configVal.put(OperaMovilContract.SUCURSAL.CVETRA, CveTra);
            configVal.put(OperaMovilContract.SUCURSAL.ANDRID, IMEI);
            configVal.put(OperaMovilContract.SUCURSAL.ID_USUARIO, _idUsuario);

            long res = db.update(OperaMovilContract.SUCURSAL.Table, configVal, whereClause, whereArgs);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e("SetSucursal", e.toString());
        }
    }

    public Sucursales GetDetalleSelectedSucursal(String cveSuc) {
        try {
            Sucursales sucursal = new Sucursales();
            db = dbHelper.getReadableDatabase();
            query = "select " + OperaMovilContract.KSUCURSALES.KS_CVESUC +
                    "," + OperaMovilContract.KSUCURSALES.KS_NOMSUC +
                    "," + OperaMovilContract.KSUCURSALES.KS_IP +
                    "," + OperaMovilContract.KSUCURSALES.KS_BASEDT +
                    "," + OperaMovilContract.KSUCURSALES.KS_PABASE +
                    "," + OperaMovilContract.KSUCURSALES.KS_CVESUCSINFONIA +
                    "," + OperaMovilContract.KSUCURSALES.KS_IP_DEV +
                    "," + OperaMovilContract.KSUCURSALES.KS_PABASE_DEV +
                    " from " + OperaMovilContract.KSUCURSALES.Table + "  where  " + OperaMovilContract.KSUCURSALES.KS_CVESUC + " = '" + cveSuc + "' ; ";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    sucursal.setKS_CVESUC(cursor.getString(cursor.getColumnIndexOrThrow("KS_CVESUC")));
                    sucursal.setKS_NOMSUC(cursor.getString(cursor.getColumnIndexOrThrow("KS_NOMSUC")));
                    sucursal.setKS_BASEDT(cursor.getString(cursor.getColumnIndexOrThrow("KS_BASEDT")));
                    sucursal.setKS_CVESUCSINFONIA(cursor.getInt(cursor.getColumnIndexOrThrow("KS_CVESUCSINFONIA")));
                    sucursal.setKS_IP_DEV(cursor.getString(cursor.getColumnIndexOrThrow("KS_IP_DEV")));
                    sucursal.setKS_PABASE_DEV(cursor.getString(cursor.getColumnIndexOrThrow("KS_PABASE_DEV")));
                    if (Objects.equals(Variables.Ambiente, "DESARROLLO")) {
                        sucursal.setKS_IP(cursor.getString(cursor.getColumnIndexOrThrow("KS_IP_DEV")));
                        sucursal.setKS_PABASE(cursor.getString(cursor.getColumnIndexOrThrow("KS_PABASE_DEV")));
                    } else {
                        sucursal.setKS_IP(cursor.getString(cursor.getColumnIndexOrThrow("KS_IP")));
                        sucursal.setKS_PABASE(cursor.getString(cursor.getColumnIndexOrThrow("KS_PABASE")));
                    }
                }
                cursor.close();
                return sucursal;
            }
            return null;
        } catch (Exception e) {
            Log.e("GetDetalleSucursal", e.toString());
            return null;
        }
    }


    public Sucursales GetDetalleSucursal() {
        try {
            Sucursales sucursal = new Sucursales();
            db = dbHelper.getReadableDatabase();
            query = "select " + OperaMovilContract.SUCURSAL.CVESUC +
                    "," + OperaMovilContract.SUCURSAL.NOMSUC +
                    "," + OperaMovilContract.SUCURSAL.IP +
                    "," + OperaMovilContract.SUCURSAL.BASEDT +
                    "," + OperaMovilContract.SUCURSAL.PABASE +
                    "," + OperaMovilContract.SUCURSAL.CVESUCSINFONIA +
                    "," + OperaMovilContract.SUCURSAL.IP_DEV +
                    "," + OperaMovilContract.SUCURSAL.CVETRA +
                    "," + OperaMovilContract.SUCURSAL.PABASE_DEV +
                    "," + OperaMovilContract.SUCURSAL.ID_USUARIO +
                    " from " + OperaMovilContract.SUCURSAL.Table + ";";

            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    sucursal.setKS_CVESUC(cursor.getString(cursor.getColumnIndexOrThrow("KS_CVESUC")));
                    sucursal.setKS_NOMSUC(cursor.getString(cursor.getColumnIndexOrThrow("KS_NOMSUC")));
                    sucursal.setKS_BASEDT(cursor.getString(cursor.getColumnIndexOrThrow("KS_BASEDT")));
                    sucursal.setKS_CVESUCSINFONIA(cursor.getInt(cursor.getColumnIndexOrThrow("KS_CVESUCSINFONIA")));
                    sucursal.setKS_IP_DEV(cursor.getString(cursor.getColumnIndexOrThrow("KS_IP_DEV")));
                    if (Objects.equals(Variables.Ambiente, "DESARROLLO")) {
                        sucursal.setKS_IP(cursor.getString(cursor.getColumnIndexOrThrow("KS_IP_DEV")));
                        sucursal.setKS_PABASE(cursor.getString(cursor.getColumnIndexOrThrow("KS_PABASE_DEV")));
                    } else {
                        sucursal.setKS_IP(cursor.getString(cursor.getColumnIndexOrThrow("KS_IP")));
                        sucursal.setKS_PABASE(cursor.getString(cursor.getColumnIndexOrThrow("KS_PABASE")));
                    }
                    sucursal.setKS_CVEUSR(cursor.getString(cursor.getColumnIndexOrThrow(OperaMovilContract.SUCURSAL.CVETRA)));
                    sucursal.setKS_IDUSUARIO(cursor.getInt(cursor.getColumnIndexOrThrow(OperaMovilContract.SUCURSAL.ID_USUARIO)));
                }
                cursor.close();
                return sucursal;
            }
            return null;
        } catch (Exception e) {
            Log.e("GetDetalleSucursal", e.toString());
            return null;
        }
    }

    public interface LocationResultListener {
        void onLocationResult(double latitud, double longitud);
    }

    public void getUbicacion(Context context, LocationResultListener listener) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) context, location -> {
                    if (location != null) {
                        double latitud = location.getLatitude();
                        double longitud = location.getLongitude();
                        listener.onLocationResult(latitud, longitud);
                    } else {
                        // Error log no se obtieiene la ubicacion
                    }
                })
                .addOnFailureListener((Activity) context, e -> {
                    // Error log fallo en fusedLocationCliente
                });
    }

    public void insertLog(String modulo, String accion) {
        String usuario = GetDetalleSucursal().getKS_CVEUSR();
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getUbicacion(context, (latitud, longitud) -> {
                String latLon = latitud + "," + longitud;
                insertarRegistroBitacora(usuario, modulo, latLon, androidId, accion);
            });
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void insertarRegistroBitacora(String usuario, String modulo, String latLon, String androidId, String accion) {
        try {
            String query = "INSERT INTO BITACORAAPPS (BA_FECHAS, BA_USUARI, BA_MODULO, BA_LATLON, BA_TELEID,BA_ACCION,BA_ORIGEN) " +
                    "VALUES (GETDATE(), '" + usuario + "', '" + modulo.toUpperCase() + "', '" + latLon + "', '" + androidId.toUpperCase() + "','" + accion.toUpperCase() + "','SINFONIA MOVIL');";
            BD_SQL.ejecuta(query, context);
        } catch (Exception e) {
            //Excepcion
        }
    }


    public ArrayList<Sucursales> GetListaSucursales() {
        ArrayList<Sucursales> listasucursales = new ArrayList<>();
        try {
            db = dbHelper.getReadableDatabase();
            query = "select " + OperaMovilContract.KSUCURSALES.KS_CVESUC +
                    "," + OperaMovilContract.KSUCURSALES.KS_NOMSUC +
                    "," + OperaMovilContract.KSUCURSALES.KS_IP +
                    "," + OperaMovilContract.KSUCURSALES.KS_BASEDT +
                    "," + OperaMovilContract.KSUCURSALES.KS_PABASE +
                    "," + OperaMovilContract.KSUCURSALES.KS_CVESUCSINFONIA +
                    "," + OperaMovilContract.KSUCURSALES.KS_IP_DEV +
                    "," + OperaMovilContract.KSUCURSALES.KS_PABASE_DEV +
                    " from " + OperaMovilContract.KSUCURSALES.Table + " ; ";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Sucursales obj = new Sucursales();
                    obj.setKS_CVESUC(cursor.getString(cursor.getColumnIndexOrThrow("KS_CVESUC")));
                    obj.setKS_NOMSUC(cursor.getString(cursor.getColumnIndexOrThrow("KS_NOMSUC")));
                    obj.setKS_IP(cursor.getString(cursor.getColumnIndexOrThrow("KS_IP")));
                    obj.setKS_BASEDT(cursor.getString(cursor.getColumnIndexOrThrow("KS_BASEDT")));
                    obj.setKS_PABASE(cursor.getString(cursor.getColumnIndexOrThrow("KS_PABASE")));
                    obj.setKS_CVESUCSINFONIA(cursor.getInt(cursor.getColumnIndexOrThrow("KS_CVESUCSINFONIA")));
                    obj.setKS_IP_DEV(cursor.getString(cursor.getColumnIndexOrThrow("KS_IP_DEV")));
                    obj.setKS_PABASE_DEV(cursor.getString(cursor.getColumnIndexOrThrow("KS_PABASE_DEV")));
                    listasucursales.add(obj);
                }
                cursor.close();
                return listasucursales;
            }
        } catch (Exception e) {
            Log.e("GetListaSucursales", e.toString());
            return null;
        }
        return null;
    }
}