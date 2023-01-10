package com.androidproject.hokipercetakan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPercetakan extends RecyclerView.Adapter<AdapterPercetakan.ViewHolderPercetakan> {
    private Context ctx;
    private ArrayList arrID, arrNama, arrJenis, arrHarga;

    public AdapterPercetakan(Context ctx, ArrayList arrID, ArrayList arrNama, ArrayList arrJenis, ArrayList arrHarga) {
        this.ctx = ctx;
        this.arrID = arrID;
        this.arrNama = arrNama;
        this.arrJenis = arrJenis;
        this.arrHarga = arrHarga;
    }

    @NonNull
    @Override
    public AdapterPercetakan.ViewHolderPercetakan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater pompa = LayoutInflater.from(ctx);
        View view = pompa.inflate(R.layout.card_item, parent, false);
        return new ViewHolderPercetakan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPercetakan.ViewHolderPercetakan holder, int position) {
        holder.tvId.setText(arrID.get(position).toString());
        holder.tvNama.setText(arrNama.get(position).toString());
        holder.tvJenis.setText(arrJenis.get(position).toString());
        holder.tvHarga.setText(arrHarga.get(position).toString());

        holder.cvProduk.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder jendelaPesan = new AlertDialog.Builder(ctx);
                jendelaPesan.setMessage("Pilih printah yang diinginkan !");
                jendelaPesan.setTitle("Perhatian");
                jendelaPesan.setCancelable(true);

                jendelaPesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDatabaseHelper myDb = new MyDatabaseHelper(ctx);
                        long eksekusi = myDb.hapusProduk(arrID.get(position).toString());

                        if (eksekusi == -1){
                            Toast.makeText(ctx, "Gagal Menghapus data !", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ctx, "Data berhasil di hapus", Toast.LENGTH_SHORT).show();
                            if (position==0){
                                MainActivity.posisiData = 0;
                            }else {
                                MainActivity.posisiData = position - 1;
                            }
                            dialog.dismiss();
                            ((MainActivity) ctx).onResume();
                        }
                    }
                });

                jendelaPesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent pindah = new Intent(ctx, UbahActivity.class);
                        pindah.putExtra("varID", arrID.get(position).toString());
                        pindah.putExtra("varJudul", arrNama.get(position).toString());
                        pindah.putExtra("varPenulis", arrJenis.get(position).toString());
                        pindah.putExtra("varTahun", arrHarga.get(position).toString());
                        pindah.putExtra("varPosisi", position);
                        ctx.startActivity(pindah);

                    }
                });

                jendelaPesan.show();
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrNama.size();
    }

    public class ViewHolderPercetakan extends RecyclerView.ViewHolder {
        TextView tvId, tvNama, tvJenis, tvHarga;
        CardView cvProduk;

        public ViewHolderPercetakan(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvJenis = itemView.findViewById(R.id.tv_jenis);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            cvProduk = itemView.findViewById(R.id.cv_produk);
        }
    }
}
