package com.jvmfrog.endportalcoords.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.util.DialogButton;

public class Dialogs {
    public static void angleEqualException(Context context) {
        error(context, R.string.angleEqualException,
                new DialogButton("OK", null), null).show();
    }

    public static void angleOppositeException(Context context) {
        error(context, R.string.angleOppositeException,
                new DialogButton("OK", null), null).show();
    }

    public static void checkAllFields(Context context) {
        error(context, R.string.check_all_fields,
                new DialogButton("OK", null), null).show();
    }

    @SuppressLint("DefaultLocale")
    public static MaterialAlertDialogBuilder endPortalCoordinates(Context context, float x, float z) {
        return new MaterialAlertDialogBuilder(context)
                .setTitle("End Portal Coordinates")
                .setMessage(String.format("X:%d Z:%d", (int) x, (int) z))
                .setPositiveButton("Ok", null);
    }

    public static MaterialAlertDialogBuilder error(Context context, int msg,
                                                   DialogButton positiveDialogButton, DialogButton negativeDialogButton) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context)
                .setIcon(R.drawable.ic_round_error_outline_24)
                .setTitle(R.string.error)
                .setMessage(msg)
                .setPositiveButton(positiveDialogButton.msg, positiveDialogButton.onClickListener);

        if (negativeDialogButton != null)
            builder.setNegativeButton(negativeDialogButton.msg, negativeDialogButton.onClickListener);

        return builder;
    }
}
