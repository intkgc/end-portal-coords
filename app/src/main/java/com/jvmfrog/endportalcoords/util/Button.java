package com.jvmfrog.endportalcoords.util;

import android.content.DialogInterface;

public class Button {
    public String msg;
    public DialogInterface.OnClickListener onClickListener;

    public Button(String msg, DialogInterface.OnClickListener onClickListener) {
        this.msg = msg;
        this.onClickListener = onClickListener;
    }
}
