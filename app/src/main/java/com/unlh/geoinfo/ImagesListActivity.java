package com.unlh.geoinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class ImagesListActivity extends ListActivity {
    private ImagesDataSouce dataSouce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_list);

        dataSouce = new ImagesDataSouce(this);
        dataSouce.open();

        List<Image> values = dataSouce.getAllImages();
        ArrayAdapter<Image> adapter = new ArrayAdapter<Image>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        dataSouce.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSouce.close();
        super.onPause();
    }
}