package com.example.asus.contact;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class AddNewActivity extends AppCompatActivity {


    ImageButton btn_img;
    AlertDialog imageChooseDialog;
    Gallery gallery;
    ImageSwitcher is;
    int imagePosition;
    String sex;
    String kaoqin ;

    EditText et_name;
    EditText et_no;
    EditText et_position;
    EditText et_phone;
    EditText et_remark;

    RadioGroup rg_sex ;
    RadioButton rb_male;
    RadioButton rb_female;

    RadioGroup rg_kaoqin ;
    RadioButton rb_daoke;
    RadioButton rb_queke;
    RadioButton rb_qingjia;
    RadioButton rb_chidao;
    RadioButton rb_zaotui;




    Button btn_save;
    Button btn_return;

    private int[] images = {R.drawable.icon,
            R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnew);



        initWidget();

        btn_save.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                if(name.equals("")){
                    Toast.makeText(AddNewActivity.this,"姓名不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                String no = et_no.getText().toString();
                if(no.equals("")){
                    Toast.makeText(AddNewActivity.this,"学号不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                String position = et_position.getText().toString();
                String phone = et_phone.getText().toString();
                String remark = et_remark.getText().toString();
                int imageId = images[imagePosition];

                UserRepo repo = new UserRepo() ;
                User user = new User();
                user.imageId = imageId;
                user.name = name;
                user.no = no;
                user.sex = sex;
                user.position = position;
                user.phone = phone;
                user.remark = remark;
                user.kaoqin = kaoqin;


                //save users to DateBase
                int success = DBHelper.getInstance(AddNewActivity.this).save(user);

                if(success != -1){
                    Toast.makeText(AddNewActivity.this,"添加成功！",Toast.LENGTH_LONG).show();
                    // resultCode为1的时候代表用户添加成功，返回到主界面
                    setResult(1);
                    finish();


                }else{
                    Toast.makeText(AddNewActivity.this,"添加失败，请重新操作！",Toast.LENGTH_LONG).show();
                    // resultCode为2的时候代表用户添加失败，返回到主界面
                    setResult(2);
                    finish();

                }
            }
        });

        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rb_male.getId() ==checkedId ) {
                    sex = rb_male.getText().toString();
                } else if (rb_female.getId() == checkedId) {
                    sex = rb_female.getText().toString();
                }
            }
        });

        rg_kaoqin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rb_daoke.getId() ==checkedId ) {
                    kaoqin = rb_daoke.getText().toString();
                } else if (rb_queke.getId() == checkedId) {
                    kaoqin = rb_queke.getText().toString();
                } else if (rb_qingjia.getId() == checkedId) {
                    kaoqin = rb_qingjia.getText().toString();
                } else if (rb_chidao.getId() == checkedId) {
                    kaoqin = rb_chidao.getText().toString();
                } else if (rb_zaotui.getId() == checkedId) {
                    kaoqin = rb_zaotui.getText().toString();
                }

            }
        });


        btn_img .setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                initImageChooseDialog();
                imageChooseDialog.show();
            }
        });

        btn_return.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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

        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);

        rg_kaoqin = (RadioGroup) findViewById(R.id.rg_kaoqin);
        rb_daoke = (RadioButton) findViewById(R.id.rb_daoke);
        rb_queke = (RadioButton) findViewById(R.id.rb_queke);
        rb_qingjia = (RadioButton) findViewById(R.id.rb_qingjia);
        rb_chidao = (RadioButton) findViewById(R.id.rb_chidao);
        rb_zaotui = (RadioButton) findViewById(R.id.rb_zaotui);

        btn_img = (ImageButton) this.findViewById(R.id.btn_img);

        btn_save = (Button) this.findViewById(R.id.btn_save);
        btn_return = (Button) this.findViewById(R.id.btn_return);
    }



    private void initImageChooseDialog() {
        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setTitle("请选择图片：");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btn_img.setImageResource(images[imagePosition%images.length]);

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.imageswitch,null);
        gallery = (Gallery) view.findViewById(R.id.img_gallery);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setSelection(images.length/2);
        is = (ImageSwitcher) view.findViewById(R.id.image_switcher);
        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imagePosition= position;
                is.setImageResource(images[position%images.length]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        is.setFactory(new MyViewFactory(this));
        builder.setView(view);
        imageChooseDialog = builder.create();
    }

    class ImageAdapter extends BaseAdapter{
        private Context context;

        public ImageAdapter(Context context){
            this.context = context;
        }
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv = new ImageView(context);
            iv.setImageResource(images [position%images.length]);
            iv.setAdjustViewBounds(true);
            iv.setLayoutParams(new Gallery.LayoutParams(80,80));
            iv.setPadding(15,10,15,10);
            return iv;
        }
    }
    class MyViewFactory implements ViewFactory{
        private Context context ;
        public MyViewFactory(Context context){
            this.context = context ;
        }
        public View makeView(){
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(new ImageSwitcher.LayoutParams(90,90));
            return null;

        }
    }
}
