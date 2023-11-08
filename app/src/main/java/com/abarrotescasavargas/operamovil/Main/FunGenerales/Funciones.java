package com.abarrotescasavargas.operamovil.Main.FunGenerales;

import static com.abarrotescasavargas.operamovil.Main.FunGenerales.Variables._IPGODADDY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.abarrotescasavargas.operamovil.Main.Entidades.Sucursales;
import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;
import com.abarrotescasavargas.operamovil.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Funciones {
    public static boolean ValidaConexionServidorWeb() {
        try {
            String Comando = "ping -c 1 " + _IPGODADDY;
            Process p = Runtime.getRuntime().exec(Comando);
            int val = p.waitFor();
            return (val == 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayAdapter<String> CargaSpinnerSucursales(Context context) {
        SucursalRepository sucursalRepository = new SucursalRepository(context);
        ArrayList<Sucursales> listasucursales = sucursalRepository.GetListaSucursales();
        ArrayList<String> listaSucursales = new ArrayList<>();
        listaSucursales.add(context.getResources().getString(R.string.Sp_elija_sucursal));
        for (Sucursales sucursal : listasucursales) {
            String cveSucursal = sucursal.getKS_CVESUC();
            String nomSucursal = sucursal.getKS_NOMSUC();
            listaSucursales.add(cveSucursal + "-" + nomSucursal);
        }
        return new ArrayAdapter<>(context, R.layout.cmb_item, listaSucursales);
    }

    public static boolean ValidaConexionServidorLocal(Context context) {
        try {
            if(ValidaWifi(context)){
                SucursalRepository sucursalRepository = new SucursalRepository(context);
                String Comando = "ping -c 1 " + sucursalRepository.GetDetalleSucursal().getKS_IP();
                Process p = Runtime.getRuntime().exec(Comando);
                int val = p.waitFor();
                boolean reachable = (val == 0);
                if (!reachable) {
                    Toast.makeText(context.getApplicationContext(),
                            context.getResources().getString(R.string.msj_servidor_local_no_responde,sucursalRepository.GetDetalleSucursal().getKS_IP()),
                            Toast.LENGTH_LONG).show();
                }
                return reachable;
            }
            else{
                Toast.makeText(context.getApplicationContext(), R.string.Cn_activar_wifi, Toast.LENGTH_LONG).show();
                return  false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean ValidaConexionServidorLocalSinToast(Context context) {
        try {
            if(ValidaWifi(context)){
                SucursalRepository sucursalRepository = new SucursalRepository(context);
                String Comando = "ping -c 1 " + sucursalRepository.GetDetalleSucursal().getKS_IP();
                Process p = Runtime.getRuntime().exec(Comando);
                int val = p.waitFor();
                boolean reachable = (val == 0);
                return reachable;
            }
            else{
                return  false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean ValidaWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
           return  activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        }
        else return false;
    }

    public static boolean ValidaDatos(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            return  activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        else return false;
    }

    public static Bitmap decodeFile(String path) {
        try {
            BitmapFactory.Options bounds = new BitmapFactory.Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bounds);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            final int REQUIRED_SIZE = 100; // 75 tenia 1000
            int scale = 1; //
            while (opts.outWidth / scale / 2 >= REQUIRED_SIZE && opts.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            opts.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeFile(path, opts);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
            return Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        } catch (Throwable e) {

            e.printStackTrace();
            // Cambio Rafael Lemus
        }
        return null;
    }

    public static File ComprimeBitmapToFile(File file) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();
            final int REQUIRED_SIZE = 250;
            int scale = 2;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);
            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream(file);
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream); // originalmente tenía 100 y aún asi la compresión ya era muy buena pero habra que ver
            return file;
        } catch (Exception e) {
            return null;
        }
    }


}



