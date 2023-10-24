package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

public class ListReporteMantenimiento {

    private  int IdReporte;
    private  String Categoria;
    private  String SubCategoria;
    private  String Descripcion;
    private String Status;


    public ListReporteMantenimiento(String categoria, String subCategoria, String descripcion, String status, int idReporte) {
        Categoria = categoria;
        SubCategoria = subCategoria;
        Descripcion = descripcion;
        Status = status;
        IdReporte = idReporte;
    }

    public int getIdReporte() {
        return IdReporte;
    }

    public void setIdReporte(int idReporte) {
        IdReporte = idReporte;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getSubCategoria() {
        return SubCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        SubCategoria = subCategoria;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
