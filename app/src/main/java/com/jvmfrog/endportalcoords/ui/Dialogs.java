package com.jvmfrog.endportalcoords.ui;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.util.Button;

public class Dialogs {
    public static void angleEqualException(Context context) {
        error(context, R.string.angleEqualException,
                new Button("Ok", null), null).show();
    }

    public static void angleOppositeException(Context context) {
        error(context, R.string.angleOppositeException,
                new Button("Ok", null), null).show();
    }

    public static void checkAllFields(Context context) {
        error(context, R.string.check_all_fields,
                new Button("Ok", null), null).show();
    }

    @SuppressLint("DefaultLocale")
    public static MaterialAlertDialogBuilder endPortalCoordinates(Context context, float x, float z) {
        return new MaterialAlertDialogBuilder(context)
                .setTitle("End Portal Coordinates")
                .setMessage(String.format("X:%d Z:%d", (int) x, (int) z))
                .setPositiveButton("Ok", null);
    }

    public static MaterialAlertDialogBuilder error(Context context, int msg,
                                                   Button positiveButton, Button negativeButton) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context)
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .setTitle(R.string.error)
                .setMessage(msg)
                .setPositiveButton(positiveButton.msg, positiveButton.onClickListener);

        if (negativeButton != null)
            builder.setNegativeButton(negativeButton.msg, negativeButton.onClickListener);

        return builder;
    }
}
