package com.fatiner.platehandler.managers.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatiner.platehandler.globals.SharedGlobals;

public class SharedMainManager {

    private SharedMainManager(){}

    private static SharedPreferences getSharedFromMain(Context context){
        return context.getSharedPreferences(
                SharedGlobals.SR_HOME,
                Context.MODE_PRIVATE
        );
    }

    //Recent
    public static String getSharedRecent(Context context){
        return getSharedFromMain(context).getString(
                SharedGlobals.KY_RECENT,
                SharedGlobals.DF_STRING
        );
    }

    public static void setSharedRecent(Context context, String json){
        getSharedFromMain(context).edit().putString(
                SharedGlobals.KY_RECENT, json).apply();
    }

    public static boolean isSharedRecentAvailable(Context context){
        return getSharedFromMain(context).contains(SharedGlobals.KY_RECENT);
    }

    public static void removeSharedRecent(Context context){
        getSharedFromMain(context).edit().remove(
                SharedGlobals.KY_RECENT).apply();
    }

    //Dish
    public static int getSharedDish(Context context){
        return getSharedFromMain(context).getInt(
                SharedGlobals.KY_DAY,
                SharedGlobals.DF_INT
        );
    }

    public static void setSharedDish(Context context, int id){
        getSharedFromMain(context).edit().putInt(
                SharedGlobals.KY_DAY, id).apply();
    }

    public static void removeSharedDish(Context context){
        getSharedFromMain(context).edit().remove(
                SharedGlobals.KY_DAY).apply();
    }

    public static boolean isSharedDishAvailable(Context context){
        return getSharedFromMain(context).contains(SharedGlobals.KY_DAY);
    }

    //Date
    public static String getSharedDate(Context context){
        return getSharedFromMain(context).getString(
                SharedGlobals.KY_DATE,
                SharedGlobals.DF_STRING
        );
    }

    public static void setSharedDate(Context context, String date){
        getSharedFromMain(context).edit().putString(
                SharedGlobals.KY_DATE, date).apply();
    }

    public static boolean isSharedDateAvailable(Context context){
        return getSharedFromMain(context).contains(SharedGlobals.KY_DATE);
    }
}
