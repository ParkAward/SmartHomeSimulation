package com.lovehp30.mirrorcontrol.main.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.slider.Slider;
import com.lovehp30.mirrorcontrol.R;
import com.lovehp30.mirrorcontrol.main.topics.TopicsFragment;

public class ControlFragment extends Fragment {
    String ip = "";
    String key = "";
    int bright=0;

    private ControlViewModel mViewModel;

    public static ControlFragment setting(int bright,String ip,String key) {
        ControlFragment fragment = new ControlFragment();
        Bundle args = new Bundle();
        args.putString("address", ip);
        args.putString("key", key);
        args.putInt("bright", bright);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ip = getArguments().getString("address");
            key = getArguments().getString("key");
            bright = getArguments().getInt("bright");
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.control_fragment, container, false);
        Button monitorOn = v.findViewById(R.id.monitor_on);
        monitorOn.setOnClickListener(vi->{
            ControlVolleyRequest.onlyGetRequest(v.getContext(),ip,key,"monitor/on");

        });
        Button monitorOff = v.findViewById(R.id.monitor_off);
        monitorOff.setOnClickListener(vi->{
            ControlVolleyRequest.onlyGetRequest(v.getContext(),ip,key,"monitor/off");
        });
        TextView br_text = v.findViewById(R.id.br_text);
        br_text.setText(bright+"");
        Slider slider = v.findViewById(R.id.brightness);
        if(bright>=0) {
            slider.setValue(bright);
            slider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    br_text.setText(value+"");
                }
            });
            slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(@NonNull Slider slider) {

                }
                @Override
                public void onStopTrackingTouch(@NonNull Slider slider) {
                    ControlVolleyRequest.onlyGetRequest(v.getContext(),ip,key,"brightness/"+(int)slider.getValue());
                }
            });
        }


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ControlViewModel.class);
        // TODO: Use the ViewModel
    }

}