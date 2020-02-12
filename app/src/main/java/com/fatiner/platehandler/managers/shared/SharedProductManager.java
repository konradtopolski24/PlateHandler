package com.fatiner.platehandler.managers.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatiner.platehandler.globals.SharedGlobals;

public class SharedProductManager {

    private SharedProductManager(){}

    private static SharedPreferences getSharedFromProducts(Context context){
        return context.getSharedPreferences(
                SharedGlobals.SR_PRODUCT,
                Context.MODE_PRIVATE
        );
    }

    //Alphabetical Products
    public static boolean getSharedProductAlphabetical(Context context){
        return getSharedFromProducts(context).getBoolean(
                SharedGlobals.KY_ALPHABETICAL,
                SharedGlobals.DF_BOOL
        );
    }

    public static void setSharedProductAlphabetical(Context context, boolean isAlphabetical){
        getSharedFromProducts(context).edit().putBoolean(
                SharedGlobals.KY_ALPHABETICAL, isAlphabetical).apply();
    }

    //Type Products
    public static int getSharedProductType(Context context){
        return getSharedFromProducts(context).getInt(
                SharedGlobals.KY_TYPE,
                SharedGlobals.DF_INT
        );
    }

    public static void setSharedProductType(Context context, int type){
        getSharedFromProducts(context).edit().putInt(
                SharedGlobals.KY_TYPE, type).apply();
    }

    public static void removeSharedProductType(Context context){
        getSharedFromProducts(context).edit().remove(
                SharedGlobals.KY_TYPE).apply();
    }

    public static boolean isSharedProductTypeAvailable(Context context){
        return getSharedFromProducts(context).contains(SharedGlobals.KY_TYPE);
    }
}
