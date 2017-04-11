package com.qiaoxg.lucky.bean;

/**
 * Created by admin on 2017/4/1.
 */

public class DoubleColorBall {

    private String number;
    private int type;//0:red;1:blue
    private boolean selected;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
