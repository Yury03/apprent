package com.example.apprent.presentation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.example.apprent.R;
import com.example.apprent.data.notifications.NotificationHelper;
import com.example.apprent.presentation.mainactivity.MainActivity;

//public class ExampleService extends Service {
//
//    public ExampleService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    private static final int NOTIFICATION_ID = 123;
//
//    // Переменная для отслеживания состояния сервиса
//    private boolean isRunning = false;
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // Вызывается при запуске сервиса
//
//        // Установить флаг запущенного состояния
//        isRunning = true;
//
//        // Получить информацию из приложения
//        String message = intent.getStringExtra("message");
//
//        // Отобразить баннер с информацией
//        showBanner(message);
//
//        // Вернуть флаг повторной загрузки сервиса при его прерывании
//        return START_REDELIVER_INTENT;
//    }
//
//    @Override
//    public void onDestroy() {
//        // Вызывается при остановке сервиса
//
//        // Установить флаг остановленного состояния
//        isRunning = false;
//
//        // Скрыть баннер с информацией
//        hideBanner();
//
//        // Удалить уведомление
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(NOTIFICATION_ID);
//
//        super.onDestroy();
//    }
//
//    private void showBanner(String message) {
//        // Отобразить баннер с информацией
//        // ...
//
//        // Создать уведомление для отображения в строке состояния
//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle("MyService")
//                .setContentText(message)
//                .setSmallIcon(R.drawable.ic_notification)
//                .build();
//
//        // Отобразить уведомление
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(NOTIFICATION_ID, notification);
//    }
//
//    private void hideBanner() {
//        // Скрыть баннер с информацией
//        // ...
//    }
//
//    public boolean isRunning() {
//        // Получить текущее состояние сервиса
//        return isRunning;
//    }
//}
public class ExampleService extends Service {
    private WindowManager mWindowManager;
    private ViewGroup mOverlayView;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public class ExampleBinder extends Binder {
        public ExampleService getService() {
            return ExampleService.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();

        // Получение объекта WindowManager для отображения баннера поверх других окон
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Создание макета для баннера
        mOverlayView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.overlay_layout, null);

        // Получение ссылок на элементы макета
        TextView textView = mOverlayView.findViewById(R.id.overlay_text);
        Button button = mOverlayView.findViewById(R.id.overlay_button);

        // Установка текста и обработчика кнопки
        textView.setText("Для практической работы №6");
        button.setOnClickListener(view -> {
            Intent intent = new Intent(ExampleService.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            stopSelf();
        });

        // Определение параметров для макета (размер, позиция и т.д.)
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;

        // Добавление макета на экран
        mWindowManager.addView(mOverlayView, params);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Удаление макета с экрана
        if (mOverlayView != null) {
            mWindowManager.removeView(mOverlayView);
            mOverlayView = null;
        }
    }
}

