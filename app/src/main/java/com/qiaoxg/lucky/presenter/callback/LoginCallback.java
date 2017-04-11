package com.qiaoxg.lucky.presenter.callback;

import com.qiaoxg.lucky.bean.UserBean;

/**
 * Created by admin on 2017/3/30.
 */

public interface LoginCallback {

    void loginOk(UserBean user);

    void loginFail(String errorStr);

    void loginLoading(String msgStr);
}
