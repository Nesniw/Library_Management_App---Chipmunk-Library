package com.testdrive.app_perpustakaan_uas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    List<ProductUser> productList = new ArrayList<>();
    ProductUser productLists;
    TextInputEditText username, namaLengkap, email;
    TextView kode;
    Button update, logout, ubahpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findID();
        displayData();


        update.setOnClickListener(View-> {
            String url2 = AppConfig.IP_SERVER + "/Volley_perpus/update_data_user.php";
            StringRequest strReq = new StringRequest(Request.Method.POST, url2, response -> {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    Toast.makeText(ProfileActivity.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, Throwable::printStackTrace) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("kd_user", String.valueOf(kode.getText()));
                    params.put("username", String.valueOf(username.getText()));
                    params.put("nama_lengkap", String.valueOf(namaLengkap.getText()));
                    params.put("email", String.valueOf(email.getText()));
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(strReq);
        });

        logout.setOnClickListener(View->{
            String url2 = AppConfig.IP_SERVER + "/Volley_perpus/logout.php";
            StringRequest strReq = new StringRequest(Request.Method.POST, url2, response -> {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    Toast.makeText(ProfileActivity.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, Throwable::printStackTrace);
            Volley.newRequestQueue(this).add(strReq);
        });

        ubahpassword.setOnClickListener(View->{
            String url2 = AppConfig.IP_SERVER + "/Volley_perpus/ubah_password.php";
            StringRequest strReq = new StringRequest(Request.Method.POST, url2, response -> {

                    Intent intent = new Intent(ProfileActivity.this, UbahPassword.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

            }, Throwable::printStackTrace);
            Volley.newRequestQueue(this).add(strReq);
        });
    }

    private void displayData() {
        String url = AppConfig.IP_SERVER + "/Volley_perpus/view_data_user.php";
        StringRequest strReq = new StringRequest(Request.Method.GET, url, responses -> {

            try {
                JSONObject jsonObj = new JSONObject(responses);
                Toast.makeText(ProfileActivity.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonObj.getJSONArray("datauser");
                Log.d("TAG", "data length: " + jsonArray.length());
                ProductUser product;

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject item = jsonArray.getJSONObject(i);
                    kode.setText(item.getString("kd_user"));
                    username.setText(item.getString("username"));
                    namaLengkap.setText(item.getString("nama_lengkap"));
                    email.setText(item.getString("email"));

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        Volley.newRequestQueue(this).add(strReq);
    }


    public void findID(){
        username = findViewById(R.id.upUser);
        namaLengkap = findViewById(R.id.upFullname);
        email = findViewById(R.id.upEmail);

        kode = findViewById(R.id.kodeText);
        update = findViewById(R.id.btnUpdates);
        logout = findViewById(R.id.btnLogout);
        ubahpassword = findViewById(R.id.btnUbahPassword);
    }
}