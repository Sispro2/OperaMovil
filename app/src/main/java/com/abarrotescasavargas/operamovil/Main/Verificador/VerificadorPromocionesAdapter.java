package com.abarrotescasavargas.operamovil.Main.Verificador;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abarrotescasavargas.operamovil.R;

import java.util.List;

public class VerificadorPromocionesAdapter extends RecyclerView.Adapter<VerificadorPromocionesAdapter.PromocionViewHolder> {
    private List<PromoDetalle> listaPrecios;

    public VerificadorPromocionesAdapter(List<PromoDetalle> listaPrecios) {
        this.listaPrecios = listaPrecios;
    }

    @NonNull
    @Override
    public PromocionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_detalle_promociones, parent, false);
        return new PromocionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PromocionViewHolder holder, int position) {
        PromoDetalle promoDetalle = listaPrecios.get(position);
        if (listaPrecios.size()==0)
        {
            setTextWithMarquee(holder.detallePromo,"No hay promociones activas");
            holder.cvePromo.setText("");
            holder.fechaIni.setText("");
            holder.fechaFin.setText("");
        }
        else
        {
            setTextWithMarquee(holder.detallePromo,promoDetalle.getDescripcion());
            holder.cvePromo.setText(promoDetalle.getCve());
            holder.fechaIni.setText(promoDetalle.getFechaIni());
            holder.fechaFin.setText(promoDetalle.getFechaFin());
        }
    }


    @Override
    public int getItemCount() {
        return listaPrecios.size();
    }

    public class PromocionViewHolder extends RecyclerView.ViewHolder {
        public TextView detallePromo,cvePromo,fechaIni,fechaFin;
        public LinearLayout detallePromoFicha;

        public PromocionViewHolder(View view) {
            super(view);
            detallePromoFicha = view.findViewById(R.id.detallePromoFicha);
            detallePromo = view.findViewById(R.id.detallePromo);
            fechaFin = view.findViewById(R.id.fechaFin);
            fechaIni = view.findViewById(R.id.fechaIni);
            cvePromo =view.findViewById(R.id.cvePromo);
        }
    }
    private void setTextWithMarquee(TextView textView, String text) {
        if (textView != null && !TextUtils.isEmpty(text)) {
            textView.setText(text);
            textView.setSelected(true); // Activa el efecto de marquesina
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine(true);
            textView.setMarqueeRepeatLimit(-1); // Configura el límite de repetición a -1 para que sea infinito
            textView.setHorizontallyScrolling(true);
        }
    }

}
