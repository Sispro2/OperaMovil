package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Evidencia implements Parcelable {
    @SerializedName("nombreEvidencia")
    @Expose
    private String nombreEvidencia;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("nombreCorto")
    @Expose
    private String nombreCorto;
    @SerializedName("url")
    @Expose
    private String url;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nombreEvidencia);
        dest.writeString(status);
        dest.writeString(nombreCorto);
        dest.writeString(url);
    }

    protected Evidencia(Parcel in) {
        nombreEvidencia = in.readString();
        status = in.readString();
        nombreCorto = in.readString();
        url = in.readString();
    }

    public static final Creator<Evidencia> CREATOR = new Creator<Evidencia>() {
        @Override
        public Evidencia createFromParcel(Parcel in) {
            return new Evidencia(in);
        }

        @Override
        public Evidencia[] newArray(int size) {
            return new Evidencia[size];
        }
    };

}
