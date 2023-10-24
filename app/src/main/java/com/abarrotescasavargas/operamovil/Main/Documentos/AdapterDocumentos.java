package com.abarrotescasavargas.operamovil.Main.Documentos;

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

public class AdapterDocumentos extends RecyclerView.Adapter<AdapterDocumentos.ViewHolder> {
    private List<ListDocumentos> mData;
    private static Context context = null;
    static OnItemClickListener listener = null;

    public interface OnItemClickListener {
        void onItemClick(ListDocumentos item);
    }

    public AdapterDocumentos(List<ListDocumentos> listDocumentos, Context context, OnItemClickListener listener) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        AdapterDocumentos.context = context;
        AdapterDocumentos.listener = listener;
        mData = listDocumentos;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_documentos_detalle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.cvDocumentoDetalle.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        //holder.cvDocumentoDetalle.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition)); // asigna la animacion
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListDocumentos> items) {
        mData = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvDocumentoDetalle;
        TextView txtvDocumento, txtvStatus;
        ImageView imvDocumento;

        ViewHolder(View itemView) {
            super(itemView);
            txtvDocumento = itemView.findViewById(R.id.txtvEvidencia);
            txtvStatus = itemView.findViewById(R.id.txtvSubCategoria);
            cvDocumentoDetalle = itemView.findViewById(R.id.cvDocumentoDetalle);
            imvDocumento = itemView.findViewById(R.id.imvDocumento);
        }

        void bindData(final ListDocumentos item) {
            txtvDocumento.setText(item.getNombreDocumento());
            txtvStatus.setText(item.getStatus());
            if (Objects.equals(item.getStatus(), "Pendiente")) {
                txtvStatus.setTextColor(Color.parseColor("#FF8300"));
                imvDocumento.setImageResource(R.drawable.documento);
            } else {
                txtvStatus.setTextColor(Color.parseColor("#005CB9"));
                Glide.with(context)
                        .load(item.getUrl())
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)).into(imvDocumento);
            }


            //txtvNombreCandidato.setText(item.getFolio());
            //folio.setText(context.getString(R.string.TD_folio, item.getFolio()));
            //envia.setText(context.getString(R.string.TD_sucursal_origen, item.getOrigen()));
            itemView.setOnClickListener(v -> listener.onItemClick(item));

        }
    }


}
