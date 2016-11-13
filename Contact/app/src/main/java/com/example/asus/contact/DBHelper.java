package com.example.asus.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ASUS on 2016/11/12.
 */
public class DBHelper  extends SQLiteOpenHelper{

    public final static String DB_NAME = "student";
    public final static int VERSION = 2;
    private static DBHelper instance = null;


    private SQLiteDatabase db;

    public static DBHelper getInstance(Context context){
        if(instance == null){
            instance = new DBHelper(context);

        }
        return instance ;
    }

    private  void openDatabase(){
        if(db == null){
            db = this.getWritableDatabase();
        }
    }

    private  DBHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String CREATE_TABLE_STUDENT="CREATE TABLE "+ User.TABLE+"("
                +User .KEY_id+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +User.KEY_imageId+" TEXT, "
                +User.KEY_name+" TEXT, "
                +User.KEY_no+" TEXT, "
                +User.KEY_sex+" TEXT, "
                +User.KEY_position+" TEXT, "
                +User.KEY_phone+" TEXT, "
                +User.KEY_remark+" TEXT, "
                +User.KEY_kaoqin+" TEXT, ";
        db.execSQL(CREATE_TABLE_STUDENT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists user";
        db.execSQL(sql);
        onCreate(db);
    }

    public int save (User user){
        openDatabase();
        ContentValues value = new ContentValues();
        value.put("name",user.name );
        value.put("no",user.no );
        value.put("sex",user.sex );
        value.put("position",user.position );
        value.put("phone",user.phone);
        value.put("remark",user.remark );
        value.put("kaoqin",user.kaoqin );
        value.put("imageid",user.imageId );

        return (int )db.insert("user",null,value) ;
    }



    public ArrayList getUserList(){
        openDatabase() ;
        Cursor cursor = db.query("user",null,null,null,null,null,null,null);
        ArrayList list = new ArrayList();
        while(cursor.moveToNext()){
            HashMap map = new HashMap() ;
            map.put("_id",cursor.getInt(cursor .getColumnIndex("_id") ));
            map.put("imageid",cursor.getInt(cursor .getColumnIndex("imageid") ));
            map.put("name",cursor.getString(cursor .getColumnIndex("name") ));
            map.put("sex",cursor.getString(cursor .getColumnIndex("sex") ));
            map.put("no",cursor.getString(cursor .getColumnIndex("no") ));
            map.put("position",cursor.getString(cursor .getColumnIndex("position") ));
            map.put("phone",cursor.getString(cursor .getColumnIndex("phone") ));
            map.put("remark",cursor.getString(cursor .getColumnIndex("remark") ));
            map.put("kaoqin",cursor.getString(cursor .getColumnIndex("kaoqin") ));
            list.add(map);

        }
        return list;
    }
}
