package com.abarrotescasavargas.operamovil.Main.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5   ;
    private static final String DATABASE_NOMBRE = "OperaMovil.db";
    Context context;

    static String Sucursal = "CREATE TABLE " + OperaMovilContract.SUCURSAL.Table + " (" +
            OperaMovilContract.SUCURSAL._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            OperaMovilContract.SUCURSAL.CVESUC + " TEXT NOT NULL," +
            OperaMovilContract.SUCURSAL.NOMSUC + " TEXT NOT NULL," +
            OperaMovilContract.SUCURSAL.IP + " TEXT NOT NULL," +
            OperaMovilContract.SUCURSAL.BASEDT + " TEXT NOT NULL," +
            OperaMovilContract.SUCURSAL.PABASE + " TEXT NOT NULL," +
            OperaMovilContract.SUCURSAL.CVESUCSINFONIA + " INTEGER NOT NULL," +
            OperaMovilContract.SUCURSAL.IP_DEV + " TEXT NOT NULL," +
            OperaMovilContract.SUCURSAL.PABASE_DEV + " TEXT NOT NULL,"+
            OperaMovilContract.SUCURSAL.CVETRA+" TEXT , " +
            OperaMovilContract.SUCURSAL.ANDRID +" TEXT , "+OperaMovilContract.SUCURSAL.ID_USUARIO+" INTEGER  ); ";

    static String Sucursales = "CREATE TABLE " + OperaMovilContract.KSUCURSALES.Table + " (" +
            OperaMovilContract.KSUCURSALES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            OperaMovilContract.KSUCURSALES.KS_CVESUC + " TEXT NOT NULL," +
            OperaMovilContract.KSUCURSALES.KS_NOMSUC + " TEXT NOT NULL," +
            OperaMovilContract.KSUCURSALES.KS_IP + " TEXT NOT NULL," +
            OperaMovilContract.KSUCURSALES.KS_BASEDT + " TEXT NOT NULL," +
            OperaMovilContract.KSUCURSALES.KS_PABASE + " TEXT NOT NULL," +
            OperaMovilContract.KSUCURSALES.KS_CVESUCSINFONIA + " INTEGER NOT NULL," +
            OperaMovilContract.KSUCURSALES.KS_IP_DEV + " TEXT NOT NULL," +
            OperaMovilContract.KSUCURSALES.KS_PABASE_DEV + " TEXT NOT NULL); ";

    static String PREMOVIMIENTO_ALMACEN= "CREATE TABLE "+OperaMovilContract.PREMOVIMIENTO_ALMACEN.Table +"(" +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN.ID_PREMOVIMIENTO_ALMACEN + " TEXT NOT NULL," +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN.FOLIO + " TEXT NOT NULL," +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN.OBSERVACIONES + " TEXT NOT NULL," +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN.ID_SUCURSAL_ORIGEN + " INTEGER NOT NULL, " +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN.ID_SUCURSAL_DESTINO + " INTEGER NOT NULL, " +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN.SUBTOTAL + " FLOAT NOT NULL, " +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN.TOTAL_NETO + " FLOAT NOT NULL, " +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN.REFERENCIA + " TEXT NOT NULL, " +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN.TOTAL_IVA + " FLOAT NOT NULL, " +
            OperaMovilContract.PREMOVIMIENTO_ALMACEN.TOTAL_IEPS + " FLOAT NOT NULL " +
            "); ";

    static String DETALLE_TRANSFERENCIA= "CREATE TABLE "+OperaMovilContract.DETALLE_TRANSFERENCIA.Table +"(" +
            OperaMovilContract.DETALLE_TRANSFERENCIA._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.ID_ARTICULO + " TEXT NOT NULL," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.ID_PREMOVIMIENTO_ALMACEN + " TEXT NOT NULL," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.DESC_MAYOREO + " TEXT NOT NULL," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.DESC_SUPER + " TEXT NOT NULL," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.CODIGO_BARRAS1 + " TEXT NOT NULL," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.CODIGO_BARRAS2 + " TEXT NOT NULL," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.CANTIDAD + " FLOAT NOT NULL," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.RECIBIDO + " TEXT ," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.CLAVE + " TEXT NOT NULL," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.UNIDAD + " TEXT NOT NULL ," +
            OperaMovilContract.DETALLE_TRANSFERENCIA.STATUS + " INTEGER NOT NULL, " +
            OperaMovilContract.DETALLE_TRANSFERENCIA.COSTO_UNITARIO + " FLOAT NOT NULL " +
            ");";
    static String ARTICULOS= "CREATE TABLE "+OperaMovilContract.ARTICULO.Table +"(" +
            OperaMovilContract.ARTICULO._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            OperaMovilContract.ARTICULO.NA_CVEART + " TEXT NOT NULL," +
            OperaMovilContract.ARTICULO.NA_NOMART + " TEXT NOT NULL," +
            OperaMovilContract.ARTICULO.NA_FECALT + " TEXT NOT NULL," +
            OperaMovilContract.ARTICULO.NA_EVIDEN + " TEXT ," +
            OperaMovilContract.ARTICULO.NA_LINNEG + " TEXT NOT NULL," +
            OperaMovilContract.ARTICULO.NA_EXISTE + " TEXT  ); ";

    static  String REZAGADOINI = "CREATE TABLE "+OperaMovilContract.REZAGADOS.Table +"(" +
            OperaMovilContract.REZAGADOS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            OperaMovilContract.REZAGADOS.RI_CVEART + " TEXT NOT NULL," +
            OperaMovilContract.REZAGADOS.RI_DIASVT + " TEXT NOT NULL," +
            OperaMovilContract.REZAGADOS.RI_EXISTE + " TEXT NOT NULL," +
            OperaMovilContract.REZAGADOS.RI_FECDAT + " TEXT NOT NULL," +
            OperaMovilContract.REZAGADOS.RI_NOMART + " TEXT NOT NULL," +
            OperaMovilContract.REZAGADOS.RI_URLWEB + " TEXT  ); ";

    static String CONCURSO_VENTAS = "CREATE TABLE " + OperaMovilContract.CONCURSO_VENTAS.Table + "(" +
            OperaMovilContract.CONCURSO_VENTAS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_CONSEC + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_CVEART + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_TIPOCO + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_FECINI + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_FECFIN + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_CVESUC + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_CVEPRO + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_VTACAN + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_VTAMON + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_VTAPTO + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_FECEVI + " TEXT," +
            OperaMovilContract.CONCURSO_VENTAS.CV_EVIDEN + " TEXT);";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Sucursal);
        db.execSQL(Sucursales);
        db.execSQL(PREMOVIMIENTO_ALMACEN);
        db.execSQL(DETALLE_TRANSFERENCIA);
        db.execSQL(REZAGADOINI);
        db.execSQL(ARTICULOS);
        db.execSQL(CONCURSO_VENTAS);
        Fill fill = new Fill(db, context);
        fill.OnCreate();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + OperaMovilContract.ARTICULO.Table);
        db.execSQL("DROP TABLE IF EXISTS " + OperaMovilContract.SUCURSAL.Table);
        db.execSQL("DROP TABLE IF EXISTS " + OperaMovilContract.KSUCURSALES.Table);
        db.execSQL("DROP TABLE IF EXISTS " + OperaMovilContract.PREMOVIMIENTO_ALMACEN.Table);
        db.execSQL("DROP TABLE IF EXISTS " + OperaMovilContract.DETALLE_TRANSFERENCIA.Table);
        db.execSQL("DROP TABLE IF EXISTS " + OperaMovilContract.REZAGADOS.Table);
        db.execSQL("DROP TABLE IF EXISTS " + OperaMovilContract.CONCURSO_VENTAS.Table);
        onCreate(db);
    }
}
