package com.abarrotescasavargas.operamovil.Main.BaseDatos;

import android.provider.BaseColumns;

public class OperaMovilContract {
    private OperaMovilContract() {
    }

    public static class KSUCURSALES implements BaseColumns {
        public static final String Table = "KSUCURSALES";
        public static final String KS_CVESUC = "KS_CVESUC";
        public static final String KS_NOMSUC = "KS_NOMSUC";
        public static final String KS_IP = "KS_IP";
        public static final String KS_BASEDT = "KS_BASEDT";
        public static final String KS_PABASE = "KS_PABASE";
        public static final String KS_CVESUCSINFONIA = "KS_CVESUCSINFONIA";
        public static final String KS_IP_DEV = "KS_IP_DEV";
        public static final String KS_PABASE_DEV = "KS_PABASE_DEV";
    }

    public static class SUCURSAL implements BaseColumns {
        public static final String Table = "SUCURSAL";
        public static final String CVESUC = "KS_CVESUC";
        public static final String NOMSUC = "KS_NOMSUC";
        public static final String IP = "KS_IP";
        public static final String BASEDT = "KS_BASEDT";
        public static final String PABASE = "KS_PABASE";
        public static final String CVESUCSINFONIA = "KS_CVESUCSINFONIA";
        public static final String IP_DEV = "KS_IP_DEV";
        public static final String PABASE_DEV = "KS_PABASE_DEV";
        public static final String CVETRA = "KS_CVETRA";
        public static final String ANDRID = "KS_IMEI";
        public static  final String ID_USUARIO = "KS_USUARIO";
    }

    public static class PREMOVIMIENTO_ALMACEN implements BaseColumns {
        public static final String Table = "PREMOVIMIENTO_ALMACEN";
        public static final String ID_PREMOVIMIENTO_ALMACEN = "ID_PREMOVIMIENTO_ALMACEN";
        public static final String FOLIO = "FOLIO";
        public static final String OBSERVACIONES = "OBSERVACIONES";
        public static final String ID_SUCURSAL_ORIGEN = "ID_SUCURSAL_ORIGEN";
        public static final String ID_SUCURSAL_DESTINO = "ID_SUCURSAL_DESTINO";
        public static final String SUBTOTAL = "SUBTOTAL";
        public static final String TOTAL_NETO = "TOTAL_NETO";
        public static final String REFERENCIA = "REFERENCIA";
        public static final String TOTAL_IVA = "TOTAL_IVA";
        public static final String TOTAL_IEPS = "TOTAL_IEPS";
        public  static final String FECHA_REGISTRO= "FECHA_REGISTRO";
       // public static final String COSTO_UNITARIO = "COSTO_UNITARIO";
    }

    public static class DETALLE_TRANSFERENCIA implements BaseColumns {
        public static final String Table = "DETALLE_TRANSFERENCIA";
        public static final String ID_ARTICULO = "ID_ARTICULO";
        public static final String ID_PREMOVIMIENTO_ALMACEN = "ID_PREMOVIMIENTO_ALMACEN";
        public static final String DESC_MAYOREO = "DESC_MAYOREO";
        public static final String DESC_SUPER = "DESC_SUPER";
        public static final String CODIGO_BARRAS1 = "CODIGO_BARRAS1";
        public static final String CODIGO_BARRAS2 = "CODIGO_BARRAS2";
        public static final String CANTIDAD = "CANTIDAD";
        public static final String RECIBIDO = "RECIBIDO";
        public static final String CLAVE = "CLAVE";
        public static final String UNIDAD = "UNIDAD";
        public static final String STATUS = "STATUS";
        public static final String COSTO_UNITARIO = "COSTO_UNITARIO";
        public static final String ENVIADO = "ENVIADO";
    }

    public static class REZAGADOS implements BaseColumns {
        public static final String Table = "REZAGADOINI";
        public static final String RI_CVEART = "RI_CVEART";
        public static final String RI_NOMART = "RI_NOMART";
        public static final String RI_FECDAT = "RI_FECDAT";
        public static final String RI_DIASVT = "RI_DIASVT";
        public static final String RI_EXISTE = "RI_EXISTE";
        public static final String RI_URLWEB = "RI_URLWEB";
    }

    public static class ARTICULO implements BaseColumns {
        public static final String Table = "ARTICULO";
        public static final String NA_CVEART = "NA_CVEART";
        public static final String NA_NOMART = "NA_NOMART";
        public static final String NA_FECALT = "NA_FECALT";
        public static final String NA_EVIDEN = "NA_EVIDEN";
        public static final String NA_LINNEG = "NA_LINNEG";
        public static final String NA_EXISTE = "NA_EXISTE";
    }

    public static class CONCURSO_VENTAS implements BaseColumns {
        public static final String Table = "CONCURSO_VENTAS";
        public static final String CV_CONSEC = "CV_CONSEC";
        public static final String CV_TIPOCO = "CV_TIPOCO";
        public static final String CV_FECINI = "CV_FECINI";
        public static final String CV_FECFIN = "CV_FECFIN";
        public static final String CV_CVESUC = "CV_CVESUC";
        public static final String CV_CVEPRO = "CV_CVEPRO";
        public static final String CV_CVEART = "CV_CVEART";
        public static final String CV_VTACAN = "CV_VTACAN";
        public static final String CV_VTAMON = "CV_VTAMON";
        public static final String CV_VTAPTO = "CV_VTAPTO";
        public static final String CV_FECEVI = "CV_FECEVI";
        public static final String CV_EVIDEN = "CV_EVIDEN";

    }
}
