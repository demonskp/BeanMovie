package com.fykj.yzy.beanmovie;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

/**
 * Created by 易镇艺 on 2017/7/28.
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        Config.DEBUG=true;
        initUMeng();
    }


/**
  *配置友盟相关设置，目前还只有微信一个
 **/
    private void initUMeng(){
        PlatformConfig.setWeixin("wx9a47a0fe47a09dba","9cddcadc24e7a039ed1bcee39ab93fd7");

        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(false);
        config.isOpenShareEditActivity(true);
//        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO);
//        config.setFacebookAuthType(UMShareConfig.AUTH_TYPE_SSO);
        config.setShareToLinkedInFriendScope(UMShareConfig.LINKED_IN_FRIEND_SCOPE_ANYONE);
    }
}
