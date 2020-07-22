package com.fatiner.platehandler.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatiner.platehandler.globals.Shared;

public class SharedManager {

    private SharedManager() {
    }

    private static SharedPreferences getPreferences(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(Context context, String name, String key) {
        return getPreferences(context, name).getBoolean(key, Shared.DF_BOOL);
    }

    public static int getInt(Context context, String name, String key) {
        return getPreferences(context, name).getInt(key, Shared.DF_INT);
    }

    public static String getString(Context context, String name, String key) {
        return getPreferences(context, name).getString(key, Shared.DF_STRING);
    }

    public static void setValue(Context context, String name, String key, boolean value) {
        getPreferences(context, name).edit().putBoolean(key, value).apply();
    }

    public static void setValue(Context context, String name, String key, int value) {
        getPreferences(context, name).edit().putInt(key, value).apply();
    }

    public static void setValue(Context context, String name, String key, String value) {
        getPreferences(context, name).edit().putString(key, value).apply();
    }

    public static void removeValue(Context context, String name, String key) {
        getPreferences(context, name).edit().remove(key).apply();
    }

    public static boolean isValueAvailable(Context context, String name, String key) {
        return getPreferences(context, name).contains(key);
    }

    public static void removeAll(Context context, String name) {
        getPreferences(context, name).edit().clear().apply();
    }
}
