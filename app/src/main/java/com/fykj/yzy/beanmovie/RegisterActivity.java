package com.fykj.yzy.beanmovie;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/7/8.
 */

public class RegisterActivity extends AppCompatActivity{


    @BindView(R.id.eye)
    CheckBox eye;
    //密码框
    @BindView(R.id.register_password)
    EditText password;
    //电话框
    @BindView(R.id.register_tel)
    EditText tel;
    //昵称
    @BindView(R.id.register_nickn)
    EditText nickn;

    DBManage db;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);





        eye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }
                else
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
            }
        });
    }

    @OnClick(R.id.bt_register)
    void register(View view)  {
        User user = new User();
        db = DBManage.getDBManage(this);
        long register_tel =Long.valueOf(tel.getText().toString());
        String register_password = password.getText().toString();
        String register_nickn = nickn.getText().toString();
        user.setName(register_nickn);
        user.setPassWorld(register_password);
        user.setId(register_tel);
        try {
            /*判断是否注册成功再跳转*/
            db.insertUser(user);
        } catch (Exception e) {
            showToast("注册失败，该用户已存在");
        }
    }
    private void showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }


}
