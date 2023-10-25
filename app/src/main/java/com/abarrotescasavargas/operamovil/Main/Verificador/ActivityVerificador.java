package com.abarrotescasavargas.operamovil.Main.Verificador;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.abarrotescasavargas.operamovil.Main.BaseDatos.BD_SQL;
import com.abarrotescasavargas.operamovil.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ActivityVerificador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaner);

        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
        options.setPrompt("Escanear QR");
        options.setCameraId(0);
        options.setOrientationLocked(false);
        options.setBeepEnabled(true);
        options.setCaptureActivity(CaptureActivityPortrait.class);
        options.setBarcodeImageEnabled(false);
        BarcodeLauncher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> BarcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            consultaSQL();



        }
    });

    private void consultaSQL(){
        BD_SQL.tabla("SELECT * FROM articulo WHERE activo = 1",false,this);

    }
}