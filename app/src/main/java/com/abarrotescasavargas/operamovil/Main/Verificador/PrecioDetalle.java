package com.abarrotescasavargas.operamovil.Main.Verificador;

public class PrecioDetalle {
    private String cantidad;
    private String precio;
    private Boolean oferta;
    public PrecioDetalle(String cantidad, String precio,Boolean oferta) {
        this.cantidad = cantidad;
        this.precio = precio;
        this.oferta = oferta;
    }

    public String getCantidad() {
        return cantidad;
    }

    public Boolean getOferta() {
        return oferta;
    }

    public void setOferta(Boolean oferta) {
        this.oferta = oferta;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }






}
