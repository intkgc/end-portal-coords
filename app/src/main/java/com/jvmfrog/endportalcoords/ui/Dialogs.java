package com.jvmfrog.endportalcoords.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.util.Button;

public class Dialogs {
    public static void angleEqualException(Context context) {
        error(context, R.string.angleEqualException,
                new Button("OK", null), null).show();
    }

    public static void angleOppositeException(Context context) {
        error(context, R.string.angleOppositeException,
                new Button("OK", null), null).show();
    }

    public static void checkAllFields(Context context) {
        error(context, R.string.check_all_fields,
                new Button("OK", null), null).show();
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
                .setIcon(R.drawable.ic_round_error_outline_24)
                .setTitle(R.string.error)
                .setMessage(msg)
                .setPositiveButton(positiveButton.msg, positiveButton.onClickListener);

        if (negativeButton != null)
            builder.setNegativeButton(negativeButton.msg, negativeButton.onClickListener);

        return builder;
    }

    public static void saveCoordinates(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog builder = new MaterialAlertDialogBuilder(c)
                .setIcon(R.drawable.ic_round_save_24)
                .setTitle("Save Coordinates")
                .setMessage("Enter coordinates name")
                .setView(taskEditText)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        builder.show();
    }
}
