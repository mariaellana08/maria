package com.androidproject.hokipercetakan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UbahActivity extends AppCompatActivity {
    private EditText etNama, etJenis, etHarga;
    private Button btnUbah;
    private String getID, getNama, getJenis, getHarga;
    private int getPosisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);
        etNama = findViewById(R.id.et_nama);
        etJenis = findViewById(R.id.et_jenis);
        etHarga = findViewById(R.id.et_harga);
        btnUbah = findViewById(R.id.btn_ubah);

        Intent terima = getIntent();
        getID = terima.getStringExtra("varID");
        getNama = terima.getStringExtra("varNama");
        getJenis = terima.getStringExtra("varJenis");
        getHarga = terima.getStringExtra("varHarga");
        getPosisi = terima.getIntExtra("varPosisi", -1);

        etNama.setText(getNama);
        etJenis.setText(getJenis);
        etHarga.setText(getHarga);


        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtNama = etNama.getText().toString();
                String txtJenis = etJenis.getText().toString();
                String txtHarga = etHarga.getText().toString();

                if (txtNama.trim().equals("")){
                    etNama.setError("Nama Produk Tidak Boleh Kosong");
                }
                else if (txtJenis.trim().equals("")){
                    etJenis.setError("Jenis Produk Tidak Boleh Kosong");
                }
                else if (txtHarga.trim().equals("")){
                    etHarga.setError("Harga Produk Tidak Boleh Kosong");
                }else {
                    MyDatabaseHelper myDB = new MyDatabaseHelper(UbahActivity.this);
                    long eksekusi = myDB.ubahProduk(getID, txtNama, txtJenis, Integer.valueOf(txtHarga));

                    if (eksekusi == -1){
                        Toast.makeText(UbahActivity.this, "Gagal mengubah data", Toast.LENGTH_SHORT).show();
                        etNama.requestFocus();
                    } else {
                        Toast.makeText(UbahActivity.this, "Berhasil menngubah data", Toast.LENGTH_SHORT).show();
                        MainActivity.posisiData = getPosisi;
                        finish();
                    }
                }

            }
        });
    }
}