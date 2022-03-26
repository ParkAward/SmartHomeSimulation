package com.lovehp30.mirrorcontrol.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.lovehp30.mirrorcontrol.R;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataGraphFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String title;
    private String[] time;
    private String[] data;

    public DataGraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param time Parameter 1.
     * @param data Parameter 2.
     * @return A new instance of fragment DataGraphFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataGraphFragment newInstance(String[] time, String[] data, String title) {
        DataGraphFragment fragment = new DataGraphFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM1, time);
        args.putStringArray(ARG_PARAM2, data);
        args.putString(ARG_PARAM3, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            time = getArguments().getStringArray(ARG_PARAM1);
            data = getArguments().getStringArray(ARG_PARAM2);
            title = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_data_graph, container, false);
        // Inflate the layout for this fragment

        LineChart chart = v.findViewById(R.id.chart);
        chart.invalidate();//초기화
        chart.clear();
        ArrayList<Entry> values = new ArrayList<>();

        //data
        for (int i = 0; i < data.length; i++) {
            long x = DateToMill(time[i]);
            String temp = data[i].split(":")[1];
            if(!temp.equals("nan")){
                float val = Float.valueOf(temp);
                values.add(new Entry(x, val)); //x : time y : data
            }
        }
        //data sampling
        int sample=0;
        String s = data[sample].split(":")[1];
        while (s.equals("nan"))s = data[++sample].split(":")[1];
        sample = (int)Float.parseFloat(s) %9;


        LineDataSet lineDataset = new LineDataSet(values, title);
        lineDataset.setColor(ContextCompat.getColor(getContext(), ColorAnInt(sample,false))); //LineChart에서 Line Color 설정
        lineDataset.setCircleColor(ContextCompat.getColor(getContext(),ColorAnInt(sample,true))); // LineChart에서 Line Circle Color 설정
        lineDataset.setCircleColorHole(ContextCompat.getColor(getContext(), ColorAnInt(sample,true))); // LineChart에서 Line Hole Circle Color 설정
        lineDataset.setDrawFilled(true);
        lineDataset.setFillColor(ContextCompat.getColor(getContext(), ColorAnInt(sample,false)));

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataset);

        lineData.setValueTextColor(R.color.material_on_surface_stroke);
        lineData.setValueTextSize(9);

        // x축 설정
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setLabelCount(8);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(false);
        xAxis.setValueFormatter((value, axis) -> MillToDateSimple((long) value)+"시");
        //확대시 정보 자세하게 보여주는 부분 미설정
        // y출 설정
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);
        chart.setData(lineData);
        chart.animateXY(500,500);
        return v;
    }
    public String MillToDateSimple(long mills) {
        String pattern = "MM-dd EEE H";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String date = (String) formatter.format(new Timestamp(mills));
        return date;
    }
    public String MillToDate(long mills) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String date = (String) formatter.format(new Timestamp(mills));
        return date;
    }

    public long DateToMill(String date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date trans_date = null;
        try {
            trans_date = formatter.parse(date);
        } catch (ParseException e) { // TODO Auto-generated catch block e.printStackTrace(); } return trans_date.getTime(); }
            e.printStackTrace();
        }
        return trans_date.getTime();
    }
    int ColorAnInt(int rand,boolean type){
        switch (rand){
            case 0: return type? R.color.graph1: R.color.graph1_l;
            case 1: return type? R.color.graph2: R.color.graph2_l;
            case 2: return type? R.color.graph3: R.color.graph3_l;
            case 3: return type? R.color.graph4: R.color.graph4_l;
            case 4: return type? R.color.graph5: R.color.graph5_l;
            case 5: return type? R.color.graph6: R.color.graph6_l;
            case 6: return type? R.color.graph7: R.color.graph7_l;
            case 7: return type? R.color.graph8: R.color.graph8_l;
            case 8: return type? R.color.graph9: R.color.graph9_l;
            default: return type? R.color.graph10: R.color.graph10_l;
        }
    }
}