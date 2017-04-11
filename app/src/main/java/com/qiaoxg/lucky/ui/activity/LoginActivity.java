package com.qiaoxg.lucky.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.app.BaseActivity;
import com.qiaoxg.lucky.bean.UserBean;
import com.qiaoxg.lucky.data.SharedPrefercencesUtils;
import com.qiaoxg.lucky.presenter.LoginPresenter;
import com.qiaoxg.lucky.ui.common.ActivityConstants;
import com.qiaoxg.lucky.ui.view.ILoginView;
import com.qiaoxg.lucky.utils.UIHelper;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginView {

    private static final String TAG = "LoginActivity";

    private TextView mLoginBtn;
    private TextView mRegisterBtn;
    private EditText mPhoneNumEt, mPswEt;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initPresenter();

        UMShareAPI.get(this).fetchAuthResultWithBundle(this, null, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.e(TAG, "onStart: ");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.e(TAG, "onComplete: ");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.e(TAG, "onCancel: ");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    private void initPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    private void initView() {
        mLoginBtn = (TextView) findViewById(R.id.login_tv_login);
        mLoginBtn.setOnClickListener(this);
        mRegisterBtn = (TextView) findViewById(R.id.login_tv_register);
        mRegisterBtn.setOnClickListener(this);

        mPhoneNumEt = (EditText) findViewById(R.id.login_et_phoneNum);
        mPswEt = (EditText) findViewById(R.id.login_et_password);

        ImageButton backIb = (ImageButton) findViewById(R.id.title_back);
        backIb.setOnClickListener(this);
        UIHelper.showWidegt(backIb, true);

        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("用户登录");

        findViewById(R.id.login_tv_findpsw).setOnClickListener(this);
        findViewById(R.id.login_tv_register).setOnClickListener(this);
        findViewById(R.id.login_rl_weixinLogin).setOnClickListener(this);
        findViewById(R.id.login_rl_QQLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_tv_login:
                showProgressDialog(LoginActivity.this, "正在登录...", false);
                UserBean user = new UserBean();
                user.setPassword(mPswEt.getText().toString());
                user.setUsername(mPhoneNumEt.getText().toString());
                mPresenter.login(user);
                break;
            case R.id.login_tv_register:
                UIHelper.gotoActivity(LoginActivity.this, RegisterStepOneActivity.class);
//                getOutOfThisActivity(TestActivity.class);
                break;
            case R.id.title_back:
                goBack(LoginActivity.this);
                break;
            case R.id.login_tv_findpsw:
                Intent i = new Intent(LoginActivity.this, RegisterStepOneActivity.class);
                i.putExtra(ActivityConstants.PARAM_KEY_REGISTER_OR_FIND, ActivityConstants.PARAM_VALUE_FIND_PASSWORD);
                startActivity(i);
                break;
            case R.id.login_rl_weixinLogin:
                UIHelper.showToast("暂未实现");
//                mPresenter.loginThirdPlatform(SHARE_MEDIA.WEIXIN, LoginActivity.this);
                break;
            case R.id.login_rl_QQLogin:
                showProgressDialog(LoginActivity.this, "正在登录QQ...", false);
                mPresenter.loginThirdPlatform(SHARE_MEDIA.QQ, LoginActivity.this);
                break;
        }
    }

    @Override
    public void loginSuccess(UserBean user) {
        SharedPrefercencesUtils.saveLoginUserInfo(user);//用户登录成功之后，将信息保存在本地，用于自动登录
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
                UIHelper.showToast("登录成功");
//                getOutOfThisActivity(MainActivity.class);
            }
        });
    }

    @Override
    public void loginFail(final String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
                showToast(errorMsg);
            }
        });
    }

    @Override
    public void loginLoading(String msgStr) {
        showProgressDialog(LoginActivity.this, msgStr, false);
    }

    private void getOutOfThisActivity(Class targetClass) {
        LoginActivity.this.finish();
        Intent i = new Intent();
        i.setClass(LoginActivity.this, targetClass);
        startActivity(i);
    }

}
