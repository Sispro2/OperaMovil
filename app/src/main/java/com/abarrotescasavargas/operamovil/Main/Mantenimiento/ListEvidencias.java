package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

import java.io.Serializable;

public class ListEvidencias implements Serializable  {


    private String nombreEvidencia;
    private String status;
    private  String nombreCorto;
    private  String url;

    public ListEvidencias(String nombreEvidencia, String status, String nombreCorto, String url) {
        this.nombreEvidencia = nombreEvidencia;
        this.status = status;
        this.nombreCorto = nombreCorto;
        this.url = url;
    }

    public String getNombreEvidencia() {
        return nombreEvidencia;
    }

    public void setNombreEvidencia(String nombreEvidencia) {
        this.nombreEvidencia = nombreEvidencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
