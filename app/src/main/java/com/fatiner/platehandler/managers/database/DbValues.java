package com.fatiner.platehandler.managers.database;

import android.content.ContentValues;

import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.managers.TypeManager;

public class DbValues {

    private DbValues() {}

    public static ContentValues getValues(Recipe recipe, boolean isId) {
        ContentValues contentValues = new ContentValues();
        if(isId) contentValues.put(DbGlobals.CL_PD_ID, recipe.getId());
        contentValues.put(DbGlobals.CL_RP_NAME, recipe.getName());
        contentValues.put(DbGlobals.CL_RP_AUTHOR, recipe.getAuthor());
        contentValues.put(DbGlobals.CL_RP_SERVING, recipe.getServing());
        contentValues.put(DbGlobals.CL_RP_TIME, recipe.getTime());
        contentValues.put(DbGlobals.CL_RP_DIFFICULTY, recipe.getDifficulty());
        contentValues.put(DbGlobals.CL_RP_SPICINESS, recipe.getSpiciness());
        contentValues.put(DbGlobals.CL_RP_COUNTRY, recipe.getCountry());
        contentValues.put(DbGlobals.CL_RP_TYPE, recipe.getType());
        contentValues.put(DbGlobals.CL_RP_PREFERENCE, TypeManager.booleanToInteger(recipe.getPreference()));
        contentValues.put(DbGlobals.CL_RP_FAVORITE, TypeManager.booleanToInteger(recipe.getFavorite()));
        return contentValues;
    }

    public static ContentValues getValues(Ingredient ingredient, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbGlobals.CL_IG_RECIPE, id);
        contentValues.put(DbGlobals.CL_IG_PRODUCT, ingredient.getProduct().getId());
        contentValues.put(DbGlobals.CL_IG_AMOUNT, ingredient.getAmount());
        contentValues.put(DbGlobals.CL_IG_MEASURE, ingredient.getMeasure());
        contentValues.put(DbGlobals.CL_IG_CATEGORY, ingredient.getCategory());
        return contentValues;
    }

    public static ContentValues getValues(Step step, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbGlobals.CL_ST_RECIPE, id);
        contentValues.put(DbGlobals.CL_ST_INSTRUCTION, step.getInstruction());
        return contentValues;
    }

    public static ContentValues getValues(boolean isFavorite) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbGlobals.CL_RP_FAVORITE, TypeManager.booleanToInteger(isFavorite));
        return contentValues;
    }

    public static ContentValues getValues(Product product, boolean isId) {
        ContentValues contentValues = new ContentValues();
        if(isId) contentValues.put(DbGlobals.CL_PD_ID, product.getId());
        contentValues.put(DbGlobals.CL_PD_NAME, product.getName());
        contentValues.put(DbGlobals.CL_PD_TYPE, product.getType());
        contentValues.put(DbGlobals.CL_PD_CARBOHYDRATES, product.getCarbohydrates());
        contentValues.put(DbGlobals.CL_PD_PROTEIN, product.getProtein());
        contentValues.put(DbGlobals.CL_PD_FAT, product.getFat());
        return contentValues;
    }
}
