package com.fatiner.platehandler.managers.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.globals.DatabaseGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Step;

import java.util.ArrayList;

public class DbCommandManager {

    private DbCommandManager(){}

    //
    //insert
    //
    public static void insertRecipeValues(SQLiteDatabase db){
        db.insert(DatabaseGlobals.TAB_RECIPES_DB_MAIN, null,
                DbContentManager.getRecipeValues());
    }

    public static void insertIngredientValues(SQLiteDatabase db, int id){
        ArrayList<Category> categories = RecipeDetails.getRecipe().getCategories();
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < categories.size(); i++){
            ArrayList<Ingredient> ingredients = categories.get(i).getIngredients();
            for(int j = MainGlobals.INT_STARTING_VAR_INIT; j < ingredients.size(); j++){
                ContentValues values = DbContentManager.getIngredientValues(id, i, j);
                db.insert(DatabaseGlobals.TAB_INGREDIENTS_DB_MAIN, null, values);
            }
        }
    }

    public static void insertStepValues(SQLiteDatabase db, int id){
        ArrayList<Step> steps = RecipeDetails.getRecipe().getSteps();
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < steps.size(); i++){
            ContentValues values = DbContentManager.getStepValues(id, i);
            db.insert(DatabaseGlobals.TAB_STEPS_DB_MAIN, null, values);
        }
    }

    public static void insertProductValues(SQLiteDatabase db){
        db.insert(DatabaseGlobals.TAB_PRODUCTS_DB_MAIN, null,
                DbContentManager.getProductValues());
    }

    //
    //update
    //
    public static void updateRecipeValues(SQLiteDatabase db, int id){
        db.update(DatabaseGlobals.TAB_RECIPES_DB_MAIN, DbContentManager.getRecipeValues(),
                DatabaseGlobals.COL_ID_TAB_RECIPES + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void updateFavoriteValues(SQLiteDatabase db, int id){
        db.update(DatabaseGlobals.TAB_RECIPES_DB_MAIN, DbContentManager.getFavoriteValues(),
                DatabaseGlobals.COL_ID_TAB_RECIPES + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void updateProductValues(SQLiteDatabase db, int id){
        db.update(DatabaseGlobals.TAB_PRODUCTS_DB_MAIN, DbContentManager.getProductValues(),
                DatabaseGlobals.COL_ID_TAB_PRODUCTS + " = ?",
                new String[] {Integer.toString(id)});
    }

    //
    //delete
    //
    public static void deleteRecipeValues(SQLiteDatabase db, int id){
        db.delete(DatabaseGlobals.TAB_RECIPES_DB_MAIN,
                DatabaseGlobals.COL_ID_TAB_RECIPES + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteIngredientValues(SQLiteDatabase db, int id){
        db.delete(DatabaseGlobals.TAB_INGREDIENTS_DB_MAIN,
                DatabaseGlobals.COL_IDREC_TAB_INGREDIENTS + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteStepValues(SQLiteDatabase db, int id){
        db.delete(DatabaseGlobals.TAB_STEPS_DB_MAIN,
                DatabaseGlobals.COL_IDREC_TAB_STEPS + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteProductValues(SQLiteDatabase db, int id){
        db.delete(DatabaseGlobals.TAB_PRODUCTS_DB_MAIN,
                DatabaseGlobals.COL_ID_TAB_PRODUCTS + " = ?",
                new String[] {Integer.toString(id)});
    }
}
