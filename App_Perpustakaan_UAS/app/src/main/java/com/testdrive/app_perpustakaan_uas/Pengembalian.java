package com.testdrive.app_perpustakaan_uas;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Pengembalian extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ProductPeminjaman> productList = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengembalian);

        recyclerView = findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        displayData();

    }

    private void displayData() {
        String url = AppConfig.IP_SERVER + "/Volley_perpus/view_data_peminjam.php";

        StringRequest strReq = new StringRequest(Request.Method.GET, url, responses -> {
            productList.clear();
            try {
                JSONObject jsonObj = new JSONObject(responses);
                Toast.makeText(Pengembalian.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonObj.getJSONArray("data");
                Log.d("TAG", "data length: " + jsonArray.length());
                ProductPeminjaman product;

                productList.clear();
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject item = jsonArray.getJSONObject(i);

                    product = new ProductPeminjaman();
                    product.setKodepeminjam(item.getString("kd_peminjaman"));
                    product.setNama(item.getString("nama_peminjam"));
                    product.setNotelp(item.getString("no_telp"));
                    product.setAlamat(item.getString("alamat"));
                    product.setTglpinjam(item.getString("tgl_pinjam"));
                    product.setKodebuku(item.getString("kd_buku"));


                    productList.add(product);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            recyclerView.setAdapter(new AdapterPengembalian(Pengembalian.this, productList));
        }, Throwable::printStackTrace);
        Volley.newRequestQueue(this).add(strReq);
    }


}