package com.abarrotescasavargas.operamovil.Main.DetalleTransferencias;

public class DetallePremovimiento {

    private int  ID_ARTICULO;
    private String  DESC_MAYOREO;
    private String  DESC_SUPER;
    private String  CODIGO_BARRAS1;
    private String  CODIGO_BARRAS2;
    private float  CANTIDAD;
    private String  RECIBIDO;
    private String  CLAVE;
    private String  UNIDAD;
    private int  STATUS;
    private  int ID_PREMOVIMIENTO_ALMACEN;

    private  float COSTO_UNITARIO;

    public float getCOSTO_UNITARIO() {
        return COSTO_UNITARIO;
    }

    public void setCOSTO_UNITARIO(float COSTO_UNITARIO) {
        this.COSTO_UNITARIO = COSTO_UNITARIO;
    }

    public int getID_PREMOVIMIENTO_ALMACEN() {
        return ID_PREMOVIMIENTO_ALMACEN;
    }

    public void setID_PREMOVIMIENTO_ALMACEN(int ID_PREMOVIMIENTO_ALMACEN) {
        this.ID_PREMOVIMIENTO_ALMACEN = ID_PREMOVIMIENTO_ALMACEN;
    }

    public int getID_ARTICULO() {
        return ID_ARTICULO;
    }

    public void setID_ARTICULO(int ID_ARTICULO) {
        this.ID_ARTICULO = ID_ARTICULO;
    }

    public String getDESC_MAYOREO() {
        return DESC_MAYOREO;
    }

    public void setDESC_MAYOREO(String DESC_MAYOREO) {
        this.DESC_MAYOREO = DESC_MAYOREO;
    }

    public String getDESC_SUPER() {
        return DESC_SUPER;
    }

    public void setDESC_SUPER(String DESC_SUPER) {
        this.DESC_SUPER = DESC_SUPER;
    }

    public String getCODIGO_BARRAS1() {
        return CODIGO_BARRAS1;
    }

    public void setCODIGO_BARRAS1(String CODIGO_BARRAS1) {
        this.CODIGO_BARRAS1 = CODIGO_BARRAS1;
    }

    public String getCODIGO_BARRAS2() {
        return CODIGO_BARRAS2;
    }

    public void setCODIGO_BARRAS2(String CODIGO_BARRAS2) {
        this.CODIGO_BARRAS2 = CODIGO_BARRAS2;
    }

    public float getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(float CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public String getRECIBIDO() {
        return RECIBIDO;
    }

    public void setRECIBIDO(String RECIBIDO) {
        this.RECIBIDO = RECIBIDO;
    }

    public String getCLAVE() {
        return CLAVE;
    }

    public void setCLAVE(String CLAVE) {
        this.CLAVE = CLAVE;
    }

    public String getUNIDAD() {
        return UNIDAD;
    }

    public void setUNIDAD(String UNIDAD) {
        this.UNIDAD = UNIDAD;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }
}
