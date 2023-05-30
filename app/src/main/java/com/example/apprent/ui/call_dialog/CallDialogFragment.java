package com.example.apprent.ui.call_dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.apprent.R;

public class CallDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_fragment_call, null);
        builder.setView(dialogView);

        EditText name = dialogView.findViewById(R.id.fragment_call_name);
        EditText phone = dialogView.findViewById(R.id.fragment_call_phone);
//        builder.setTitle("Пример диалогового окна")
//                .setMessage("Это пример диалогового окна, созданного с помощью DialogFragment.")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // Обработка нажатия кнопки "OK"
//                    }
//                })
//                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // Обработка нажатия кнопки "Отмена"
//                        dialog.cancel();
//                    }
//                });
        Dialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return alertDialog;
    }

    public static String TAG = "PurchaseConfirmationDialog";
}
