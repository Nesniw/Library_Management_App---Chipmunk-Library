package com.testdrive.app_perpustakaan_uas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterPengembalian extends RecyclerView.Adapter<AdapterPengembalian.ImageViewHolder>{
    Context context;
    List<ProductPeminjaman> productLists;

    //generate constructor
    public AdapterPengembalian(Context context, List<ProductPeminjaman> productList) {
        this.context = context;
        this.productLists = productList;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singledatapengembalian,null);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final ProductPeminjaman product = productLists.get(position);
        holder.nama.setText(productLists.get(position).getNama());
        holder.tglpinjam.setText(productLists.get(position).getTglpinjam());
        holder.kembali.setOnClickListener(view -> {
            String url = AppConfig.IP_SERVER + "/Volley_perpus/pengembalian.php";
            StringRequest strReq = new StringRequest(Request.Method.POST, url, responses -> {
                try {
                    JSONObject jsonObj = new JSONObject(responses);
                    Toast.makeText(context, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }, Throwable::printStackTrace){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<>();
                    params.put("kd_peminjaman", product.getKodepeminjam());
                    params.put("statusb","Available");
                    params.put("statusp","Returned");
                    params.put("kd_buku",product.getKodebuku());
                    return params;
                }
            };
            Volley.newRequestQueue(context).add(strReq);
            productLists.remove(position);
            notifyDataSetChanged();

            Intent secondActivity = new Intent(context, DetailPengembalian.class);
            secondActivity.putExtra("kodepinjam", product.getKodepeminjam());
            context.startActivity(secondActivity);
                }
        );
    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        TextView tglpinjam;
        Button kembali;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            nama   = itemView.findViewById(R.id.txtNamaPeminjam);
            tglpinjam       = itemView.findViewById(R.id.txttglPinjam);
            kembali         = itemView.findViewById(R.id.btnKembali);
        }
    }
}