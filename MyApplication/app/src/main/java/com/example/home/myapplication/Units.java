package com.example.home.myapplication;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class Units extends AppCompatActivity  {
    ListView listView;
    Cursor cursor;
    DataBaseHelper sqlHelper;
    SimpleCursorAdapter userAdapter;
    String unit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soldier);
        listView = (ListView) findViewById(R.id.lvData);
        sqlHelper = new DataBaseHelper(getApplicationContext());
        sqlHelper = new DataBaseHelper(this);
        Intent intent = getIntent();
        unit = intent.getStringExtra("unit");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(Units.this, Soldiers.class);
                intent1.putExtra("id",position);
                intent1.putExtra("units",unit);
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        sqlHelper.open();
        cursor = sqlHelper.database.rawQuery("Select * from "+unit+" ", null);
        String[] headers = new String[]{DataBaseHelper.COLUMN_NAME_UNITS};
        userAdapter = new SimpleCursorAdapter(this, R.layout.soldier_item,
                cursor, headers, new int[]{R.id.soldier}, 0);
        listView.setAdapter(userAdapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        sqlHelper.close();
        cursor.close();
    }

}
