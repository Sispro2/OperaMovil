package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseReporteMantto {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("reportes")
    @Expose
    private ArrayList<ReporteMantenimiento> reportesMantenimiento;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ReporteMantenimiento> getReportesMantenimiento() {
        return reportesMantenimiento;
    }

    public void setReportesMantenimiento(ArrayList<ReporteMantenimiento> reportesMantenimiento) {
        this.reportesMantenimiento = reportesMantenimiento;
    }
}
