package com.testdrive.app_perpustakaan_uas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UbahPassword extends AppCompatActivity {

    EditText etOldPassword;
    EditText etNewPassword;
    Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        etOldPassword = findViewById(R.id.old_password_edit_text);
        etNewPassword = findViewById(R.id.new_password_edit_text);
        btnChangePassword = findViewById(R.id.change_password_button);

        btnChangePassword.setOnClickListener(View -> changePassword());
    }

    private void changePassword() {
        String oldPassword = etOldPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(oldPassword)) {
            etOldPassword.setError("Enter your old password");
            etOldPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("Enter your new password");
            etNewPassword.requestFocus();
            return;
        }

        // Make a request to the PHP script to change the password
        String url = AppConfig.IP_SERVER + "/Volley_perpus/ubah_password.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        Toast.makeText(UbahPassword.this, message, Toast.LENGTH_SHORT).show();

                        // Check if password change was successful
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            // Password change successful, navigate to MainActivity
                            Intent intent = new Intent(UbahPassword.this, ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("ChangePasswordActivity", "Error: " + error.getMessage());
                    Toast.makeText(UbahPassword.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Pass the old and new passwords to the PHP script
                Map<String, String> params = new HashMap<>();
                params.put("old_password", oldPassword);
                params.put("new_password", newPassword);
                return params;
            }
        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}