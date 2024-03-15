package com.abarrotescasavargas.operamovil.Main.Transferencias.Detalle;

public class OBJTransferenciaDetalleAdapter {
    private String clave;
    private String descripcion;
    private int conteo;
    private float cantidad;
    private boolean diferencias;

    public OBJTransferenciaDetalleAdapter(String clave, String descripcion,float cantidad,boolean diferencias) {
        this.clave = clave;
        this.diferencias =  false;
        this.descripcion = descripcion;
        this.cantidad= cantidad;
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
        return diferencias;
    }

    public void setDiferencias(boolean diferencias) {
        this.diferencias = diferencias;
    }
}
