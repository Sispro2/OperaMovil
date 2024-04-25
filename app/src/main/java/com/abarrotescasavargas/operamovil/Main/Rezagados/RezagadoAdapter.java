package com.abarrotescasavargas.operamovil.Main.Rezagados;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import com.abarrotescasavargas.operamovil.Main.BaseDatos.BD_SQL;
import com.abarrotescasavargas.operamovil.Main.Menu2.DBRezagado;
import com.abarrotescasavargas.operamovil.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RezagadoAdapter extends RecyclerView.Adapter<RezagadoAdapter.ViewHolder> {
    private static List<DBRezagado> mData;
    private static Context context = null;
    static OnItemClickListener listener = null;
    private TextView textViewNumeroRegistros;

    public interface OnItemClickListener {
        void onItemClick(DBRezagado item);
    }

    public RezagadoAdapter(List<DBRezagado> listRezagados, Context context, OnItemClickListener listener) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        RezagadoAdapter.context = context;
        RezagadoAdapter.listener = listener;
        this.textViewNumeroRegistros = textViewNumeroRegistros;
        mData = listRezagados;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rezagados_detalle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (holder.cvRezagadoDetalle != null) {
            holder.cvRezagadoDetalle.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
            holder.bindData(mData.get(position));
        }
    }
    public void actualizarDatos(List<DBRezagado> nuevosDatos) {
        mData = nuevosDatos;

        // Notifica al RecyclerView que los datos han cambiado
        notifyDataSetChanged();
    }

    public List<DBRezagado> cursorToRezagados(Cursor cursor) {
        List<DBRezagado> rezagados = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("ID");
            int cvartIndex = cursor.getColumnIndex("RI_CVEART");
            int nomartIndex = cursor.getColumnIndex("RI_NOMART");
            int fecdadIndex = cursor.getColumnIndex("RI_FECDAT");
            int existeIndex = cursor.getColumnIndex("RI_EXISTE");
            int diasrezIndex = cursor.getColumnIndex("RI_DIASREZ");
            int urlIndex = cursor.getColumnIndex("RI_URLWEB");

            do {
                DBRezagado rezagado = new DBRezagado();
                rezagado.setRI_CVEART(cursor.getString(cvartIndex));
                rezagado.setRI_NOMART(cursor.getString(nomartIndex));
                rezagado.setRI_FECDAT(cursor.getString(fecdadIndex));
                rezagado.setRI_EXISTE(cursor.getString(existeIndex));
                rezagado.setRI_DIASVT(cursor.getString(diasrezIndex));
                rezagado.setRI_URLWEB(cursor.getString(urlIndex));

                rezagados.add(rezagado);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return rezagados;
    }


    public void setItems(List<DBRezagado> items) {
        mData = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvRezagadoDetalle;
        TextView RI_CVEART, RI_NOMART, RI_FECDAT, RI_EXISTE,RI_DIASREZ;
        ImageView imvDocumento, imvEtapa;


        ViewHolder(View itemView) {
            super(itemView);
            RI_CVEART = itemView.findViewById(R.id.Clave);
            RI_NOMART = itemView.findViewById(R.id.Nombre);
            RI_FECDAT = itemView.findViewById(R.id.FE);
            RI_EXISTE = itemView.findViewById(R.id.existencia);
            RI_DIASREZ = itemView.findViewById(R.id.dias);
            cvRezagadoDetalle = itemView.findViewById(R.id.cvRezagadoDetalle);
            imvDocumento = itemView.findViewById(R.id.imvDocumento);
            imvEtapa = itemView.findViewById(R.id.imgvEtapa);



            imvEtapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(100); // Duración de la animación en milisegundos
                    view.startAnimation(scaleAnimation);

                    // Obtiene los valores de RI_CVEART y RI_NOMART del elemento actual
                    String cveArt = mData.get(getAdapterPosition()).getRI_CVEART();
                    String cveNom = mData.get(getAdapterPosition()).getRI_NOMART();

                    // Utiliza el contexto para iniciar la nueva actividad con la bandera FLAG_ACTIVITY_NEW_TASK
                    Intent intent = new Intent(context, CapturaRezagado.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Pasa los valores a la actividad CapturaRezagado como extras
                    intent.putExtra("cveArt", cveArt);
                    intent.putExtra("cveNom", cveNom);
                    intent.putExtra("cveSucursal", "SucTest");
                    context.startActivity(intent);
                }
            });


            // Restablece el tamaño original cuando se suelta
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

        void bindData(final DBRezagado item) {
            // Asigna la propiedad correcta de DBRezagado y activa el efecto de marquesina
            setTextWithMarquee(RI_CVEART, item.getRI_CVEART());
            setTextWithMarquee(RI_NOMART, item.getRI_NOMART());
            setTextWithMarquee(RI_FECDAT, expresionesRegulares(item.getRI_FECDAT()));
            setTextWithMarquee(RI_EXISTE, "Existencia inicial: " + item.getRI_EXISTE() +"| Existencia actual: "+ BD_SQL.ObtenerExistenciaTotalClave(item.getRI_CVEART()));
            setTextWithMarquee(RI_DIASREZ, "Días en existencia: " + item.getRI_DIASVT());


            if (!TextUtils.isEmpty(item.getRI_URLWEB())) {
                try {
                    new URL(item.getRI_URLWEB());
                    String test = item.getRI_URLWEB();
                    Log.v("Data llena URL: ", test);
                    Glide.with(context)
                            .load(test)
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)).into(imvDocumento);
                } catch (MalformedURLException e) {
                    Log.v("Data no es una URL válida", item.getRI_CVEART());
                    imvDocumento.setImageResource(R.drawable.without_image_foreground);
                }
            } else {
                imvDocumento.setImageResource(R.drawable.without_image_foreground);
                Log.v("Data null", item.getRI_CVEART());
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

