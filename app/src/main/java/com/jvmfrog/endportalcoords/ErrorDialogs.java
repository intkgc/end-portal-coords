package com.jvmfrog.endportalcoords;

import android.content.Context;
import android.content.DialogInterface;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ErrorDialogs {
    public void angleEqualException(Context context) {
        dialogBuilder(context, R.string.angleEqualException,
                new Button("Ok", (dialog, which) -> {
                    //
                }), null).show();
    }

    public void angleOppositeException(Context context) {
        dialogBuilder(context, R.string.angleOppositeException,
                new Button("Ok", (dialog, which) -> {
                    //
                }), null).show();
    }


    private MaterialAlertDialogBuilder dialogBuilder(Context context, int msg, Button positiveButton, Button negativeButton) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context)
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .setTitle(R.string.error)
                .setMessage(msg)
                .setPositiveButton(positiveButton.msg, positiveButton.onClickListener);

        if (negativeButton != null)
            builder.setNegativeButton(negativeButton.msg, negativeButton.onClickListener);

        return builder;
    }

    private static class Button {
        public String msg;
        public DialogInterface.OnClickListener onClickListener;

        public Button(String msg, DialogInterface.OnClickListener onClickListener) {
            this.msg = msg;
            this.onClickListener = onClickListener;
        }
    }
}
