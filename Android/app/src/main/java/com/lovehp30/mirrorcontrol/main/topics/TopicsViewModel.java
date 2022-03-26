package com.lovehp30.mirrorcontrol.main.topics;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.lovehp30.mirrorcontrol.sqllite.MQDbOpenHelper;

import java.util.ArrayList;

public class TopicsViewModel extends ViewModel {
    private MQDbOpenHelper databaseHelper;
    private ArrayList<ListTopicItem> datas;
    // TODO: Implement the ViewModel
    void setDatabaseHelper(Context v,String ip){
        ip = ip.replaceAll("[.]","");
        databaseHelper = new MQDbOpenHelper(v,ip);
    }

    ArrayList<ListTopicItem> getDBData(){
        databaseHelper.open();
        ArrayList<ListTopicItem> list = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllColumns();
        if(cursor.getColumnCount()==0){
            list.add(new ListTopicItem(0,"",""));
            return list;
        }
            Log.e("DB","Count = "+cursor.getCount());
        while (cursor.moveToNext()){
            list.add(new ListTopicItem(
                    cursor.getLong(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("code")),
                    cursor.getString(cursor.getColumnIndex("topic"))
            ));
        }
        cursor.close();
        databaseHelper.close();
        return list;
    }
}