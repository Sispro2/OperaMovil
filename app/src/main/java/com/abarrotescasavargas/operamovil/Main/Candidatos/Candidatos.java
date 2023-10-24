package com.abarrotescasavargas.operamovil.Main.Candidatos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Candidatos implements Parcelable {

    @SerializedName("idReclutamiento")
    @Expose
    private String idReclutamiento;
    @SerializedName("nombre")
    @Expose
    private  String nombre;
    @SerializedName("puesto")
    @Expose
    private  String puesto;
    @SerializedName("fechRegistro")
    @Expose
    private String fechRegistro;
    @SerializedName("estatus")
    @Expose
    private int estatus;

    public String getIdReclutamiento() {
        return idReclutamiento;
    }

    public void setIdReclutamiento(String idReclutamiento) {
        this.idReclutamiento = idReclutamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getFechRegistro() {
        return fechRegistro;
    }

    public void setFechRegistro(String fechRegistro) {
        this.fechRegistro = fechRegistro;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(idReclutamiento);
        dest.writeString(nombre);
        dest.writeString(puesto);
        dest.writeString(fechRegistro);
        dest.writeInt(estatus);
    }
    protected Candidatos(Parcel in) {
        idReclutamiento = in.readString();
        nombre = in.readString();
        puesto = in.readString();
        fechRegistro = in.readString();
        estatus = in.readInt();
    }

    public static final Creator<Candidatos> CREATOR = new Creator<Candidatos>() {
        @Override
        public Candidatos createFromParcel(Parcel in) {
            return new Candidatos(in);
        }

        @Override
        public Candidatos[] newArray(int size) {
            return new Candidatos[size];
        }
    };

}
