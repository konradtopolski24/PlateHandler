package com.fatiner.platehandler.managers.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatiner.platehandler.globals.SharedGlobals;

public class SharedShoppingManager {

    private SharedShoppingManager(){}

    private static SharedPreferences getSharedFromShopping(Context context){
        return context.getSharedPreferences(
                SharedGlobals.NAME_SHOPPING_SHARED_SHOPPING,
                Context.MODE_PRIVATE
        );
    }

    //List
    public static String getSharedList(Context context){
        return getSharedFromShopping(context).getString(
                SharedGlobals.KEY_LIST_SHARED_SHOPPING,
                SharedGlobals.DEFAULT_STRING_SHARED_ALL
        );
    }

    public static void setSharedList(Context context, String json){
        getSharedFromShopping(context).edit().putString(
                SharedGlobals.KEY_LIST_SHARED_SHOPPING, json).apply();
    }

    public static void removeSharedList(Context context){
        getSharedFromShopping(context).edit().remove(
                SharedGlobals.KEY_LIST_SHARED_SHOPPING).apply();
    }

    public static boolean isSharedListAvailable(Context context){
        return getSharedFromShopping(context).contains(SharedGlobals.KEY_LIST_SHARED_SHOPPING);
    }
}
