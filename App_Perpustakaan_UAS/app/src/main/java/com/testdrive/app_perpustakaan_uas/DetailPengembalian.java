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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailPengembalian extends AppCompatActivity {

    TextView tglPINJ, tglPEBG, selisih,denda, prompt;
    int nominaldenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengembalian);

        tglPINJ = findViewById(R.id.tglPinjamPen);
        tglPEBG = findViewById(R.id.tglKembPen);
        selisih = findViewById(R.id.SelisihHAri);
        denda = findViewById(R.id.denda);
        prompt = findViewById(R.id.prompt);

        final String[] tglPinjam = {null};
        final String[] tglKembali = {null};


        List<ProductPeminjaman> productList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String kdPeminjaman = bundle.getString("kodepinjam");


        //Fetch data
        //view_data_viewhistory.php?kd_peminjaman=" + kdPeminjaman;
        String url = AppConfig.IP_SERVER + "/Volley_perpus/view_data_detailpengembalian.php?kd_peminjaman=" + kdPeminjaman;;
        StringRequest strReq = new StringRequest(Request.Method.GET, url, responses -> {
            try {
                JSONObject jsonObj = new JSONObject(responses);
                Toast.makeText(DetailPengembalian.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonObj.getJSONArray("data");
                Log.d("TAG", "data length: " + jsonArray.length());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    tglPinjam[0] = jsonObject.getString("tgl_pinjam");
                    tglKembali[0] = jsonObject.getString("tgl_kembali");
                }
                // Parsing tanggal peminjaman
                Date tanggalPeminjaman = dateFormat.parse(tglPinjam[0]);
                Date tanggalPengembalian = dateFormat.parse(tglKembali[0]);

                // Calculate the difference in milliseconds between the two dates
                long differenceMillis = tanggalPengembalian.getTime() - tanggalPeminjaman.getTime();

                // Convert the difference to days
                int selisihHari = (int) (differenceMillis / (24 * 60 * 60 * 1000));

                // Calculate the fine (denda) if the difference is greater than 7 days
                //int nominaldenda = selisihHari > 7 ? selisihHari * 1000 : 0;

                if(selisihHari>7){
                    nominaldenda = selisihHari*1000;
                    prompt.setText("Yahh kamu telat balikinnya, jadi kena denda sebesar "+nominaldenda);
                } else {
                    nominaldenda = 0;
                    prompt.setText("Yeii kamu nggak kena denda");
                }


                //setText
                tglPINJ.setText(tglPinjam[0]);
                tglPEBG.setText(tglKembali[0]);
                selisih.setText(String.valueOf(selisihHari));
                denda.setText(String.valueOf(nominaldenda));


            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        Volley.newRequestQueue(this).add(strReq);

    }
}