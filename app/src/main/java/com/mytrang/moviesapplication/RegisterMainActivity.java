package com.mytrang.moviesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mytrang.moviesapplication.Client.UserClient;
import com.mytrang.moviesapplication.Service.MovieService;
import com.mytrang.moviesapplication.model.User;
import com.mytrang.moviesapplication.model.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterMainActivity extends AppCompatActivity {
    private MovieService movieService;
    EditText txtName, txtEmail, txtPassword, txtRepass;
    Button register;
    ImageView close;

    String name, email, password, repass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        anhXa();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUser();
            }

            private void checkUser() {
                name = txtName.getText().toString().trim();
                email = txtEmail.getText().toString().trim();
                password = txtPassword.getText().toString().trim();
                repass = txtRepass.getText().toString().trim();

                if (name.isEmpty()) {
                    txtName.setError("Name is required");
                    txtName.requestFocus();
                }
                if (email.isEmpty()) {
                    txtEmail.setError("Email is required!");
                    txtEmail.requestFocus();
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txtEmail.setError("Enter a valid email!");
                    txtEmail.requestFocus();
                }
                if (password.isEmpty()) {
                    txtPassword.setError("Password is required!");
                    txtPassword.requestFocus();
                }
                if (password.length() < 6) {
                    txtPassword.setError("Password should be atleast 6 character long");
                    txtPassword.requestFocus();
                }
                if (repass.isEmpty()) {
                    txtRepass.setError("Request Password is required!");
                    txtRepass.requestFocus();
                }
                if (!repass.equals(password)) {
                    txtRepass.setError("No same!");
                    txtRepass.requestFocus();
                }
                Call<UserModel> call = UserClient
                        .getInstance()
                        .getApi()
                        .getRegister(name, email, password);

                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {

                            User model = response.body().getData();
                            Toast.makeText(RegisterMainActivity.this, "Email: " + model.getEmail().toString(), Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(RegisterMainActivity.this, LoginMainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(RegisterMainActivity.this, "False", Toast.LENGTH_LONG).show();

                    }
                });


            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void anhXa() {
        txtName = findViewById(R.id.name_register);
        txtEmail = findViewById(R.id.email_register);
        txtPassword = findViewById(R.id.pass_register);
        txtRepass = findViewById(R.id.txt_password);

        register = findViewById(R.id.register);
        close = findViewById(R.id.closeRe);
    }
}