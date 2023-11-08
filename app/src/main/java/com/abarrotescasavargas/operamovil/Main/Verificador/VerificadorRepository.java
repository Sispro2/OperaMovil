package com.abarrotescasavargas.operamovil.Main.Verificador;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abarrotescasavargas.operamovil.Main.BaseDatos.BD_SQL;
import com.abarrotescasavargas.operamovil.Main.FunGenerales.ExpresionesRegulares;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VerificadorRepository {
    public List<PromoDetalle> getPromociones(String SP, List<String> parametros, Context context) {
        List<PromoDetalle> contenido = new ArrayList<>();
        String cadenaConcatenada,cveCompra,desCompra,canCompra,udsCompra,cveRegalo,desRegalo,canRegalo,udsRegalo,cveGeneral,fechaIni,fechaFin=null;
        ResultSet getConsulta = BD_SQL.ejecutaStoredProcedure(SP, parametros);
        try {
            while (getConsulta.next()) {
                cveGeneral=getConsulta.getString("Clave_Promocion");
                cveCompra=getConsulta.getString("Clave_Compra");
                desCompra=getConsulta.getString("Compra_Descripcion");
                canCompra=getConsulta.getString("Cantidad_Compra");
                udsCompra=getConsulta.getString("Unidad_Compra");
                cveRegalo=getConsulta.getString("Clave_Regalo");
                desRegalo=getConsulta.getString("Descripcion_Regalo");
                canRegalo=getConsulta.getString("Cantidad_Regalo");
                udsRegalo=getConsulta.getString("Unidad_regalo");
                fechaFin=ExpresionesRegulares.fecha(getConsulta.getString("vigencia_fin"));
                fechaIni= ExpresionesRegulares.fecha(getConsulta.getString("vigencia_inicio"));
                SpannableString  cveCompraHipervinculo = createClickableText(desCompra, cveCompra, context);
                SpannableString  cveRegaloHipervinculo = createClickableText(desRegalo, cveRegalo, context);
                cadenaConcatenada = "Compra " + canCompra + " " + udsCompra + " de " + cveCompraHipervinculo +"("+cveCompra +") y llevate " + canRegalo + " " + udsRegalo + " de " + cveRegaloHipervinculo +"("+cveCompra +")";

                PromoDetalle row = new PromoDetalle(cveGeneral,cadenaConcatenada,fechaFin,fechaIni);
                contenido.add(row);
            }
            if (contenido.size() == 0)
            {
                cadenaConcatenada="No hay promociones disponibles para este articulo.";
                PromoDetalle row = new PromoDetalle("",cadenaConcatenada,"","");
                contenido.add(row);
            }
            return contenido;
        } catch (SQLException e) {
            Log.v("Error VerificadorRepository", e.toString());
        }
        return null;
    }
    private SpannableString createClickableText(final String text, final String link, final Context context) {
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        };
        spannableString.setSpan(clickableSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    public List<String> getValores(Context context)
    {
        String query="SELECT * FROM articulo ";

        ResultSet data = BD_SQL.tabla(query, true, context);
        List<String> valoresColumna = new ArrayList<>();
        valoresColumna.add(" ");
        try {
            if (data != null) {
                while (data.next()) {
                    String valor = data.getString("clave") +" - " +data.getString("desc_super");
                    valoresColumna.add(valor);
                }
            }
            return valoresColumna;
        } catch (Exception e) {
            return null;
        }

    }
    public ResultSet getDataValor(Context context,String cve)
    {
        String query="SELECT * FROM articulo WHERE clave='"+cve+"' OR codigo_barras1='"+cve+"'";
        ResultSet data = BD_SQL.tabla(query, true, context);
        return data;
    }
    public List<PrecioDetalle> getTablaPrecios(String SP, List<String> parametros) {
        ResultSet getConsulta = BD_SQL.ejecutaStoredProcedure(SP, parametros);
        List<PrecioDetalle> dataPrecios = new ArrayList<>();

        try {
            int cantidadInicio = 0;
            int cantidadFin = 0;
            double precioActual = 0.0;
            double precioAnterior = -1.0;
            Boolean promo = null;

            while (getConsulta.next()) {
                cantidadInicio = getConsulta.getInt("cantidad");
                precioActual = getConsulta.getDouble("PrecioVenta");

                if (precioActual != precioAnterior && precioAnterior != -1.0) {
                    String rango = (cantidadFin) + " a " + (cantidadInicio - 1);
                    PrecioDetalle row = new PrecioDetalle(rango, "$" + String.format("%.1f", precioAnterior),promo);
                    dataPrecios.add(row);
                }

                promo=getConsulta.getBoolean("esOferta");
                cantidadFin = cantidadInicio;
                precioAnterior = precioActual;
            }

            String rango = (cantidadFin) + " o más";
            PrecioDetalle row = new PrecioDetalle(rango, "$" + String.format("%.1f", precioActual),promo);
            dataPrecios.add(row);

            return dataPrecios;
        } catch (SQLException e) {
            Log.v("Error VerificadorRepository", e.toString());
        }
        return null;
    }



}
