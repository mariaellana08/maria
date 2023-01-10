package com.androidproject.hokipercetakan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context ctx;
    private static final String DATABASE_NAME = "db_percetakan";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tbl_produk";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAMA_PRODUK = "nama";
    private static final String FIELD_JENIS_PRODUK = "jenis";
    private static final String FIELD_HARGA_PRODUK = "harga";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIELD_NAMA_PRODUK + " TEXT, " +
                FIELD_JENIS_PRODUK + " TEXT, " + FIELD_HARGA_PRODUK + " INTEGER ); " ;

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long tambahBuku(String nama, String jenis, int harga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIELD_NAMA_PRODUK, nama);
        cv.put(FIELD_JENIS_PRODUK, jenis);
        cv.put(FIELD_HARGA_PRODUK, harga);

        long eksekusi = db.insert(TABLE_NAME, null, cv);

        return eksekusi;
    }

    public long ubahProduk(String id, String nama, String jenis, int harga){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIELD_NAMA_PRODUK, nama);
        cv.put(FIELD_JENIS_PRODUK, jenis);
        cv.put(FIELD_HARGA_PRODUK, harga);

        long eksekusi = db.update(TABLE_NAME, cv, "id = ?", new String[]{id});
        return eksekusi;
    }

    public long hapusProduk(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        long eksekusi = db.delete(TABLE_NAME,"id = ?", new String[]{id});
        return eksekusi;
    }

    public Cursor bacaSemuaData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
}
