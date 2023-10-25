package com.abarrotescasavargas.operamovil.Main;

public class Respuesta {
    /*Variables*/
    private boolean status;
    private int codigo;
    private String message;
    private String header;
    private String comensales;
    /*Variables*/


    /*Setters y Getters*/
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComensales() {
        return comensales;
    }

    public void setComensales(String comensales) {
        this.comensales = comensales;
    }
    /*Setters y Getters*/
}
