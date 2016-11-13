package com.example.asus.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by ASUS on 2016/11/13.
 */
public class DetailActivity extends Activity{

    HashMap map;
    ImageButton btn_img;

    EditText et_name;
    EditText et_no;
    EditText et_sex;
    EditText et_position;
    EditText et_phone;
    EditText et_remark;
    EditText et_kaoqin;

    Button btn_modify;
    Button btn_delete;
    Button btn_return;

    private int user_id=0;

    boolean flage = false ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout .detail );
        initWidget() ;
        setEditTextDisable();
        Intent intent = getIntent();
        map = (HashMap) intent.getSerializableExtra("usermap");
        displayData();

       user_id =0;
       user_id  =intent.getIntExtra("user_Id", 0);

        btn_modify.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {

                if(flage){
                    flage = false;
                    btn_modify .setText("修改");
                    setEditTextDisable();

                    String name = et_name.getText().toString();
                    if(name.equals("")){
                        Toast.makeText(DetailActivity .this,"姓名不能为空！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    String no = et_no.getText().toString();
                    if(no.equals("")){
                        Toast.makeText(DetailActivity.this,"学号不能为空！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    String position = et_position.getText().toString();
                    String phone = et_phone.getText().toString();
                    String remark = et_remark.getText().toString();
                    String sex = et_sex.getText().toString();
                    String kaoqin = et_kaoqin.getText().toString();
                    //int imageId = images[imagePosition];


                    UserRepo repo = new UserRepo() ;
                    User user  = repo.getStudentById(user_id );
                    //user.imageId = imageId;
                    user.name = name;
                    user.no = no;
                    user.sex = sex;
                    user.position = position;
                    user.phone = phone;
                    user.remark = remark;
                    user.kaoqin = kaoqin;
                    user.id=user_id;


                    //save users to DateBase

                    if(user_id!=0){
                        repo.update(user);
                        Toast.makeText(DetailActivity.this,"修改成功！",Toast.LENGTH_LONG).show();
                        // resultCode为1的时候代表用户添加成功，返回到主界面
                       // setResult(1);
                       // finish();


                    }else{
                        setEditTextAble();
                        Toast.makeText(DetailActivity.this,"修改失败，请重新操作！",Toast.LENGTH_LONG).show();
                        // resultCode为2的时候代表用户添加失败，返回到主界面
                        //setResult(2);
                        //finish();

                    }

                }else{
                    flage = true ;
                    btn_modify .setText("保存");
                    setEditTextAble();
                }


            }
        });
        btn_return.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });
        btn_delete .setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                UserRepo repo = new UserRepo() ;
                repo.delete(user_id);
                Toast.makeText(DetailActivity.this,"学生信息已删除！",Toast.LENGTH_LONG).show();
                setResult(2);
                finish();
            }
        });


    }

    public void initWidget(){
        et_name = (EditText) this.findViewById(R.id.et_name);
        et_no = (EditText) this.findViewById(R.id.et_no);
        et_position = (EditText) this.findViewById(R.id.et_position);
        et_phone = (EditText) this.findViewById(R.id.et_phone);
        et_remark = (EditText) this.findViewById(R.id.et_remark);
        et_sex = (EditText) this.findViewById(R.id.et_sex);
        et_kaoqin = (EditText) this.findViewById(R.id.et_kaoqin);
        btn_img = (ImageButton) this.findViewById(R.id.btn_img);

        btn_modify = (Button)this.findViewById(R.id.btn_modify);
        btn_delete = (Button)this.findViewById(R.id.btn_delete);
        btn_return = (Button)this.findViewById(R.id.btn_return);

    }

    private void setEditTextDisable(){
        et_name.setEnabled(false);
        et_no.setEnabled(false);
        et_position.setEnabled(false);
        et_sex.setEnabled(false);
        et_phone.setEnabled(false);
        et_remark.setEnabled(false);
        et_kaoqin.setEnabled(false);
        btn_img.setEnabled(false);
    }
    private void setEditTextAble(){
        et_name.setEnabled(true);
        et_no.setEnabled(true);
        et_position.setEnabled(true);
        et_sex.setEnabled(true);
        et_phone.setEnabled(true);
        et_remark.setEnabled(true);
        et_kaoqin.setEnabled(true);
        btn_img.setEnabled(true);
    }


    private void displayData(){
        et_name.setText(String.valueOf(map.get("name")));
        et_no.setText(String.valueOf(map.get("no")));
        et_sex.setText(String.valueOf(map.get("sex")));
        et_position.setText(String.valueOf(map.get("position")));
        et_phone.setText(String.valueOf(map.get("phone")));
        et_remark.setText(String.valueOf(map.get("remark")));
        et_kaoqin.setText(String.valueOf(map.get("kaoqin")));
        btn_img.setImageResource(Integer.parseInt(String.valueOf(map.get("imageid"))));

    }

}
