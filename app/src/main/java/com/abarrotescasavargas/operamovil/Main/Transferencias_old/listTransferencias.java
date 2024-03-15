package com.abarrotescasavargas.operamovil.Main.Transferencias_old;

import java.io.Serializable;

public class listTransferencias implements Serializable {
    public int Id_premovimiento_almacen;
    private String Folio;
    private String Observaciones;
    private  String SucOrigen;
    private int IdSucursalOrigen;

    private int IdSucursalDestino;
    private float Subtotal;
    private float TotalNeto;
    private String Referencia;
    private float TotalIva;
    private float TotalIeps;

    public String getSucOrigen() {
        return SucOrigen;
    }

    public void setSucOrigen(String sucOrigen) {
        SucOrigen = sucOrigen;
    }

    public listTransferencias(int id_premovimiento_almacen, String folio, String observaciones, String sucOrigen) {

        Id_premovimiento_almacen = id_premovimiento_almacen;
        Folio = folio;
        Observaciones = observaciones;
        SucOrigen= sucOrigen;
       /* IdSucursalOrigen = idSucursalOrigen;

        IdSucursalDestino = idSucursalDestino;
        Subtotal = subtotal;
        TotalNeto = totalNeto;
        Referencia = referencia;
        TotalIva = totalIva;
        TotalIeps = totalIeps;*/

    }

    public int getIdSucursalDestino() {
        return IdSucursalDestino;
    }

    public void setIdSucursalDestino(int idSucursalDestino) {
        IdSucursalDestino = idSucursalDestino;
    }

    public float getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(float subtotal) {
        Subtotal = subtotal;
    }

    public float getTotalNeto() {
        return TotalNeto;
    }

    public void setTotalNeto(float totalNeto) {
        TotalNeto = totalNeto;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public float getTotalIva() {
        return TotalIva;
    }

    public void setTotalIva(float totalIva) {
        TotalIva = totalIva;
    }

    public float getTotalIeps() {
        return TotalIeps;
    }

    public void setTotalIeps(float totalIeps) {
        TotalIeps = totalIeps;
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

    public int getIdSucursalOrigen() {
        return IdSucursalOrigen;
    }

    public void setIdSucursalOrigen(int idSucursalOrigen) {
        IdSucursalOrigen = idSucursalOrigen;
    }

    public int getId_premovimiento_almacen() {
        return Id_premovimiento_almacen;
    }

    public void setId_premovimiento_almacen(int id_premovimiento_almacen) {
        Id_premovimiento_almacen = id_premovimiento_almacen;
    }


}
