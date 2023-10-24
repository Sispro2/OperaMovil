package com.abarrotescasavargas.operamovil.Main.Modelos;

public class RespuestaWS {
    private boolean status;
    private String message;
    private String header;

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }

}
