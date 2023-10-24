package com.abarrotescasavargas.operamovil.Main.Documentos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Documentos implements Parcelable {
    @SerializedName("nombreDocumento")
    @Expose
    private String nombreDocumento;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("nombreCorto")
    @Expose
    private String nombreCorto;

    protected Documentos(Parcel in) {
        nombreDocumento = in.readString();
        status = in.readString();
        url = in.readString();
        nombreCorto= in.readString();
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    @NonNull
    @Override
    public String toString() {
        return "Documentos{" +
                "nombreDocumento='" + nombreDocumento + '\'' +
                ", status='" + status + '\'' +
                ", url='" + url + '\'' +
                ", nombreCorto='" + nombreCorto + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nombreDocumento);
        dest.writeString(status);
        dest.writeString(url);
        dest.writeString(nombreCorto);
    }

    public static final Creator<Documentos> CREATOR = new Creator<Documentos>() {
        @Override
        public Documentos createFromParcel(Parcel in) {
            return new Documentos(in);
        }

        @Override
        public Documentos[] newArray(int size) {
            return new Documentos[size];
        }
    };

}
