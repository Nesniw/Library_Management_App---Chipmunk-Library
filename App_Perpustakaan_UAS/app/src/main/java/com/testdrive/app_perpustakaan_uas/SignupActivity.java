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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText username, fullname, email, password;
    Button btn_signup, btn_gologin;
    TextView textViewLogin;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findID();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();

                String FN, UN, P, E;

                UN = String.valueOf(username.getText());
                FN = String.valueOf(fullname.getText());
                E = String.valueOf(email.getText());
                P = String.valueOf(password.getText());

                if (!UN.equals("") && !FN.equals("") && !E.equals("") && !P.equals("")) {
                    pBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "username";
                            field[1] = "nama_lengkap";
                            field[2] = "email";
                            field[3] = "password";

                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = UN;
                            data[1] = FN;
                            data[2] = E;
                            data[3] = P;


                            PutData putData = new PutData(AppConfig.IP_SERVER +"/Volley_perpus/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    pBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
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

        btn_gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findID() {
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_signup = findViewById(R.id.btn_register);
        btn_gologin = findViewById(R.id.btn_goLogin);
        textViewLogin = findViewById(R.id.TextLog);
        pBar = findViewById(R.id.ProgBar);
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        boolean isValid = true;
        if (isEmpty(fullname)) {
            fullname.setError("Fullname is required");
            isValid = false;
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
            isValid = false;
        }

        if (isEmpty(username)) {
            username.setError("Username is required");
            isValid = false;
        }

        if (isEmpty(password)) {
            password.setError("Password is required");
            isValid = false;
        }
    }
}
