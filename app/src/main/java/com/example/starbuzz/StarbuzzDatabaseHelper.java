package com.example.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;

    public StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }


    private static void insertDrink(SQLiteDatabase db, String name, String description, int imgResId){
        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        cv.put("DESCRIPTION", description);
        cv.put("IMAGE_RESOURCE_ID", imgResId);
        db.insert("DRINK", null, cv);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion <1){
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT," +
                    "DESCRIPTION TEXT," +
                    "IMAGE_RESOURCE_ID INTEGER);");
            insertDrink(db, "Latte", "Эспрессо и много молочной пенки", R.drawable.latte);
            insertDrink(db, "Cappucino", "Эспрессо и молочная шапочка сверху", R.drawable.cappucino);
            insertDrink(db, "Filter", "Чистейщий эсктракт кофейных зерен, вытряхивающий ваше нутро наружу", R.drawable.filter);
        }
        if(oldVersion < 2){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC");
        }
    }
}
