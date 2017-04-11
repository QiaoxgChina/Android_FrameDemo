package com.qiaoxg.lucky.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.app.BaseActivity;
import com.qiaoxg.lucky.utils.HttpsUtil;
import com.qiaoxg.lucky.utils.UIHelper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends BaseActivity implements View.OnClickListener {

    private static final int MSG_UPDATE_RESULT = 0;

    private TextView mResultTv;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_RESULT:
                    mResultTv.setText((String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initTitleBar();
        findViewById(R.id.test_btn_http).setOnClickListener(this);
        findViewById(R.id.test_btn_https).setOnClickListener(this);
        mResultTv = (TextView) findViewById(R.id.test_tv_result);
    }

    public void initTitleBar() {
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("测试Activity");
        ImageButton backBtn = (ImageButton) findViewById(R.id.title_back);
        backBtn.setOnClickListener(this);
        UIHelper.showWidegt(backBtn, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_btn_http:
                goto12306ByHttp();
                break;
            case R.id.test_btn_https:
                goto12306ByHttps();
                break;
            case R.id.title_back:
                goBack(TestActivity.this);
                break;
        }
    }

    String URL = "https://kyfw.12306.cn/otn/";

    private void goto12306ByHttps() {
        OkHttpClient mOkHttpClient = HttpsUtil.getHttpsOkHttpClient(this);
        final Request request = new Request.Builder()
                .url(URL)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.what = MSG_UPDATE_RESULT;
                msg.obj = e.getMessage();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg = new Message();
                msg.what = MSG_UPDATE_RESULT;
                msg.obj = response.toString();
                mHandler.sendMessage(msg);
            }
        });
    }

    private void goto12306ByHttp() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(URL)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.what = MSG_UPDATE_RESULT;
                msg.obj = e.getMessage();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg = new Message();
                msg.what = MSG_UPDATE_RESULT;
                msg.obj = response.toString();
                mHandler.sendMessage(msg);
            }
        });
    }
}
