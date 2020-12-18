package com.example.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DrinkActivity extends AppCompatActivity {

    private static final String TAG = "DrinkActivity";

    public static final String EXTRA_DRINKID = "drinkId";
    private int drinkId;
    private CheckBox checkBox;
    private SQLiteOpenHelper starbuzzDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        drinkId = (int) getIntent().getExtras().get(EXTRA_DRINKID);
        Log.i(TAG, Thread.currentThread().toString());


        starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try{
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[] {Integer.toString(drinkId)},
                    null, null, null);

            if (cursor.moveToFirst()){
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                //проверка любимый напиток или нет
                boolean isFavorite = (cursor.getInt(3) == 1);
                checkBox = findViewById(R.id.checkbox_favorite);
                checkBox.setChecked(isFavorite);

                TextView name = findViewById(R.id.name);
                name.setText(nameText);

                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);

                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
            }
            cursor.close();
            db.close();
        }catch (SQLException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void onFavoriteClicked(View view){

        new UpdateDrinkTask().execute(drinkId);

    }

    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean>{

        private ContentValues cv;

        //кладем значение чекбокса в CV
        @Override
        protected void onPreExecute() {
            checkBox = findViewById(R.id.checkbox_favorite);
            cv = new ContentValues();
            cv.put("FAVORITE", checkBox.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            int drinkId = integers[0];
            Log.i(TAG, Thread.currentThread().toString());
            starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
            try{
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("DRINK", cv, "_id = ?", new String[]{Integer.toString(drinkId)});
                db.close();
                return true;
            }catch (SQLException e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(!success)
                Toast.makeText(DrinkActivity.this, "DB is unavalable", Toast.LENGTH_SHORT).show();
        }
    }
}