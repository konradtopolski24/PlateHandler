package com.fatiner.platehandler.managers.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.globals.MainGlobals;

import java.util.ArrayList;

public class DbCommands {

    private DbCommands(){}

    public static void insertRecipe(SQLiteDatabase db, Recipe recipe, boolean isId){
        db.insert(
                DbGlobals.TAB_RECIPES_DB_MAIN,
                null,
                DbValues.getValues(recipe, isId));
    }

    public static void insertIngredients(SQLiteDatabase db, ArrayList<Category> categories, int id) {
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < categories.size(); i++){
            Category category = categories.get(i);
            ArrayList<Ingredient> ingredients = categories.get(i).getIngredients();
            for(int j = MainGlobals.INT_STARTING_VAR_INIT; j < ingredients.size(); j++){
                Ingredient ingredient = category.getIngredients().get(j);
                ingredient.setCategory(category.getName());
                ContentValues values = DbValues.getValues(ingredient, id);
                db.insert(DbGlobals.TAB_INGREDIENTS_DB_MAIN, null, values);
            }
        }
    }

    public static void insertIngredient(SQLiteDatabase db, Ingredient ingredient){
        db.insert(
                DbGlobals.TAB_INGREDIENTS_DB_MAIN,
                null,
                DbValues.getValues(ingredient, ingredient.getIdRec()));
    }

    public static void insertSteps(SQLiteDatabase db, ArrayList<Step> steps, int id){
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < steps.size(); i++){
            Step step = steps.get(i);
            ContentValues values = DbValues.getValues(step, id);
            db.insert(DbGlobals.TAB_STEPS_DB_MAIN, null, values);
        }
    }

    public static void insertStep(SQLiteDatabase db, Step step){
        db.insert(
                DbGlobals.TAB_STEPS_DB_MAIN,
                null,
                DbValues.getValues(step, step.getIdRec()));
    }

    public static void insertProduct(SQLiteDatabase db, Product product, boolean isId){
        db.insert(
                DbGlobals.TAB_PRODUCTS_DB_MAIN,
                null,
                DbValues.getValues(product, isId)
        );
    }

    public static void updateRecipe(SQLiteDatabase db, Recipe recipe) {
        db.update(DbGlobals.TAB_RECIPES_DB_MAIN, DbValues.getValues(recipe, false),
                DbGlobals.COL_ID_TAB_RECIPES + " = ?",
                new String[] {Integer.toString(recipe.getId())});
    }

    public static void updateFavorite(SQLiteDatabase db, boolean isFavorite, int id){
        db.update(
                DbGlobals.TAB_RECIPES_DB_MAIN,
                DbValues.getValues(isFavorite),
                DbGlobals.COL_ID_TAB_RECIPES + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void updateProduct(SQLiteDatabase db, Product product) {
        db.update(DbGlobals.TAB_PRODUCTS_DB_MAIN, DbValues.getValues(product, false),
                DbGlobals.COL_ID_TAB_PRODUCTS + " = ?",
                new String[] {Integer.toString(product.getId())});
    }

    public static void deleteRecipe(SQLiteDatabase db, int id) {
        db.delete(DbGlobals.TAB_RECIPES_DB_MAIN,
                DbGlobals.COL_ID_TAB_RECIPES + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteIngredients(SQLiteDatabase db, int id) {
        db.delete(DbGlobals.TAB_INGREDIENTS_DB_MAIN,
                DbGlobals.COL_IDREC_TAB_INGREDIENTS + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteSteps(SQLiteDatabase db, int id) {
        db.delete(DbGlobals.TAB_STEPS_DB_MAIN,
                DbGlobals.COL_IDREC_TAB_STEPS + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteProduct(SQLiteDatabase db, int id) {
        db.delete(DbGlobals.TAB_PRODUCTS_DB_MAIN,
                DbGlobals.COL_ID_TAB_PRODUCTS + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DbGlobals.NAME_RECIPES_DB_MAIN);
    }
}
