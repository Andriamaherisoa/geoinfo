package com.unlh.geoinfo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.unlh.geoinfo.helper.MySQLiteHelper;
import com.unlh.geoinfo.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImagesDataSouce {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITRE,
            MySQLiteHelper.COLUMN_IMAGEPATH,
            MySQLiteHelper.COLUMN_LAT,
            MySQLiteHelper.COLUMN_LON
    };

    public ImagesDataSouce(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Image createImage (Image image) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITRE, image.getTitre());
        values.put(MySQLiteHelper.COLUMN_IMAGEPATH, image.getImagepath());
        values.put(MySQLiteHelper.COLUMN_LAT, image.getLat());
        values.put(MySQLiteHelper.COLUMN_LON, image.getLon());
        long insertId = database.insert(MySQLiteHelper.TABLE_IMAGES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_IMAGES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Image newImage = cursorToImage(cursor);
        cursor.close();
        return newImage;
    }

    public void deleteImage(Image image) {
        long id = image.getId();
        System.out.println("Image to delete with id: " + id);
        database.delete(MySQLiteHelper.TABLE_IMAGES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Image> getAllImages() {
        List<Image> images = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_IMAGES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Image comment = cursorToImage(cursor);
            images.add(comment);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return images;
    }


    private Image cursorToImage(Cursor cursor) {
        Image image = new Image();
        image.setId(cursor.getLong(0));
        image.setTitre(cursor.getString(1));
        image.setImagepath(cursor.getString(2));
        image.setLon(cursor.getDouble(3));
        image.setLat(cursor.getDouble(4));
        return image;
    }
}
