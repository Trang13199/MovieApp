package com.mytrang.moviesapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mytrang.moviesapplication.Client.UserClient;
import com.mytrang.moviesapplication.model.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMainActivity extends AppCompatActivity {
    private EditText txtemal, txtpassword;
    private TextView txtRegister;
    private String email, password;
    private Button login, forgetPassword, faceBook;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        anhXa();

        preferences = getSharedPreferences("data", MODE_PRIVATE);
//        txtemal.setText(preferences.getString("email",""));
//        txtpassword.setText(preferences.getString("password", ""));



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = txtemal.getText().toString().trim();
                password = txtpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
                    txtemal.setError("Email is required!");
                    txtemal.requestFocus();

                    txtpassword.setError("Password is required!");
                    txtpassword.requestFocus();
                }else{
                    getLogin();
                }
            }

            private void getLogin() {
                Call<UserModel> call = UserClient
                        .getInstance()
                        .getApi()
                        .getLoginUser(email, password);

                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if(response.isSuccessful()){
                            response.body().getData();
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.commit();
                            Toast.makeText(LoginMainActivity.this,"Login Successful", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(LoginMainActivity.this, ListActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginMainActivity.this,"Login failing", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(LoginMainActivity.this,"Throwable: "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }
        });


        faceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMainActivity.this, RegisterMainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void anhXa() {
        txtemal = (EditText) findViewById(R.id.txt_email);
        txtpassword = (EditText) findViewById(R.id.txt_password);
        txtRegister = (TextView) findViewById(R.id.register);

        login = (Button) findViewById(R.id.btn_login);
        forgetPassword = (Button) findViewById(R.id.btn_forgot_pass);
        faceBook = (Button) findViewById(R.id.btn_fb);

    }
}