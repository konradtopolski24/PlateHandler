package com.fatiner.platehandler.managers.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatiner.platehandler.globals.SharedGlobals;

public class SharedMainManager {

    private SharedMainManager(){}

    private static SharedPreferences getSharedFromMain(Context context){
        return context.getSharedPreferences(
                SharedGlobals.NAME_MAIN_SHARED_MAIN,
                Context.MODE_PRIVATE
        );
    }

    //Recent
    public static String getSharedRecent(Context context){
        return getSharedFromMain(context).getString(
                SharedGlobals.KEY_RECENT_SHARED_MAIN,
                SharedGlobals.DEFAULT_STRING_SHARED_ALL
        );
    }

    public static void setSharedRecent(Context context, String json){
        getSharedFromMain(context).edit().putString(
                SharedGlobals.KEY_RECENT_SHARED_MAIN, json).apply();
    }

    public static boolean isSharedRecentAvailable(Context context){
        return getSharedFromMain(context).contains(SharedGlobals.KEY_RECENT_SHARED_MAIN);
    }

    //Dish
    public static int getSharedDish(Context context){
        return getSharedFromMain(context).getInt(
                SharedGlobals.KEY_DISH_SHARED_MAIN,
                SharedGlobals.DEFAULT_INT_SHARED_ALL
        );
    }

    public static void setSharedDish(Context context, int id){
        getSharedFromMain(context).edit().putInt(
                SharedGlobals.KEY_DISH_SHARED_MAIN, id).apply();
    }

    public static void removeSharedDish(Context context){
        getSharedFromMain(context).edit().remove(
                SharedGlobals.KEY_DISH_SHARED_MAIN).apply();
    }

    public static boolean isSharedDishAvailable(Context context){
        return getSharedFromMain(context).contains(SharedGlobals.KEY_DISH_SHARED_MAIN);
    }

    //Date
    public static String getSharedDate(Context context){
        return getSharedFromMain(context).getString(
                SharedGlobals.KEY_DATE_SHARED_MAIN,
                SharedGlobals.DEFAULT_STRING_SHARED_ALL
        );
    }

    public static void setSharedDate(Context context, String date){
        getSharedFromMain(context).edit().putString(
                SharedGlobals.KEY_DATE_SHARED_MAIN, date).apply();
    }

    public static boolean isSharedDateAvailable(Context context){
        return getSharedFromMain(context).contains(SharedGlobals.KEY_DATE_SHARED_MAIN);
    }
}
