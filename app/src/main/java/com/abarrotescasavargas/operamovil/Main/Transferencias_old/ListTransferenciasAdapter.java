package com.abarrotescasavargas.operamovil.Main.Transferencias_old;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.abarrotescasavargas.operamovil.R;

import java.util.List;

public class ListTransferenciasAdapter extends RecyclerView.Adapter<ListTransferenciasAdapter.ViewHolder> {
    private List<listTransferencias> mData;
    private final LayoutInflater mInflater;
    private static Context context = null;
    static OnItemClickListener listener = null;

    public interface OnItemClickListener {
        void onItemClick(listTransferencias item);
    }
    public ListTransferenciasAdapter(List<listTransferencias> listTransferencias, Context context, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        ListTransferenciasAdapter.context = context;
        ListTransferenciasAdapter.listener = listener;
        mData = listTransferencias;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detalle_pmalmacen, parent, false); // la vista que va a mostrar
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //  holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition)); // asigna la animacion
        holder.bindData(mData.get(position));
    }

    public void setItems(List<listTransferencias> items) {
        mData = items;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView folio, recibe, envia;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.imvDocumento);
            folio = itemView.findViewById(R.id.txtvEvidencia);
            envia = itemView.findViewById(R.id.txtvDescripcion);
            //recibe = itemView.findViewById(R.id.sucursalRecibe);
            // hora_recoleccion = itemView.findViewById(R.id.recoleccion);
            cardView = itemView.findViewById(R.id.cv);
        }

        void bindData(final listTransferencias item) {
            //folio.setText(item.getFolio());
            folio.setText(context.getString(R.string.TD_folio, item.getFolio()));
            envia.setText(context.getString(R.string.TD_sucursal_origen, item.getSucOrigen()));
           /* envia.setText(context.getString(R.string.TD_sucursal_origen, item.getSucursalOrigen()));
            recibe.setText(context.getString(R.string.TD_sucursal_destino, item.getSucursalDestino()));*/
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
