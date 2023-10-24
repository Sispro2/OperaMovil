package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

import android.content.Context;
import android.graphics.Color;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


import java.util.List;
import java.util.Objects;

public class AdapterEvidenciasMtto extends RecyclerView.Adapter<AdapterEvidenciasMtto.ViewHolder>{

    private List<ListEvidencias> mData;
    private static Context context = null;
    static OnItemClickListener listener = null;
    private  static  final String Status = "Pendiente";

    public interface OnItemClickListener {
        void onItemClick(ListEvidencias item);
    }

    public AdapterEvidenciasMtto(List<ListEvidencias> listEvidencias , Context context, OnItemClickListener listener) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        AdapterEvidenciasMtto.context = context;
        AdapterEvidenciasMtto.listener = listener;
        mData = listEvidencias;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_evidencia_detalle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.cvEvidenciaDetalle.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        //holder.cvEvidenciaDetalle.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition)); // asigna la animacion
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListEvidencias> items) {
        mData = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvEvidenciaDetalle;
        TextView txtvEvidencia, txtvStatus;
        ImageView imvEvidencia;

        ViewHolder(View itemView) {
            super(itemView);
            cvEvidenciaDetalle = itemView.findViewById(R.id.cvEvidenciaDetalle);
            imvEvidencia = itemView.findViewById(R.id.imvEvidencia);
            txtvEvidencia = itemView.findViewById(R.id.txtvEvidencia);
            txtvStatus = itemView.findViewById(R.id.txtvStatus);
        }

        void bindData(final ListEvidencias item) {
                txtvEvidencia.setText(context.getString(R.string.ev_mt_nombre_evidencia,item.getNombreEvidencia()));
                txtvStatus.setText(context.getString(R.string.ev_mt_status_evidencia,item.getStatus()));
            if (Objects.equals(item.getStatus(), Status)) {
                txtvStatus.setTextColor(Color.parseColor("#FF8300"));
                imvEvidencia.setImageResource(R.drawable.documento);
            } else {
                txtvStatus.setTextColor(Color.parseColor("#005CB9"));
                Glide.with(context)
                        .load(item.getUrl())
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)).into(imvEvidencia);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(item));

        }
    }
}
