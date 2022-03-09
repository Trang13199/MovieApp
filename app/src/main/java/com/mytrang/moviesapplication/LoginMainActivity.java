package com.mytrang.moviesapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mytrang.moviesapplication.Client.UserClient;
import com.mytrang.moviesapplication.model.User;
import com.mytrang.moviesapplication.model.UserModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMainActivity extends AppCompatActivity {
    private EditText txtemal, txtpassword;
    private TextView txtRegister;
    private String email, password;
    private Button login, forgetPassword;
    private LoginButton facebook;
    private ImageView close;


    SharedPreferences preferences;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        anhXa();

        preferences = getSharedPreferences("data", MODE_PRIVATE);

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

                            User m = response.body().getData();
                            Log.e("abc", m.getAccessToken().toString());


                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putString("token", response.body().getData().getAccessToken().toString());
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


        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMainActivity.this, RegisterMainActivity.class);
                startActivity(i);
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        printHashKey(LoginMainActivity.this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        facebook.setReadPermissions("email");
        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String token = loginResult.getAccessToken().getToken();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("token", token);
                editor.commit();

//                Intent intent = new Intent(LoginMainActivity.this, ListActivity.class);
//                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void anhXa() {
        txtemal = (EditText) findViewById(R.id.txt_email);
        txtpassword = (EditText) findViewById(R.id.txt_password);
        txtRegister = (TextView) findViewById(R.id.txt_dangky);

        login = (Button) findViewById(R.id.btn_login);
        forgetPassword = (Button) findViewById(R.id.btn_forgot_pass);

        facebook = findViewById(R.id.login_button);
        close = findViewById(R.id.close);
    }



    //// phuong thuc lay hask key
//    public static void printHashKey(Context pContext) {
//        try {
//            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("TAG", "printHashKey()", e);
//        } catch (Exception e) {
//            Log.e("TAG", "printHashKey()", e);
//        }
//    }

}