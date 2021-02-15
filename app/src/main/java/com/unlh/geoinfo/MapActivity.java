package com.unlh.geoinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    private ImagesDataSouce dataSouce;
    private List<Image> images = new ArrayList<>();

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
}