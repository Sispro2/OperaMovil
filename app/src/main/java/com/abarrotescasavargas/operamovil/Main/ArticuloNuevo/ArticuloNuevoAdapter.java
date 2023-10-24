package com.abarrotescasavargas.operamovil.Main.ArticuloNuevo;

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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticuloNuevoAdapter extends RecyclerView.Adapter<ArticuloNuevoAdapter.ViewHolder>
{
    private static List<DBArticuloNuevo> mData;
    private static Context context = null;
    static OnItemClickListener listener = null;

    public interface OnItemClickListener {
        void onItemClick(DBArticuloNuevo item);
    }
    public ArticuloNuevoAdapter(List<DBArticuloNuevo> listArtNuevo, Context context, OnItemClickListener listener) {
        ArticuloNuevoAdapter.context = context;
        ArticuloNuevoAdapter.listener = listener;
        mData = listArtNuevo;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_nuevo_articulo_detalle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.cvArticuloDetalle != null) {
            holder.cvArticuloDetalle.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
            holder.bindData(mData.get(position));
        }
    }
    public void actualizarDatos(List<DBArticuloNuevo> nuevosDatos) {
        mData = nuevosDatos;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cvArticuloDetalle;
        TextView NA_CVEART, NA_NOMART, NA_FECALT,NA_LINNEG,NA_EXISTE;
        ImageView imvDocumento, imvEtapa;
        ViewHolder(View itemView) {
            super(itemView);
            NA_CVEART = itemView.findViewById(R.id.ClaveArt);
            NA_NOMART = itemView.findViewById(R.id.NombreArt);
            NA_FECALT = itemView.findViewById(R.id.FEArt);
            NA_LINNEG = itemView.findViewById(R.id.lineaDeNegocio);
            NA_EXISTE = itemView.findViewById(R.id.existenciaArt);

            cvArticuloDetalle = itemView.findViewById(R.id.articuloNuevoDetalle);
            imvDocumento = itemView.findViewById(R.id.imvDocumentoArt);
            imvEtapa = itemView.findViewById(R.id.imgvEtapaArt);
            imvEtapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(100);
                    view.startAnimation(scaleAnimation);
                    String cveArt = mData.get(getAdapterPosition()).getNA_CVEART();
                    String cveNom = mData.get(getAdapterPosition()).getNA_NOMART();
                    Intent intent = new Intent(context, CapturaArticuloNuevo.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("cveArt", cveArt);
                    intent.putExtra("cveNom", cveNom);
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
        void bindData(final DBArticuloNuevo item) {
            // Asigna la propiedad correcta de DBArticuloNuevo y activa el efecto de marquesina
            setTextWithMarquee(NA_CVEART, item.getNA_CVEART());
            setTextWithMarquee(NA_NOMART, item.getNA_NOMART());
            setTextWithMarquee(NA_LINNEG, item.getNA_LINNEG());
            setTextWithMarquee(NA_EXISTE, "Existencia: " + item.getNA_EXISTE());
            setTextWithMarquee(NA_FECALT, expresionesRegulares(item.getNA_FECALT()));

            if (!TextUtils.isEmpty(item.getNA_EVIDEN())) {
                try {
                    new URL(item.getNA_EVIDEN());
                    String test = item.getNA_EVIDEN();
                    Log.v("Data llena URL: ", test);
                    Glide.with(context)
                            .load(test)
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)).into(imvDocumento);
                } catch (MalformedURLException e) {
                    Log.v("Data no es una URL válida", item.getNA_CVEART());
                    imvDocumento.setImageResource(R.drawable.without_image_foreground);
                }
            } else {
                imvDocumento.setImageResource(R.drawable.without_image_foreground);
                Log.v("Data null", item.getNA_CVEART());
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
