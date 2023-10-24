package com.abarrotescasavargas.operamovil.Main;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.abarrotescasavargas.operamovil.Main.Menu2.DBRezagado;
import com.abarrotescasavargas.operamovil.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterARTREZA extends RecyclerView.Adapter<AdapterARTREZA.ViewHolderDatos> {

    ArrayList<String>listDatos;
    ArrayList<String> listNombre;
    ArrayList<String> listUltimaVenta;
    ArrayList<String> listRezagado;
    ArrayList<String> listExistencia;

    ArrayList<String>listFecha;

    //View.OnClickListener listener;

    RecyclerItemClick itemClick;
    private List<DBRezagado> mData;

    //List<ArticuloRezagado> listaRezagados;

    public AdapterARTREZA(ArrayList<String> listDatos, ArrayList<String> listNombre, ArrayList<String> listUltimaVenta, ArrayList<String> listRezagado, ArrayList<String> listExistencia, ArrayList<String>listFecha, RecyclerItemClick itemClick) {
        this.listDatos = listDatos;
        this.listNombre = listNombre;
        this.listUltimaVenta = listUltimaVenta;
        this.listRezagado = listRezagado;
        this.listExistencia = listExistencia;
        this.listFecha = listFecha;
        this.itemClick=itemClick;

    }

    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);

        //view.setOnClickListener(this);

        return  new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {

        final String item = listDatos .get(position);

        holder.asignarDatos(listDatos.get(position));
        holder.asignarNombres(listNombre.get(position));
        holder.asignarUltimaVenta(listUltimaVenta.get(position));
        holder.asignarRezagado(listRezagado.get(position));
        holder.asignarExistencia(listExistencia.get(position));
        holder.asignarFecha(listFecha.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClick.itemClick(item);

                //Toast.makeText(view.getContext(), listDatos.get(position), Toast.LENGTH_LONG).show();

            }
        });
    }
    public void actualizarDatos(List<DBRezagado> nuevosDatos) {
        // Actualiza los datos internos del adaptador con los nuevos datos
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

            do {
                DBRezagado rezagado = new DBRezagado();
                rezagado.setRI_CVEART(cursor.getString(cvartIndex));
                rezagado.setRI_NOMART(cursor.getString(nomartIndex));
                rezagado.setRI_FECDAT(cursor.getString(fecdadIndex));
                rezagado.setRI_EXISTE(cursor.getString(existeIndex));
                rezagado.setRI_DIASVT(cursor.getString(diasrezIndex));

                rezagados.add(rezagado);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return rezagados;
    }
    @Override
    public int getItemCount() {
        return listDatos.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView dato;
        TextView nombre;
        TextView ultimaVenta;
        TextView rezagado;
        TextView existencia;
        TextView fecha;

        public ViewHolderDatos(View itemView) {
            super(itemView);

            dato = (TextView) itemView.findViewById(R.id.idDato);
            nombre = (TextView) itemView.findViewById(R.id.idnombre);
            ultimaVenta = (TextView) itemView.findViewById(R.id.idUltimaVenta);
            rezagado = (TextView) itemView.findViewById(R.id.idDiasRezagado);
            existencia = (TextView) itemView.findViewById(R.id.idExistencias);
            fecha = (TextView) itemView.findViewById(R.id.idfechas);

        }

        public void asignarDatos(String datos) {
            dato.setText(datos);
        }

        public void asignarNombres(String nombres) {
            nombre.setText(nombres);
        }


        public void asignarUltimaVenta(String ultimaVentas) {
            ultimaVenta.setText(ultimaVentas);
        }

        public void asignarRezagado(String rezagados) {
            rezagado.setText(rezagados);
        }

        public void asignarExistencia(String existencias) {
            existencia.setText(existencias);
        }

        public void asignarFecha(String fechas){
            fecha.setText(fechas);
        }
    }

        public interface RecyclerItemClick{
        void itemClick(String item);

        }

}
