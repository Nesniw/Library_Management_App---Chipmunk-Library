package com.testdrive.app_perpustakaan_uas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewHistory extends AppCompatActivity {

    TextView kdpeminjaman, nama, notelp, alamat, tglpinjam, kdbuku, tglkembali, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        findID();
        DisplayData();
    }

    private void findID(){
        kdpeminjaman = findViewById(R.id.viewkdpeminjam);
        nama = findViewById(R.id.viewnamapeminjam);
        notelp = findViewById(R.id.viewnotelp);
        alamat = findViewById(R.id.viewalamat);
        tglpinjam = findViewById(R.id.viewtglpinjam);
        kdbuku = findViewById(R.id.viewkdbuku);
        tglkembali = findViewById(R.id.viewtglkembali);
        status = findViewById(R.id.viewstatus);
    }

    private void DisplayData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String kdPeminjaman = bundle.getString("kd_peminjaman");

        String url = AppConfig.IP_SERVER + "/Volley_perpus/view_data_viewhistory.php?kd_peminjaman=" + kdPeminjaman;
        StringRequest strReq = new StringRequest(Request.Method.GET, url, responses -> {
            try {
                JSONObject jsonObj = new JSONObject(responses);
                Toast.makeText(ViewHistory.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonObj.getJSONArray("data");
                Log.d("TAG", "data length: " + jsonArray.length());

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject item = jsonArray.getJSONObject(i);
                    kdpeminjaman.setText(kdPeminjaman);
                    nama.setText(item.getString("nama_peminjam"));
                    notelp.setText(item.getString("no_telp"));
                    alamat.setText(item.getString("alamat"));
                    tglpinjam.setText(item.getString("tgl_pinjam"));
                    kdbuku.setText(item.getString("kd_buku"));
                    tglkembali.setText(item.getString("tgl_kembali"));
                    status.setText(item.getString("status"));
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        Volley.newRequestQueue(this).add(strReq);
    }
}