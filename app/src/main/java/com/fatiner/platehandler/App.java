package com.fatiner.platehandler;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.fatiner.platehandler.globals.MainGlobals;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        manageChannel();
    }

    private void manageChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(MainGlobals.STR_CHANNEL_ID,
                    MainGlobals.STR_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
