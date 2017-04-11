package com.qiaoxg.lucky.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.app.BaseActivity;
import com.qiaoxg.lucky.app.MyApplication;
import com.qiaoxg.lucky.bean.UserBean;

/**
 * Created by admin on 2017/3/30.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private static final int MSG_UPDATE_TIME = 0;
    private static final int MSG_GOTO_MAINACTIVITY = 1;

    private UserBean mCurrUser;

    private LinearLayout mSkipLl;
    private TextView mTimeTv;

    private int mTotalCount = 3;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mHandler == null) {
                return;
            }
            switch (msg.what) {
                case MSG_GOTO_MAINACTIVITY:
                    SplashActivity.this.finish();
//                    if (mCurrUser != null) {
//                        //TODO 做自动登录
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                    }
                    break;
                case MSG_UPDATE_TIME:
                    mTimeTv.setText(mTotalCount + "S跳过");
                    if (mTotalCount > 0) {
                        mTotalCount--;
                        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, 1000);
                    } else {
                        if (mHandler.hasMessages(MSG_GOTO_MAINACTIVITY)) {
                            mHandler.removeMessages(MSG_GOTO_MAINACTIVITY);
                        }
                        mHandler.sendEmptyMessage(MSG_GOTO_MAINACTIVITY);
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        mHandler.sendEmptyMessage(MSG_UPDATE_TIME);
        mCurrUser = MyApplication.getCurrLoginUser();
    }

    private void initView() {
        mSkipLl = (LinearLayout) findViewById(R.id.splash_ll_skip);
        mSkipLl.setOnClickListener(this);
        mTimeTv = (TextView) findViewById(R.id.splash_tv_skip);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.splash_ll_skip) {
            if (mHandler != null && mHandler.hasMessages(MSG_GOTO_MAINACTIVITY)) {
                mHandler.removeMessages(MSG_GOTO_MAINACTIVITY);
            }
            if (mHandler != null) {
                mHandler.sendEmptyMessage(MSG_GOTO_MAINACTIVITY);
            }
        }
    }
}
