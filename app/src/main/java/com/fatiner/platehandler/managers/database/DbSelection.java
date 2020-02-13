package com.fatiner.platehandler.managers.database;

import android.content.Context;

import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.globals.SharedGlobals;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;

import java.util.ArrayList;

public class DbSelection {

    private DbSelection(){}

    private static void addSelectionBool(ArrayList<String> strings, Context context, String column, String name, String key) {
        if(SharedManager.isValueAvailable(context, name, key)){
            boolean value = SharedManager.getBoolean(context, name, key);
            String text = String.valueOf(TypeManager.booleanToInteger(value));
            strings.add(column + " = " + text);
        }
    }

    private static void addSelectionString(ArrayList<String> strings, Context context, String column, String name, String key) {
        if(SharedManager.isValueAvailable(context, name, key)){
            String text = SharedManager.getString(context, name, key);
            strings.add(column + " = '" + text + "'");
        }
    }

    private static void addSelectionInt(ArrayList<String> strings, Context context, String column, String name, String key) {
        if(SharedManager.isValueAvailable(context, name, key)){
            int value = SharedManager.getInt(context, name, key);
            String text = String.valueOf(value);
            strings.add(column + " = " + text);
        }
    }

    public static ArrayList<String> getRecipeSelection(Context context){
        ArrayList<String> strings = new ArrayList<>();
        addSelectionBool(strings, context, DbGlobals.CL_RP_FAVORITE, SharedGlobals.SR_RECIPE, SharedGlobals.KY_FAVORITE);
        addSelectionString(strings, context, DbGlobals.CL_RP_AUTHOR, SharedGlobals.SR_RECIPE, SharedGlobals.KY_AUTHOR);
        addSelectionInt(strings, context, DbGlobals.CL_RP_DIFFICULTY, SharedGlobals.SR_RECIPE, SharedGlobals.KY_DIFFICULTY);
        addSelectionInt(strings, context, DbGlobals.CL_RP_SPICINESS, SharedGlobals.SR_RECIPE, SharedGlobals.KY_SPICINESS);
        addSelectionInt(strings, context, DbGlobals.CL_RP_COUNTRY, SharedGlobals.SR_RECIPE, SharedGlobals.KY_COUNTRY);
        addSelectionInt(strings, context, DbGlobals.CL_RP_TYPE, SharedGlobals.SR_RECIPE, SharedGlobals.KY_TYPE);
        addSelectionBool(strings, context, DbGlobals.CL_RP_PREFERENCE, SharedGlobals.SR_RECIPE, SharedGlobals.KY_PREFERENCE);
        return strings;
    }

    public static ArrayList<String> getProductSelection(Context context){
        ArrayList<String> strings = new ArrayList<>();
        addSelectionInt(strings, context, DbGlobals.CL_RP_TYPE, SharedGlobals.SR_PRODUCT, SharedGlobals.KY_TYPE);
        return strings;
    }
}
