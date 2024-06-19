    package com.testdrive.app_perpustakaan_uas;

    import android.app.DatePickerDialog;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.DatePicker;
    import android.widget.ImageButton;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.android.volley.Request;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;
    import com.google.android.material.textfield.TextInputEditText;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Locale;
    import java.util.Map;

    public class History extends AppCompatActivity {

        TextInputEditText tvDateRes;
        TextView TotalBuku;
        Button btnSearch;
        SimpleDateFormat dateFormatter;
        Locale localeID = new Locale("in", "ID");
        RecyclerView recyclerView;
        List<ProductPeminjaman> productList = new ArrayList<>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);

            btnSearch = findViewById(R.id.btnSearch);
            TotalBuku = findViewById(R.id.totalBuku);
            dateFormatter = new SimpleDateFormat("yyyy-MM-dd", localeID);
            tvDateRes = findViewById(R.id.edittglPinjam);
            ImageButton btncalendar = findViewById(R.id.btnCalendar);
            btncalendar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    showDateDialog();
                }
            });

            recyclerView = findViewById(R.id.recyview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
            displayData();

            btnSearch.setOnClickListener(view -> {
                if (tvDateRes.getText().toString().isEmpty()) {
                    displayData();
                    TotalBuku.setVisibility(View.GONE);
                } else {
                    displayDataSearch();
                    TotalBuku.setVisibility(View.VISIBLE);
                }
            });
        }

        private void displayDataSearch() {
            String url = AppConfig.IP_SERVER + "/Volley_perpus/search_history.php";
            StringRequest strReq = new StringRequest(Request.Method.POST, url, responses -> {
                productList.clear();
                try {
                    JSONObject jsonObj = new JSONObject(responses);
                    Toast.makeText(History.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = jsonObj.getJSONArray("data");
                    Log.d("TAG", "data length: " + jsonArray.length());

                    int bookCount = jsonArray.length(); // Get the count of books
                    TotalBuku.setText("Total buku yang dipinjam hari ini:  " + bookCount +" buku");


                    // Display the count
                    Toast.makeText(History.this, "Total buku yang dipinjam hari ini adalah: " + bookCount, Toast.LENGTH_SHORT).show();

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
                        product.setStatus(item.getString("status"));
                        product.setTglkembali(item.getString("tgl_kembali"));
                        product.setKodebuku(item.getString("kd_buku"));

                        productList.add(product);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setAdapter(new AdapterHistory(History.this, productList));
            }, Throwable::printStackTrace){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("tgl_pinjam", tvDateRes.getText().toString());
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(strReq);
        }


        private void displayData() {
            String url = AppConfig.IP_SERVER + "/Volley_perpus/view_data_history.php";
            StringRequest strReq = new StringRequest(Request.Method.GET, url, responses -> {
                productList.clear();
                try {
                    JSONObject jsonObj = new JSONObject(responses);
                    Toast.makeText(History.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
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
                        product.setStatus(item.getString("status"));
                        product.setTglkembali(item.getString("tgl_kembali"));
                        product.setKodebuku(item.getString("kd_buku"));


                        productList.add(product);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setAdapter(new AdapterHistory(History.this, productList));
            }, Throwable::printStackTrace);
            Volley.newRequestQueue(this).add(strReq);
        }

        private void showDateDialog(){
            Calendar newCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    tvDateRes.setText(dateFormatter.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        }
    }