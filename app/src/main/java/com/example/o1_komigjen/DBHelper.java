package com.example.o1_komigjen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "oblig1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table images(id INTEGER primary key AUTOINCREMENT, name TEXT, uri TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists images");
    }
    public void insertUserData(String name, @NonNull Uri imgUri) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("uri", imgUri.toString());
        DB.insert("images", null, contentValues);
    }

    public Cursor getAllData() {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from images", null);
        return cursor;
    }
    public Uri getFirstImage() {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from images Order By name", null);
        cursor.moveToFirst();
        return Uri.parse(cursor.getString(2));
    }
    public Cursor getInOrder() {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from images Order By name", null);
        return cursor;
    }

    public Cursor getInReverseOrder() {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from images Order By name desc", null);
        return cursor;
    }

    public void deleteFromId(int id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("images", "id=?", new String[]{String.valueOf(id)});
    }

    public void deleteALL(){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("delete from images");
    }

}
