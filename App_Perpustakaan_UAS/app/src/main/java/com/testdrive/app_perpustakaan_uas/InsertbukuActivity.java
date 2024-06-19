package com.testdrive.app_perpustakaan_uas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InsertbukuActivity extends AppCompatActivity {
    ImageView imageView;
    TextInputEditText judul, penerbit, pengarang, tahun, nomorRak, isbn;
    Button save, display, edit;
    Bitmap bitmap;
    String kode="";

    String[] items = {"Romansa", "Non Fiksi", "Horror", "Fantasi", "Fiksi Ilmiah"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertbuku);

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);

        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String genre = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Genre:"+genre, Toast.LENGTH_SHORT).show();
            }
        });

        findId();
        imagePick();
        insertData();
        displayData();
        updateData();
    }

    private void updateData() {
        if(getIntent().getBundleExtra("databuku")!=null){
            Bundle bundle = getIntent().getBundleExtra("databuku");
            kode = bundle.getString("kd_buku");
            Picasso.get().load(bundle.getString("image")).into(imageView);
            judul.setText(bundle.getString("judul"));
            autoCompleteTxt.setText(bundle.getString("genre"));
            penerbit.setText(bundle.getString("penerbit"));
            pengarang.setText(bundle.getString("pengarang"));
            tahun.setText(bundle.getString("tahun_terbit"));
            nomorRak.setText(bundle.getString("no_rak"));
            isbn.setText(bundle.getString("isbn"));

            adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
            autoCompleteTxt.setAdapter(adapterItems);
            autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String genre = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(getApplicationContext(), "Genre:"+genre, Toast.LENGTH_SHORT).show();
                }
            });

            //visible edit button
            save.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);

            edit.setOnClickListener(view ->{
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if(bitmap != null){
                    String url1 = AppConfig.IP_SERVER + "/Volley_perpus/update_dataWithImage.php";
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                    StringRequest strReq = new StringRequest(Request.Method.POST, url1, response -> {
                        try{
                            JSONObject jsonObj = new JSONObject(response);
                            Toast.makeText(InsertbukuActivity.this, jsonObj.getString("message"),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InsertbukuActivity.this, Displaybuku.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace){
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<>();
                            params.put("kd_buku", kode);
                            params.put("image", base64Image);
                            params.put("judul", String.valueOf(judul.getText()));
                            params.put("genre", String.valueOf(autoCompleteTxt.getText()));
                            params.put("penerbit", String.valueOf(penerbit.getText()));
                            params.put("pengarang", String.valueOf(pengarang.getText()));
                            params.put("tahun_terbit", String.valueOf(tahun.getText()));
                            params.put("no_rak", String.valueOf(nomorRak.getText()));
                            params.put("isbn", String.valueOf(isbn.getText()));
                            return params;
                        }
                    };
                    Volley.newRequestQueue(this).add(strReq);
                }
                else {
                    String url2 = AppConfig.IP_SERVER + "/Volley_perpus/update_data.php";
                    StringRequest strReq = new StringRequest(Request.Method.POST, url2, response -> {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Toast.makeText(InsertbukuActivity.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InsertbukuActivity.this, Displaybuku.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("kd_buku", kode);
                            params.put("judul", String.valueOf(judul.getText()));
                            params.put("genre", String.valueOf(autoCompleteTxt.getText()));
                            params.put("penerbit", String.valueOf(penerbit.getText()));
                            params.put("pengarang", String.valueOf(pengarang.getText()));
                            params.put("tahun_terbit", String.valueOf(tahun.getText()));
                            params.put("no_rak", String.valueOf(nomorRak.getText()));
                            params.put("isbn", String.valueOf(isbn.getText()));
                            return params;
                        }
                    };
                    Volley.newRequestQueue(this).add(strReq);
                }
            });
        }
    }

    private void displayData() {
        display.setOnClickListener(view -> {
            Intent intent = new Intent(InsertbukuActivity.this, Displaybuku.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void insertData() {
        save.setOnClickListener(view ->{
            ByteArrayOutputStream byteArrayOutputStream;
            byteArrayOutputStream = new ByteArrayOutputStream();
            if(bitmap != null){
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                String url = AppConfig.IP_SERVER + "/Volley_perpus/send_data.php";
                StringRequest strReq = new StringRequest(Request.Method.POST, url, responses -> {
                    try{
                        JSONObject jsonObj = new JSONObject(responses);
                        Toast.makeText(InsertbukuActivity.this, jsonObj.getString("message"),Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.vert_image);
                        judul.setText("");
                        autoCompleteTxt.setText("");
                        penerbit.setText("");
                        pengarang.setText("");
                        tahun.setText("");
                        nomorRak.setText("");
                        isbn.setText("");
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("image", base64Image);
                        params.put("judul",String.valueOf(judul.getText()));
                        params.put("genre",String.valueOf(autoCompleteTxt.getText()));
                        params.put("penerbit",String.valueOf(penerbit.getText()));
                        params.put("pengarang",String.valueOf(pengarang.getText()));
                        params.put("tahun_terbit",String.valueOf(tahun.getText()));
                        params.put("no_rak",String.valueOf(nomorRak.getText()));
                        params.put("isbn",String.valueOf(isbn.getText()));
                        params.put("status","Available");

                        return params;
                    }
                };
                Volley.newRequestQueue(this).add(strReq);
            }
            else
                Toast.makeText(getApplicationContext(),"Select the image first", Toast.LENGTH_SHORT).show();
        });
    }

    private void imagePick(){
        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        assert data != null;
                        Uri uri = data.getData();
                        try{
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });

        imageView.setOnClickListener(view ->{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });
    }

    private void findId() {
        imageView = findViewById(R.id.clickToUploadImg);
        judul = findViewById(R.id.editJudul);
        penerbit = findViewById(R.id.editPenerbit);
        pengarang = findViewById(R.id.editPengarang);
        tahun = findViewById(R.id.editTahun);
        nomorRak = findViewById(R.id.editRak);
        isbn = findViewById(R.id.editISBN);
        save = findViewById(R.id.btnUpload);
        edit = findViewById(R.id.btnUpdate);
        display = findViewById(R.id.btnDisplay);
    }
}