package com.abarrotescasavargas.operamovil.Main.Transferencias.Detalle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abarrotescasavargas.operamovil.Main.BaseDatos.DbHelper;
import com.abarrotescasavargas.operamovil.Main.BaseDatos.OperaMovilContract;
import com.abarrotescasavargas.operamovil.R;
import java.util.ArrayList;
import java.util.List;
public class TransferenciaDetalleAdapter extends RecyclerView.Adapter<TransferenciaDetalleAdapter.TransferenciaViewHolder> {
    private Context mContext;
    private ArrayList<OBJTransferenciaDetalleAdapter> mDetalleTransferenciaList;
    private ArrayList<OBJTransferenciaDetalleAdapter> mFilteredDetalleTransferenciaList;
    private static DbHelper dbHelper;


    public TransferenciaDetalleAdapter(Context context, ArrayList<OBJTransferenciaDetalleAdapter> detalleTransferenciaList) {
        this.mContext = context;
        this.mDetalleTransferenciaList = detalleTransferenciaList;
        this.mFilteredDetalleTransferenciaList = new ArrayList<>(detalleTransferenciaList);
        dbHelper = new DbHelper(mContext);
    }

    @NonNull
    @Override
    public TransferenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transferencia_detalle_adapter, parent, false);
        return new TransferenciaViewHolder(view, this);
    }


    @Override
    public void onBindViewHolder(@NonNull TransferenciaViewHolder holder, int position) {

        OBJTransferenciaDetalleAdapter currentItem = mFilteredDetalleTransferenciaList.get(position);

        holder.txtClave.setText(currentItem.getClave());
        holder.txtDescripcion.setText(currentItem.getDescripcion());
        holder.unidad.setText(currentItem.getUnidad());
        holder.cantidad.setText(String.valueOf(currentItem.getCantidad()));
        if (currentItem.getConteo()!=0)
        {
            holder.conteo.setText(String.valueOf(currentItem.getConteo()));
        }

        updateBackground(holder, currentItem);
    }



    @Override
    public int getItemCount() {
        return mFilteredDetalleTransferenciaList.size();
    }

    public static class TransferenciaViewHolder extends RecyclerView.ViewHolder {
        TextView txtClave,unidad;
        TextView txtDescripcion,cantidad;
        EditText conteo;
        TransferenciaDetalleAdapter adapter;
        RelativeLayout fondo;
        Context context;
        public TransferenciaViewHolder(@NonNull View itemView, TransferenciaDetalleAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            txtClave = itemView.findViewById(R.id.txtClave);
            cantidad =itemView.findViewById(R.id.cantidad);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            conteo = itemView.findViewById(R.id.conteo);
            unidad = itemView.findViewById(R.id.unidad);
            fondo = itemView.findViewById(R.id.fondo);

            conteo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        try {
                            int nuevaCantidad = Integer.parseInt(s.toString());
                            adapter.updateCantidad(position, nuevaCantidad);
                        } catch (NumberFormatException e) {
                            adapter.updateCantidad(position, 0);
                        }
                        OBJTransferenciaDetalleAdapter currentItem = adapter.mFilteredDetalleTransferenciaList.get(position);
                        adapter.updateBackground(TransferenciaViewHolder.this, currentItem);
                    }
                }
            });
        }
    }
    public void updateBackground(TransferenciaViewHolder holder, OBJTransferenciaDetalleAdapter item) {
        if (item.tieneDiferencias()) {
            holder.fondo.setBackgroundResource(R.drawable.transferencia_incorrecto);
        } else {
            holder.fondo.setBackgroundResource(R.drawable.transferencia_correcto);
        }
        buscarYActualizarValor(item.getClave(),String.valueOf(item.getCveTrans()),item.getConteo());
    }
    public void buscarYActualizarValor(String cveArt, String idTrans, int nuevoValor) {
        // Obtener una instancia de la base de datos en modo escritura
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Asegurarse de que idTrans no sea null y asignar un valor predeterminado en caso contrario
        String idTransString = (idTrans != null) ? String.valueOf(idTrans) : "";

        // Definir la consulta SQL para buscar el valor
        String consulta = "SELECT * FROM TRANSFERENCIAS WHERE TR_IDTRAN = ? AND TR_CVEART = ?";

        // Definir los argumentos de la consulta
        String[] argumentos = { idTransString, cveArt };

        // Ejecutar la consulta
        Cursor cursor = db.rawQuery(consulta, argumentos);

        // Verificar si se encontraron resultados
        if (cursor.moveToFirst()) {
            do {
                // Actualizar el valor encontrado
                ContentValues valores = new ContentValues();
                valores.put(OperaMovilContract.TRANSFERENCIAS.TR_CONTEO, nuevoValor);

                // Definir la condición para la actualización
                String condicion = "TR_IDTRAN = ? AND TR_CVEART = ?";
                String[] argumentosUpdate = { idTransString, cveArt };

                // Ejecutar la sentencia de actualización
                db.update("TRANSFERENCIAS", valores, condicion, argumentosUpdate);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
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
