package com.abarrotescasavargas.operamovil.Main.Documentos;

import java.io.Serializable;

public class ListDocumentos implements Serializable {
    private String NombreDocumento;
    private String Status;
    private  String Url;
    private  String NombreCorto;

    public ListDocumentos(String nombreDocumento, String status, String url, String nombreCorto) {
        NombreDocumento = nombreDocumento;
        Status = status;
        Url= url;
        NombreCorto= nombreCorto;
    }

    public String getNombreDocumento() {
        return NombreDocumento;
    }
    public String getUrl() {  return Url;   }

    public void setNombreDocumento(String nombreDocumento) {
        NombreDocumento = nombreDocumento;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getNombreCorto() {
        return NombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        NombreCorto = nombreCorto;
    }
}
