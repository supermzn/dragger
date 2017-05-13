package com.example.dragger;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by mazena on 13.05.17.
 */

public class SuccessDialog {
    public static void showSelectedTargetDialog(Activity activity,
                                                int targetId, final DialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.target_reached)
                .setMessage(activity.getString(R.string.congratulations,
                        String.valueOf(targetId)));

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                callback.resetCenterButton();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
