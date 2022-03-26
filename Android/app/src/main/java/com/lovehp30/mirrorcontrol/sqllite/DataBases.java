package com.lovehp30.mirrorcontrol.sqllite;

import android.provider.BaseColumns;

// DataBase Table
public final class DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String CODE = "code";
        public static final String TOPIC = "topic";
        public static final String _TABLENAME = "iotlist";
        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        +_ID+" integer primary key autoincrement, "
                        +CODE+" text not null , "
                        +TOPIC+" text not null );";
    }
}