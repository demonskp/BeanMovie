package com.fykj.yzy.beanmovie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.bean.User;
import com.fykj.yzy.beanmovie.bean.UserHolder;
import com.fykj.yzy.beanmovie.util.Code;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.util.NullUtil;
import com.fykj.yzy.beanmovie.util.PreferencesUtils;
import com.fykj.yzy.beanmovie.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    @BindView(R.id.iv_showCode)
    ImageView iv_showCode;
    @BindView(R.id.reset_input_phone)
    EditText phoneEdit;
    @BindView(R.id.et_phoneCodes)
    EditText codeEdit;
    @BindView(R.id.input_new_password)
    EditText newPassEdit;
    @BindView(R.id.input_second_password)
    EditText secondPassEdit;
    //产生的验证码
    private String realCode;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        //将验证码用图片的形式显示出来
        iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
        user= UserHolder.getUserHolder().getUser();

    }

    @OnClick(R.id.iv_showCode)
    public void refresh(View view){
        iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
        Log.v(TAG,"realCode"+realCode);
    }
    @OnClick(R.id.nav_top_back)
    public void back(View view){
        finish();
    }

    @OnClick(R.id.button2)
    public void ensure(){
        String phoneText=phoneEdit.getText()+"";
        long id=0;
        if (NullUtil.isNull(phoneText)){
            ToastUtil.showLongToast(this,"手机号不能为空");
            return;
        }else {
            id=Long.valueOf(phoneText);
        }
        if (NullUtil.isNull(newPassEdit.getText().toString())){
            ToastUtil.showLongToast(this,"密码不能为空");
            return;
        }

        if (id!=user.getId()){
            ToastUtil.showLongToast(this,"手机号与当前账户不符");
            return;
        }

        if (!codeEdit.getText().toString().equals(realCode)){
            ToastUtil.showLongToast(this,"验证码错误，请重新输入！");
            Log.d(TAG, "ensure: "+realCode+"   "+codeEdit.getText().toString());
            return;
        }

        if (!newPassEdit.getText().toString().equals(secondPassEdit.getText().toString())){
            ToastUtil.showLongToast(this,"两次密码输入不同，请重新检查！");
            return;
        }

        User newUser=new User();
        newUser.setId(user.getId());
        newUser.setMotto(user.getMotto());
        newUser.setName(user.getName());
        newUser.setPassWorld(newPassEdit.getText().toString());

        if (DBManage.getDBManage(this).updataUser(newUser)){
            UserHolder.getUserHolder().setUser(newUser);
            PreferencesUtils.clear(this);
            PreferencesUtils.putLong(this,PreferencesUtils.USER_ID,newUser.getId());
            PreferencesUtils.putString(this,PreferencesUtils.USER_PASS,newUser.getPassWorld());
            ToastUtil.showLongToast(this,"修改密码成功");
            finish();
        }else {
            ToastUtil.showLongToast(this,"修改失败，请重试！");
        }

    }
}
