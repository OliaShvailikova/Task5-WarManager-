package com.example.home.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity  {

    DataBaseHelper sqlHelper;
    Button return_bd;
    Cursor cursor;
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        return_bd=(Button)findViewById(R.id.update);
        sqlHelper = new DataBaseHelper(getApplicationContext());
        sqlHelper = new DataBaseHelper(this);
        sqlHelper.open();
        if(j==0){
            for (int i =0; i<207360;i++) {
                sqlHelper.database.execSQL("update "+ DataBaseHelper.TABLE_SOLDIERS+ " set "+
                        "id_platoon = 1 where "+DataBaseHelper.COLUMN_ID+" = "+(i+10)+";");
                i=i+9;
            }
            for (int i = 0; i < 207360; i++) {
                sqlHelper.database.execSQL("update " + DataBaseHelper.TABLE_SOLDIERS + " set " +
                        "id_company = 1 where " + DataBaseHelper.COLUMN_ID + " =" + (i + 60) + ";");
                i = i + 79;
            }
            for (int i = 0; i < 207360; i++) {
                sqlHelper.database.execSQL("update " + DataBaseHelper.TABLE_SOLDIERS + " set " +
                        "id_battalion = 1 where " + DataBaseHelper.COLUMN_ID + " =" + (i + 400) + ";");
                i = i + 959;
            }
            for (int i = 0; i < 207360; i++) {
                sqlHelper.database.execSQL("update " + DataBaseHelper.TABLE_SOLDIERS + " set " +
                        "id_regiment = 1 where " + DataBaseHelper.COLUMN_ID + " =" + (i + 3000) + ";");
                i = i + 5759;
            }
            for (int i = 0; i < 207360; i++) {
                sqlHelper.database.execSQL("update " + DataBaseHelper.TABLE_SOLDIERS + " set " +
                        "id_brigade = 1 where " + DataBaseHelper.COLUMN_ID + " =" + (i + 20000) + ";");
                i = i + 34559;
            }
            for (int i = 0; i < 207360; i++) {
                sqlHelper.database.execSQL("update " + DataBaseHelper.TABLE_SOLDIERS + " set " +
                        "id_division = 1 where " + DataBaseHelper.COLUMN_ID + " =" + (i + 200000) + ";");
                i = i + 207360;
            }
        }
    }

    public void setCursor(String unit){
        Intent intent = new Intent(MainActivity.this,Units.class);
        intent.putExtra("unit",unit);
        startActivity(intent);
    }

    public void onButtonClick(View view) {
        cursor =sqlHelper.database.rawQuery("Select * from "+DataBaseHelper.TABLE_SOLDIERS +
                " where "+DataBaseHelper.COLUMN_KILLED+"= 1", null);
        if (cursor!=null){
           if (cursor.moveToFirst()){
               do {
                 sqlHelper.database.execSQL("update " + DataBaseHelper.TABLE_SOLDIERS + " set " +
                        DataBaseHelper.COLUMN_KILLED+" = 0 where "+DataBaseHelper.COLUMN_ID+" = "+
                 cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID))+";");
               } while (cursor.moveToNext());
           }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_division:
                setCursor(DataBaseHelper.TABLE_DIVISION);
                return true;
            case R.id.action_brigade:
                setCursor(DataBaseHelper.TABLE_BRIGADE);
                return true;
            case R.id.action_regiment:
                setCursor(DataBaseHelper.TABLE_REGIMENT);
                return true;
            case R.id.action_battalion:
                setCursor(DataBaseHelper.TABLE_BATTALION);
                return true;
            case R.id.action_company:
                setCursor(DataBaseHelper.TABLE_COMPANY);
                return true;
            case R.id.action_platoon:
                setCursor(DataBaseHelper.TABLE_PLATOON);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        sqlHelper.close();
    }

}

