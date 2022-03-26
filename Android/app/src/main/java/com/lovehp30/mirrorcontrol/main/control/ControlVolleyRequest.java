package com.lovehp30.mirrorcontrol.main.control;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class ControlVolleyRequest {
    public static void onlyGetRequest(Context context,String ip,String key,String event){
        Log.e("Request",event);
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://"+ip+":8080/api/"+event+"?apiKey="+key;
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
        },null);
        queue.add(request);
    }
}
