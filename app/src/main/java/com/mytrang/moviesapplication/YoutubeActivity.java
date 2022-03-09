package com.mytrang.moviesapplication;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class YoutubeActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    TextView name, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
//        printHashKey(YoutubeActivity.this);
//
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
//
///// xu ly phan hoi dang nhap
//        callbackManager = CallbackManager.Factory.create();
//
////        name = findViewById(R.id.textname);
////        pass = findViewById(R.id.textpass);
//
//
//       LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions("email");
//        // If using in a fragment
////        loginButton.setFragment(this);
//
//        // Callback registration
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                name.setText("User id: "+loginResult.getAccessToken().getUserId());
//                pass.setText("Token: "+loginResult.getAccessToken().getToken());
//
//                Intent intent = new Intent(YoutubeActivity.this, ListActivity.class);
//                startActivity(intent);
//
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });
///// de phan hoi dang nhap chung ta can dang ly goi lai
//
////        callbackManager = CallbackManager.Factory.create();
////
////        LoginManager.getInstance().registerCallback(callbackManager,
////                new FacebookCallback<LoginResult>() {
////                    @Override
////                    public void onSuccess(LoginResult loginResult) {
////                        // App code
////                    }
////
////                    @Override
////                    public void onCancel() {
////                        // App code
////                    }
////
////                    @Override
////                    public void onError(FacebookException exception) {
////                        // App code
////                    }
////                });
///// kiem tra trang thai dang nhap
//
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//
//
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
//
//    }
//
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
//
///// chuyen ket qua dang nhap den LoginManager qua callbackManager
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
    }
}