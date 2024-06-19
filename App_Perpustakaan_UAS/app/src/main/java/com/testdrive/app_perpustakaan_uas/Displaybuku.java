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

import java.util.ArrayList;
import java.util.List;

public class Displaybuku extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ProductBuku> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaybuku);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        displayData();
    }

    private void displayData() {
        String url = AppConfig.IP_SERVER + "/Volley_perpus/view_data.php";

        StringRequest strReq = new StringRequest(Request.Method.GET, url, responses -> {
            productList.clear();
            try {
                JSONObject jsonObj = new JSONObject(responses);
                Toast.makeText(Displaybuku.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonObj.getJSONArray("data");
                Log.d("TAG", "data length: " + jsonArray.length());
                ProductBuku product;
                productList.clear();
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject item = jsonArray.getJSONObject(i);

                    product = new ProductBuku();
                    product.setKodeBuku(item.getString("kd_buku"));
                    product.setImage(AppConfig.IP_SERVER+"/Volley_perpus/"+item.getString("image"));
                    product.setJudul(item.getString("judul"));
                    product.setGenre(item.getString("genre"));
                    product.setPenerbit(item.getString("penerbit"));
                    product.setPengarang(item.getString("pengarang"));
                    product.setTahun(item.getString("tahun_terbit"));
                    product.setRak(item.getString("no_rak"));
                    product.setRak(item.getString("no_rak"));
                    product.setIsbn(item.getString("isbn"));

                    productList.add(product);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            recyclerView.setAdapter(new MyAdapter(Displaybuku.this, productList));
        }, Throwable::printStackTrace);
        Volley.newRequestQueue(this).add(strReq);
    }
}