package com.qiaoxg.lucky.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by admin on 2017/3/30.
 * App中的Activity都继承这个
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private ProgressDialog mProgressDialog;
    private static Toast mToast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * 显示progressDialog
     *
     * @param context  上下文
     * @param msgStr   要显示的信息
     * @param isCancel 点击外边是否消失
     */
    public void showProgressDialog(Context context, String msgStr, boolean isCancel) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(msgStr);
            mProgressDialog.setCanceledOnTouchOutside(isCancel);
        } else {
            mProgressDialog.setMessage(msgStr);
        }
        mProgressDialog.show();
    }

    /**
     * 隐藏progressDialog
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.hide();
    }

    /**
     * 显示吐司
     *
     * @param msgStr
     */
    public static void showToast(String msgStr) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getAppContext(), msgStr, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msgStr);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 返回
     *
     * @param activity
     */
    public void goBack(Activity activity) {
        activity.finish();
    }

}
