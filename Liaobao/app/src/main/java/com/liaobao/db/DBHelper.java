package com.liaobao.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * <br>author:jinpneg</br>
 * <br>Time：2017/6/6 20:33</br>
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "liaobao_num1";
    private static final int DB_VERSION = 1;
    private static DBHelper mInstance;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public synchronized static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }
        return mInstance;
    }

    ;

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * 聊天记录
         */
        String sql_msg = "Create table IF NOT EXISTS " + DBcolumns.TABLE_MSG
                + "(" + DBcolumns.MSG_ID + " integer primary key autoincrement,"
                + DBcolumns.MSG_FROM + " VARCHAR(30),"
                + DBcolumns.MSG_TO + " VARCHAR(30) ,"
                + DBcolumns.MSG_TYPE + " VARCHAR(30) ,"
                + DBcolumns.MSG_CONTENT + " VARCHAR(30) ,"
                + DBcolumns.MSG_ISCOMING + " INTEGER DEFAULT 0,"
                + DBcolumns.MSG_DATE + " VARCHAR(50),"
                + DBcolumns.MSG_ISREADED + " VARCHAR(50),"
                + DBcolumns.MSG_BAK1 + " VARCHAR(50),"
                + DBcolumns.MSG_BAK2 + " VARCHAR(50),"
                + DBcolumns.MSG_BAK3 + " VARCHAR(50),"
                + DBcolumns.MSG_BAK4 + " VARCHAR(50),"
                + DBcolumns.MSG_BAK5 + " VARCHAR(50),"
                + DBcolumns.MSG_BAK6 + " VARCHAR(50));";

        db.execSQL(sql_msg);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
