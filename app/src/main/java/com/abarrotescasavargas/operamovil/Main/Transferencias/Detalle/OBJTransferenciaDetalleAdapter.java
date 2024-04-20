package com.abarrotescasavargas.operamovil.Main.Transferencias.Detalle;

public class OBJTransferenciaDetalleAdapter {
    private String clave;
    private String descripcion;
    private int conteo;
    private float cantidad;
    private boolean diferencias;
    private String unidad;

    private int cveTrans;
    public OBJTransferenciaDetalleAdapter(String clave, String descripcion,float cantidad,boolean diferencias, String unidad,int cveTrans) {
        this.clave = clave;
        this.diferencias =  false;
        this.descripcion = descripcion;
        this.cantidad= cantidad;
        this.unidad =  unidad;
        this.cveTrans = cveTrans;
    }

    public int getCveTrans() {
        return cveTrans;
    }

    public void setCveTrans(int cveTrans) {
        this.cveTrans = cveTrans;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getClave() {
        return clave;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getConteo() {
        return conteo;
    }

    public void setConteo(int conteo) {
        this.conteo = conteo;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
    public boolean tieneDiferencias() {
        return getCantidad() != getConteo();
    }


    public void setDiferencias(boolean diferencias) {
        this.diferencias = diferencias;
    }
}
