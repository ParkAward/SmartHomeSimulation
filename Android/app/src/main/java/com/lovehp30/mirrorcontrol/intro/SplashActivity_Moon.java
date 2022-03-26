package com.lovehp30.mirrorcontrol.intro;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lovehp30.mirrorcontrol.R;
import com.lovehp30.mirrorcontrol.login.LoginActivity;

public class SplashActivity_Moon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_moon);
        setTheme(R.style.AppTheme);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}