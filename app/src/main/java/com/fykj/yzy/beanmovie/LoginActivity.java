package com.fykj.yzy.beanmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.R;

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
        long id=Long.valueOf(textEdit.getText()+"");

        if (dbManage.login(id,passworldEdit.getText()+"")){

        }
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        dbManage=DBManage.getDBManage(this);
    }
}
