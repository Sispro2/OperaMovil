package com.abarrotescasavargas.operamovil.Main.Candidatos;

import java.io.Serializable;

public class ListCandidatos implements Serializable {

    private String Nombre;
    private String FechaIngreso;
    private String Puesto;
    private String Etapa;
    private String IdReclutamiento;



    public ListCandidatos(String nombre, String fechaIngreso, String puesto, String etapa, String idReclutamiento) {
        Nombre = nombre;
        FechaIngreso = fechaIngreso;
        Puesto = puesto;
        Etapa = etapa;
        IdReclutamiento= idReclutamiento;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        FechaIngreso = fechaIngreso;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String puesto) {
        Puesto = puesto;
    }

    public String getEtapa() {
        return Etapa;
    }

    public void setEtapa(String etapa) {
        Etapa = etapa;
    }
    public String getIdReclutamiento() {
        return IdReclutamiento;
    }

    public void setIdReclutamiento(String idReclutamiento) {
        this.IdReclutamiento = idReclutamiento;
    }
}
