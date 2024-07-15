package com.example.androidsession.presentation.home_page_activity.sub_activity;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class AlertDialogActivity {

    public interface AlertDialogListener {
        void onPositiveButtonClick();
    }

    public static void showConfirmationDialog(Context context, String userName, AlertDialogListener listener) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmation")
                .setMessage("Do you want to set " + userName + " as inactive/active?")
                .setPositiveButton("Yes", (dialog, which) -> listener.onPositiveButtonClick())
                .setNegativeButton("No", null)
                .show();
    }
}

