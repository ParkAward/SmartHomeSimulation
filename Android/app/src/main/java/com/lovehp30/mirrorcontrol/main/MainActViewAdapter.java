package com.lovehp30.mirrorcontrol.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.lovehp30.mirrorcontrol.common.ErrorFragment;
import com.lovehp30.mirrorcontrol.main.control.ControlFragment;
import com.lovehp30.mirrorcontrol.main.topics.ListTopicItem;
import com.lovehp30.mirrorcontrol.main.topics.TopicsFragment;

public class MainActViewAdapter extends FragmentStateAdapter {
    public int mCount;
    TopicsFragment list;
    String ip,key;
    int bright=0;
    public MainActViewAdapter(int bright,String ip,String key,@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.ip = ip;
        this.key = key;
        this.bright = bright;


    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        if(position == 0)return MainActivity.isVerifySkyMoon?new ControlFragment():new ErrorFragment();
        if(position == 0)return new ControlFragment().setting(bright,ip,key);
        else {
            list = new TopicsFragment().setting("lovehp12.duckdns.org");
            return MainActivity.isVerifySunLite?list:new ErrorFragment();

        }
    }


    @Override
    public boolean containsItem(long itemId) {
        return super.containsItem(itemId);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public void addListData(ListTopicItem item){
        list.addData(item);
    }


    public void refreshList(){
        list.restart();
    }


}
