package com.abarrotescasavargas.operamovil.Main.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLite extends SQLiteOpenHelper {

    public AdminSQLite(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // aqui se crean las tablas
        db.execSQL("create table Articulos (clave,codigoBarras1,codigoBarras2,descripcion)");

        db.execSQL("create table prueba (proveedor,fecha,HoraEntre,clavePro,numFactura,placas,nomTransportista,nomRecibido,observaciones,sqlRFCPRO, sqlFOLFIS, sqlIMPORT)");

        db.execSQL("create table transferencia (id_articulo , cantidad_Contada , nombreArt,unidadRealMedida,idTransferencia)");
        // pintar la tabla //
        db.execSQL("create table pintarTabla (claveArt,id_articulo)");



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if Exists Articulos");
        db.execSQL("create table Articulos (clave,codigoBarras1,codigoBarras2,descripcion)");

        db.execSQL("drop table if Exists prueba");
        db.execSQL("create table prueba (proveedor,fecha,HoraEntre,clavePro,numFactura,placas,nomTransportista,nomRecibido,observaciones)");

        db.execSQL("drop table if Exists transferencia");
        db.execSQL("create table transferencia (id_articulo, cantidad_Contada, nombreArt,unidadRealMedida,idTransferencia)");


        db.execSQL("drop table if Exists pintarTabla");
        db.execSQL("create table pintarTabla (claveArt,id_articulo)");


    }

}
