package com.example.sudo.zadatakpt.helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;


public class Popup extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(getActivity());
        dialogbuilder.setTitle("Greška")
                .setMessage("Ups, došlo je do pogreške")
                .setPositiveButton("U redu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return dialogbuilder.create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        //super.show(manager, tag);
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();
        } catch (IllegalStateException e) {
            Log.d("ABSDIALOGFRAG", "Exception", e);
        }
    }
}
