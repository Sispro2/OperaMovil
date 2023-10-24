package com.abarrotescasavargas.operamovil.Main.Login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.abarrotescasavargas.operamovil.Main.BaseDatos.BD_SQL;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.DbHelper;
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;

import java.sql.ResultSet;

public class ValidaCredencialesRepository {
    private static DbHelper dbHelper;
    private static Context context;
    private static SQLiteDatabase db;

    public ValidaCredencialesRepository(Context context) {
        ValidaCredencialesRepository.context = context;
        dbHelper = new DbHelper(context);
    }
    public boolean GetExisteUsuarioPasswordSql(String usuario, String password) {
        try {
            //  Rafa
            Log.d("ALGO", "solo para que distinga un cambio"); 
            ResultSet resultSet = BD_SQL.tabla("select count (id_usuario) as 'existe' " +
                    "from usuario where clave = upper('"+usuario+"') and CONVERT(NVARCHAR(50), " +
                    "DECRYPTBYPASSPHRASE('S4GR4V.C.2018', contrasena)) = '"+password+"' and activo= 1;", true, ValidaCredencialesRepository.context);
            if (resultSet != null) {
                int existe= resultSet.getInt(1);
                resultSet.close();
                if(existe>0){
                    //GuardaSucursalUsuario(cveSuc);
                    GuardaSucursalUsuario(usuario, "XOEIKDFOIDFOODFODF");
                }
                else {
                    // Imprimir un mensaje de error en caso de que no se encuentren registros
                    Log.v("GetExisteUsuarioPasswordSql", "No se encontraron registros que coincidan con las credenciales.");
                    return false;
                }
                return existe>0;
            }
        } catch (Exception e) {
            Log.e("SetSucursal", e.toString());
            return false;
        }
        return false;
    }
    private  void GuardaSucursalUsuario( String cveTra, String IMEI){
        SucursalRepository sucursalRepository = new SucursalRepository(ValidaCredencialesRepository.context);
        sucursalRepository.SetUsuario(ValidaCredencialesRepository.context,cveTra, IMEI);
        //sucursalRepository.SetSucursal(ValidaCredencialesRepository.context, sucursalRepository.GetDetalleSelectedSucursal(cveSuc));
    }
}
