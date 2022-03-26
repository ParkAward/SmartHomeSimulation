package com.lovehp30.mirrorcontrol.main;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.lovehp30.mirrorcontrol.R;
import com.lovehp30.mirrorcontrol.login.LoginActivity;
import com.lovehp30.mirrorcontrol.main.control.ControlVolleyRequest;
import com.lovehp30.mirrorcontrol.sqllite.MQDbOpenHelper;
import com.lovehp30.mirrorcontrol.main.topics.ListTopicItem;

public class MainActivity extends AppCompatActivity {
    public static boolean isVerifySunLite,isVerifySkyMoon;
    String ip,key;
    int bright;
    ActionBar actionBar;
    DrawerLayout drawer;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.parseColor("#3d3d3d"));
        Bundle bundle = getIntent().getExtras();
        ip = bundle.getString("ipAddress");
        key = bundle.getString("apiKey");
        isVerifySkyMoon = bundle.getBoolean("isVerifySkyMoon");
        isVerifySunLite = bundle.getBoolean("isVerifySunLite");
        bright = bundle.getInt("brightness");
        Log.e("Main",isVerifySkyMoon+"  "+isVerifySunLite);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle;
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.outline_menu_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        ImageView img = v.findViewById(R.id.nh_imageView);
        img.setImageResource(isVerifySkyMoon? R.drawable.monitor_on: R.drawable.monitor_off);
        TextView textView = v.findViewById(R.id.nh_title);
        textView.setText(isVerifySkyMoon?ip:"Not Connected");
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.mirror_reboot:
                    ControlVolleyRequest.onlyGetRequest(this,
                            ip,key,"reboot");
                    finish();
                    break;
            }
            item.setChecked(false);
            drawer.closeDrawers();
            return true;
        });
        //pager
        ViewPager2 pager2 = findViewById(R.id.viewPager);
        MainActViewAdapter adapter=new MainActViewAdapter(bright,ip,key,getSupportFragmentManager(),getLifecycle());
        pager2.setAdapter(adapter);
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.e("Scroll",position+" "+positionOffset+" "+positionOffsetPixels);
                if(position ==0 && positionOffset ==0) {
                    fab.setVisibility(View.INVISIBLE);
                }
                else if(position==0 && positionOffset>0){
                    fab.setVisibility(View.VISIBLE);
                    fab.setAlpha(positionOffset);
                }else if(position==1 && positionOffset==0){
                }else{
                }
            }
        });
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
            View edit  = getLayoutInflater().inflate(R.layout.edit_layout, null);
            Button ed_btn = edit.findViewById(R.id.ed_ok);
            builder.setView(edit);
            AlertDialog ad = builder.create();
            ed_btn.setOnClickListener(v1 -> {
                TextInputEditText code = edit.findViewById(R.id.ed_code);
                TextInputEditText topic = edit.findViewById(R.id.ed_topic);
                if(!code.getText().toString().equals("")){
                    MQDbOpenHelper helper = new MQDbOpenHelper(getBaseContext(),"lovehp12duckdnsorg");
                    helper.open();
                    helper.insertColumn(code.getText().toString(),topic.getText().toString());
                    Cursor cursor = helper.getRecentColumns();
                    if(cursor.moveToNext()) {
                        adapter.addListData(new ListTopicItem(
                                cursor.getLong(cursor.getColumnIndex("_id")),
                                cursor.getString(cursor.getColumnIndex("code")),
                                cursor.getString(cursor.getColumnIndex("topic"))
                        ));
                    }
                    helper.close();
                    cursor.close();
                    ad.dismiss();
                }
            });
            ad.show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logOutThisIp(View v){
        Intent in = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(in);
        Animatoo.animateSlideRight(this);
        finish();

    }




}