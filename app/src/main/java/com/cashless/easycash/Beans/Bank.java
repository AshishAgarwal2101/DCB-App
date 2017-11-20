package com.cashless.easycash.Beans;

import android.widget.RadioButton;

/**
 * Created by Varsha on 05-11-2017.
 */

public class Bank {
    String name;
    int resId;
    Boolean radioButton;

    public Bank(int resId, String name, Boolean value) {
        this.name = name;
        this.resId = resId;
        this.radioButton = value;
    }

    public String getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }

    public Boolean getRadioButton() {
        return radioButton;
    }
}
