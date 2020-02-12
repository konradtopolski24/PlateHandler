package com.fatiner.platehandler.managers.database;

import android.content.Context;

import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.managers.shared.SharedProductManager;
import com.fatiner.platehandler.managers.shared.SharedRecipeManager;
import com.fatiner.platehandler.managers.TypeManager;

import java.util.ArrayList;

public class DbSelection {

    private DbSelection(){}

    private static void addSelectionFavorite(ArrayList<String> strings, Context context){
        if(SharedRecipeManager.isSharedFavoriteAvailable(context)){
            String favorite = String.valueOf(TypeManager.booleanToInteger(
                    SharedRecipeManager.getSharedFavorite(context)));
            strings.add(DbGlobals.CL_RP_FAVORITE + " = " + favorite);
        }
    }

    private static void addSelectionAuthor(ArrayList<String> strings, Context context){
        if(SharedRecipeManager.isSharedAuthorAvailable(context)){
            String author = SharedRecipeManager.getSharedAuthor(context);
            strings.add(DbGlobals.CL_RP_AUTHOR + " = '" + author + "'");
        }
    }

    private static void addSelectionDifficulty(ArrayList<String> strings, Context context){
        if(SharedRecipeManager.isSharedDifficultyAvailable(context)){
            String difficulty = String.valueOf(SharedRecipeManager.getSharedDifficulty(context));
            strings.add(DbGlobals.CL_RP_DIFFICULTY + " = " + difficulty);
        }
    }

    private static void addSelectionSpiciness(ArrayList<String> strings, Context context){
        if(SharedRecipeManager.isSharedSpicinessAvailable(context)){
            String spiciness = String.valueOf(SharedRecipeManager.getSharedSpiciness(context));
            strings.add(DbGlobals.CL_RP_SPICINESS + " = " + spiciness);
        }
    }

    private static void addSelectionCountry(ArrayList<String> strings, Context context){
        if(SharedRecipeManager.isSharedCountryAvailable(context)){
            String country = String.valueOf(SharedRecipeManager.getSharedCountry(context));
            strings.add(DbGlobals.CL_RP_COUNTRY + " = " + country);
        }
    }

    private static void addSelectionType(ArrayList<String> strings, Context context){
        if(SharedRecipeManager.isSharedTypeAvailable(context)){
            String type = String.valueOf(SharedRecipeManager.getSharedType(context));
            strings.add(DbGlobals.CL_RP_TYPE + " = " + type);
        }
    }

    private static void addSelectionPreference(ArrayList<String> strings, Context context){
        if(SharedRecipeManager.isSharedPreferenceAvailable(context)){
            String preference = String.valueOf(TypeManager.booleanToInteger(
                    SharedRecipeManager.getSharedPreference(context)));
            strings.add(DbGlobals.CL_RP_PREFERENCE + " = " + preference);
        }
    }

    public static ArrayList<String> getArraySelection(Context context){
        ArrayList<String> strings = new ArrayList<>();
        addSelectionFavorite(strings, context);
        addSelectionAuthor(strings, context);
        addSelectionDifficulty(strings, context);
        addSelectionSpiciness(strings, context);
        addSelectionCountry(strings, context);
        addSelectionType(strings, context);
        addSelectionPreference(strings, context);
        return strings;
    }

    private static void addSelectionTypeProduct(ArrayList<String> strings, Context context){
        if(SharedProductManager.isSharedProductTypeAvailable(context)){
            String type = String.valueOf(SharedProductManager.getSharedProductType(context));
            strings.add(DbGlobals.CL_PD_TYPE + " = " + type);
        }
    }

    public static ArrayList<String> getArraySelectionProduct(Context context){
        ArrayList<String> strings = new ArrayList<>();
        addSelectionTypeProduct(strings, context);
        return strings;
    }
}
