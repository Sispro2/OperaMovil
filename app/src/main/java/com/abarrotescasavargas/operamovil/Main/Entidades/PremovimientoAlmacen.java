package com.abarrotescasavargas.operamovil.Main.Entidades;

public class PremovimientoAlmacen {

    private String  ID_PREMOVIMIENTO_ALMACEN;
    private String  FOLIO;
    private String  OBSERVACIONES;
    private int  ID_SUCURSAL_ORIGEN;

    public String getID_PREMOVIMIENTO_ALMACEN() {
        return ID_PREMOVIMIENTO_ALMACEN;
    }

    public void setID_PREMOVIMIENTO_ALMACEN(String ID_PREMOVIMIENTO_ALMACEN) {
        this.ID_PREMOVIMIENTO_ALMACEN = ID_PREMOVIMIENTO_ALMACEN;
    }

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

    public void setID_SUCURSAL_ORIGEN(int ID_SUCURSAL_ORIGEN) {
        this.ID_SUCURSAL_ORIGEN = ID_SUCURSAL_ORIGEN;
    }
}
