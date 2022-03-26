package com.lovehp30.mirrorcontrol.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;
import com.lovehp30.mirrorcontrol.main.MainActivity;
import com.lovehp30.mirrorcontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    boolean isVerifySunLite = true;
    boolean isVerifySkyMoon = false;
    int bright = 0;
    TextInputEditText ed_ipAddress,ed_Api;
    CheckBox checkBox;
    String originKey = "0bb2a5ddbc354cc5be0a24d120c4c289";
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_MaterialComponents_DayNight_DarkActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(Color.parseColor("#3d3d3d"));
        ed_ipAddress = findViewById(R.id.login_Ip);
         ed_Api = findViewById(R.id.login_Api);
         checkBox= findViewById(R.id.loign_rememberMe);

        SharedPreferences sf  = getSharedPreferences("File",MODE_PRIVATE);
        String text1 = sf.getString("ip","");
        String text2 = sf.getString("key","");
        assert text1 != null;
        if(!text1.equals(""))
            checkBox.setChecked(true);
        ed_ipAddress.setText(text1);
        ed_Api.setText(originKey);

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("File",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(checkBox.isChecked()){
            String text1 = ed_ipAddress.getText().toString();
            String text2 = ed_Api.getText().toString();

            editor.putString("ip",text1);
            editor.putString("key",text2);
        }
        else {
            editor.putString("ip", "");
            editor.putString("key", "");
        }
        editor.apply();
    }

    public void verifyLogin(View view) {
        ProgressDialog myProgressDialog= ProgressDialog.show(this, "Please Wait", "Trying to login..", true);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+ed_ipAddress.getText().toString()+":8080/api/config?apiKey="+ed_Api.getText().toString();
        String brightness = "http://"+ed_ipAddress.getText().toString()+":8080/api/brightness?apiKey="+ed_Api.getText().toString();
        StringRequest display = new StringRequest(
                Request.Method.GET,
                brightness,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        bright = obj.getInt("result");
                        Log.e("LOGIN_BRIGHTNESS",bright+"");

                        //sunLite 에 대한 volley 작성해야함
                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                        in.putExtra("ipAddress",ed_ipAddress.getText().toString());
                        in.putExtra("apiKey",ed_Api.getText().toString());
                        in.putExtra("isVerifySunLite",isVerifySunLite);
                        in.putExtra("isVerifySkyMoon",isVerifySkyMoon);
                        in.putExtra("brightness",bright);
                        startActivity(in);
                        Animatoo.animateSlideLeft(this);
                        myProgressDialog.dismiss();
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                null
        );
        Log.e("LoginUrl",url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    queue.add(display);
                    try {
                        JSONObject array = new JSONObject(response);
                        isVerifySkyMoon = array.getBoolean("success");
                        Log.e("LOGIN_API",isVerifySkyMoon+"");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    in.putExtra("ipAddress",ed_ipAddress.getText().toString());
                    in.putExtra("apiKey",ed_Api.getText().toString());
                    in.putExtra("isVerifySunLite",isVerifySunLite);
                    in.putExtra("isVerifySkyMoon",isVerifySkyMoon);
                    in.putExtra("brightness",-1);

                    startActivity(in);
                    Animatoo.animateSlideLeft(this);
                    myProgressDialog.dismiss();
                    finish();

                }
        );
        queue.add(request);



    }

}