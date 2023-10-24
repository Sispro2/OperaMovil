package com.abarrotescasavargas.operamovil.Main.Candidatos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.abarrotescasavargas.operamovil.R;

import java.util.List;

public class AdapterCandidatos extends RecyclerView.Adapter<AdapterCandidatos.ViewHolder> {
    private List<ListCandidatos> mData;
    private static Context context = null;
    static OnItemClickListener listener = null;

    public interface OnItemClickListener {
        void onItemClick(ListCandidatos item);
    }
    public AdapterCandidatos(List<ListCandidatos> listCandidatos, Context context, OnItemClickListener listener) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        AdapterCandidatos.context = context;
        AdapterCandidatos.listener = listener;
        mData = listCandidatos;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_candidato_detalle, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
          holder.cvCandidatoDetalle.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        //holder.cvCandidatoDetalle.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition)); // asigna la animacion
        holder.bindData(mData.get(position));
    }
    public void setItems(List<ListCandidatos> items) {
        mData = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvCandidatoDetalle;
        TextView txtvNombreCandidato, txtvFechaIngreso, txtvPuestoCandidato, txtvEtapa;
        ViewHolder(View itemView) {
            super(itemView);
            txtvNombreCandidato = itemView.findViewById(R.id.txtvEvidencia);
            txtvFechaIngreso = itemView.findViewById(R.id.txtvSubCategoria);
            txtvPuestoCandidato = itemView.findViewById(R.id.txtvDescripcion);
            txtvEtapa = itemView.findViewById(R.id.txtvStatus);
            cvCandidatoDetalle = itemView.findViewById(R.id.cvCandidatoDetalle);
        }
        void bindData(final ListCandidatos item) {
            txtvNombreCandidato.setText(item.getNombre());
            txtvFechaIngreso.setText(item.getFechaIngreso());
            txtvPuestoCandidato.setText(item.getPuesto());
            txtvEtapa.setText(item.getEtapa());

            //txtvNombreCandidato.setText(item.getFolio());
            //folio.setText(context.getString(R.string.TD_folio, item.getFolio()));
            //envia.setText(context.getString(R.string.TD_sucursal_origen, item.getOrigen()));
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
