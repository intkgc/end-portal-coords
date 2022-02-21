package com.jvmfrog.endportalcoords;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ErrorDialogs {
    /*
    Init:
    ErrorDialogs errorDialogs = new ErrorDialogs();

    Use:
    errorDialogs.angleEqualException(context);
    errorDialogs.angleOppositeException(context);
     */

    public void angleEqualException(Context context) {
        AlertDialog builder = new MaterialAlertDialogBuilder(context)
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .setTitle(R.string.error)
                .setMessage(R.string.angleEqualException)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                })
                .show();
    }

    public void angleOppositeException(Context context) {
        AlertDialog builder = new MaterialAlertDialogBuilder(context)
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .setTitle(R.string.error)
                .setMessage(R.string.angleOppositeException)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                })
                .show();
    }
}
