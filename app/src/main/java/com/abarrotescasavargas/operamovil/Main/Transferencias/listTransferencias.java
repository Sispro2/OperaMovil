package com.abarrotescasavargas.operamovil.Main.Transferencias;

import java.io.Serializable;

public class listTransferencias implements Serializable {

    private String Folio;
    private String Observaciones;
    private String Origen;
    public  int Id_premovimiento_almacen;



    public listTransferencias(String folio, String observaciones, String origen, int id_premovimiento_almacen) {
        Folio = folio;
        Observaciones = observaciones;
        Origen = origen;
        Id_premovimiento_almacen= id_premovimiento_almacen;
        /*CveDestino = cveDestino;
        Id_Movimiento_Almacen = id_Movimiento_Almacen;*/
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public String getOrigen() {
        return Origen;
    }

    public void setOrigen(String origen) {
        Origen = origen;
    }

    public int getId_premovimiento_almacen() {
        return Id_premovimiento_almacen;
    }

    public void setId_premovimiento_almacen(int id_premovimiento_almacen) {
        Id_premovimiento_almacen = id_premovimiento_almacen;
    }


}
