package com.qiaoxg.lucky.model;

import android.app.Activity;

import com.qiaoxg.lucky.bean.UserBean;
import com.qiaoxg.lucky.presenter.callback.LoginCallback;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by admin on 2017/3/30.
 */

public interface ILoginModel {

    void login(UserBean user, LoginCallback callback);

    /**
     * 登录第三方平台
     *
     * @param platform
     */
    void loginThirdPlatform(final SHARE_MEDIA platform, Activity activity, LoginCallback callback);
}
