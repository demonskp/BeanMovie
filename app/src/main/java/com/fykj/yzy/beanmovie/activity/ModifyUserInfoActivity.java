package com.fykj.yzy.beanmovie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.bean.User;
import com.fykj.yzy.beanmovie.bean.UserHolder;
import com.fykj.yzy.beanmovie.util.DialogCreaterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyUserInfoActivity extends AppCompatActivity {

    DBManage db;
    User user;


    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.tv_click_password)
    TextView tv_click_password;
    @BindView(R.id.autograph_text)
    EditText autograph_text;
    @BindView(R.id.modify_user_info)
    Button modify_user_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_info);
        ButterKnife.bind(this);
        db=DBManage.getDBManage(this);
        nickname.setEnabled(false);
        autograph_text.setEnabled(false);
        user= UserHolder.getUserHolder().getUser();
        showUserInfo();
    }



    @OnClick(R.id.nav_top_back)
    public void back(View view){
        finish();
    }
    int status = 0;
    /*编辑->完成 的实现*/
    @OnClick(R.id.modify_user_info)
    public void ModifyUserInfo(View view){
        if (status == 0){
            modify_user_info.setText("完成");
            nickname.setEnabled(true);
            autograph_text.setEnabled(true);
        }else if(status == 1){
            modify_user_info.setText("编辑");
            nickname.setEnabled(false);
            autograph_text.setEnabled(false);
            modifyUserInfo();
        }
        status=(status+1)%2;//循环实现2个不同的功能
    }

    /*加载当前用户的昵称和签名*/
    private void showUserInfo(){
        nickname.setText(user.getName()+"");
        autograph_text.setText(user.getMotto()+"");
    }


    /*修改当前用户的昵称和个人签名*/
    private void modifyUserInfo(){
        String nickname1 = nickname.getText().toString();
        String autograph_text1 = autograph_text.getText().toString();
        User user=new User();
        user.setId(this.user.getId());
        user.setName(nickname1);
        user.setMotto(autograph_text1);
        user.setPassWorld(this.user.getPassWorld());
        if (db.updataUser(user)){
            UserHolder.getUserHolder().setUser(user);
            this.user=user;
            showUserInfo();
        }else {
            Toast.makeText(this,"更新设置失败",Toast.LENGTH_SHORT).show();
            showUserInfo();
        }
    }

    @OnClick(R.id.tv_click_password)
    public void showResetPassword(View view){
        Intent intent = new Intent(this,ResetPasswordActivity.class);
        startActivity(intent);
    }
    public void showToast(String context){
        Toast.makeText(this,context,Toast.LENGTH_SHORT).show();
    }

}
