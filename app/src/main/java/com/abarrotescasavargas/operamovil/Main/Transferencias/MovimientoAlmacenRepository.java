package com.abarrotescasavargas.operamovil.Main.Transferencias;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.abarrotescasavargas.operamovil.Main.BaseDatos.BD_SQL;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.DbHelper;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.OperaMovilContract;
import com.abarrotescasavargas.operamovil.Main.DetalleTransferencias.DetallePremovimiento;
import com.abarrotescasavargas.operamovil.Main.Entidades.PremovimientoAlmacen;

import java.sql.ResultSet;

public class MovimientoAlmacenRepository {
    private static DbHelper dbHelper;
    private static Context context;
    private static SQLiteDatabase db;

    public MovimientoAlmacenRepository(Context context) {
        MovimientoAlmacenRepository.context = context;
        dbHelper = new DbHelper(context);
    }

    public static Cursor GetSetTransferenciasPendientes(Context context) {
        if (GetTransferenciasPendientesSql(context)) {
            try {
                db = dbHelper.getReadableDatabase();
                String query = "SELECT  A.ID_PREMOVIMIENTO_ALMACEN " +
                        ",A.FOLIO" +
                        ",A.OBSERVACIONES " +
                        ",B.KS_NOMSUC " +
                        "FROM PREMOVIMIENTO_ALMACEN A INNER JOIN KSUCURSALES B ON A.ID_SUCURSAL_ORIGEN= B.KS_CVESUC; ";
                return db.rawQuery(query, null);
            } catch (Exception e) {
                Log.e("GetTransferenciasPendientes", e.toString());
                return null;
            }
        } else return null;
    }

    public static boolean GetTransferenciasPendientesSql(Context context) {
        boolean bandera = false;
        try {
            PremovimientoAlmacen pma = new PremovimientoAlmacen();
            ResultSet resultSet = BD_SQL.tabla("select " +
                    "[id_premovimiento_almacen]" +
                    " ,[folio]" +
                    " ,[observaciones] " +
                    ",[id_sucursal_origen] from " +
                    "[premovimiento_almacen] where [estatus] = 0 and [id_movimiento_almacen_tipo] = '2' " +
                    "order by [id_premovimiento_almacen] desc;", true, context);
            if (resultSet != null) {

                dbHelper = new DbHelper(context);
                db = dbHelper.getWritableDatabase();

               /* String whereClause = "ID_MOVIMIENTO_ALMACEN=?";
                String[] whereArgs = {String.valueOf(resultSet.getInt(2))};
                db.delete(OperaMovilContract.PREMOVIMIENTO_ALMACEN.Table, whereClause, whereArgs);*/
                String whereClause = "";
                String[] whereArgs = {};
                db.delete(OperaMovilContract.PREMOVIMIENTO_ALMACEN.Table, whereClause, whereArgs);
                db.close();

                do {
                    pma.setID_PREMOVIMIENTO_ALMACEN(resultSet.getString(1));
                    pma.setFOLIO(resultSet.getString(2));
                    pma.setOBSERVACIONES(resultSet.getString(3));
                    pma.setID_SUCURSAL_ORIGEN(resultSet.getInt(4));
                    bandera = SetPremovimientoAlmacen(pma, context);
                } while (resultSet.next());
                resultSet.close();
            }
            return bandera;
        } catch (Exception e) {
            Log.e("GetTransferenciasPendientesSql", e.toString());
            return bandera;
        }
    }

    private static boolean SetPremovimientoAlmacen(PremovimientoAlmacen pma, Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        try {
            ContentValues configVal = new ContentValues();
            configVal.put(OperaMovilContract.PREMOVIMIENTO_ALMACEN.ID_PREMOVIMIENTO_ALMACEN, pma.getID_PREMOVIMIENTO_ALMACEN());
            configVal.put(OperaMovilContract.PREMOVIMIENTO_ALMACEN.FOLIO, pma.getFOLIO());
            configVal.put(OperaMovilContract.PREMOVIMIENTO_ALMACEN.OBSERVACIONES, pma.getOBSERVACIONES());
            configVal.put(OperaMovilContract.PREMOVIMIENTO_ALMACEN.ID_SUCURSAL_ORIGEN, pma.getID_SUCURSAL_ORIGEN());
            long res = db.insertOrThrow(OperaMovilContract.PREMOVIMIENTO_ALMACEN.Table, null, configVal);
            db.close();
            return res > 0;
        } catch (Exception e) {
            db.close();
            Log.e("SetMovimientoAlmacenDetalle", e.toString());
            return false;
        }
    }

    private static void GetSetDetalleMovimiento(int id_premovimiento_almacen) {
        //boolean bandera = false;
        try {
            DetallePremovimiento dpm = new DetallePremovimiento();
            ResultSet resultSet = BD_SQL.tabla(" select A.id_articulo " +
                    ",B.desc_mayoreo" +
                    ",B.desc_super" +
                    ",B.codigo_barras1" +
                    ",B.codigo_barras2" +
                    ",A.cantidad" +
                    ",B.clave" +
                    ",c.descripcion " +
                    " from [premovimiento_almacen_detalle] A" +
                    " inner join [articulo] B on A.id_articulo= B.id_articulo" +
                    " inner join [unidad_medida] c on A.id_unidad_medida= c.id_unidad_medida" +
                    " where id_premovimiento_almacen = '" + id_premovimiento_almacen + "' order by desc_mayoreo desc; ", true, context);
            if (resultSet != null) {
                dbHelper = new DbHelper(context);
                db = dbHelper.getWritableDatabase();

                String whereClause = "ID_PREMOVIMIENTO_ALMACEN=? and STATUS=?";
                String[] whereArgs = {String.valueOf(id_premovimiento_almacen), "0"};
                db.delete(OperaMovilContract.DETALLE_TRANSFERENCIA.Table, whereClause, whereArgs);

                do {
                    dpm.setID_ARTICULO(resultSet.getInt(1));
                    dpm.setDESC_MAYOREO(resultSet.getString(2));
                    dpm.setDESC_SUPER(resultSet.getString(3));
                    dpm.setCODIGO_BARRAS1(resultSet.getString(4));
                    dpm.setCODIGO_BARRAS2(resultSet.getString(5));
                    dpm.setCANTIDAD(resultSet.getString(6));
                    dpm.setCLAVE(resultSet.getString(7));
                    dpm.setUNIDAD(resultSet.getString(8));
                    dpm.setSTATUS(0);
                    dpm.setID_PREMOVIMIENTO_ALMACEN(id_premovimiento_almacen);
                    // bandera = SetDetalle(dpm, context);
                    SetDetalle(dpm, context);
                } while (resultSet.next());
                resultSet.close();
            }
            //return bandera;
        } catch (Exception e) {
            Log.e("GetTransferenciasPendientesSql", e.toString());
            //return bandera;
        }
    }

    private static void SetDetalle(DetallePremovimiento dpm, Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();

        int existe = 0;
        String query = " SELECT count(ID_ARTICULO) as 'EXISTS' FROM DETALLE_TRANSFERENCIA WHERE ID_ARTICULO = '"+ dpm.getID_ARTICULO()+"' AND ID_PREMOVIMIENTO_ALMACEN= '"+dpm.getID_PREMOVIMIENTO_ALMACEN()+"' AND CLAVE = '"+dpm.getCLAVE()+"';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    existe = cursor.getInt(0);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }

        if (existe == 0) {
            try {
                ContentValues configVal = new ContentValues();
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.ID_ARTICULO, dpm.getID_ARTICULO());
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.ID_PREMOVIMIENTO_ALMACEN, dpm.getID_PREMOVIMIENTO_ALMACEN());
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.DESC_MAYOREO, dpm.getDESC_MAYOREO());
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.DESC_SUPER, dpm.getDESC_SUPER());
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.CODIGO_BARRAS1, dpm.getCODIGO_BARRAS1());
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.CODIGO_BARRAS2, dpm.getCODIGO_BARRAS2());
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.CANTIDAD, dpm.getCANTIDAD());
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.CLAVE, dpm.getCLAVE());
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.UNIDAD, dpm.getUNIDAD());
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.STATUS, dpm.getSTATUS());
                long res = db.insertOrThrow(OperaMovilContract.DETALLE_TRANSFERENCIA.Table, null, configVal);
                db.close();
                //return res > 0;
            } catch (Exception e) {
                db.close();
                Log.e("SetMovimientoAlmacenDetalle", e.toString());
            }
        }
    }

    public static Cursor GetDetalleTransferencia(int id_premovimiento_almacen) {
        try {
            GetSetDetalleMovimiento(id_premovimiento_almacen);
            db = dbHelper.getReadableDatabase();
            String query = "SELECT  ID_ARTICULO" +
                    ",ID_PREMOVIMIENTO_ALMACEN" +
                    ",DESC_MAYOREO" +
                    ",DESC_SUPER" +
                    ",CODIGO_BARRAS1" +
                    ",CODIGO_BARRAS2" +
                    ",ifnull(RECIBIDO,0) AS 'CANTIDAD' " +
                    ",CLAVE" +
                    ",UNIDAD," +
                    " STATUS " +
                    "from DETALLE_TRANSFERENCIA where ID_PREMOVIMIENTO_ALMACEN = '" + id_premovimiento_almacen + "'  order by STATUS asc, CLAVE ASC ; ";
            //"from DETALLE_TRANSFERENCIA where ID_PREMOVIMIENTO_ALMACEN = '"+id_premovimiento_almacen+"' AND STATUS= '0'; ";
            return db.rawQuery(query, null);
        } catch (Exception e) {
            Log.e("GetTransferenciasPendientes", e.toString());
            return null;
        }
    }

    public static boolean ActualizaStatus(String clave, int id_premovimiento_almacen, int id_articulo, Context context, String recibido) {

        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("RECIBIDO", recibido);
        cv.put("STATUS", "1");
        String[] args = new String[]{clave, String.valueOf(id_premovimiento_almacen), String.valueOf(id_articulo)};
        long res = db.update(OperaMovilContract.DETALLE_TRANSFERENCIA.Table, cv, "CLAVE=? and ID_PREMOVIMIENTO_ALMACEN=? and ID_ARTICULO=?", args);
        return res > 0;

    }

}

