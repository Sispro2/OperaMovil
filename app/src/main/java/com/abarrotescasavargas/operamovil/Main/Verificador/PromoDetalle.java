package com.abarrotescasavargas.operamovil.Main.Verificador;

public class PromoDetalle {
    private String cve;
    private String descripcion;
    private  String fechaIni;
    private String fechaFin;

    public PromoDetalle(String cve, String descripcion,String fechaFin, String fechaIni) {
        this.cve = cve;
        this.descripcion = descripcion;
        this.fechaFin=fechaFin;
        this.fechaIni=fechaIni;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCve() {
        return cve;
    }

    public void setCve(String cve) {
        this.cve = cve;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
