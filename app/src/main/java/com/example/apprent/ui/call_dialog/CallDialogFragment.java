package com.example.apprent.ui.call_dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.apprent.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallDialogFragment extends DialogFragment {
    private String name;
    private String phone;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_fragment_call, null);
        builder.setView(dialogView);

        EditText nameET = dialogView.findViewById(R.id.fragment_call_name);
        EditText phoneET = dialogView.findViewById(R.id.fragment_call_phone);
        ImageButton close = dialogView.findViewById(R.id.fragment_call_close);
        ImageButton requestCall = dialogView.findViewById(R.id.fragment_call_request_a_call);
        requestCall.setOnClickListener(v -> {
            name = nameET.getText().toString();
            phone = phoneET.getText().toString();
            switch (fieldsAreValid()) {
                case 0:
                    Toast.makeText(getContext(), "0", Toast.LENGTH_LONG).show();
                    this.dismiss();
                    break;
                case 1:
                    Toast.makeText(getContext(), "1", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(getContext(), "2", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(getContext(), "3", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(getContext(), "4", Toast.LENGTH_LONG).show();
                    break;
            }
        });
        close.setOnClickListener(v -> {
            this.dismiss();
        });
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

    private int fieldsAreValid() {
        int b = -1;
        String phoneRegex = "^((\\+7|7|8)+([0-9]){10})$";
        String nameRegex = "^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Pattern namePattern = Pattern.compile(nameRegex);

        if (name.isEmpty() || phone.isEmpty()) {
            if (name.isEmpty()) {
                return 1;
            } else {
                return 2;
            }
//            return 3; //оба поля пустые
        } else {

            phone = phone.trim();
            phone = phone.replace("-", "");
            name = name.trim();
            Matcher phoneMatcher = phonePattern.matcher(phone);
            Matcher nameMatcher = namePattern.matcher(name);
            if (phoneMatcher.matches() && nameMatcher.matches()) {
                b = 0; //верный телефон, верное имя
            } else if (phoneMatcher.matches()) {
                b = 2; //верный телефон, неверное имя
            } else if (nameMatcher.matches()) {
                b = 3; //неверный телефон, верное имя
            } else if (!phoneMatcher.matches() && !nameMatcher.matches()) {
                b = 4; //неверный телефон, неверное имя
            }
        }
        return b;
    }
}
