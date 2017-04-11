package com.qiaoxg.lucky.bean;

/**
 * Created by admin on 2017/4/1.
 */

public class LotteryResult {

    private String title;
    private long dateTime;
    private String[] redBall;
    private String[] blueBall;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String[] getRedBall() {
        return redBall;
    }

    public void setRedBall(String[] redBall) {
        this.redBall = redBall;
    }

    public String[] getBlueBall() {
        return blueBall;
    }

    public void setBlueBall(String[] blueBall) {
        this.blueBall = blueBall;
    }
}
