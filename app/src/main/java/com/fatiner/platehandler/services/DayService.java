package com.fatiner.platehandler.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbOperations;
import com.fatiner.platehandler.managers.shared.SharedMainManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class DayService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification(String name) {
        PendingIntent intent = getPendingIntent();
        Notification notification = getNotification(name, intent);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(MainGlobals.INT_STARTING_VAR_INIT, notification);
    }

    private Notification getNotification(String name, PendingIntent intent) {
        return new NotificationCompat.Builder(this, MainGlobals.STR_CHANNEL_ID)
                .setContentTitle(getString(R.string.nt_title))
                .setContentText(name)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(name))
                .setSmallIcon(R.drawable.ic_home)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(intent)
                .setAutoCancel(true)
                .setVibrate(MainGlobals.NOTIFICATION_VIBRATION)
                .build();
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(this, MainGlobals.INT_STARTING_VAR_INIT,
                intent, MainGlobals.INT_STARTING_VAR_INIT);
    }

    private class AsyncDayService extends AsyncTask<Type, Void, Boolean> {

        private ArrayList<Integer> ids;
        private ArrayList<Recipe> dish;
        private Type type;

        protected void onPreExecute(){
            ids = new ArrayList<>();
            dish = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Type... types) {
            type = types[MainGlobals.INT_STARTING_VAR_INIT];
            try{

                switch(type) {
                    case IDS:
                        DbOperations.readIds(getApplicationContext(), ids);
                        break;
                    case DAY:
                        DbOperations.readDay(getApplicationContext(), dish,
                                SharedMainManager.getSharedDish(getApplicationContext()));
                        break;
                }
                return true;
            }catch (SQLiteException e){
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(success){
                switch(type) {
                    case IDS:
                        readIdsFinished(ids);
                        break;
                    case DAY:
                        readDayFinished(dish);
                        break;
                }
            }
        }
    }

    private void readIdsFinished(ArrayList<Integer> ids) {
        if(ids.isEmpty()) return;
        setNewDishId(ids);
        asyncReadDay();
    }

    private void readDayFinished(ArrayList<Recipe> dish) {
        Recipe recipe = dish.get(MainGlobals.INT_STARTING_VAR_INIT);
        showNotification(recipe.getName());
    }

    private void setNewDishId(ArrayList<Integer> ids){
        int newId;
        if(ids.size() == MainGlobals.INT_INCREMENT_VAR_INIT) {
            newId = ids.get(MainGlobals.INT_STARTING_VAR_INIT);
        } else {
            int currentId = SharedMainManager.getSharedDish(getApplicationContext());
            newId = currentId;
            while(currentId == newId){
                newId = ids.get(new Random().nextInt(ids.size()));
            }
        }
        SharedMainManager.setSharedDish(getApplicationContext(), newId);
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable(){
            @Override
            public void run(){
                String currentTime = TypeManager.dateToString(Calendar.getInstance().getTime());
                if(!SharedMainManager.getSharedDate(getApplicationContext()).equals(currentTime)) {
                    SharedMainManager.setSharedDate(getApplicationContext(), currentTime);
                    asyncReadIds();
                }
                handler.postDelayed(this, MainGlobals.ONE_MINUTE);
            }
        });
    }

    private void asyncReadIds(){
        new AsyncDayService().execute(Type.IDS);
    }

    private void asyncReadDay(){
        new AsyncDayService().execute(Type.DAY);
    }

    private enum Type {
        IDS, DAY
    }
}
