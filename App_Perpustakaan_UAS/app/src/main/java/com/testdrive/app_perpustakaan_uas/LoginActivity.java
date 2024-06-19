package com.testdrive.app_perpustakaan_uas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText username, password;
    Button register, login;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findID();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                checkUsername();

                String UN, P;

                UN = String.valueOf(username.getText());
                P = String.valueOf(password.getText());

                if (!UN.equals("") && !P.equals("")) {
                    pBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = UN;
                            data[1] = P;


                            PutData putData = new PutData(AppConfig.IP_SERVER +"/Volley_perpus/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    pBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")){

                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        }

    private void findID(){
        username = findViewById(R.id.uName);
        password = findViewById(R.id.pWord);
        register = findViewById(R.id.btn_goRegister);
        login = findViewById(R.id.btn_login);
        pBar = findViewById(R.id.ProgBarLogin);
    }


    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public void checkUsername() {
        boolean isValid = true;

        if (isEmpty(username)) {
            username.setError("You must enter username to login!");
            isValid = false;
        }

        if (isEmpty(password)) {
            password.setError("You must enter password to Login");
            isValid = false;
        }

    }


}