package com.qiaoxg.lucky.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qiaoxg.lucky.bean.UserBean;

/**
 * Created by admin on 2017/3/31.
 */

public class GsonUtils {

    private static Gson mGson;

    public static Gson getGsonInstance() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    /**
     * 将string解析成UserBean
     * @param str
     * @return
     */
    public static UserBean fomatStringToUserBean(String str) {
        if (TextUtils.isEmpty(str)){
            return null;
        }
        return getGsonInstance().fromJson(str, new TypeToken<UserBean>() {}.getType());
    }

}
