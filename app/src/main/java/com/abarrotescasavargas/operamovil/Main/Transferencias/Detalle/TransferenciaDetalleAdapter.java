package com.abarrotescasavargas.operamovil.Main.Transferencias.Detalle;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.abarrotescasavargas.operamovil.R;
import java.util.ArrayList;
import java.util.List;
public class TransferenciaDetalleAdapter extends RecyclerView.Adapter<TransferenciaDetalleAdapter.TransferenciaViewHolder> {
    private Context mContext;
    private ArrayList<OBJTransferenciaDetalleAdapter> mDetalleTransferenciaList; // Lista original
    private ArrayList<OBJTransferenciaDetalleAdapter> mFilteredDetalleTransferenciaList; // Lista para mostrar


    public TransferenciaDetalleAdapter(Context context, ArrayList<OBJTransferenciaDetalleAdapter> detalleTransferenciaList) {
        this.mContext = context;
        this.mDetalleTransferenciaList = detalleTransferenciaList;
        this.mFilteredDetalleTransferenciaList = new ArrayList<>(detalleTransferenciaList);

    }

    @NonNull
    @Override
    public TransferenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transferencia_detalle_adapter, parent, false);
        return new TransferenciaViewHolder(view, this); // Pasar 'this' como referencia al adaptador
    }


    @Override
    public void onBindViewHolder(@NonNull TransferenciaViewHolder holder, int position) {
        OBJTransferenciaDetalleAdapter currentItem = mFilteredDetalleTransferenciaList.get(position); // Usa la lista filtrada

        if (currentItem.tieneDiferencias()) {
            // Aquí aplicas el borde con diferencias
            holder.itemView.setBackgroundResource(R.drawable.transferencia_incorrecto);
        } else {
            // Aplicas un fondo normal sin bordes destacados
            holder.itemView.setBackgroundResource(R.drawable.transferencia_correcto);
        }


        holder.txtClave.setText(currentItem.getClave());
        holder.txtDescripcion.setText(currentItem.getDescripcion());
        holder.cantidad.setText(String.valueOf(currentItem.getCantidad()));
        // Asegúrate de establecer el valor de inputCantidad desde el modelo de datos
        holder.conteo.setText(String.valueOf(currentItem.getConteo()));


    }


    @Override
    public int getItemCount() {
        return mFilteredDetalleTransferenciaList.size(); // Retorna el tamaño de la lista filtrada
    }

    public static class TransferenciaViewHolder extends RecyclerView.ViewHolder {
        TextView txtClave;
        TextView txtDescripcion,cantidad;
        EditText conteo;
        TransferenciaDetalleAdapter adapter;

        public TransferenciaViewHolder(@NonNull View itemView, TransferenciaDetalleAdapter adapter) {
            super(itemView);
            this.adapter = adapter; // Guardar la referencia al adaptador
            txtClave = itemView.findViewById(R.id.txtClave);
            cantidad =itemView.findViewById(R.id.cantidad);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            conteo = itemView.findViewById(R.id.conteo);
            conteo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // No es necesario implementar
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // No es necesario implementar
                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        try {
                            int cantidad = Integer.parseInt(s.toString());
                            adapter.updateCantidad(position, cantidad);
                        } catch (NumberFormatException e) {
                            adapter.updateCantidad(position, 0);
                        }
                    }
                }

            });

        }
    }

    public void filterList(List<OBJTransferenciaDetalleAdapter> filteredList) {
        mFilteredDetalleTransferenciaList.clear();
        mFilteredDetalleTransferenciaList.addAll(filteredList);
        notifyDataSetChanged();
    }
    public void updateCantidad(int position, int cantidad) {
        if (position >= 0 && position < mFilteredDetalleTransferenciaList.size()) {
            OBJTransferenciaDetalleAdapter item = mFilteredDetalleTransferenciaList.get(position);
            item.setConteo(cantidad);
        }
    }

}
