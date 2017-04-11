package com.qiaoxg.lucky.presenter;

import android.app.Activity;

import com.qiaoxg.lucky.bean.UserBean;
import com.qiaoxg.lucky.model.ILoginModel;
import com.qiaoxg.lucky.model.impl.LoginModel;
import com.qiaoxg.lucky.presenter.callback.LoginCallback;
import com.qiaoxg.lucky.ui.view.ILoginView;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by admin on 2017/3/30.
 */

public class LoginPresenter {

    private ILoginView iLoginView;
    private ILoginModel iLoginModel;

    public LoginPresenter(ILoginView iView) {
        this.iLoginView = iView;
        iLoginModel = new LoginModel();
    }

    /**
     * 登录
     *
     * @param user
     */
    public void login(UserBean user) {
        iLoginModel.login(user, new LoginCallback() {
            @Override
            public void loginOk(UserBean user) {
                iLoginView.loginSuccess(user);
            }

            @Override
            public void loginFail(String errorStr) {
                iLoginView.loginFail(errorStr);
            }

            @Override
            public void loginLoading(String msgStr) {
                iLoginView.loginLoading(msgStr);
            }
        });
    }

    /**
     * 第三方平台登录
     *
     * @param platform
     */
    public void loginThirdPlatform(SHARE_MEDIA platform, Activity activity) {
        iLoginModel.loginThirdPlatform(platform, activity, new LoginCallback() {
            @Override
            public void loginOk(UserBean user) {
                iLoginView.loginSuccess(user);
            }

            @Override
            public void loginFail(String errorStr) {
                iLoginView.loginFail(errorStr);
            }

            @Override
            public void loginLoading(String msgStr) {
                iLoginView.loginLoading(msgStr);
            }
        });
    }
}
