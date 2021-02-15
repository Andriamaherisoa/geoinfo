package com.unlh.geoinfo;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unlh.geoinfo.dao.ImagesDataSouce;
import com.unlh.geoinfo.model.Image;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    private ImagesDataSouce dataSouce;
    private List<Image> images = new ArrayList<>();
    String currentImagePath = null;
    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dataSouce = new ImagesDataSouce(this);
        dataSouce.open();

        images = dataSouce.getAllImages();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        LatLng Pos = new LatLng(49.49715574384904, 0.1266454727250322);
        map.addMarker(new MarkerOptions().position(Pos).title("Université du Havre"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Pos));

        for(Image i: images) {
            Bitmap bitmap = BitmapFactory.decodeFile(i.getImagepath());
            Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, 84, 84, false);
            LatLng imageLatLng = new LatLng(i.getLat(), i.getLon());
            map.addMarker(new MarkerOptions()
                                .position(imageLatLng)
                                .title((i.getTitre() != null ? i.getTitre() : ""))
                                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        }
    }

    public void captureImage(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(cameraIntent.resolveActivity(getPackageManager()) != null) {
            File imageFile = null;
            try {
                imageFile = getImageFile();
            }catch(IOException e){
                e.printStackTrace();
            }

            if(imageFile != null){
                Uri imageUri = FileProvider.getUriForFile(this, "com.unlh.geoinfo.fileprovider", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, IMAGE_REQUEST);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                displayImage();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "CANCELED ", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void displayImage() {
        Intent intent = new Intent(this, DisplayImage.class);
        intent.putExtra("image_path", currentImagePath);
        startActivity(intent);
    }

    private File getImageFile () throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMDD_HHmmss").format(new Date());
        String imageName = "jpg_" + timestamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName,".jpg", storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }
}