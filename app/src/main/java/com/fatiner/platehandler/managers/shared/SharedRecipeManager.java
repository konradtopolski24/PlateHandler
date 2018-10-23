package com.fatiner.platehandler.managers.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatiner.platehandler.globals.SharedGlobals;

public class SharedRecipeManager {

    private SharedRecipeManager(){}

    private static SharedPreferences getSharedFromRecipes(Context context){
        return context.getSharedPreferences(
                SharedGlobals.NAME_RECIPES_SHARED_RECIPES,
                Context.MODE_PRIVATE
        );
    }

    //Alphabetical
    public static boolean getSharedAlphabetical(Context context){
        return getSharedFromRecipes(context).getBoolean(
                SharedGlobals.KEY_ALPHABETICAL_SHARED_RECIPES,
                SharedGlobals.DEFAULT_BOOL_SHARED_ALL
        );
    }

    public static void setSharedAlphabetical(Context context, boolean isAlphabetical){
        getSharedFromRecipes(context).edit().putBoolean(
                SharedGlobals.KEY_ALPHABETICAL_SHARED_RECIPES, isAlphabetical).apply();
    }

    //Favorite
    public static boolean getSharedFavorite(Context context){
        return getSharedFromRecipes(context).getBoolean(
                SharedGlobals.KEY_FAVORITE_SHARED_RECIPES,
                SharedGlobals.DEFAULT_BOOL_SHARED_ALL
        );
    }

    public static void setSharedFavorite(Context context, boolean isFavorite){
        getSharedFromRecipes(context).edit().putBoolean(
                SharedGlobals.KEY_FAVORITE_SHARED_RECIPES, isFavorite).apply();
    }

    public static void removeSharedFavorite(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KEY_FAVORITE_SHARED_RECIPES).apply();
    }

    public static boolean isSharedFavoriteAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KEY_FAVORITE_SHARED_RECIPES);
    }

    //Author
    public static String getSharedAuthor(Context context){
        return getSharedFromRecipes(context).getString(
                SharedGlobals.KEY_AUTHOR_SHARED_RECIPES,
                SharedGlobals.DEFAULT_STRING_SHARED_ALL
        );
    }

    public static void setSharedAuthor(Context context, String author){
        getSharedFromRecipes(context).edit().putString(
                SharedGlobals.KEY_AUTHOR_SHARED_RECIPES, author).apply();
    }

    public static void removeSharedAuthor(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KEY_AUTHOR_SHARED_RECIPES).apply();
    }

    public static boolean isSharedAuthorAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KEY_AUTHOR_SHARED_RECIPES);
    }

    //Difficulty
    public static int getSharedDifficulty(Context context){
        return getSharedFromRecipes(context).getInt(
                SharedGlobals.KEY_DIFFICULTY_SHARED_RECIPES,
                SharedGlobals.DEFAULT_INT_SHARED_ALL
        );
    }

    public static void setSharedDifficulty(Context context, int difficulty){
        getSharedFromRecipes(context).edit().putInt(
                SharedGlobals.KEY_DIFFICULTY_SHARED_RECIPES, difficulty).apply();
    }

    public static void removeSharedDifficulty(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KEY_DIFFICULTY_SHARED_RECIPES).apply();
    }

    public static boolean isSharedDifficultyAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KEY_DIFFICULTY_SHARED_RECIPES);
    }

    //Spiciness
    public static int getSharedSpiciness(Context context){
        return getSharedFromRecipes(context).getInt(
                SharedGlobals.KEY_SPICINESS_SHARED_RECIPES,
                SharedGlobals.DEFAULT_INT_SHARED_ALL
        );
    }

    public static void setSharedSpiciness(Context context, int spiciness){
        getSharedFromRecipes(context).edit().putInt(
                SharedGlobals.KEY_SPICINESS_SHARED_RECIPES, spiciness).apply();
    }

    public static void removeSharedSpiciness(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KEY_SPICINESS_SHARED_RECIPES).apply();
    }

    public static boolean isSharedSpicinessAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KEY_SPICINESS_SHARED_RECIPES);
    }

    //Country
    public static int getSharedCountry(Context context){
        return getSharedFromRecipes(context).getInt(
                SharedGlobals.KEY_COUNTRY_SHARED_RECIPES,
                SharedGlobals.DEFAULT_INT_SHARED_ALL
        );
    }

    public static void setSharedCountry(Context context, int country){
        getSharedFromRecipes(context).edit().putInt(
                SharedGlobals.KEY_COUNTRY_SHARED_RECIPES, country).apply();
    }

    public static void removeSharedCountry(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KEY_COUNTRY_SHARED_RECIPES).apply();
    }

    public static boolean isSharedCountryAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KEY_COUNTRY_SHARED_RECIPES);
    }

    //Type
    public static int getSharedType(Context context){
        return getSharedFromRecipes(context).getInt(
                SharedGlobals.KEY_TYPE_SHARED_RECIPES,
                SharedGlobals.DEFAULT_INT_SHARED_ALL
        );
    }

    public static void setSharedType(Context context, int type){
        getSharedFromRecipes(context).edit().putInt(
                SharedGlobals.KEY_TYPE_SHARED_RECIPES, type).apply();
    }

    public static void removeSharedType(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KEY_TYPE_SHARED_RECIPES).apply();
    }

    public static boolean isSharedTypeAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KEY_TYPE_SHARED_RECIPES);
    }

    //Preference
    public static boolean getSharedPreference(Context context){
        return getSharedFromRecipes(context).getBoolean(
                SharedGlobals.KEY_PREFERENCE_SHARED_RECIPES,
                SharedGlobals.DEFAULT_BOOL_SHARED_ALL
        );
    }

    public static void setSharedPreference(Context context, boolean isMeat){
        getSharedFromRecipes(context).edit().putBoolean(
                SharedGlobals.KEY_PREFERENCE_SHARED_RECIPES, isMeat).apply();
    }

    public static void removeSharedPreference(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KEY_PREFERENCE_SHARED_RECIPES).apply();
    }

    public static boolean isSharedPreferenceAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KEY_PREFERENCE_SHARED_RECIPES);
    }
}
