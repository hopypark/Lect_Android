package com.example.singlediary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class NoteDatabase {
    private final static String TAG ="NoteDatabase";

    private static NoteDatabase database;
    public static String TABLE_NOTE = "NOTE";
    public static int DATABASE_VERSION = 1;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public NoteDatabase(Context context) {
        this.context = context;
    }
    public static NoteDatabase getInstance(Context context){
        if(database == null) database = new NoteDatabase(context);
        return database;
    }

    public boolean open(){
        println("opening database [" + AppConstants.DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return  true;
    }

    public void close(){
        println("closing database [" + AppConstants.DATABASE_NAME + "].");
        db.close();
        database = null;
    }

    public Cursor rawQuery(String sql){
        println("\nexecuteQuery called.\n");

        Cursor cursor = null;
        try{
            cursor = db.rawQuery(sql, null);
            println("cursor count: " + cursor.getCount());
        } catch (Exception e){
            Log.d(TAG, "Exception in executeQuery", e);
        }
        return cursor;
    }

    public boolean execSQL(String sql){
        println("\nexecute called.\n");

        try{
            Log.d(TAG, "SQL: " + sql);
            db.execSQL(sql);
        } catch (Exception e){
            Log.d(TAG, "Exception in executeQuery", e);
            return false;
        }
        return true;
    }



    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, AppConstants.DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            println("Creating database [" + AppConstants.DATABASE_NAME + "].");
            println("create table [" + TABLE_NOTE + "].");

            // drop sql
            String DROP_SQL = "drop table if exists " + TABLE_NOTE;
            try {
                db.execSQL(DROP_SQL);
            } catch (Exception e){
                Log.e(TAG, "Exception in DROP_SQL", e);
            }
            // create sql
            String CREATE_SQL = "create table " + TABLE_NOTE + "("
                    + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + " WEATHER TEXT DEFAULT '', "
                    + " ADDRESS TEXT DEFAULT '', "
                    + " LOCATION_X TEXT DEFAULT '', "
                    + " LOCATION_Y TEXT DEFAULT '', "
                    + " CONTENTS TEXT DEFAULT '', "
                    + " MOOD TEXT, "
                    + " PICTURE TEXT DEFAULT '', "
                    + " CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + " MODIFY_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";

            try {
                db.execSQL(CREATE_SQL);
            } catch (Exception e) {
                Log.d(TAG, "Exception in CREATE_SQL" + e);
            }
            // create index sql
            String CREATE_INDEX_SQL = "create index " + TABLE_NOTE + "_IDX ON " + TABLE_NOTE + "("
                    + "CREATE_DATE"
                    + ")";
            try {
                db.execSQL(CREATE_INDEX_SQL);
            }catch (Exception e){
                Log.d(TAG, "Exception in CREATE_SQL", e);
            }
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
//            super.onOpen(db);
            println("Opened database [" + AppConstants.DATABASE_NAME + "].");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion);
        }
    }

    private void println(String data){
        Log.d(TAG, data);
    }
}
