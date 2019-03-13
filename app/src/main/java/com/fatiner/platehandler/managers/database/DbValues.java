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
        if(isId) {
            contentValues.put(DbGlobals.COL_ID_TAB_PRODUCTS, recipe.getId());
        }
        contentValues.put(DbGlobals.COL_NAME_TAB_RECIPES, recipe.getName());
        contentValues.put(DbGlobals.COL_AUTHOR_TAB_RECIPES, recipe.getAuthor());
        contentValues.put(DbGlobals.COL_SERVING_TAB_RECIPES, recipe.getServing());
        contentValues.put(DbGlobals.COL_TIME_TAB_RECIPES, recipe.getTime());
        contentValues.put(DbGlobals.COL_DIFFICULTY_TAB_RECIPES, recipe.getDifficulty());
        contentValues.put(DbGlobals.COL_SPICINESS_TAB_RECIPES, recipe.getSpiciness());
        contentValues.put(DbGlobals.COL_COUNTRY_TAB_RECIPES, recipe.getCountry());
        contentValues.put(DbGlobals.COL_TYPE_TAB_RECIPES, recipe.getType());
        contentValues.put(DbGlobals.COL_PREFERENCES_TAB_RECIPES,
                TypeManager.booleanToInteger(recipe.getPreference()));
        contentValues.put(DbGlobals.COL_FAVORITE_TAB_RECIPES,
                TypeManager.booleanToInteger(recipe.getFavorite()));
        return contentValues;
    }

    public static ContentValues getValues(Ingredient ingredient, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbGlobals.COL_IDREC_TAB_INGREDIENTS, id);
        contentValues.put(DbGlobals.COL_IDPROD_TAB_INGREDIENTS, ingredient.getProduct().getId());
        contentValues.put(DbGlobals.COL_AMOUNT_TAB_INGREDIENTS, ingredient.getAmount());
        contentValues.put(DbGlobals.COL_MEASURE_TAB_INGREDIENTS, ingredient.getMeasure());
        contentValues.put(DbGlobals.COL_CATEGORY_TAB_INGREDIENTS, ingredient.getCategory());
        return contentValues;
    }

    public static ContentValues getValues(Step step, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbGlobals.COL_IDREC_TAB_STEPS, id);
        contentValues.put(DbGlobals.COL_INSTRUCTION_TAB_STEPS, step.getInstruction());
        return contentValues;
    }

    public static ContentValues getValues(boolean isFavorite) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbGlobals.COL_FAVORITE_TAB_RECIPES,
                TypeManager.booleanToInteger(isFavorite));
        return contentValues;
    }

    public static ContentValues getValues(Product product, boolean isId) {
        ContentValues contentValues = new ContentValues();
        if(isId) {
            contentValues.put(DbGlobals.COL_ID_TAB_PRODUCTS, product.getId());
        }
        contentValues.put(DbGlobals.COL_NAME_TAB_PRODUCTS, product.getName());
        contentValues.put(DbGlobals.COL_TYPE_TAB_PRODUCTS, product.getType());
        contentValues.put(DbGlobals.COL_CARBOHYDRATES_TAB_PRODUCTS,
                product.getCarbohydrates());
        contentValues.put(DbGlobals.COL_PROTEIN_TAB_PRODUCTS, product.getProtein());
        contentValues.put(DbGlobals.COL_FAT_TAB_PRODUCTS, product.getFat());
        return contentValues;
    }
}
