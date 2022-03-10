package com.mytrang.moviesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.txt_email_forgot);
    }

    /// checking if the input in form is valid
    boolean validateInput() {
        if (email.isEmpty()) {
            etEmail.setError("Please enter Email!");
            etEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter Valid Email!");
            return false;
        }
        return true;
    }

    public void performForgetPassword(View view) {
        if (validateInput()) {
            // input valid, here send data to your server

            Intent intent = new Intent(this, LoginMainActivity.class);
            startActivity(intent);
        }
    }
}