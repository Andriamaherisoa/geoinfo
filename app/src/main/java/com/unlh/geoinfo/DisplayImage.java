package com.unlh.geoinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.unlh.geoinfo.dao.ImagesDataSouce;
import com.unlh.geoinfo.model.Image;

import java.io.File;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;


public class DisplayImage extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    ImageView imageView;
    EditText titreEditText;
    private static final int FINE_LOCATION_PERMISSION_CODE = 100;
    private static final int COARSE_LOCATION_PERMISSION_CODE = 101;

    private FusedLocationProviderClient fusedLocationClient;
    private ImagesDataSouce dataSouce;
    private String imagePath;

    @RequiresApi(api = Build.VERSION_CODES.P)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        imageView = findViewById(R.id.imageView);
        titreEditText = findViewById(R.id.titreEditText);

        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("image_path"));
        imageView.setImageBitmap(bitmap);
        this.imagePath = getIntent().getStringExtra("image_path");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation();
        dataSouce = new ImagesDataSouce(this);
        dataSouce.open();
    }

    public void getLocation() {
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_PERMISSION_CODE);
        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, COARSE_LOCATION_PERMISSION_CODE);

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
        });
    }

    public void cancel(View view) {
        File file = new File(imagePath);
        file.delete();
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void sendData(View view) {
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_PERMISSION_CODE);
        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, COARSE_LOCATION_PERMISSION_CODE);

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    String titre = titreEditText.getText().toString();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    dataSouce.createImage(new Image(titre, latitude, longitude, imagePath));
                    navigateToMain();
                }
            }
        });
    }

    void navigateToMain() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode) {
        if (ActivityCompat.checkSelfPermission(DisplayImage.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(DisplayImage.this,
                    new String[]{permission},
                    requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == FINE_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DisplayImage.this,
                        "Fine Location Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(DisplayImage.this,
                        "Fine Location Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == COARSE_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DisplayImage.this,
                        "Coarse Location Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(DisplayImage.this,
                        "Coarse Location Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    protected void onResume() {
        dataSouce.open();
        super.onResume();
    }

    protected void onPause() {
        dataSouce.close();
        super.onPause();
    }
}