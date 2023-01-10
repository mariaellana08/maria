package com.androidproject.hokipercetakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvProduk;
    MyDatabaseHelper myDB;
    AdapterPercetakan adapterPercetakan;
    ArrayList<String> arrID, arrNama, arrJenis, arrHarga;
    public static int posisiData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new MyDatabaseHelper(MainActivity.this);
    }

    public void bukaActivityTambah(View view) {
        startActivity(new Intent(MainActivity.this, TambahActivity.class));
    }

    private void SQLiteToArrayList(){
        Cursor cursor = myDB.bacaSemuaData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                arrID.add(cursor.getString(0));
                arrNama.add(cursor.getString(1));
                arrJenis.add(cursor.getString(2));
                arrHarga.add(cursor.getString(3));
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        arrID = new ArrayList<>();
        arrNama = new ArrayList<>();
        arrJenis = new ArrayList<>();
        arrHarga = new ArrayList<>();

        SQLiteToArrayList();

        rvProduk = findViewById(R.id.rv_produk);
        adapterPercetakan = new AdapterPercetakan(MainActivity.this,arrID, arrNama, arrJenis, arrHarga);
        rvProduk.setAdapter(adapterPercetakan);
        rvProduk.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvProduk.smoothScrollToPosition(posisiData);
    }
}