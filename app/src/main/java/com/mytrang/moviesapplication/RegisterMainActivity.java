package com.mytrang.moviesapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText txtName, txtEmail, txtPassword, txtRepass;
    private Button register;
    private ImageView close;
    private TextView law;

    private TextView messName, messEmail, messPass, messRepass;

    String name, email, password, repass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
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
                    messName.setText("Name is required");
                    messName.setVisibility(View.VISIBLE);
                    return;
                }
                messName.setVisibility(View.GONE);
                if (email.isEmpty()) {
                    messEmail.setText("Email is required!");
                    messEmail.setVisibility(View.VISIBLE);
                    return;
                }
                messEmail.setVisibility(View.GONE);
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    messEmail.setText("Enter a valid email!");
                    messEmail.setVisibility(View.VISIBLE);
                    return;
                }
                messEmail.setVisibility(View.GONE);
                if (password.isEmpty()) {
                    messPass.setText("Password is required!");
                    messPass.setVisibility(View.VISIBLE);
                    return;
                }
                messPass.setVisibility(View.GONE);
                if (password.length() < 6) {
                    messPass.setText("Password should be atleast 6 character long");
                    messPass.setVisibility(View.VISIBLE);
                    return;
                }
                messPass.setVisibility(View.GONE);
                if (repass.isEmpty()) {
                    messRepass.setText("Request Password is required!");
                    messRepass.setVisibility(View.VISIBLE);
                    return;
                }
                messRepass.setVisibility(View.GONE);
                if (!repass.equals(password)) {
//                    txtRepass.setError("No same!");
//                    txtRepass.requestFocus();
                    messRepass.setText("No same!");
                    messRepass.setVisibility(View.VISIBLE);
                    return;
                }
                messRepass.setVisibility(View.GONE);


                Call<UserModel> call = UserClient
                        .getInstance()
                        .getApi()
                        .getRegister(name, email, password);

                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getError() && response.body().getMessage().equals("Email has been existed")){
                                Toast.makeText(RegisterMainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }else {
                                User model = response.body().getData();
                                Toast.makeText(RegisterMainActivity.this, "Dang ky thanh cong", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(RegisterMainActivity.this, LoginMainActivity.class);
//                            startActivity(intent);
                                finish();
                            }

                        } else {

                            Toast.makeText(RegisterMainActivity.this, "Dang ky that bai", Toast.LENGTH_LONG).show();

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

        law.setText(updatetext(law.getText().toString()));

    }

    private SpannableString updatetext(String toString) {

        SpannableString sp = new SpannableString(toString);
        ForegroundColorSpan fc = new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan fc1 = new ForegroundColorSpan(Color.RED);
        UnderlineSpan un = new UnderlineSpan();
        UnderlineSpan un1 = new UnderlineSpan();
        sp.setSpan(fc, 47, 66, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(un, 47, 66, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(fc1, 69, 86, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(un1, 69, 86, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    private void anhXa() {
        txtName = findViewById(R.id.name_register);
        txtEmail = findViewById(R.id.email_register);
        txtPassword = findViewById(R.id.pass_register);
        txtRepass = findViewById(R.id.txt_password);

        register = findViewById(R.id.register);
        close = findViewById(R.id.closeRe);

        law = findViewById(R.id.law);


        messName = findViewById(R.id.name_mess);
        messEmail = findViewById(R.id.email_mess);
        messPass = findViewById(R.id.pass_mess);
        messRepass = findViewById(R.id.mess_repass);
    }
}