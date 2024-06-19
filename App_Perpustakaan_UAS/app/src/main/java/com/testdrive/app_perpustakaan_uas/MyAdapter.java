package com.testdrive.app_perpustakaan_uas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ImageViewHolder>{
    Context context;
    List<ProductBuku> productLists;

    //generate constructor
    public MyAdapter(Context context, List<ProductBuku> productList) {
        this.context = context;
        this.productLists = productList;
    }
    @NonNull
    @Override
    public MyAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singledata,null);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final ProductBuku product = productLists.get(position);
        Picasso.get().load(productLists.get(position).getImages()).into(holder.imageView);
        holder.judul.setText(productLists.get(position).getJudul());
        holder.pengarang.setText(productLists.get(position).getPengarang());

        holder.flowmenu.setOnClickListener(view->{
            PopupMenu popupMenu = new PopupMenu(context,holder.flowmenu);
            popupMenu.inflate(R.menu.flow_menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.edit_menu:
                        Bundle bundle = new Bundle();
                        bundle.putString("kd_buku", product.getKodeBuku());
                        bundle.putString("image", product.getImages());
                        bundle.putString("judul", product.getJudul());
                        bundle.putString("genre", product.getGenre());
                        bundle.putString("penerbit", product.getPenerbit());
                        bundle.putString("pengarang", product.getPengarang());
                        bundle.putString("tahun_terbit", product.getTahun());
                        bundle.putString("no_rak", product.getRak());
                        bundle.putString("isbn", product.getIsbn());
                        Intent intent = new Intent(context, InsertbukuActivity.class);
                        intent.putExtra("databuku", bundle);
                        context.startActivity(intent);
                        break;

                    case R.id.delete_menu:
                        String url = AppConfig.IP_SERVER + "/Volley_perpus/delete_data.php";
                        StringRequest strReq = new StringRequest(Request.Method.POST, url, responses -> {
                            try {
                                JSONObject jsonObj = new JSONObject(responses);
                                Toast.makeText(context, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                                if ("Success Delete".equals(jsonObj.getString("message"))) {
                                    productLists.remove(position);
                                    notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, Throwable::printStackTrace) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("kd_buku", product.getKodeBuku());
                                return params;
                            }
                        };
                        Volley.newRequestQueue(context).add(strReq);
                        break;
                    default:
                        return false;
                }
                return false;
            });
            //display menu
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView judul;
        TextView pengarang;
        ImageButton flowmenu;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView   = itemView.findViewById(R.id.ImageProduct);
            judul       = itemView.findViewById(R.id.txtJudul);
            pengarang   = itemView.findViewById(R.id.txtPengarang);
            flowmenu    = itemView.findViewById(R.id.flowmenu);
        }
    }
}
