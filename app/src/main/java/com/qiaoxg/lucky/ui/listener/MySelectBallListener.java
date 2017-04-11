package com.qiaoxg.lucky.ui.listener;

import com.qiaoxg.lucky.bean.DoubleColorBall;

import java.util.List;

/**
 * Created by admin on 2017/4/6.
 */

public interface MySelectBallListener {
    void onSelectRedBallChanged(List<DoubleColorBall> numberList);

    void onSelectBlueBallChanged(List<DoubleColorBall> numberList);
}
