package com.example.asus.contact;


/**
 * Created by ASUS on 2016/11/13.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class UserRepo {
    private DBHelper dbHelper;



    public int insert(User user){
        //打开连接，写入数据
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(User.KEY_imageId,user.imageId );
        values.put(User.KEY_name,user.name);
        values.put(User.KEY_no,user.no);
        values.put(User.KEY_sex,user.sex);
        values.put(User.KEY_position,user.position);
        values.put(User.KEY_phone,user.phone);
        values.put(User.KEY_remark,user.remark);
        values.put(User.KEY_kaoqin,user.kaoqin );

        long student_Id=db.insert(User.TABLE,null,values);
        db.close();
        return (int)student_Id;
    }

    public void delete(int id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(User.TABLE,User.KEY_id+"=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void update(User user){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(User.KEY_imageId,user.imageId );
        values.put(User.KEY_name,user.name);
        values.put(User.KEY_no,user.no);
        values.put(User.KEY_sex,user.sex);
        values.put(User.KEY_position,user.position);
        values.put(User.KEY_phone,user.phone);
        values.put(User.KEY_remark,user.remark);
        values.put(User.KEY_kaoqin,user.kaoqin );

        db.update(User.TABLE,values,User.KEY_id+"=?",new String[] { String.valueOf(user.id) });
        db.close();
    }

    public User getStudentById(int Id){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                User.KEY_imageId+","+
                User.KEY_name+","+
                User.KEY_no+","+
                User.KEY_sex+","+
                User.KEY_position+","+
                User.KEY_phone+","+
                User.KEY_remark+","+
                User.KEY_kaoqin+" FROM "+User.TABLE
                + " WHERE " +
                User.KEY_id + "=?";
        User user = new User() ;
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if(cursor.moveToFirst()){
            do{
                user.id  =cursor.getInt(cursor.getColumnIndex(User.KEY_id));
                user.imageId =cursor.getInt(cursor.getColumnIndex(User.KEY_imageId));
                user.name =cursor.getString(cursor.getColumnIndex(User.KEY_name));
                user.no  =cursor.getString(cursor.getColumnIndex(User.KEY_no));
                user.sex =cursor.getString(cursor.getColumnIndex(User.KEY_sex));

                user.position   =cursor.getString(cursor.getColumnIndex(User .KEY_position));
                user.phone =cursor.getString(cursor.getColumnIndex(User.KEY_phone));
                user.remark =cursor.getString(cursor.getColumnIndex(User.KEY_remark));
                user.kaoqin =cursor.getString(cursor.getColumnIndex(User.KEY_kaoqin));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return user;
    }
}
