package com.qiaoxg.lucky.model.impl;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.qiaoxg.lucky.app.MyApplication;
import com.qiaoxg.lucky.bean.UserBean;
import com.qiaoxg.lucky.model.ILoginModel;
import com.qiaoxg.lucky.presenter.callback.LoginCallback;
import com.qiaoxg.lucky.ui.activity.LoginActivity;
import com.qiaoxg.lucky.utils.UIHelper;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by admin on 2017/3/30.
 */

public class LoginModel implements ILoginModel {
    private static final String TAG = "LoginModel";

    @Override
    public void login(final UserBean user, final LoginCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                if (TextUtils.isEmpty(user.getUsername())) {
                    callback.loginFail("手机号不能为空");
                    return;
                }
                if (user.getUsername().length() != 11) {
                    callback.loginFail("手机号格式不正确");
                    return;
                }
                if (TextUtils.isEmpty(user.getPassword())) {
                    callback.loginFail("密码不能为空");
                    return;
                }
                callback.loginOk(user);
            }
        }).start();
    }

    /**
     * 授权。如果授权成功，则获取用户信息
     *
     * @param platform
     */
    public void loginThirdPlatform(final SHARE_MEDIA platform, final Activity activity, final LoginCallback callback) {
        UMShareAPI.get(activity).doOauthVerify(activity, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                callback.loginLoading("正在跳转...");
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                UIHelper.showToast("成功了");
                // 获取uid
                if (!TextUtils.isEmpty(data.get("uid"))) {
                    // uid不为空，获取用户信息
                    callback.loginLoading("登录成功");
                    getThirdPlatformUserInfo(platform, activity, callback);
                } else {
                    //"授权失败..."
                    callback.loginFail("授权失败");
                }
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                UIHelper.showToast("失败了");
                callback.loginFail("授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                UIHelper.showToast("取消了");
                callback.loginFail("取消了");
            }
        });

    }

    /**
     * 获取用户信息
     *
     * @param platform
     */
    private void getThirdPlatformUserInfo(SHARE_MEDIA platform, Activity activity, final LoginCallback callback) {
        UMShareAPI.get(MyApplication.getAppContext()).getPlatformInfo(activity, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                callback.loginLoading("正在获取用户信息...");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.e(TAG, "onComplete: userInfo is " + map.toString());
                UserBean user = new UserBean();
                user.setNickName(map.get("name"));
                user.setTokenId(map.get("accessToken"));
                user.setIconUrl(map.get("iconurl"));
                user.setUuid(map.get("uid"));
                callback.loginOk(user);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

}
