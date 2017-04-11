package com.qiaoxg.lucky.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.qiaoxg.lucky.app.MyApplication;
import com.qiaoxg.lucky.bean.UserBean;
import com.qiaoxg.lucky.utils.GsonUtils;

/**
 * Created by admin on 2017/3/31.
 */

public class SharedPrefercencesUtils {

    private static final String SPDATA_KEY_USER = "USER";

    /**
     * 保存登录成功的用户信息
     * @param user
     */
    public static void saveLoginUserInfo(UserBean user) {
        SharedPreferences preferences = MyApplication.getAppContext().getSharedPreferences(SPDATA_KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String userStr = GsonUtils.getGsonInstance().toJson(user);
        editor.putString(SPDATA_KEY_USER, userStr);
        editor.commit();
    }

    /**
     * 获取保存在本地的用户数据
     * @return
     */
    public static UserBean getLoginUserInfo() {
        SharedPreferences preferences = MyApplication.getAppContext().getSharedPreferences(SPDATA_KEY_USER, Context.MODE_PRIVATE);
        String userStr = preferences.getString(SPDATA_KEY_USER, "");
        return GsonUtils.fomatStringToUserBean(userStr);
    }

    /**
     * 清空本地保存的用户信息
     */
    public static void clearLoginUserInfo(){
        saveLoginUserInfo(null);
    }

}
