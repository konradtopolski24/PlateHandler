package com.fatiner.platehandler.managers.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatiner.platehandler.globals.SharedGlobals;

public class SharedRecipeManager {

    private SharedRecipeManager(){}

    private static SharedPreferences getSharedFromRecipes(Context context){
        return context.getSharedPreferences(
                SharedGlobals.SR_RECIPE,
                Context.MODE_PRIVATE
        );
    }

    //Alphabetical
    public static boolean getSharedAlphabetical(Context context){
        return getSharedFromRecipes(context).getBoolean(
                SharedGlobals.KY_ALPHABETICAL,
                SharedGlobals.DF_BOOL
        );
    }

    public static void setSharedAlphabetical(Context context, boolean isAlphabetical){
        getSharedFromRecipes(context).edit().putBoolean(
                SharedGlobals.KY_ALPHABETICAL, isAlphabetical).apply();
    }

    //Favorite
    public static boolean getSharedFavorite(Context context){
        return getSharedFromRecipes(context).getBoolean(
                SharedGlobals.KY_FAVORITE,
                SharedGlobals.DF_BOOL
        );
    }

    public static void setSharedFavorite(Context context, boolean isFavorite){
        getSharedFromRecipes(context).edit().putBoolean(
                SharedGlobals.KY_FAVORITE, isFavorite).apply();
    }

    public static void removeSharedFavorite(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KY_FAVORITE).apply();
    }

    public static boolean isSharedFavoriteAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KY_FAVORITE);
    }

    //Author
    public static String getSharedAuthor(Context context){
        return getSharedFromRecipes(context).getString(
                SharedGlobals.KY_AUTHOR,
                SharedGlobals.DF_STRING
        );
    }

    public static void setSharedAuthor(Context context, String author){
        getSharedFromRecipes(context).edit().putString(
                SharedGlobals.KY_AUTHOR, author).apply();
    }

    public static void removeSharedAuthor(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KY_AUTHOR).apply();
    }

    public static boolean isSharedAuthorAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KY_AUTHOR);
    }

    //Difficulty
    public static int getSharedDifficulty(Context context){
        return getSharedFromRecipes(context).getInt(
                SharedGlobals.KY_DIFFICULTY,
                SharedGlobals.DF_INT
        );
    }

    public static void setSharedDifficulty(Context context, int difficulty){
        getSharedFromRecipes(context).edit().putInt(
                SharedGlobals.KY_DIFFICULTY, difficulty).apply();
    }

    public static void removeSharedDifficulty(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KY_DIFFICULTY).apply();
    }

    public static boolean isSharedDifficultyAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KY_DIFFICULTY);
    }

    //Spiciness
    public static int getSharedSpiciness(Context context){
        return getSharedFromRecipes(context).getInt(
                SharedGlobals.KY_SPICINESS,
                SharedGlobals.DF_INT
        );
    }

    public static void setSharedSpiciness(Context context, int spiciness){
        getSharedFromRecipes(context).edit().putInt(
                SharedGlobals.KY_SPICINESS, spiciness).apply();
    }

    public static void removeSharedSpiciness(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KY_SPICINESS).apply();
    }

    public static boolean isSharedSpicinessAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KY_SPICINESS);
    }

    //Country
    public static int getSharedCountry(Context context){
        return getSharedFromRecipes(context).getInt(
                SharedGlobals.KY_COUNTRY,
                SharedGlobals.DF_INT
        );
    }

    public static void setSharedCountry(Context context, int country){
        getSharedFromRecipes(context).edit().putInt(
                SharedGlobals.KY_COUNTRY, country).apply();
    }

    public static void removeSharedCountry(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KY_COUNTRY).apply();
    }

    public static boolean isSharedCountryAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KY_COUNTRY);
    }

    //Type
    public static int getSharedType(Context context){
        return getSharedFromRecipes(context).getInt(
                SharedGlobals.KY_TYPE,
                SharedGlobals.DF_INT
        );
    }

    public static void setSharedType(Context context, int type){
        getSharedFromRecipes(context).edit().putInt(
                SharedGlobals.KY_TYPE, type).apply();
    }

    public static void removeSharedType(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KY_TYPE).apply();
    }

    public static boolean isSharedTypeAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KY_TYPE);
    }

    //Preference
    public static boolean getSharedPreference(Context context){
        return getSharedFromRecipes(context).getBoolean(
                SharedGlobals.KY_PREFERENCE,
                SharedGlobals.DF_BOOL
        );
    }

    public static void setSharedPreference(Context context, boolean isMeat){
        getSharedFromRecipes(context).edit().putBoolean(
                SharedGlobals.KY_PREFERENCE, isMeat).apply();
    }

    public static void removeSharedPreference(Context context){
        getSharedFromRecipes(context).edit().remove(
                SharedGlobals.KY_PREFERENCE).apply();
    }

    public static boolean isSharedPreferenceAvailable(Context context){
        return getSharedFromRecipes(context).contains(SharedGlobals.KY_PREFERENCE);
    }
}
