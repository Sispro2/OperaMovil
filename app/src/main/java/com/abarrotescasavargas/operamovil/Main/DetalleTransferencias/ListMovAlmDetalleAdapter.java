package com.abarrotescasavargas.operamovil.Main.DetalleTransferencias;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.abarrotescasavargas.operamovil.Main.FunGenerales.Funciones;
import com.abarrotescasavargas.operamovil.R;

import java.util.List;
import java.util.Objects;

public class ListMovAlmDetalleAdapter extends RecyclerView.Adapter<ListMovAlmDetalleAdapter.ViewHolder> {
    private List<listaDetalles> mData;
    private final LayoutInflater mInflater;
    private static Context context = null;
    static OnItemClickListener listener = null;


    public void setFilteredList(List<listaDetalles> filteredList)
    {
        this.mData= filteredList;
        notifyDataSetChanged();
    }

    public ListMovAlmDetalleAdapter(List<listaDetalles> list_Mov_Alm_Detalle, Context context, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        mData = list_Mov_Alm_Detalle;
        ListMovAlmDetalleAdapter.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_mov_alm_detalle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        //holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition)); // asigna la animacion
        holder.bindData(mData.get(position));
    }

    public void setItems(List<listaDetalles> items) {
        mData = items;
    }


    public interface OnItemClickListener {
        void onItemClick(listaDetalles item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtClave, txtCantidad, txtDescripcion, txtStatus;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            txtClave = itemView.findViewById(R.id.txtClave);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            cardView = itemView.findViewById(R.id.cv_articulos);
            txtStatus = itemView.findViewById(R.id.status);
        }

        void bindData(final listaDetalles item) {
            txtClave.setText(context.getString(R.string.DT_clave, item.getClave()));
            setTextWithMarquee(txtDescripcion, item.getDescSuper());
            txtStatus.setText(Objects.equals(item.getStatus(), "0") ? "" : context.getString(R.string.DT_validada));
            if (!Objects.equals(item.getStatus(), "0")) {
                txtCantidad.setText(context.getString(R.string.DT_cantidad, item.getCantidad(), item.getUnidad()));
            }
            else{
                txtCantidad.setText("");
            }
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }


        private  void setTextWithMarquee(TextView textView, String text) {
            if (textView != null && !TextUtils.isEmpty(text)) {
                textView.setText(text);
                textView.setSelected(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setSingleLine(true);
            }
        }

    }
}