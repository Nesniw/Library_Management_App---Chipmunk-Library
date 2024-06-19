package com.testdrive.app_perpustakaan_uas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPilihBuku extends RecyclerView.Adapter<AdapterPilihBuku.ImageViewHolder>{
    Context context, link;
    List<ProductBuku> productLists;

    //generate constructor
    public AdapterPilihBuku(Context context, List<ProductBuku> productList) {
        this.context = context;
        this.productLists = productList;
    }
    @NonNull
    @Override
    public AdapterPilihBuku.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singledatapilihbuku,null);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final ProductBuku product = productLists.get(position);
        Picasso.get().load(productLists.get(position).getImages()).into(holder.imageView);
        holder.judul.setText(productLists.get(position).getJudul());
        holder.pengarang.setText(productLists.get(position).getPengarang());
        holder.pinjam.setOnClickListener(view -> {
                    Intent secondActivity = new Intent(context, InputPeminjam.class);
                    secondActivity.putExtra("kd_buku", product.getKodeBuku());
                    context.startActivity(secondActivity);

                    productLists.remove(position);
                }
        );
    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView judul;
        TextView pengarang;
        Button pinjam;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView   = itemView.findViewById(R.id.ImageProduct);
            judul       = itemView.findViewById(R.id.txtJudul);
            pengarang   = itemView.findViewById(R.id.txtPengarang);
            pinjam      = itemView.findViewById(R.id.btnPilih);
        }
    }
}


