package com.qiaoxg.lucky.ui.view;

import com.qiaoxg.lucky.bean.UserBean;

/**
 * Created by admin on 2017/3/30.
 */

public interface ILoginView {

    void loginSuccess(UserBean user);

    void loginFail(String errorMsg);

    void loginLoading(String msgStr);

}
