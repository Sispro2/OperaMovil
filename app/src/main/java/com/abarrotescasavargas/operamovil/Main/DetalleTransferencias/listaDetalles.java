package com.abarrotescasavargas.operamovil.Main.DetalleTransferencias;

import java.io.Serializable;

public class listaDetalles implements Serializable {

    private int IdArticulo;
    private  int IdPremovimientoAlmacen;
    private String DescMayoreo;
    private String DescSuper;
    private String CodigoBarras1;
    private String CodigoBarras2;
    private float Cantidad;
    private String Clave;
    private String Unidad;
    private String Status;



    public listaDetalles(int idArticulo, int idPremovimientoAlmacen, String descMayoreo, String descSuper, String codigoBarras1, String codigoBarras2, float cantidad, String clave, String unidad, String recibido) {
        IdArticulo = idArticulo;
        IdPremovimientoAlmacen = idPremovimientoAlmacen;
        DescMayoreo = descMayoreo;
        DescSuper = descSuper;
        CodigoBarras1 = codigoBarras1;
        CodigoBarras2 = codigoBarras2;
        Cantidad = cantidad;
        Clave = clave;
        Unidad = unidad;
        Status = recibido;
    }

    public int getIdArticulo() {
        return IdArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        IdArticulo = idArticulo;
    }

    public int getIdPremovimientoAlmacen() {
        return IdPremovimientoAlmacen;
    }

    public void setIdPremovimientoAlmacen(int idPremovimientoAlmacen) {
        IdPremovimientoAlmacen = idPremovimientoAlmacen;
    }

    public String getDescMayoreo() {
        return DescMayoreo;
    }

    public void setDescMayoreo(String descMayoreo) {
        DescMayoreo = descMayoreo;
    }

    public String getDescSuper() {
        return DescSuper;
    }

    public void setDescSuper(String descSuper) {
        DescSuper = descSuper;
    }

    public String getCodigoBarras1() {
        return CodigoBarras1;
    }

    public void setCodigoBarras1(String codigoBarras1) {
        CodigoBarras1 = codigoBarras1;
    }

    public String getCodigoBarras2() {
        return CodigoBarras2;
    }

    public void setCodigoBarras2(String codigoBarras2) {
        CodigoBarras2 = codigoBarras2;
    }

    public float getCantidad() {
        return Cantidad;
    }

    public void setCantidad(float cantidad) {
        Cantidad = cantidad;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

}