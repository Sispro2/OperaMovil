package com.abarrotescasavargas.operamovil.Main.ConcursoVentas;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConcursoVentasAdapter extends RecyclerView.Adapter<ConcursoVentasAdapter.ViewHolder>{

    private static List<DBConcursoVentas> mData;
    private static Context context = null;
    static OnItemClickListener listener = null;

    public interface OnItemClickListener {
        void onItemClick(DBConcursoVentas item);
    }

    public ConcursoVentasAdapter(List<DBConcursoVentas> listConcursoVtas, Context context, OnItemClickListener listener) {
        ConcursoVentasAdapter.context = context;
        ConcursoVentasAdapter.listener = listener;
        mData = listConcursoVtas;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_concurso_ventas_detalles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.cvConcursoVentas != null) {
            holder.cvConcursoVentas.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
            holder.bindData(mData.get(position));
        }
    }

    public void actualizarDatos(List<DBConcursoVentas> nuevosDatos) {
        mData = nuevosDatos;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cvConcursoVentas;
        TextView CV_CVEART, CV_FECINI, CV_CVEPRO,CV_FECFIN;
        ImageView imvDocumento, imvEtapa;
        ViewHolder(View itemView) {
            super(itemView);
            CV_CVEART = itemView.findViewById(R.id.ClaveCon);
            CV_CVEPRO = itemView.findViewById(R.id.NombreCon);
            CV_FECINI = itemView.findViewById(R.id.existenciaCon);
            CV_FECFIN = itemView.findViewById(R.id.FECon);

            cvConcursoVentas = itemView.findViewById(R.id.concursoVentasDetalle);
            imvDocumento = itemView.findViewById(R.id.imvDocumentoCon);
            imvEtapa = itemView.findViewById(R.id.imgvEtapaCon);
            imvEtapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(100);
                    view.startAnimation(scaleAnimation);
                    String cveCon = mData.get(getAdapterPosition()).getCV_CVEPRO();
                    String cveNom = mData.get(getAdapterPosition()).getCV_CONSEC();
                    Intent intent = new Intent(context, CapturaConcursoVentas.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("cvePro", cveCon);
                    intent.putExtra("cveArt", cveNom);
                    intent.putExtra("cveSucursal", "SucTest");
                    context.startActivity(intent);
                }
            });

            imvEtapa.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        imvEtapa.setScaleX(1.0f);
                        imvEtapa.setScaleY(1.0f);
                    }
                    return false;
                }
            });

        }
        void bindData(final DBConcursoVentas item) {
            // Asigna la propiedad correcta de DBArticuloNuevo y activa el efecto de marquesina
            setTextWithMarquee(CV_CVEART, item.getCV_CVEPRO());
            setTextWithMarquee(CV_CVEPRO, item.getCV_CVEART() );
            setTextWithMarquee(CV_FECFIN,"Incio: "+ expresionesRegulares(item.getCV_FECFIN()));
            setTextWithMarquee(CV_FECINI,"Fin: "+ expresionesRegulares(item.getCV_FECINI()));

            if (!TextUtils.isEmpty(item.getCV_EVIDEN())) {
                    String test = item.getCV_EVIDEN();
                    test="https://abarrotescasavargas.mx/api/uploads"+test;
                    Log.v("Data llena URL: ", test);
                    Glide.with(context)
                            .load(test)
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)).into(imvDocumento);
            } else {
                imvDocumento.setImageResource(R.drawable.without_image_foreground);
            }
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
        private String expresionesRegulares(String fechaHoraOriginal)
        {
            //Extrae la fecha en formato "yyyy-MM-dd" de una cadena de fecha y hora
            //proporcionada en el parámetro de entrada y devuelve la fecha extraída,
            //o null si no se encontró una fecha válida en la cadena.
            String patronFecha = "(\\d{4}-\\d{2}-\\d{2})";
            Pattern patron = Pattern.compile(patronFecha);
            Matcher matcher = patron.matcher(fechaHoraOriginal);
            if (matcher.find()) {
                String fechaExtraida = matcher.group(1);
                return fechaExtraida;
            } else {
                return null;
            }
        }
        private void setTextWithMarquee(TextView textView, String text) {
            if (textView != null && !TextUtils.isEmpty(text)) {
                textView.setText(text);
                textView.setSelected(true); // Activa el efecto de marquesina
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setSingleLine(true);
            }
        }
    }
}