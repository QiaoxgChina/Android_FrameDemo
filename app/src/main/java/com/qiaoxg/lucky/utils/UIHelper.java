package com.qiaoxg.lucky.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.qiaoxg.lucky.app.MyApplication;

/**
 * Created by admin on 2017/3/30.
 */

public class UIHelper {

    private static final String TAG = "UIHelper";

    private static Toast mToast;

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
     * 设置View的显示状态
     * @param v
     * @param isShow
     */
    public static void showWidegt(View v, boolean isShow) {
        if (v == null) {
            return;
        }
        int visibleInt = View.GONE;
        if (isShow) {
            visibleInt = View.VISIBLE;
        }
        v.setVisibility(visibleInt);
    }

    /**
     * Activity间跳转
     * @param fromActivity
     * @param toActivity
     */
    public static void gotoActivity(Activity fromActivity, Class toActivity) {
        fromActivity.startActivity(new Intent(fromActivity, toActivity));
    }
}
