package com.abarrotescasavargas.operamovil.Main.Verificador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.abarrotescasavargas.operamovil.R;
import java.util.List;

public class VerificadorPreciosAdapter extends RecyclerView.Adapter<VerificadorPreciosAdapter.PrecioViewHolder> {

    private List<PrecioDetalle> listaPrecios;


    public VerificadorPreciosAdapter(List<PrecioDetalle> listaPrecios) {
        this.listaPrecios = listaPrecios;
    }

    @NonNull
    @Override
    public PrecioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_detalle_precios, parent, false);
        return new PrecioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrecioViewHolder holder, int position) {
        PrecioDetalle precioDetalle = listaPrecios.get(position);
        holder.precioTextView.setText(precioDetalle.getPrecio());
        holder.compraTextView.setText(precioDetalle.getCantidad());

        boolean tieneOferta = precioDetalle.getOferta();

        if (tieneOferta) {
            holder.fichaPrecios.setBackgroundResource(R.drawable.es_oferta);
        } else {
            holder.fichaPrecios.setBackgroundResource(R.drawable.rounded_edittext_background);
        }
    }
    @Override
    public int getItemCount() {
        if (listaPrecios != null) {
            return listaPrecios.size();
        } else {
            return 0;
        }
    }

    public class PrecioViewHolder extends RecyclerView.ViewHolder {
        public TextView precioTextView,compraTextView;
        public LinearLayout fichaPrecios;
        public PrecioViewHolder(View view) {
            super(view);
            fichaPrecios = itemView.findViewById(R.id.fichaPrecios);
            precioTextView = view.findViewById(R.id.precio);
            compraTextView = view.findViewById(R.id.compra);
        }
    }
}
