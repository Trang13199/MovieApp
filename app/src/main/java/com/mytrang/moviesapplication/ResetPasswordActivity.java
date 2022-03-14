package com.mytrang.moviesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText password, conformPassword, conformOTP;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        password = findViewById(R.id.password);
        conformPassword = findViewById(R.id.conform_pass);
        conformOTP = findViewById(R.id.otp);

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = password.getText().toString().trim();
                String conformPass = conformPassword.getText().toString().trim();
                String conformotp = conformOTP.getText().toString().trim();

                if (!pass.matches(conformPass)) {
                    conformPassword.setError("Mat khau chua trung khop!");
                    conformPassword.requestFocus();
                    return;
                }
                Intent intent = getIntent();
//                intent
            }
        });

    }


}