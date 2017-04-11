package com.qiaoxg.lucky.app;

import android.app.Application;
import android.content.Context;

import com.qiaoxg.lucky.bean.UserBean;
import com.qiaoxg.lucky.data.SharedPrefercencesUtils;
import com.qiaoxg.lucky.utils.ImageLoaderUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by admin on 2017/3/30.
 */

public class MyApplication extends Application {

    private static Context mContext;
    private static UserBean mCurrLoginUser;

    // Umeng整个平台的Controller,负责管理整个SDK的配置、操作等处理
//    private static UMSocialService mUmengController;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //初始化ImageLoader的配置
        ImageLoaderUtils.configImageLoader();

//        mUmengController = UMServiceFactory
//                .getUMSocialService(AppConstants.UMENG_DESCRIPTOR);
        Config.DEBUG = true;
    }

    public static Context getAppContext() {
        return mContext;
    }

    /**
     * get current logined or lastest user
     *
     * @return
     */
    public static UserBean getCurrLoginUser() {
        if (mCurrLoginUser == null) {
            mCurrLoginUser = SharedPrefercencesUtils.getLoginUserInfo();
        }
        return mCurrLoginUser;
    }

//    public static UMSocialService getUmengController(){
//        return mUmengController;
//    }

    //各个平台的配置，建议放在全局Application或者程序入口
    static {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("1106084810", "hOcNfa85P7roE8bx");
    }

}
