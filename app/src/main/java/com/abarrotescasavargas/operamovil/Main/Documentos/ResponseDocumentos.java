package com.abarrotescasavargas.operamovil.Main.Documentos;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseDocumentos {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("documentos")
    @Expose
    private ArrayList<Documentos> documentos;

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

    public ArrayList<Documentos> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(ArrayList<Documentos> documentos) {
        this.documentos = documentos;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResponseDocumentos{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", documentos=" + documentos +
                '}';
    }
}
