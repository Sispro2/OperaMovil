package com.abarrotescasavargas.operamovil.Main.DetalleTransferencias;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abarrotescasavargas.operamovil.Main.FunGenerales.Funciones;
import com.abarrotescasavargas.operamovil.Main.Login.LoginActivity;
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;
import com.abarrotescasavargas.operamovil.Main.Transferencias.TransferenciasActivity;
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
    private    SearchView searchView;
    TextView txtFolio, txtTotal, txtOrigen, txtObservaciones, txtContados;
    RecyclerView recyclerView;
    MovimientoAlmacenRepository movimientoAlmacenRepository;
    AlertDialog dialog;
    Button btnTransferir;
    SucursalRepository sucursalRepository;
    private Context context;
    // private  String FolioSinText= "";

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tranferencias);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setup();
        events();
    }

    private void setup() {
        txtFolio = findViewById(R.id.txtFolio);
        txtOrigen = findViewById(R.id.txtvDescripcion);
        txtObservaciones = findViewById(R.id.observaciones);
        txtTotal = findViewById(R.id.txtTotal);
        txtContados = findViewById(R.id.txtContados);
        btnTransferir = findViewById(R.id.txtvStatus);
        btnTransferir.setVisibility(View.GONE);
        searchView = findViewById(R.id.srcBuscar);
        searchView.clearFocus();
        transferencia = (listTransferencias) getIntent().getSerializableExtra("DetalleTransferencia");
        txtFolio.setText(getString(R.string.T_folio_transferencia, transferencia.getFolio()));
       // FolioSinText= transferencia.getFolio();
        txtOrigen.setText(getString(R.string.T_sucursal_origen, transferencia.getSucOrigen()));

        Funciones.setTextWithMarquee(txtObservaciones, transferencia.getObservaciones());
        id_premovimiento_almacen = transferencia.getId_premovimiento_almacen();
        recyclerView = findViewById(R.id.reciclerDetalle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        context = getApplicationContext();
        sucursalRepository = new SucursalRepository(context);
        traeDetalleTransferencia();
    }

    private void events() {
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
        btnTransferir.setOnClickListener(v -> {
            GuardaPremovimiento();
        });
    }

    private  void GuardaPremovimiento(){
        new android.app.AlertDialog.Builder(this)
                .setTitle("¿Ingresar la Transferencia?")
                .setMessage("La Mercancia se Ingresarán al Almacén")
                .setPositiveButton("aceptar", (dialog, which) -> {
                    progressDialog = new ProgressDialog(this);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.sincronizando_dialog);
                    if (Funciones.ValidaConexionServidorLocal(getApplicationContext())) {
                        if( MovimientoAlmacenRepository.SetMovimientoAlmacen(transferencia.getFolio())){
                           // progressDialog.cancel();
                            new android.app.AlertDialog.Builder(this)
                                    .setTitle("Exito")
                                    .setMessage("Se Ingreso la mercancia")
                                    .setPositiveButton("aceptar", (dialogs, whichs) -> {
                                        Intent intent = new Intent(getApplicationContext(), TransferenciasActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.transition.in_left, R.transition.out_left);
                                        finish();
                                    })
                                    .create().show();
                        }else{
                            progressDialog.cancel();
                            new android.app.AlertDialog.Builder(this)
                                    .setTitle("Error")
                                    .setMessage("Algo Salio mal, intente nuevamente")
                                    .setPositiveButton("aceptar", (dialogs, whichs) -> {
                                    })
                                    .create().show();
                        }
                    }
                    else{
                        progressDialog.cancel();
                        Toast.makeText(DetalleTranferencias.this,
                                context.getResources().getString(R.string.msj_servidor_local_no_responde, sucursalRepository.GetDetalleSucursal().getKS_IP()),
                                Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .create().show();
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
            float _cantidad;
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
                        _cantidad = cursor.getFloat(6);
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
        // descSuper.setText(getString(R.string.cd_desc_super, item.getDescSuper()));
        setTextWithMarquee(descSuper, item.getDescSuper());
        unidad.setText(getString(R.string.cd_unidad, item.getUnidad()));
        guardar.setOnClickListener(v -> {
            ActualizaStatus(item, Float.parseFloat(cantidad.getText().toString()));
            dialog.dismiss();
        });
        cantidad.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                ActualizaStatus(item, Float.parseFloat(cantidad.getText().toString()));
                dialog.dismiss();
                return true;
            }
            return false;
        });
    }

    private void ActualizaStatus(listaDetalles item, float recibido) {
        if (recibido > item.getCantidad() || recibido < item.getCantidad()) {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Advertencia")
                    .setMessage("Hay diferencia con la cantidad enviada")
                    .setPositiveButton("si, continuar", (dialog, which) -> {
                        if (MovimientoAlmacenRepository.ActualizaStatus(item.getClave(), item.getIdPremovimientoAlmacen(), item.getIdArticulo(), this, recibido)) {
                            listTransferencias item2 = new listTransferencias( id_premovimiento_almacen,
                                    transferencia.getFolio()
                                    , transferencia.getObservaciones()
                                    ,transferencia.getSucOrigen()
                                   );
                            Intent intent = new Intent(this, DetalleTranferencias.class);
                            intent.putExtra("DetalleTransferencia", item2);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Volver a Contar", (dialog, which) -> dialog.dismiss())
                    .create().show();

        } else if (item.getCantidad() ==recibido) {
            if (MovimientoAlmacenRepository.ActualizaStatus(item.getClave(), item.getIdPremovimientoAlmacen(), item.getIdArticulo(), this, recibido)) {
                listTransferencias item2 = new listTransferencias(id_premovimiento_almacen,
                        transferencia.getFolio()
                        , transferencia.getObservaciones()
                        ,transferencia.getSucOrigen());
                Intent intent = new Intent(this, DetalleTranferencias.class);
                intent.putExtra("DetalleTransferencia", item2);
                startActivity(intent);
                finish();
            }
        }




       /* progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.sincronizando_dialog);*/


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