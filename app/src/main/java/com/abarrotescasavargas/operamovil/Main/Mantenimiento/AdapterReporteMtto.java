package com.abarrotescasavargas.operamovil.Main.Mantenimiento;

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

public class AdapterReporteMtto extends RecyclerView.Adapter<AdapterReporteMtto.ViewHolder> {

    private List<ListReporteMantenimiento> listReportesMantenimiento;
    private static Context context = null;
    static OnItemClickListener listener = null;


    public interface OnItemClickListener {
        void onItemClick(ListReporteMantenimiento item);
    }

    public AdapterReporteMtto(List<ListReporteMantenimiento> listReportesMantenimiento, Context context, OnItemClickListener listener) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        AdapterReporteMtto.context = context;
        AdapterReporteMtto.listener = listener;
        this.listReportesMantenimiento = listReportesMantenimiento;
    }

    @Override
    public int getItemCount() {
        return listReportesMantenimiento.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detalle_reporte_mantenimiento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //holder.cvReporteMDetalle.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        holder.cvReporteMDetalle.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition)); // asigna la animacion
        holder.bindData(listReportesMantenimiento.get(position));
    }

    public void setItems(List<ListReporteMantenimiento> items) { listReportesMantenimiento = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvReporteMDetalle;
        TextView  txtvCategoria, txtvSubCategoria, txtvDescripcion, txtvStatus;
        ViewHolder(View itemView) {
            super(itemView);
            cvReporteMDetalle = itemView.findViewById(R.id.cvReporteMDetalle);
            txtvCategoria= itemView.findViewById(R.id.txtvEvidencia);
            txtvSubCategoria= itemView.findViewById(R.id.txtvSubCategoria);
            txtvDescripcion= itemView.findViewById(R.id.txtvDescripcion);
            txtvStatus= itemView.findViewById(R.id.txtvStatus);
        }
        void bindData(final ListReporteMantenimiento item) {
            txtvCategoria.setText(context.getString(R.string.mt_categoria, item.getCategoria()));
            txtvSubCategoria.setText(context.getString(R.string.mt_subcategoria, item.getSubCategoria()));
            txtvDescripcion.setText(context.getString(R.string.mt_descripcion, item.getDescripcion()));
            txtvStatus.setText(context.getString(R.string.mt_status, item.getStatus()));
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

}
