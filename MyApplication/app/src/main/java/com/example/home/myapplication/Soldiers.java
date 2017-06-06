package com.example.home.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Soldiers extends AppCompatActivity {
    ListView listView,leader;
    Cursor cursor,leaderCursor;
    int positio;
    DataBaseHelper sqlHelper;
    SimpleCursorAdapter userAdapter,leaderAdapter;
    String unit;
    int posit = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soldier);
        listView = (ListView) findViewById(R.id.lvData);
        sqlHelper = new DataBaseHelper(getApplicationContext());
        sqlHelper = new DataBaseHelper(this);
        int defaultValue = 0;
        leader = (ListView)findViewById(R.id.lvLeader);
        Intent intent1 = getIntent();
        positio= intent1.getIntExtra("id",defaultValue);
        unit = intent1.getStringExtra("units");
        positio = positio+1;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               posit=(position+1)+(10*(positio-1));
                AlertDialog.Builder builder=new AlertDialog.Builder(Soldiers.this);
                builder.setMessage(R.string.killed).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int lead = cursor.getInt(cursor.getColumnIndex("id_"+unit));
                        sqlHelper.database.execSQL("update "+DataBaseHelper.TABLE_SOLDIERS+" set "+
                                DataBaseHelper.COLUMN_KILLED+" = 1 where "+DataBaseHelper.COLUMN_ID+" ="+posit+";");
                        if (lead==1){
                            sqlHelper.database.execSQL("update "+DataBaseHelper.TABLE_SOLDIERS+" set id_"+
                                    unit+" = 0 where "+DataBaseHelper.COLUMN_ID+" ="+posit+";");
                            sqlHelper.database.execSQL("update "+DataBaseHelper.TABLE_SOLDIERS+" set id_"+
                                    unit+" = 1 where "+DataBaseHelper.COLUMN_ID+" ="+(posit-1)+";");
                        }
                        Toast.makeText(getApplicationContext(),R.string.message,Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialogn = builder.create();
                alertDialogn.show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        sqlHelper.open();
        cursor = sqlHelper.database.rawQuery("Select * from " + DataBaseHelper.TABLE_SOLDIERS +
                 " s inner join " + unit + " u on s." + unit + "=u." + DataBaseHelper.COLUMN_ID +
                 " where u." + DataBaseHelper.COLUMN_ID + " =" + positio+
                 " and s."+DataBaseHelper.COLUMN_KILLED+"=0", null);
        String[] headers = new String[]{DataBaseHelper.COLUMN_NAME};
        userAdapter = new SimpleCursorAdapter(this, R.layout.soldier_item,
                    cursor, headers, new int[]{R.id.soldier}, 0);
        listView.setAdapter(userAdapter);
        leaderCursor = sqlHelper.database.rawQuery("Select * from " + DataBaseHelper.TABLE_SOLDIERS +
                " s inner join " + unit + " u on s." + unit + "=u." + DataBaseHelper.COLUMN_ID +
                " where u." + DataBaseHelper.COLUMN_ID + " =" + positio+" and s.id_"+unit+" = 1 "+
                "and s."+DataBaseHelper.COLUMN_KILLED+"=0",null);
        leaderAdapter = new SimpleCursorAdapter(this, R.layout.leader_item, leaderCursor, headers,
                new int[]{R.id.leader}, 0);
        leader.setAdapter(leaderAdapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sqlHelper.close();
        cursor.close();
    }

}


