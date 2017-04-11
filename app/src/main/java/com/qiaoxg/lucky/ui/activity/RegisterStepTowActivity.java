package com.qiaoxg.lucky.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.app.BaseActivity;
import com.qiaoxg.lucky.ui.common.ActivityConstants;
import com.qiaoxg.lucky.utils.UIHelper;

public class RegisterStepTowActivity extends BaseActivity implements View.OnClickListener{

    private int mPageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_tow);
        initIntentData();
        initView();
    }

    private void initIntentData() {
        mPageType = getIntent().getIntExtra(ActivityConstants.PARAM_KEY_REGISTER_OR_FIND, ActivityConstants.PARAM_VALUE_REGISTER);
    }

    private void initView() {
        TextView titleTv = (TextView)findViewById(R.id.title_tv);
        if (mPageType == ActivityConstants.PARAM_VALUE_FIND_PASSWORD) {
            titleTv.setText(R.string.mine_find_psw);
        } else {
            titleTv.setText(R.string.mine_register);
        }

        ImageButton backIb = (ImageButton) findViewById(R.id.title_back);
        UIHelper.showWidegt(backIb,true);
        backIb.setOnClickListener(this);

        findViewById(R.id.register_tv_done).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_tv_done:
                UIHelper.showToast("注册成功");
//                RegisterStepTowActivity.this.finish();
//                UIHelper.gotoActivity(RegisterStepTowActivity.this, MainActivity.class);
                break;
            case R.id.title_back:
                goBack(RegisterStepTowActivity.this);
                break;
        }
    }
}
