package com.example.home.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class DataBaseHelper {

    private final Context mCtx;
    final static String TABLE_SOLDIERS = "soldiers";
    final static String TABLE_PLATOON="platoon";
    final static String TABLE_COMPANY="company";
    final static String TABLE_BATTALION="battalion";
    final static String TABLE_REGIMENT="regiment";
    final static String TABLE_BRIGADE="brigade";
    final static String TABLE_DIVISION="division";
    final static String COLUMN_NAME="name";
    final static String COLUMN_NAME_UNITS="name_of_unit";
    final static String COLUMN_ID="_id";
    final static String COLUMN_ID_PLATOON="platoon";
    final static String COLUMN_ID_COMPANY="company";
    final static String COLUMN_ID_BATTALION="battalion";
    final static String COLUMN_ID_REGIMENT="regiment";
    final static String COLUMN_ID_BRIGADE="brigade";
    final static String COLUMN_ID_DIVISION="division";
    final static String COLUMN_KILLED="killed";



    private int DB_VERSION = 1;
    private DBHelper mDBHelper;

    protected SQLiteDatabase database;



    public DataBaseHelper(Context ctx) {
        mCtx = ctx;
    }


    public void open() {
        mDBHelper = new DBHelper(mCtx);
        database = mDBHelper.getWritableDatabase();
    }


    public void close() {

        if (mDBHelper != null) mDBHelper.close();
    }




    private class DBHelper extends SQLiteOpenHelper {

        DBHelper(Context context) {
            super(context, "myDB", null, DB_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("pragma foreign_keys=on");

            ContentValues cv = new ContentValues();

            db.execSQL("create table "+TABLE_SOLDIERS+" ("+
                    COLUMN_ID+" integer primary key autoincrement, "+
                    COLUMN_NAME+" text, "+
                    COLUMN_ID_PLATOON+" integer references "+TABLE_PLATOON+" ("+COLUMN_ID+"), " +
                    COLUMN_ID_COMPANY+" integer references "+TABLE_COMPANY+" ("+COLUMN_ID+"), "+
                    COLUMN_ID_BATTALION+" integer references "+TABLE_BATTALION+" ("+COLUMN_ID+"), "+
                    COLUMN_ID_REGIMENT+" integer references "+TABLE_REGIMENT+" ("+COLUMN_ID+"), "+
                    COLUMN_ID_BRIGADE+" integer references "+TABLE_BRIGADE+" ("+COLUMN_ID+"), "+
                    COLUMN_ID_DIVISION+" integer references "+TABLE_DIVISION+" ("+COLUMN_ID+"), "+
                    COLUMN_KILLED+" integer, "+
                   "id_platoon integer, id_company integer, id_battalion integer, id_regiment integer, "+
                    "id_brigade integer , id_division integer "+
                    ");");

            for (int i = 0; i <207360; i++) {
                cv.clear();
                cv.put(COLUMN_ID, i + 1);
                cv.put(COLUMN_NAME, "Солдат № " + (i + 1));
                cv.put(COLUMN_ID_PLATOON, i / 10 + 1);
                cv.put(COLUMN_ID_COMPANY, i / 80 + 1);
                cv.put(COLUMN_ID_BATTALION, i / 960 + 1);
                cv.put(COLUMN_ID_REGIMENT, i / 5760 + 1);
                cv.put(COLUMN_ID_BRIGADE, i / 34560 + 1);
                cv.put(COLUMN_ID_DIVISION, i / 207360 + 1);
                cv.put(COLUMN_KILLED,0);
                cv.put("id_platoon",0);
                cv.put("id_company",0);
                cv.put("id_battalion",0);
                cv.put("id_regiment",0);
                cv.put("id_brigade",0);
                cv.put("id_division",0);
                db.insert(TABLE_SOLDIERS, null, cv);
            }


            db.execSQL("create table "+TABLE_PLATOON+" ("
                    + COLUMN_ID+" integer primary key autoincrement," +
                    COLUMN_NAME_UNITS+" text "
                    + ");");

            for (int i = 0; i <20736; i++) {
                cv.clear();
                cv.put(COLUMN_ID, i+1);
                cv.put(COLUMN_NAME_UNITS, "Взвод № " +( i+1));
                db.insert(TABLE_PLATOON, null, cv);
            }

           db.execSQL("create table "+TABLE_COMPANY+" ("
                    + COLUMN_ID+" integer primary key autoincrement," +
                    COLUMN_NAME_UNITS+" text "
                    + ");");

           for (int i = 0; i <2592; i++) {
                cv.clear();
                cv.put(COLUMN_ID, i+1);
                cv.put(COLUMN_NAME_UNITS, "Рота № "+(i+1)  );
                db.insert(TABLE_COMPANY, null, cv);
           }

            db.execSQL("create table "+TABLE_BATTALION+" ("
                    + COLUMN_ID+" integer primary key autoincrement," +
                    COLUMN_NAME_UNITS+" text "
                    + ");");

           for (int i = 0; i <216; i++) {
                cv.clear();
                cv.put(COLUMN_ID, i+1);
                cv.put(COLUMN_NAME_UNITS,"Батальон № "+(i+1)  );
                db.insert(TABLE_BATTALION, null, cv);
           }

            db.execSQL("create table "+TABLE_REGIMENT+" ("
                    + COLUMN_ID+" integer primary key autoincrement," +
                    COLUMN_NAME_UNITS+" text "
                    + ");");

           for (int i = 0; i <36; i++) {
                cv.clear();
                cv.put(COLUMN_ID, i+1);
                cv.put(COLUMN_NAME_UNITS,"Полк № "+(i+1)  );
                db.insert(TABLE_REGIMENT, null, cv);
           }

            db.execSQL("create table "+TABLE_BRIGADE+" ("
                    + COLUMN_ID+" integer primary key autoincrement," +
                    COLUMN_NAME_UNITS+" text "
                    + ");");

           for (int i = 0; i <6; i++) {
                cv.clear();
                cv.put(COLUMN_ID, i+1);
                cv.put(COLUMN_NAME_UNITS,"Бригада № "+(i+1)  );
                db.insert(TABLE_BRIGADE, null, cv);
           }

            db.execSQL("create table "+TABLE_DIVISION+" ("
                    + COLUMN_ID+" integer primary key autoincrement," +
                    COLUMN_NAME_UNITS+" text "
                    + ");");

           for (int i = 0; i <1; i++) {
                cv.clear();
                cv.put(COLUMN_ID, i+1);
                cv.put(COLUMN_NAME_UNITS, "Дивизия № "+(i+1)  );
                db.insert(TABLE_DIVISION, null, cv);
           }

        }


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
