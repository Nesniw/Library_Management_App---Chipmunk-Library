package com.testdrive.app_perpustakaan_uas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView inputBuku, pengembalian, history, daftarBuku, peminjaman, ubahProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findID();

        inputBuku.setOnClickListener(this);
        pengembalian.setOnClickListener(this);
        history.setOnClickListener(this);
        daftarBuku.setOnClickListener(this);
        peminjaman.setOnClickListener(this);
        ubahProfil.setOnClickListener(this);
    }

    private void findID() {
        inputBuku = findViewById(R.id.inputBuku);
        pengembalian = findViewById(R.id.pengembalianBuku);
        history = findViewById(R.id.history);
        daftarBuku = findViewById(R.id.daftarBuku);
        peminjaman = findViewById(R.id.peminjamanBuku);
        ubahProfil = findViewById(R.id.ubahPW);
    }

    public void onClick(View vi) {
        Intent intent;

        switch (vi.getId()) {
            case R.id.inputBuku:
                intent = new Intent(MainActivity.this, InsertbukuActivity.class);
                startActivity(intent);
                break;

            case R.id.pengembalianBuku:
                intent = new Intent(MainActivity.this, Pengembalian.class);
                startActivity(intent);
                break;

            case R.id.history:
                intent = new Intent(MainActivity.this, History.class);
                startActivity(intent);
                break;

            case R.id.daftarBuku:
                intent = new Intent(MainActivity.this, Displaybuku.class);
                startActivity(intent);
                break;

            case R.id.peminjamanBuku:
                intent = new Intent(MainActivity.this, PilihBuku.class);
                startActivity(intent);
                break;

            case R.id.ubahPW:
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }
}