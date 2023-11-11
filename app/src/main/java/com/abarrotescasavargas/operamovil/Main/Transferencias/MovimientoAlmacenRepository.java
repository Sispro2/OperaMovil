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
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;

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
            ResultSet rs = BD_SQL.tabla("select " +
                    "[id_premovimiento_almacen]" +
                    ",[folio]" +
                    ",[observaciones] " +
                    ",[id_sucursal_origen] " +
                    ",[id_sucursal_destino]," +
                    "[subtotal]" +
                    ",[total_neto]" +
                    ",[referencia]" +
                    ",[total_iva]" +
                    ",[total_ieps] from " +
                    "[premovimiento_almacen] where [estatus] = 0 and [id_movimiento_almacen_tipo] = '2' " +
                    "order by [id_premovimiento_almacen] desc;", true, context);
            if (rs != null) {
                dbHelper = new DbHelper(context);
                db = dbHelper.getWritableDatabase();
                String whereClause = "";
                String[] whereArgs = {};
                db.delete(OperaMovilContract.PREMOVIMIENTO_ALMACEN.Table, whereClause, whereArgs);
                db.close();
                do {
                    pma.setID_PREMOVIMIENTO_ALMACEN(rs.getString(1));
                    pma.setFOLIO(rs.getString(2));
                    pma.setOBSERVACIONES(rs.getString(3));
                    pma.setID_SUCURSAL_ORIGEN(rs.getInt(4));
                    pma.setID_SUCURSAL_DESTINO(rs.getInt(5));
                    pma.setSUBTOTAL(rs.getFloat(6));
                    pma.setTOTAL_NETO(rs.getFloat(7));
                    pma.setREFERENCIA(rs.getString(8));
                    pma.setTOTAL_IVA(rs.getFloat(9));
                    pma.setTOTAL_IEPS(rs.getFloat(10));
                    bandera = SetPremovimientoAlmacen(pma, context);
                } while (rs.next());
                rs.close();
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
            configVal.put(OperaMovilContract.PREMOVIMIENTO_ALMACEN.ID_SUCURSAL_DESTINO, pma.getID_SUCURSAL_DESTINO());
            configVal.put(OperaMovilContract.PREMOVIMIENTO_ALMACEN.SUBTOTAL, pma.getSUBTOTAL());
            configVal.put(OperaMovilContract.PREMOVIMIENTO_ALMACEN.TOTAL_NETO, pma.getTOTAL_NETO());
            configVal.put(OperaMovilContract.PREMOVIMIENTO_ALMACEN.REFERENCIA, pma.getREFERENCIA());
            configVal.put(OperaMovilContract.PREMOVIMIENTO_ALMACEN.TOTAL_IVA, pma.getTOTAL_IVA());
            configVal.put(OperaMovilContract.PREMOVIMIENTO_ALMACEN.TOTAL_IEPS, pma.getTOTAL_IEPS());
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
            ResultSet resultSet = BD_SQL.tabla("  select pmad.id_articulo " +
                    ",art.desc_mayoreo" +
                    ",art.desc_super" +
                    ",art.codigo_barras1,art.codigo_barras2" +
                    ",(CASE pmad.es_mayoreo WHEN 1 THEN cantidad / art.relacion_venta ELSE cantidad END) as 'cantidad'" +
                    ", art.clave " +
                    ",(CASE pmad.es_mayoreo WHEN 1 THEN umc.descripcion ELSE umv.descripcion END) AS 'descripcion'" +
                    //", costo_unitario " +
                    ",(CASE pmad.es_mayoreo WHEN 1 THEN costo_unitario * art.relacion_venta ELSE costo_unitario END ) as 'costo_unitario'\n" +
                    " from  [premovimiento_almacen_detalle] pmad left join [articulo] art on pmad.id_articulo= art.id_articulo \n" +
                    " LEFT  JOIN unidad_medida umc ON umc.id_unidad_medida = art.id_unidad_compra \n" +
                    " LEFT  JOIN unidad_medida umv ON  umv.id_unidad_medida = art.id_unidad_venta \n" +
                    " where id_premovimiento_almacen = '" + id_premovimiento_almacen + "' order by art.clave asc;  ", true, context);
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
                    dpm.setCANTIDAD(resultSet.getFloat(6));
                    dpm.setCLAVE(resultSet.getString(7));
                    dpm.setUNIDAD(resultSet.getString(8));
                    dpm.setCOSTO_UNITARIO(resultSet.getFloat(9));
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
        String query = " SELECT count(ID_ARTICULO) as 'EXISTS' FROM DETALLE_TRANSFERENCIA WHERE ID_ARTICULO = '" + dpm.getID_ARTICULO() + "' AND ID_PREMOVIMIENTO_ALMACEN= '" + dpm.getID_PREMOVIMIENTO_ALMACEN() + "' AND CLAVE = '" + dpm.getCLAVE() + "';";
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
                configVal.put(OperaMovilContract.DETALLE_TRANSFERENCIA.COSTO_UNITARIO, dpm.getCOSTO_UNITARIO());
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
                    ",ifnull(CANTIDAD,0) AS 'CANTIDAD' " +
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

    public static boolean ActualizaStatus(String clave, int id_premovimiento_almacen, int id_articulo, Context context, float recibido) {

        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("RECIBIDO", recibido);
        cv.put("STATUS", "1");
        String[] args = new String[]{clave, String.valueOf(id_premovimiento_almacen), String.valueOf(id_articulo)};
        long res = db.update(OperaMovilContract.DETALLE_TRANSFERENCIA.Table, cv, "CLAVE=? and ID_PREMOVIMIENTO_ALMACEN=? and ID_ARTICULO=?", args);
        return res > 0;

    }

    public static boolean SetMovimientoAlmacen(String Folio) {

        db = dbHelper.getReadableDatabase();
        int idMovimientoAlmacen = 0;
        String folioPremovimientoAlmacen = "";
        String query = "SELECT " + OperaMovilContract.PREMOVIMIENTO_ALMACEN.OBSERVACIONES + "" +
                ", " + OperaMovilContract.PREMOVIMIENTO_ALMACEN.SUBTOTAL + "" +
                ", " + OperaMovilContract.PREMOVIMIENTO_ALMACEN.TOTAL_NETO + "" +
                "," + OperaMovilContract.PREMOVIMIENTO_ALMACEN.ID_SUCURSAL_ORIGEN + "" +
                "," + OperaMovilContract.PREMOVIMIENTO_ALMACEN.ID_SUCURSAL_DESTINO + " " +
                "," + OperaMovilContract.PREMOVIMIENTO_ALMACEN.TOTAL_IVA + "" +
                "," + OperaMovilContract.PREMOVIMIENTO_ALMACEN.TOTAL_IEPS + " " +
                "," + OperaMovilContract.PREMOVIMIENTO_ALMACEN.REFERENCIA + " " +
                "," + OperaMovilContract.PREMOVIMIENTO_ALMACEN.ID_PREMOVIMIENTO_ALMACEN + "  FROM PREMOVIMIENTO_ALMACEN" +
                " where " + OperaMovilContract.PREMOVIMIENTO_ALMACEN.FOLIO + " = '" + Folio + "'; ";

        Cursor cursor = db.rawQuery(query, null);
        String _observaciones = "";
        float _subtotal = 0;
        float _totalNeto = 0;
        int _sucursalOrigen = 0;
        int _sucursalDestino = 0;
        float _totalIva = 0;
        float _totalIeps = 0;
        String _referencia = "";
        int _idPremovimientoAlmacen = 0;
        try {
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        _observaciones = cursor.getString(0);
                        _subtotal = cursor.getFloat(1);
                        _totalNeto = cursor.getFloat(2);
                        _sucursalOrigen = cursor.getInt(3);
                        _sucursalDestino = cursor.getInt(4);
                        _totalIva = cursor.getFloat(5);
                        _totalIeps = cursor.getFloat(6);
                        _referencia = cursor.getString(7);
                        _idPremovimientoAlmacen = cursor.getInt(8);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        boolean Continua = false;
        if (_idPremovimientoAlmacen > 0) {
            try {
                SucursalRepository sucursalRepository = new SucursalRepository(context);
//                String aux= "EXEC [dbo].[spGuardarMovAlmApp ] " +
//                        "2" +
//                        ",2" +
//                        ",'" + _observaciones + "'" +
//                        ", '" + _subtotal + "'" +
//                        ", '" + _totalNeto + "'" +
//                        "," + sucursalRepository.GetDetalleSucursal().getKS_IDUSUARIO() +
//                        ", '" + _sucursalOrigen + "'" +
//                        ", '" + _sucursalDestino + "'" +
//                        ", '" + _totalIva + "'" +
//                        ", '" + _totalIeps + "'" +
//                        ", '" + _referencia + "' ;";
                ResultSet resultSet = BD_SQL.tabla("EXEC [dbo].[spGuardarMovAlmApp ] " +
                        "2" +
                        ",2" +
                        ",'" + _observaciones + "'" +
                        ", '" + _subtotal + "'" +
                        ", '" + _totalNeto + "'" +
                        "," + sucursalRepository.GetDetalleSucursal().getKS_IDUSUARIO() +
                        ", '" + _sucursalOrigen + "'" +
                        ", '" + _sucursalDestino + "'" +
                        ", '" + _totalIva + "'" +
                        ", '" + _totalIeps + "'" +
                        ", '" + _referencia + "' ;", true, context);

                if (resultSet != null) {
                    do {
                        idMovimientoAlmacen = resultSet.getInt(1);
                        folioPremovimientoAlmacen = resultSet.getString(2);
                        Continua = true;
                    } while (resultSet.next());
                    resultSet.close();
                }
            } catch (Exception e) {
                Log.e("SetMovimientoAlmacen", e.toString());
            }
        }
        return GuardaMovimientoAlmacen(_idPremovimientoAlmacen, Continua, idMovimientoAlmacen);
    }

    private static boolean GuardaMovimientoAlmacen(int idPremovimientoAlmacen, boolean Continua, int idMovimientoAlmacen) {

        boolean bandera = false;
        Cursor cursor;
        String query = "";
        // Inserta el detalle
        try {
            db = dbHelper.getReadableDatabase();
            String _clave = "";
            float _recibido = 0;
            float _costoUnitario = 0;
            String _unidad = "";
            if (Continua) {
                query = " SELECT CLAVE, RECIBIDO, COSTO_UNITARIO, UNIDAD from DETALLE_TRANSFERENCIA  where ID_PREMOVIMIENTO_ALMACEN = '" + idPremovimientoAlmacen + "'; ";
                cursor = db.rawQuery(query, null);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            _clave = cursor.getString(0);
                            _recibido = cursor.getFloat(1);
                            _costoUnitario = cursor.getFloat(2);
                            _unidad = cursor.getString(3);
                            String aux = "EXEC [dbo].[spGuardarMADAplicacion  ] '" + idMovimientoAlmacen + "' ,'" + _clave + "', '" + _recibido + "', '" + _costoUnitario + "', '" + _unidad + "' ; ";
                            ResultSet resultSet = BD_SQL.tabla("EXEC [dbo].[spGuardarMADAplicacion] '" + idMovimientoAlmacen + "' ,'" + _clave + "', '" + _recibido + "', '" + _costoUnitario + "', '" + _unidad + "' ; ", true, context);
                            if (resultSet != null) {
                                resultSet.close();
                                bandera = true;
                            }
                            cursor.moveToNext();
                        }
                    }
                    cursor.close();
                }
                BorraPremovimientoAlmacenSqlLite(idPremovimientoAlmacen);
            }

        } catch (Exception e) {
            Log.e("SetMovimientoAlmacen", e.toString());
        }
        return bandera;
    }

    private static void BorraPremovimientoAlmacenSqlLite(int idPremovimientoAlmacen) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        String whereClause = "ID_PREMOVIMIENTO_ALMACEN=?";
        String[] whereArgs = {String.valueOf(idPremovimientoAlmacen)};
        db.delete(OperaMovilContract.PREMOVIMIENTO_ALMACEN.Table, whereClause, whereArgs);
        db.delete(OperaMovilContract.DETALLE_TRANSFERENCIA.Table, whereClause, whereArgs);
    }


}

