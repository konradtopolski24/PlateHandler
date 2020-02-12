package com.fatiner.platehandler.managers.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatiner.platehandler.globals.SharedGlobals;

public class SharedShoppingManager {

    private SharedShoppingManager(){}

    private static SharedPreferences getSharedFromShopping(Context context){
        return context.getSharedPreferences(
                SharedGlobals.SR_SHOPPING,
                Context.MODE_PRIVATE
        );
    }

    //List
    public static String getSharedList(Context context){
        return getSharedFromShopping(context).getString(
                SharedGlobals.KY_LIST,
                SharedGlobals.DF_STRING
        );
    }

    public static void setSharedList(Context context, String json){
        getSharedFromShopping(context).edit().putString(
                SharedGlobals.KY_LIST, json).apply();
    }

    public static void removeSharedList(Context context){
        getSharedFromShopping(context).edit().remove(
                SharedGlobals.KY_LIST).apply();
    }

    public static boolean isSharedListAvailable(Context context){
        return getSharedFromShopping(context).contains(SharedGlobals.KY_LIST);
    }
}
