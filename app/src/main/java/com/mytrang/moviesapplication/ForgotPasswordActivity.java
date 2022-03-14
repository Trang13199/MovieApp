package com.mytrang.moviesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mytrang.moviesapplication.Client.UserClient;
import com.mytrang.moviesapplication.model.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.txt_email_forgot);

        send = findViewById(R.id.btn_send_info);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                resetPassword();
                performForgetPassword(view);
            }
        });
    }

//     checking if the input in form is valid

    boolean validateInput() {
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Please enter Email!");
            etEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmail.setError("Please enter Valid Email!");
            return false;
        }
        return true;
    }

    public void performForgetPassword(View view) {
        if (validateInput()) {
            // input valid, here send data to your server

            String email = etEmail.getText().toString();
            Call<UserModel> call = UserClient
                    .getInstance()
                    .getApi()
                    .forgotPassword(email);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        response.body().getData();
                        Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                        startActivity(intent);
                        Toast.makeText(ForgotPasswordActivity.this, "Email send to Register Email Address", Toast.LENGTH_SHORT).show();
                        Log.e("email", "OKkkkkkkkkk");
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email NOT send to Register Email Address", Toast.LENGTH_SHORT).show();
                        Log.e("email", "Email khong ton tai");
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.e("error", "ERRORRRRRRRRRRRRRRRRRRRRRRRRRRR");
                }
            });
        }
    }
}