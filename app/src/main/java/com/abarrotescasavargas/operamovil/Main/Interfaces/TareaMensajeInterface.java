package com.abarrotescasavargas.operamovil.Main.Interfaces;

public interface TareaMensajeInterface {
    void toggleProgressBar(boolean status);
    void showMessage(String msg);
    void realizaAccion(boolean status);
    void lanzarActividad(Class<?> tipoActividad);
}
