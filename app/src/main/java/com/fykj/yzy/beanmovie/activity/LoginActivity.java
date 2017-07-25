package com.fykj.yzy.beanmovie.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.activity.MainActivity;
import com.fykj.yzy.beanmovie.bean.User;
import com.fykj.yzy.beanmovie.bean.UserHolder;
import com.fykj.yzy.beanmovie.util.NullUtil;
import com.fykj.yzy.beanmovie.util.PreferencesUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {


    private DBManage dbManage;

    @BindView(R.id.login_goRegister)
    TextView goRegisterText;
    @BindView(R.id.login_text)
    EditText textEdit;
    @BindView(R.id.login_passworld)
    EditText passworldEdit;

    @OnClick(R.id.login_goRegister)
    void goRegister(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.login_btn)
    void enterBtn(){

        boolean flag1= NullUtil.isNull(textEdit.getText()+""),
                flag2=NullUtil.isNull(passworldEdit.getText()+""),
                flag3=NullUtil.isNull(textEdit.getText().toString().trim()),
                flag4=NullUtil.isNull(passworldEdit.getText().toString().trim());

        if (flag3){
            ToastShow("账号不能为空");
        }else if (flag4){
            ToastShow("密码不能为空");
        }else {
            long id=Long.valueOf(textEdit.getText()+"");
            String pw=passworldEdit.getText().toString();
            User user=new User();

            if (dbManage.login(id,pw)){
                user=dbManage.findUser(id).get(0);
                UserHolder.getUserHolder().setUser(user);
                PreferencesUtils.putLong(this,PreferencesUtils.USER_ID,user.getId());
                PreferencesUtils.putString(this,PreferencesUtils.USER_PASS,user.getPassWorld());

                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
            }else {
                ToastShow("账号密码有误");
            }
        }
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        dbManage=DBManage.getDBManage(this);
    }
    private void ToastShow(String context){
        Toast.makeText(this,context,Toast.LENGTH_SHORT).show();
    }
}
