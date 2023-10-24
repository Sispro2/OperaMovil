package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReporteMantenimiento implements Parcelable {

    @SerializedName("idReporte")
    @Expose
    private  int idReporte;
    @SerializedName("categoria")
    @Expose
    private  String categoria;
    @SerializedName("subCategoria")
    @Expose
    private  String subCategoria;
    @SerializedName("descripcion")
    @Expose
    private  String descripcion;
    @SerializedName("status")
    @Expose
    private  String Status;

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    protected ReporteMantenimiento(Parcel in) {
        idReporte = in.readInt();
        categoria = in.readString();
        subCategoria = in.readString();
        descripcion = in.readString();
        Status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idReporte);
        dest.writeString(categoria);
        dest.writeString(subCategoria);
        dest.writeString(descripcion);
        dest.writeString(Status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReporteMantenimiento> CREATOR = new Creator<ReporteMantenimiento>() {
        @Override
        public ReporteMantenimiento createFromParcel(Parcel in) {
            return new ReporteMantenimiento(in);
        }

        @Override
        public ReporteMantenimiento[] newArray(int size) {
            return new ReporteMantenimiento[size];
        }
    };


}
