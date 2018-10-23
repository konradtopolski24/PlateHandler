package com.fatiner.platehandler.managers.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatiner.platehandler.globals.SharedGlobals;

public class SharedProductManager {

    private SharedProductManager(){}

    private static SharedPreferences getSharedFromProducts(Context context){
        return context.getSharedPreferences(
                SharedGlobals.NAME_PRODUCTS_SHARED_PRODUCTS,
                Context.MODE_PRIVATE
        );
    }

    //Alphabetical Products
    public static boolean getSharedProductAlphabetical(Context context){
        return getSharedFromProducts(context).getBoolean(
                SharedGlobals.KEY_ALPHABETICAL_SHARED_PRODUCTS,
                SharedGlobals.DEFAULT_BOOL_SHARED_ALL
        );
    }

    public static void setSharedProductAlphabetical(Context context, boolean isAlphabetical){
        getSharedFromProducts(context).edit().putBoolean(
                SharedGlobals.KEY_ALPHABETICAL_SHARED_PRODUCTS, isAlphabetical).apply();
    }

    //Type Products
    public static int getSharedProductType(Context context){
        return getSharedFromProducts(context).getInt(
                SharedGlobals.KEY_TYPE_SHARED_PRODUCTS,
                SharedGlobals.DEFAULT_INT_SHARED_ALL
        );
    }

    public static void setSharedProductType(Context context, int type){
        getSharedFromProducts(context).edit().putInt(
                SharedGlobals.KEY_TYPE_SHARED_PRODUCTS, type).apply();
    }

    public static void removeSharedProductType(Context context){
        getSharedFromProducts(context).edit().remove(
                SharedGlobals.KEY_TYPE_SHARED_PRODUCTS).apply();
    }

    public static boolean isSharedProductTypeAvailable(Context context){
        return getSharedFromProducts(context).contains(SharedGlobals.KEY_TYPE_SHARED_RECIPES);
    }
}
