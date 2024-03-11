package com.abarrotescasavargas.operamovil.Main.Entidades;

public class PremovimientoAlmacen {

    private String  ID_PREMOVIMIENTO_ALMACEN;
    private String  FOLIO;
    private String  OBSERVACIONES;
    private int  ID_SUCURSAL_ORIGEN;

    private  int ID_SUCURSAL_DESTINO;
    private  float SUBTOTAL;
    private float TOTAL_NETO;
    private String REFERENCIA;
    private  float TOTAL_IVA;
    private float TOTAL_IEPS;
    private String FECHA_REGISTRO;

    public String getFECHA_REGISTRO() {
        return FECHA_REGISTRO;
    }

    public void setFECHA_REGISTRO(String FECHA_REGISTRO) {
        this.FECHA_REGISTRO = FECHA_REGISTRO;
    }




    public int getID_SUCURSAL_DESTINO() {
        return ID_SUCURSAL_DESTINO;
    }

    public void setID_SUCURSAL_DESTINO(int ID_SUCURSAL_DESTINO) {
        this.ID_SUCURSAL_DESTINO = ID_SUCURSAL_DESTINO;
    }

    public float getSUBTOTAL() {
        return SUBTOTAL;
    }

    public void setSUBTOTAL(float SUBTOTAL) {
        this.SUBTOTAL = SUBTOTAL;
    }

    public float getTOTAL_NETO() {
        return TOTAL_NETO;
    }

    public void setTOTAL_NETO(float TOTAL_NETO) {
        this.TOTAL_NETO = TOTAL_NETO;
    }

    public String getREFERENCIA() {
        return REFERENCIA;
    }

    public void setREFERENCIA(String REFERENCIA) {
        this.REFERENCIA = REFERENCIA;
    }

    public float getTOTAL_IVA() {
        return TOTAL_IVA;
    }

    public void setTOTAL_IVA(float TOTAL_IVA) {
        this.TOTAL_IVA = TOTAL_IVA;
    }

    public float getTOTAL_IEPS() {
        return TOTAL_IEPS;
    }

    public void setTOTAL_IEPS(float TOTAL_IEPS) {
        this.TOTAL_IEPS = TOTAL_IEPS;
    }

    public String getID_PREMOVIMIENTO_ALMACEN() {
        return ID_PREMOVIMIENTO_ALMACEN;
    }

    public void setID_PREMOVIMIENTO_ALMACEN(String ID_PREMOVIMIENTO_ALMACEN) {  this.ID_PREMOVIMIENTO_ALMACEN = ID_PREMOVIMIENTO_ALMACEN; }

    public String getFOLIO() {
        return FOLIO;
    }

    public void setFOLIO(String FOLIO) {
        this.FOLIO = FOLIO;
    }

    public String getOBSERVACIONES() {
        return OBSERVACIONES;
    }

    public void setOBSERVACIONES(String OBSERVACIONES) {
        this.OBSERVACIONES = OBSERVACIONES;
    }

    public int getID_SUCURSAL_ORIGEN() {
        return ID_SUCURSAL_ORIGEN;
    }

    public void setID_SUCURSAL_ORIGEN(int ID_SUCURSAL_ORIGEN) {   this.ID_SUCURSAL_ORIGEN = ID_SUCURSAL_ORIGEN;  }
}
