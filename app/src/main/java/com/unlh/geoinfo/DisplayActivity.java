package com.unlh.geoinfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;


public class DisplayActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int FINE_LOCATION_PERMISSION_CODE = 100;
    private static final int COARSE_LOCATION_PERMISSION_CODE = 101;

    private FusedLocationProviderClient fusedLocationClient;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public void getLocation(View view) {
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_PERMISSION_CODE);
        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, COARSE_LOCATION_PERMISSION_CODE);

        // TODO: Cancellation token
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        }).addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    TextView tv = findViewById(R.id.text);
                    tv.setText(getString(R.string.location, location.getLongitude(), location.getLatitude()));
                } else {
                    System.out.println("location null");
                }
            }
        });
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode) {
        if (ActivityCompat.checkSelfPermission(DisplayActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(DisplayActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(DisplayActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == FINE_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DisplayActivity.this,
                        "Fine Location Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(DisplayActivity.this,
                        "Fine Location Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == COARSE_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DisplayActivity.this,
                        "Coarse Location Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(DisplayActivity.this,
                        "Coarse Location Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}