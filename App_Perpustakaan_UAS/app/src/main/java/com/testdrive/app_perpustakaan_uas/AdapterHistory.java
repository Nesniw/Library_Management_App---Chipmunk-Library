package com.testdrive.app_perpustakaan_uas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ImageViewHolder>{
    Context context;
    List<ProductPeminjaman> productLists;

    //generate constructor
    public AdapterHistory(Context context, List<ProductPeminjaman> productList) {
        this.context = context;
        this.productLists = productList;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singledatahistory,null);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final ProductPeminjaman product = productLists.get(position);
        holder.nama.setText(productLists.get(position).getNama());
        holder.tglpinjam.setText(productLists.get(position).getTglpinjam());
        holder.status.setText(productLists.get(position).getStatus());
        holder.relative.setOnClickListener(view -> {
            Intent secondActivity = new Intent(context, ViewHistory.class);
            secondActivity.putExtra("kd_peminjaman", product.getKodepeminjam());
            context.startActivity(secondActivity);
        });

    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        TextView tglpinjam;
        TextView status;
        View relative;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            nama   = itemView.findViewById(R.id.txtNamaPeminjam);
            tglpinjam      = itemView.findViewById(R.id.txttglPinjam);
            status      = itemView.findViewById(R.id.txtStatus);
            relative = itemView.findViewById(R.id.relative);
        }
    }
}
