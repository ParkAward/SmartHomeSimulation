package com.lovehp30.mirrorcontrol.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MQDbOpenHelper {
    public static String DATABASE_NAME = "";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    public MQDbOpenHelper(Context context, String DATABASE_NAME){
        this.mCtx = context;
        this.DATABASE_NAME = DATABASE_NAME;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB._CREATE);

        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ DataBases.CreateDB._TABLENAME);
            onCreate(db);
        }
    }


    public MQDbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDB.close();
    }

    public long insertColumn(String code, String topic){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.CODE, code);
        values.put(DataBases.CreateDB.TOPIC, topic);
        return mDB.insert(DataBases.CreateDB._TABLENAME, null, values);
    }
    public boolean deleteColumn(long id){
        return mDB.delete(DataBases.CreateDB._TABLENAME, "_id="+id, null) > 0;
    }

    // Select All
    public Cursor getAllColumns(){
        return mDB.query(DataBases.CreateDB._TABLENAME, null, null, null, null, null, null);
    }
    public Cursor getRecentColumns(){
        return mDB.query(DataBases.CreateDB._TABLENAME, null, null, null, null, null, "_id DESC","1");
    }
}
