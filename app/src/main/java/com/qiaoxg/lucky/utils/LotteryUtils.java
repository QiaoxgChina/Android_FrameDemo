package com.qiaoxg.lucky.utils;

import static com.qiaoxg.lucky.app.AppConstants.BALL_MIN_SELECTED_NUMBER_RED;

/**
 * Created by admin on 2017/4/6.
 */

public class LotteryUtils {

    private static final String TAG = "LotteryUtils";

    /**
     * 双色球普通投注方式，总共所投注数计算公式
     * @param redBallNum
     * @param blueBallNum
     * @return
     */
    public static long doubleColorOrdinary(int redBallNum, int blueBallNum) {
        long c = jieCheng(redBallNum);
        long d = jieCheng(redBallNum - BALL_MIN_SELECTED_NUMBER_RED);
        long e = jieCheng(BALL_MIN_SELECTED_NUMBER_RED);
        return (c / (d * e)) * blueBallNum;
    }

    /**
     * 求一个整数的阶乘：N！ = N*(N-1)*(N-2)...1
     * @param a
     * @return
     */
    private static long jieCheng(int a) {
        long dividend = 1;
        for (int i = 1; i <= a; i++) {
            dividend = dividend * i;
        }
        return dividend;
    }

    /**
     * 组合的计算公式
     * @param n
     * @param m
     * @return
     */
    private static int combin(int n, int m) {
        int minNum = n - m + 1;
        int divisor = 1;
        int dividend = 1;
        for (int i = minNum; i <= n; i++) {
            divisor = divisor * i;
        }
        for (int i = m; i > 0; i--) {
            dividend = dividend * i;
        }
        return divisor / dividend;
    }

}
