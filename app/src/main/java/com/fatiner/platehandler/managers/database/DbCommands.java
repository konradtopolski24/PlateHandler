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
                DbGlobals.TB_RECIPE,
                null,
                DbValues.getValues(recipe, isId));
    }

    public static void insertIngredients(SQLiteDatabase db, ArrayList<Category> categories, int id) {
        for(int i = MainGlobals.DF_ZERO; i < categories.size(); i++){
            Category category = categories.get(i);
            ArrayList<Ingredient> ingredients = categories.get(i).getIngredients();
            for(int j = MainGlobals.DF_ZERO; j < ingredients.size(); j++){
                Ingredient ingredient = category.getIngredients().get(j);
                ingredient.setCategory(category.getName());
                ContentValues values = DbValues.getValues(ingredient, id);
                db.insert(DbGlobals.TB_INGREDIENT, null, values);
            }
        }
    }

    public static void insertIngredient(SQLiteDatabase db, Ingredient ingredient){
        db.insert(
                DbGlobals.TB_INGREDIENT,
                null,
                DbValues.getValues(ingredient, ingredient.getIdRec()));
    }

    public static void insertSteps(SQLiteDatabase db, ArrayList<Step> steps, int id){
        for(int i = MainGlobals.DF_ZERO; i < steps.size(); i++){
            Step step = steps.get(i);
            ContentValues values = DbValues.getValues(step, id);
            db.insert(DbGlobals.TB_STEP, null, values);
        }
    }

    public static void insertStep(SQLiteDatabase db, Step step){
        db.insert(
                DbGlobals.TB_STEP,
                null,
                DbValues.getValues(step, step.getIdRec()));
    }

    public static void insertProduct(SQLiteDatabase db, Product product, boolean isId){
        db.insert(
                DbGlobals.TB_PRODUCT,
                null,
                DbValues.getValues(product, isId)
        );
    }

    public static void updateRecipe(SQLiteDatabase db, Recipe recipe) {
        db.update(DbGlobals.TB_RECIPE, DbValues.getValues(recipe, false),
                DbGlobals.CL_RP_ID + " = ?",
                new String[] {Integer.toString(recipe.getId())});
    }

    public static void updateFavorite(SQLiteDatabase db, boolean isFavorite, int id){
        db.update(
                DbGlobals.TB_RECIPE,
                DbValues.getValues(isFavorite),
                DbGlobals.CL_RP_ID + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void updateProduct(SQLiteDatabase db, Product product) {
        db.update(DbGlobals.TB_PRODUCT, DbValues.getValues(product, false),
                DbGlobals.CL_PD_ID + " = ?",
                new String[] {Integer.toString(product.getId())});
    }

    public static void deleteRecipe(SQLiteDatabase db, int id) {
        db.delete(DbGlobals.TB_RECIPE,
                DbGlobals.CL_RP_ID + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteIngredients(SQLiteDatabase db, int id) {
        db.delete(DbGlobals.TB_INGREDIENT,
                DbGlobals.CL_IG_RECIPE + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteSteps(SQLiteDatabase db, int id) {
        db.delete(DbGlobals.TB_STEP,
                DbGlobals.CL_ST_RECIPE + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteProduct(SQLiteDatabase db, int id) {
        db.delete(DbGlobals.TB_PRODUCT,
                DbGlobals.CL_PD_ID + " = ?",
                new String[] {Integer.toString(id)});
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DbGlobals.DB_NAME);
    }
}
