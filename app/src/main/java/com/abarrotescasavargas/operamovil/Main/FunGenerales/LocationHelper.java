package com.abarrotescasavargas.operamovil.Main.FunGenerales;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.*;

public class LocationHelper {

    private static final int PERMISSIONS_REQUEST = 1;

    public static void getDeviceLocation(Context context, OnLocationResultListener locationResultListener) {
        if (checkLocationPermission(context)) {
            FusedLocationProviderClient fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context);

            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(10000); // Intervalo de actualización en milisegundos
            locationRequest.setFastestInterval(5000); // Intervalo de actualización más rápido en milisegundos
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Location lastLocation = locationResult.getLastLocation();
                    if (lastLocation != null) {
                        double latitude = lastLocation.getLatitude();
                        double longitude = lastLocation.getLongitude();
                        locationResultListener.onLocationResult(latitude, longitude);
                    }
                }
            }, Looper.getMainLooper());
        } else {
            Toast.makeText(context, "Permiso de ubicación denegado.", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean checkLocationPermission(Context context) {
        int permissionState = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public interface OnLocationResultListener {
        void onLocationResult(double latitude, double longitude);
    }
}
