package com.testdrive.app_perpustakaan_uas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InputPeminjam extends AppCompatActivity {
    TextInputEditText nama, notelp, alamat,  tvDateResult;
    Button save, reset;
    SimpleDateFormat dateFormatter;
    String kode="";
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    boolean isValid = true;

    AutoCompleteTextView autoCompleteTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_peminjam);

        findId();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", localeID);
        tvDateResult = findViewById(R.id.edittglPinjam);
        ImageButton btncalendar = findViewById(R.id.btnCalendar);
        btncalendar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showDateDialog();
            }
        });
        insertData();
        ResetData();
    }


    private void ResetData() {
        reset.setOnClickListener(view -> {
            nama.setText("");
            notelp.setText("");
            alamat.setText("");
            tvDateResult.setText("");
        });
    }

    private void insertData() {
        save.setOnClickListener(view -> {
            checkDataEntered();
            if (isValid == true) {
                String url = AppConfig.IP_SERVER + "/Volley_perpus/send_data_peminjam.php";
                StringRequest strReq = new StringRequest(Request.Method.POST, url, responses -> {
                    try {
                        JSONObject jsonObj = new JSONObject(responses);
                        Toast.makeText(InputPeminjam.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(InputPeminjam.this, PilihBuku.class));
                        nama.setText("");
                        notelp.setText("");
                        alamat.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("nama_peminjam", String.valueOf(nama.getText()));
                        params.put("no_telp", String.valueOf(notelp.getText()));
                        params.put("alamat", String.valueOf(alamat.getText()));
                        params.put("tgl_pinjam", String.valueOf(tvDateResult.getText()));
                        params.put("status", "Borrowing");

                        Intent intent = getIntent();
                        Bundle bundle = intent.getExtras();
                        params.put("kd_buku", bundle.getString("kd_buku"));
                        params.put("statusb", "Not Available");

                        return params;
                    }
                };
                Volley.newRequestQueue(this).add(strReq);
                Intent i = new Intent(InputPeminjam.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);}
        });
    }



    private void findId() {
        nama = findViewById(R.id.editNamaPeminjam);
        notelp = findViewById(R.id.editNoTelp);
        alamat = findViewById(R.id.editAlamat);
        save = findViewById(R.id.btnUpload);
        reset = findViewById(R.id.btnReset);
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tvDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        isValid = true;
        if (isEmpty(nama)) {
            nama.setError("Nama peminjam belum diisi");
            isValid = false;
        }

        if (isEmpty(notelp)) {
            notelp.setError("Nomor telepon belum diisi");
            isValid = false;
        }

        if (isEmpty(alamat)) {
            alamat.setError("Alamat belum diisi");
            isValid = false;
        }

        if (isEmpty(tvDateResult)) {
            tvDateResult.setError("Tanggal pinjam belum diisi");
            isValid = false;
        }
    }
}