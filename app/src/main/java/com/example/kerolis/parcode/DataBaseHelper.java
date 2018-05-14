package com.example.kerolis.parcode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kerolis on 17/11/04.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Sales.db";
    public static final String TABLE_NAME = "Sales_table";

    public static final String COL2 = "SERIAL";
    public static final String COL3 = "PRICE";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT , SERIAL TEXT , PRICE INT(4)) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);

    }

    public boolean inseartData(String sreal, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, sreal);
        contentValues.put(COL3, price);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1) {
            return false;
        } else return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        return res;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME, "ID=?", new String[]{id});
        return i;
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        if (res != null && res.getCount() > 0) {
            while ((res.moveToNext()))
                db.delete(TABLE_NAME, "ID=?", new String[]{res.getInt(0) + ""});
        }

    }

    public int qsearch(String serial) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        if (res != null && res.getCount() > 0) {
            while ((res.moveToNext()))
                if (res.getString(1).equals(serial)) {
                    return res.getInt(2);
                }

        }

        return -1;


    }


}




