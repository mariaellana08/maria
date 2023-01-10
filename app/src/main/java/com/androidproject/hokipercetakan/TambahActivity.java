package com.androidproject.hokipercetakan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etJenis, etHarga;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etJenis = findViewById(R.id.et_jenis);
        etHarga = findViewById(R.id.et_harga);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNama = etNama.getText().toString();
                String getJenis = etJenis.getText().toString();
                String getHarga = etHarga.getText().toString();

                if (getNama.trim().equals("")){
                    etNama.setError("Nama produk Tidak Boleh Kosong");
                }
                else if (getJenis.trim().equals("")){
                    etJenis.setError("Jenis Produk Tidak Boleh Kosong");
                }
                else if (getHarga.trim().equals("")){
                    etHarga.setError("Harga Produk Tidak Boleh Kosong");
                }
                else {
                    MyDatabaseHelper myDB = new MyDatabaseHelper(TambahActivity.this);
                    long eksekusi = myDB.tambahBuku(getNama, getJenis, Integer.valueOf(getHarga));

                    if (eksekusi == -1){
                        Toast.makeText(TambahActivity.this, "Gagal Menambah Data !", Toast.LENGTH_SHORT).show();
                        etNama.requestFocus();
                    } else {
                        Toast.makeText(TambahActivity.this, "Tambah Data Berhasil", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}