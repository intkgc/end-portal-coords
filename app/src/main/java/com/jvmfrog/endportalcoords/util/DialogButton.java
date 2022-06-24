package com.jvmfrog.endportalcoords.util;

import android.content.DialogInterface;

public class DialogButton {
    public String msg;
    public DialogInterface.OnClickListener onClickListener;

    public DialogButton(String msg, DialogInterface.OnClickListener onClickListener) {
        this.msg = msg;
        this.onClickListener = onClickListener;
    }
}
