package com.example.asus.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ASUS on 2016/11/12.
 */
public class MainActivity extends Activity{

    GridView gv_button_menu;
    ListView lv_userList;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        loadUserList();
    }

    private void loadUserList(){
        lv_userList = (ListView) this.findViewById(R.id.lv_userlist);
        ArrayList data = DBHelper.getInstance(this).getUserList();
        adapter = new SimpleAdapter(this,data,R.layout.list_item,
                new String[]{"imageid","name","no"},
                new int[]{R.id.user_image,R.id.tv_showname,R.id.tv_showno} );
        lv_userList .setAdapter(adapter) ;
        lv_userList .setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap map = (HashMap)parent.getItemAtPosition(position) ;
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("usermap",map);
                //当requestCode为3的时候代表请求转向DetailActivity界面。
                startActivityForResult(intent,3);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU){
            if(gv_button_menu  == null){
                loadButtonMenu();
            }
            if(gv_button_menu .getVisibility() == View.GONE){
                gv_button_menu.setVisibility(View.VISIBLE);
            }else{
                gv_button_menu .setVisibility(View.GONE);
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void loadButtonMenu(){
        gv_button_menu = (GridView) this.findViewById(R.id.gv_button_menu);

        gv_button_menu.setNumColumns(5) ;
        gv_button_menu.setGravity(Gravity.TOP) ;
        gv_button_menu.setVerticalSpacing(10);
//        gv_button_menu.setHorizontalSpacing(10);
        ArrayList data = new ArrayList();
        HashMap map = new HashMap() ;
        map.put("itemImage",R.drawable.add) ;
        map.put("itemText","增加") ;
        data.add(map);

        map = new HashMap() ;
        map.put("itemImage",R.drawable.search) ;
        map.put("itemText","查找") ;
        data.add(map);

        map = new HashMap() ;
        map.put("itemImage",R.drawable.delete) ;
        map.put("itemText","删除") ;
        data.add(map);

        map = new HashMap() ;
        map.put("itemImage",R.drawable.menu) ;
        map.put("itemText","菜单") ;
        data.add(map);

        map = new HashMap() ;
        map.put("itemImage",R.drawable.exit) ;
        map.put("itemText","退出") ;
        data.add(map);

        SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.item_menu,new String[]{"itemImage","itemText"},new int []{R.id.item_image,R.id.item_text});
        //data中的每一个map都将以R.layout.item_menu的布局方式将itemImage为R.id.item_image，itemText为R.id.item_text的资源展示出来
        gv_button_menu.setAdapter(adapter);

        gv_button_menu.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               //HashMap map =  (HashMap) parent.getItemAtPosition(1);
                switch(position){
                    case 0 : {
                        Intent intent = new Intent(MainActivity.this, AddNewActivity.class);
                        startActivityForResult(intent, 0);
                        //0代表请求跳转到添加界面
                        break;
                    }
                    case 1 : {
                        break;
                    }
                    case 2 : {
                        break;
                    }
                    case 3 : {
                        break;
                    }
                    case 4 : {
                        finish() ;
                        break;
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0) {
            if (resultCode == 1) {
                //增加用户成功，进行刷新。
                ArrayList userdata = DBHelper.getInstance(this).getUserList();
                adapter = new SimpleAdapter(this,userdata,R.layout.list_item,
                        new String[]{"imageid","name","no"},
                        new int[]{R.id.user_image,R.id.tv_showname,R.id.tv_showno} );
                lv_userList.setAdapter(adapter);
            } else if (resultCode == 2) {
                //增加用户失败，不刷新。
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
