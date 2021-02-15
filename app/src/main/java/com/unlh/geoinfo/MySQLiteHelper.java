package com.unlh.geoinfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_IMAGES = "images";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITRE = "titre";
    public static final String COLUMN_IMAGEPATH = "imagepath";
    public static final String COLUMN_LON = "lon";
    public static final String COLUMN_LAT = "lat";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_IMAGES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TITRE
            + " text not null, " + COLUMN_IMAGEPATH
            + " double not null, " + COLUMN_LON
            + " double not null, " + COLUMN_LAT
            + " text not null"+ ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(db);
    }
}