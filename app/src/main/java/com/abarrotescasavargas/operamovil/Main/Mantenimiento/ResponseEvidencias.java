package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseEvidencias {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("evidencias")
    @Expose
    private ArrayList<Evidencia> evidencias;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Evidencia> getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(ArrayList<Evidencia> evidencias) {
        this.evidencias = evidencias;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResponseDocumentos{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", documentos=" + evidencias +
                '}';
    }
}
