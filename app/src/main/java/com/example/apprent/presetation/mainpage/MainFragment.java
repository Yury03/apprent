package com.example.apprent.presetation.mainpage;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apprent.R;
import com.example.apprent.data.notifications.NotificationHelper;
import com.example.apprent.presetation.mainactivity.MainActivity;

public class MainFragment extends Fragment {
    private static final String Tag = "MyApp";
    private NotificationHelper mNotificationHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationHelper = new NotificationHelper(getContext());
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(Tag, "onViewCreated [MainFragment]");
        Button button = view.findViewById(R.id.call_notification);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            //mNotificationHelper.createNotificationChannel();
                mNotificationHelper.showNotification("Заголовок уведомления", "Текст уведомления", intent);

        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}







