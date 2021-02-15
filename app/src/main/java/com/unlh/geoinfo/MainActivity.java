package com.unlh.geoinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String currentImagePath = null;
    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySQLiteHelper sql = new MySQLiteHelper(this);
        setContentView(R.layout.activity_main);
    }

    /**
     * capturing image using camera and storing it the image path
     * @param view view obect
     */
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

    public void displayMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
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

    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}