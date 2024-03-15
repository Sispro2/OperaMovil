package com.abarrotescasavargas.operamovil.Main.Transferencias.Detalle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abarrotescasavargas.operamovil.Main.DetalleTransferencias.DetalleTranferencias;
import com.abarrotescasavargas.operamovil.Main.DetalleTransferencias.ListMovAlmDetalleAdapter;
import com.abarrotescasavargas.operamovil.Main.DetalleTransferencias.listaDetalles;
import com.abarrotescasavargas.operamovil.Main.FunGenerales.Funciones;
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;
import com.abarrotescasavargas.operamovil.Main.Transferencias_old.MovimientoAlmacenRepository;
import com.abarrotescasavargas.operamovil.Main.Transferencias_old.TransferenciasActivity;
import com.abarrotescasavargas.operamovil.Main.Transferencias_old.listTransferencias;
import com.abarrotescasavargas.operamovil.R;

import java.util.ArrayList;
import java.util.List;

public class TransferenciasDetalleActivity extends AppCompatActivity {
    TextView folio,observacion,origen,contador;
    List<listaDetalles> elements;
    listTransferencias transferencia;
    MovimientoAlmacenRepository movimientoAlmacenRepository;
    private RecyclerView recyclerView;
    private TransferenciaDetalleAdapter cardAdapter;
    private int id_premovimiento_almacen;
    private ArrayList<OBJTransferenciaDetalleAdapter> detalleTransferenciaList;
    SearchView searchView;
    Button button;
    SucursalRepository sucursalRepository;
    private Context context;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transferencia_detalle);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        detalleTransferenciaList = new ArrayList<>();
        setup();
        events();
    }
    private void setup() {
        transferencia = (listTransferencias) getIntent().getSerializableExtra("DetalleTransferencia");

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new TransferenciaDetalleAdapter(this, detalleTransferenciaList);
        recyclerView.setAdapter(cardAdapter);
        button=findViewById(R.id.buttonTransferencia);

        folio = findViewById(R.id.folioTransferenciaDetalle);
        observacion = findViewById(R.id.observacionesTransferenciaDetalle);
        origen = findViewById(R.id.origenTransferenciaDetalle);
        contador = findViewById(R.id.contador);
        searchView = findViewById(R.id.buscador);
        transferencia = (listTransferencias) getIntent().getSerializableExtra("DetalleTransferencia");

        String observaciones = transferencia.getObservaciones() != null || !transferencia.getObservaciones().isEmpty() ? transferencia.getObservaciones() : " ";
        Funciones.setTextWithMarquee(observacion, observaciones);

        id_premovimiento_almacen = transferencia.getId_premovimiento_almacen();
        folio.setText(transferencia.getFolio());
        origen.setText(transferencia.getSucOrigen());

        context = getApplicationContext();
        traeDetalleTransferencia();
    }

    private  void events()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> registrosConDiferencias = new ArrayList<>();
                for (OBJTransferenciaDetalleAdapter detalle : detalleTransferenciaList) {
                    float cantidadIngresada = detalle.getCantidad();

                    float cantidadContador = detalle.getConteo();

                    if (cantidadIngresada != cantidadContador) {
                        registrosConDiferencias.add("\n\nClave: " + detalle.getClave() + "\nDescripción: " + detalle.getDescripcion());
                    }
                }

                if (registrosConDiferencias.isEmpty()) {
                    mostrarMensaje("No hay diferencias en las cantidades.");
                } else {
                    mostrarPopupDiferencias(registrosConDiferencias);
                }
            }
        });
    }
    private void traeDetalleTransferencia() {
        movimientoAlmacenRepository = new MovimientoAlmacenRepository(this);
        Cursor cursor = movimientoAlmacenRepository.GetDetalleTransferencia(id_premovimiento_almacen);
        elements = new ArrayList<>();
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        int _idarticulo = cursor.getInt(cursor.getColumnIndexOrThrow("ID_ARTICULO"));
                        int _idpremovimientoalmacen = cursor.getInt(cursor.getColumnIndexOrThrow("ID_PREMOVIMIENTO_ALMACEN"));
                        String _descmayoreo = cursor.getString(cursor.getColumnIndexOrThrow("DESC_MAYOREO"));
                        String _descsuper = cursor.getString(cursor.getColumnIndexOrThrow("DESC_SUPER"));
                        String _codigobarras1 = cursor.getString(cursor.getColumnIndexOrThrow("CODIGO_BARRAS1"));
                        String _codigobarras2 = cursor.getString(cursor.getColumnIndexOrThrow("CODIGO_BARRAS2"));
                        float _cantidad = cursor.getFloat(cursor.getColumnIndexOrThrow("CANTIDAD"));
                        String _clave = cursor.getString(cursor.getColumnIndexOrThrow("CLAVE"));
                        String _unidad = cursor.getString(cursor.getColumnIndexOrThrow("UNIDAD"));
                        String _status = cursor.getString(cursor.getColumnIndexOrThrow("STATUS"));

                        elements.add(new listaDetalles(_idarticulo, _idpremovimientoalmacen, _descmayoreo, _descsuper, _codigobarras1, _codigobarras2, _cantidad, _clave, _unidad, _status));
                    } while (cursor.moveToNext());
                }
                cursor.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        detalleTransferenciaList.clear();
        for (listaDetalles detalle : elements) {
            String clave = detalle.getClave();
            String descripcion = detalle.getDescMayoreo();
            float cantidad = detalle.getCantidad();
            OBJTransferenciaDetalleAdapter detalleAdapter = new OBJTransferenciaDetalleAdapter(clave, descripcion,cantidad,false);
            detalleTransferenciaList.add(detalleAdapter);
        }
        contador.setText(String.valueOf("No. de registros: "+cardAdapter.getItemCount()));
        cardAdapter = new TransferenciaDetalleAdapter(this, detalleTransferenciaList);

        recyclerView.setAdapter(cardAdapter);

        cardAdapter.notifyDataSetChanged();
        contador.setText("No. de registros: "+String.valueOf(cardAdapter.getItemCount()));
    }
    private void filterList(String searchText) {
        List<OBJTransferenciaDetalleAdapter> filteredList = new ArrayList<>();
        for (OBJTransferenciaDetalleAdapter item : detalleTransferenciaList) {
            if (item.getClave().toLowerCase().contains(searchText.toLowerCase()) ||
                    item.getDescripcion().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        cardAdapter.filterList(filteredList);
        contador.setText("No. de registros: "+String.valueOf(cardAdapter.getItemCount()));
    }
    private void mostrarMensaje(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recargarRecyclerViewConColores();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void recargarRecyclerViewConColores() {
        traeDetalleTransferencia();
    }


    private void mostrarPopupDiferencias(List<String> registrosConDiferencias) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registros con diferencias");
        builder.setItems(registrosConDiferencias.toArray(new String[0]), null);
        builder.setPositiveButton("Regresar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Marca los ítems con diferencias antes de recargar
                for (OBJTransferenciaDetalleAdapter detalle : detalleTransferenciaList) {
                    float cantidadIngresada = detalle.getCantidad();
                    float cantidadContador = detalle.getConteo();
                    detalle.setDiferencias(cantidadIngresada != cantidadContador);
                }
                cardAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enviarRegistrosConDiferencias(registrosConDiferencias);
            }
        });

        AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnPositivo = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                Button btnNegativo = alert.getButton(AlertDialog.BUTTON_NEGATIVE);

                btnPositivo.setTextColor(Color.RED);
                btnNegativo.setTextColor(Color.GREEN);
            }
        });
        alert.show();
    }
//    private void enviarRegistrosConDiferencias(List<String> registrosConDiferencias) {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.sincronizando_dialog);
//        boolean exito = false;
//
//        while (!exito) {
//            if (Funciones.ValidaConexionServidorLocal(getApplicationContext())) {
//                if (MovimientoAlmacenRepository.SetMovimientoAlmacen(transferencia.getFolio())) {
//                    exito = true;
//                    new android.app.AlertDialog.Builder(this)
//                            .setTitle("Éxito")
//                            .setMessage("Se ingresó la mercancía")
//                            .setPositiveButton("Aceptar", (dialog, which) -> {
//                                Intent intent = new Intent(getApplicationContext(), TransferenciasActivity.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.transition.in_left, R.transition.out_left);
//                                finish();
//                            })
//                            .create().show();
//                } else {
//                    progressDialog.cancel();
//                    new android.app.AlertDialog.Builder(this)
//                            .setTitle("Error")
//                            .setMessage("Algo salió mal, inténtelo nuevamente")
//                            .setPositiveButton("Aceptar", (dialog, which) -> {
//                                // Reintentar
//                            })
//                            .create().show();
//                }
//            } else {
//                progressDialog.cancel();
//                Toast.makeText(TransferenciasDetalleActivity.this,
//                        context.getResources().getString(R.string.msj_servidor_local_no_responde, sucursalRepository.GetDetalleSucursal().getKS_IP()),
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private void enviarRegistrosConDiferencias(List<String> registrosConDiferencias) {
        new EnviarRegistrosTask().execute(registrosConDiferencias);
    }

    private class EnviarRegistrosTask extends AsyncTask<List<String>, Void, Boolean> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TransferenciasDetalleActivity.this);
            progressDialog.setMessage("Enviando registros...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(List<String>... lists) {
            List<String> registrosConDiferencias = lists[0];
            boolean exito = false;
            while (!exito) {
                if (Funciones.ValidaConexionServidorLocal(getApplicationContext())) {
                    if (MovimientoAlmacenRepository.SetMovimientoAlmacen(transferencia.getFolio())) {
                        exito = true;
                        new android.app.AlertDialog.Builder(context)
                                .setTitle("Éxito")
                                .setMessage("Se ingresó la mercancía")
                                .setPositiveButton("Aceptar", (dialog, which) -> {
                                    Intent intent = new Intent(getApplicationContext(), TransferenciasActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.transition.in_left, R.transition.out_left);
                                    finish();
                                })
                                .create().show();
                    } else {
                        progressDialog.cancel();
                        new android.app.AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage("Algo salió mal, inténtelo nuevamente")
                                .setPositiveButton("Aceptar", (dialog, which) -> {

                                })
                                .create().show();
                    }
                } else {
                    progressDialog.cancel();
                    Toast.makeText(TransferenciasDetalleActivity.this,
                            context.getResources().getString(R.string.msj_servidor_local_no_responde, sucursalRepository.GetDetalleSucursal().getKS_IP()),
                            Toast.LENGTH_LONG).show();
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return exito;
        }

        @Override
        protected void onPostExecute(Boolean exito) {
            super.onPostExecute(exito);
            progressDialog.dismiss();

            if (exito) {
                // Operación exitosa
                mostrarMensaje("Se ingresó la mercancía");
            } else {
                // Error
                mostrarMensaje("Algo salió mal, inténtelo nuevamente");
            }
        }
    }

}
