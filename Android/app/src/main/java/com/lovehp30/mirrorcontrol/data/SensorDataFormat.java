package com.lovehp30.mirrorcontrol.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SensorDataFormat {
    private String messages;
    private String time;
    private String code;
    String str[];
    private Map<String,Float> map = new HashMap<>();
    public SensorDataFormat(String code, String messages, String time){
        this.messages = messages;
        this.time = time;
        this.code = code;
         str = messages.split(",");
        for(String s:str){
            String[] token = s.split(":");
            Float data = token[1].equals("nan")?-999:Float.valueOf(token[1]);
            map.put(token[0],data);
        }
    }
    public  String getVal(int i){
       return str[i] ;
    }
    public String getMessages(){
        return messages;
    }
    public Float getValuesOfToken(String Key){
        return map.get(Key);
    }
    public ArrayList<String> getMapKeyArrayList(){
        ArrayList<String> list = new ArrayList<>(map.keySet());
        Collections.reverse(list);
        return list;
    }

    public String getCode() {
        return code;
    }

    public String getTime() {
        return time;
    }
}
