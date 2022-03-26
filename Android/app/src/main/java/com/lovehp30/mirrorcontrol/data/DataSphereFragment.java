package com.lovehp30.mirrorcontrol.data;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lovehp30.mirrorcontrol.R;
import com.magicgoop.tagsphere.TagSphereView;
import com.magicgoop.tagsphere.item.TextTagItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DataSphereFragment extends Fragment {
    TagSphereView tagSphereView;
    String ip = "lovehp30.duckdns.org";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM0 = "param0";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private String code;
    private String topic;

    public DataSphereFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataSphereFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataSphereFragment putID(String param0, String param1, String param2) {
        DataSphereFragment fragment = new DataSphereFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM0, param0);
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ip = getArguments().getString(ARG_PARAM0);
            code = getArguments().getString(ARG_PARAM1);
            topic = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_data_sphere, container, false);
        Log.e("Fragment_SPH",code+" "+topic);
        tagSphereView = v.findViewById(R.id.tagView);
        TextPaint paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setTextSize(40f);
        paint.setColor(Color.BLACK);

        RequestQueue queue = Volley.newRequestQueue(v.getContext());
        String url = "http://"+ip+":3000/getData/"+code+"/limit/100";
        Log.e("Fragment_SPH",url);
        List<TextTagItem> item = new ArrayList<>();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response ->  {
                    try {
                        JSONArray array = new JSONArray(response);

                        Random random= new Random();
                        for(int i=0;i<array.length();i++){
                            JSONObject object = array.getJSONObject(i);
                            item.add(new TextTagItem(object.getString("message").split(",")[random.nextInt(2)]));
                        }
                        Log.e("Fragment_SPH",item.get(0).getText());

                        tagSphereView.setTextPaint(paint);
                        tagSphereView.addTagList(item);
                        tagSphereView.setRadius(2.5f);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                },
                error -> {
                    for(int i=0;i<50;i++){
                        item.add(new TextTagItem("nan"));
                    }

                }
        );
        queue.add(request);



        // Inflate the layout for this fragment
        return v;
    }
}