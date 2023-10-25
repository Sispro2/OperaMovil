package com.abarrotescasavargas.operamovil.Main.DetalleTransferencias;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abarrotescasavargas.operamovil.R;
import com.abarrotescasavargas.operamovil.Main.Transferencias.MovimientoAlmacenRepository;
import com.abarrotescasavargas.operamovil.Main.Transferencias.listTransferencias;

import java.util.ArrayList;
import java.util.List;

public class DetalleTranferencias extends AppCompatActivity {
    listTransferencias transferencia;
    ListMovAlmDetalleAdapter listAdapter;
    List<listaDetalles> elements;
    private int id_premovimiento_almacen;
    TextView txtFolio, txtTotal, txtOrigen, txtObservaciones, txtContados;
    RecyclerView recyclerView;
    MovimientoAlmacenRepository movimientoAlmacenRepository;
    AlertDialog dialog;
    Button btnTransferir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tranferencias);
        setup();
    }

    private void setup() {
        txtFolio = findViewById(R.id.txtFolio);
        txtOrigen = findViewById(R.id.txtvDescripcion);
        txtObservaciones = findViewById(R.id.observaciones);
        txtTotal = findViewById(R.id.txtTotal);
        txtContados = findViewById(R.id.txtContados);
        btnTransferir = findViewById(R.id.txtvStatus);
        btnTransferir.setVisibility(View.GONE);
        SearchView searchView = findViewById(R.id.srcBuscar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        transferencia = (listTransferencias) getIntent().getSerializableExtra("DetalleTransferencia");
        txtFolio.setText(getString(R.string.T_folio_transferencia, transferencia.getFolio()));
        txtOrigen.setText(getString(R.string.T_sucursal_origen, transferencia.getOrigen()));
        txtObservaciones.setText(getString(R.string.T_observaciones, transferencia.getObservaciones()));
        id_premovimiento_almacen = transferencia.getId_premovimiento_almacen();

        recyclerView = findViewById(R.id.reciclerDetalle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        traeDetalleTransferencia();
    }
    private void filterList(String text) {
        List<listaDetalles> filteredList = new ArrayList<>();
        for (listaDetalles item : elements) {
            if (item.getClave().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            } else if (item.getDescSuper().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (!filteredList.isEmpty()) {
            listAdapter.setFilteredList(filteredList);
        }
    }

    private void traeDetalleTransferencia() {
        movimientoAlmacenRepository = new MovimientoAlmacenRepository(this);
        Cursor cursor = movimientoAlmacenRepository.GetDetalleTransferencia(id_premovimiento_almacen);
        elements = new ArrayList<>();
        int contador = 0;
        try {
            int _idarticulo;
            int _idpremovimientoalmacen;
            String _descmayoreo;
            String _descsuper;
            String _codigobarras1;
            String _codigobarras2;
            String _cantidad;
            String _unidad;
            String _clave;
            String _status;
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        _idarticulo = cursor.getInt(0);
                        _idpremovimientoalmacen = cursor.getInt(1);
                        _descmayoreo = cursor.getString(2);
                        _descsuper = cursor.getString(3);
                        _codigobarras1 = cursor.getString(4);
                        _codigobarras2 = cursor.getString(5);
                        _cantidad = cursor.getString(6);
                        _clave = cursor.getString(7);
                        _unidad = cursor.getString(8);
                        _status = cursor.getString(9);
                        if (_status.equals("1"))
                            contador++;
                        elements.add(new listaDetalles(_idarticulo, _idpremovimientoalmacen, _descmayoreo, _descsuper, _codigobarras1, _codigobarras2, _cantidad, _clave, _unidad, _status));
                        cursor.moveToNext();
                    }
                }
                txtTotal.setText(getString(R.string.T_total_articulos, cursor.getCount()));
                txtContados.setText(getString(R.string.T_contados, String.valueOf(contador)));
                if (contador == cursor.getCount())
                    btnTransferir.setVisibility(View.VISIBLE);
                cursor.close();
                listAdapter = new ListMovAlmDetalleAdapter(elements, this, this::moveToDescription);
                recyclerView.setAdapter(listAdapter);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void moveToDescription(listaDetalles item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Captura y Guarda");
        View view = getLayoutInflater().inflate(R.layout.cantidad_dialog, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        Button guardar = view.findViewById(R.id.cd_guardar);
        EditText cantidad = view.findViewById(R.id.cd_cantidad);
        TextView descSuper = view.findViewById(R.id.cd_descsuper);
        TextView unidad = view.findViewById(R.id.cd_unidad);
        descSuper.setText(getString(R.string.cd_desc_super, item.getDescSuper()));
        unidad.setText(getString(R.string.cd_unidad, item.getUnidad()));
        guardar.setOnClickListener(v -> {
            ActualizaStatus(item, cantidad.getText().toString());
            dialog.dismiss();
        });
        cantidad.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                ActualizaStatus(item, cantidad.getText().toString());
                dialog.dismiss();
                return true;
            }
            return false;
        });
    }

    private void ActualizaStatus(listaDetalles item, String recibido) {
        if (MovimientoAlmacenRepository.ActualizaStatus(item.getClave(), item.getIdPremovimientoAlmacen(), item.getIdArticulo(), this, recibido)) {
            listTransferencias item2 = new listTransferencias(transferencia.getFolio(), transferencia.getObservaciones(), transferencia.getOrigen(), id_premovimiento_almacen);
            Intent intent = new Intent(this, DetalleTranferencias.class);
            intent.putExtra("DetalleTransferencia", item2);
            startActivity(intent);
            finish();
        }
    }
}