package com.lovehp30.mirrorcontrol.main.topics;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lovehp30.mirrorcontrol.R;
import com.lovehp30.mirrorcontrol.data.DataActivity;
import com.lovehp30.mirrorcontrol.sqllite.MQDbOpenHelper;

import java.util.ArrayList;

public class TopicsListAdapter extends BaseAdapter {

    ArrayList<ListTopicItem> datas;
    Context context;

    TopicsListAdapter(ArrayList<ListTopicItem> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.topic_list_item, parent, false);
        ListTopicItem data = datas.get(position);
        TextView view1 = convertView.findViewById(R.id.text_title);
        TextView view2 = convertView.findViewById(R.id.text_sub_title);
        view1.setText(data.title);
        view2.setText(data.subtitle.equals("")?"[Empty]":data.subtitle);

        View in = convertView.findViewById(R.id.in_layout);
        in.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DataActivity.class);
            intent.putExtra("Client_Code", data.title);
            intent.putExtra("Topics", data.subtitle);
            context.startActivity(intent);

        });
        View del = convertView.findViewById(R.id.del_layout);
        View finalConvertView = convertView;
        del.setOnClickListener(v -> {
            MQDbOpenHelper helper = new MQDbOpenHelper(context, "lovehp12duckdnsorg");
            helper.open();
            helper.deleteColumn(data.id);
            helper.close();
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animate_slide_out_right);
            finalConvertView.startAnimation(animation);
            Handler handle = new Handler();
            handle.postDelayed(() -> {
                datas.remove(position);
                notifyDataSetChanged();
                animation.cancel();
            }, 300);
//
        });

        Toast.makeText(context, datas.get(position).toString(), Toast.LENGTH_SHORT);
        return convertView;
    }

}
