package com.fatiner.platehandler.managers.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fatiner.platehandler.RecipeSQLiteOpenHelper;
import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.classes.ShoppingItem;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.globals.MainGlobals;

import java.util.ArrayList;

public class DbOperations {

    private DbOperations(){}

    public static void readIngredients(Context context, ArrayList<Ingredient> ingredients,
                                       String selection, String orderBy) {
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = DbCursors.getIngredientCursor(db, selection, orderBy);
        DbCursors.setIngredients(cursor, ingredients);
        cursor.close();
        db.close();
    }

    public static void readSteps(Context context, ArrayList<Step> steps,
                                       String selection, String orderBy) {
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = DbCursors.getStepCursor(db, selection, orderBy);
        DbCursors.setSteps(cursor, steps);
        cursor.close();
        db.close();
    }

    public static void readProducts(Context context, ArrayList<Product> products,
                                                   String selection, String orderBy){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = DbCursors.getProductCursor(db, selection, orderBy);
        DbCursors.setProducts(cursor, products);
        cursor.close();
        db.close();
    }

    public static void readProduct(Context context, Product product, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = DbCursors.getProductCursor(db, id);
        DbCursors.setProduct(cursor, product);
        cursor.close();
        db.close();
    }

    public static void readShoppingItems(Context context, ArrayList<ShoppingItem> shoppingItems, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        readIngredientsPart(db, ingredients, id);
        readShoppingItemsPart(db, shoppingItems, ingredients);
        db.close();
    }

    private static void readIngredientsPart(SQLiteDatabase db, ArrayList<Ingredient> ingredients, int id){
        Cursor cursor = DbCursors.getIngredientCursor(db, id);
        DbCursors.setIngredients(cursor, ingredients);
        cursor.close();
    }

    private static void readShoppingItemsPart(SQLiteDatabase db, ArrayList<ShoppingItem> shoppingItems, ArrayList<Ingredient> ingredients){
        for(Ingredient ingredient : ingredients){
            ShoppingItem shoppingItem = new ShoppingItem();
            String name = DbCursors.getProductName(db, ingredient.getProduct().getId());
            shoppingItem.setAmount(ingredient.getAmount());
            shoppingItem.setMeasure(ingredient.getMeasure());
            shoppingItem.setName(name);
            shoppingItems.add(shoppingItem);
        }
    }

    public static void deletedProduct(Context context, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        if(DbCursors.isProductUsable(db, id)) return;
        DbCommands.deleteProduct(db, id);
        db.close();
    }

    public static void deletedRecipe(Context context, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        DbCommands.deleteRecipe(db, id);
        DbCommands.deleteIngredients(db, id);
        DbCommands.deleteSteps(db, id);
        db.close();
    }

    public static void insertedProduct(Context context, Product product, boolean isId){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        DbCommands.insertProduct(db, product, isId);
        db.close();
    }

    public static void insertedRecipe(Context context, Recipe recipe, boolean isId){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        DbCommands.insertRecipe(db, recipe, isId);
        int id = DbCursors.getRecipeId(db);
        DbCommands.insertIngredients(db, recipe.getCategories(), id);
        DbCommands.insertSteps(db, recipe.getSteps(), id);
        db.close();
    }

    public static void updatedRecipe(Context context, Recipe recipe){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        DbCommands.updateRecipe(db, recipe);
        DbCommands.deleteIngredients(db, recipe.getId());
        DbCommands.deleteSteps(db, recipe.getId());
        DbCommands.insertIngredients(db, recipe.getCategories(), recipe.getId());
        DbCommands.insertSteps(db, recipe.getSteps(), recipe.getId());
        db.close();
    }

    public static void updatedProduct(Context context, Product product){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        DbCommands.updateProduct(db, product);
        db.close();
    }

    public static void updatedRecipeFavorite(Context context, boolean isFavorite, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        DbCommands.updateFavorite(db, isFavorite, id);
        db.close();
    }

    public static void readRecipeId(Context context, int[] id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        id[MainGlobals.DF_ZERO] = DbCursors.getRecipeId(db);
        db.close();
    }

    public static void readProductId(Context context, int[] id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        id[MainGlobals.DF_ZERO] = DbCursors.getProductId(db);
        db.close();
    }

    public static void readAuthors(Context context, ArrayList<String> authors){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = DbCursors.getAuthorsCursor(db);
        DbCursors.setAuthors(cursor, authors);
        cursor.close();
        db.close();
    }

    public static void readRecipes(Context context, ArrayList<Recipe> recipes, String selection, String orderBy){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = DbCursors.getRecipeCursor(db, selection, orderBy);
        DbCursors.setRecipeInfo(cursor, recipes);
        cursor.close();
        db.close();
    }

    public static void readRecipe(Context context, Recipe recipe, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        readRecipeInfoPart(db, recipe, id);
        readRecipeCategoriesPart(db, recipe, id);
        readRecipeProductsPart(db, recipe);
        readRecipeStepsPart(db, recipe, id);
        db.close();
    }

    private static void readRecipeInfoPart(SQLiteDatabase db, Recipe recipe, int id){
        Cursor cursor = DbCursors.getRecipeCursor(db, id);
        DbCursors.setRecipe(cursor, recipe);
        cursor.close();
    }

    private static void readRecipeCategoriesPart(SQLiteDatabase db, Recipe recipe, int id){
        Cursor cursor = DbCursors.getIngredientCursor(db, id);
        DbCursors.setCategories(cursor, recipe.getCategories());
        cursor.close();
    }

    private static void readRecipeProductsPart(SQLiteDatabase db, Recipe recipe){
        for(Category category : recipe.getCategories()){
            for(Ingredient ingredient : category.getIngredients()){
                Cursor cursor = DbCursors.getProductCursor(db, ingredient.getProduct().getId());
                Product product = new Product();
                DbCursors.setProduct(cursor, product);
                ingredient.setProduct(product);
                cursor.close();
            }
        }
    }

    private static void readRecipeStepsPart(SQLiteDatabase db, Recipe recipe, int id){
        Cursor cursor = DbCursors.getStepCursor(db, id);
        DbCursors.setSteps(cursor, recipe.getSteps());
        cursor.close();
    }

    public static void readRecent(Context context, ArrayList<Recipe> recentRecipes, ArrayList<Integer> recentIds){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        for(int id : recentIds){
            readRecentPart(db, recentRecipes, id);
        }
        db.close();
    }

    private static void readRecentPart(SQLiteDatabase db,  ArrayList<Recipe> recipes, int id){
        Cursor cursor = DbCursors.getRecipeCursor(db, id);
        Recipe recipe = new Recipe();
        DbCursors.setRecipe(cursor, recipe);
        recipes.add(recipe);
        cursor.close();
    }

    public static void readDay(Context context, ArrayList<Recipe> day, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Recipe recipe = new Recipe();
        readRecipeInfoPart(db, recipe, id);
        day.add(recipe);
        db.close();
    }

    public static void readIds(Context context, ArrayList<Integer> ids){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = DbCursors.getIdCursor(db);
        DbCursors.setIds(cursor, ids);
        cursor.close();
        db.close();
    }

    public static void insertDatabase(Context context, ArrayList<Product> products, ArrayList<Recipe> recipes, ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
        DbCommands.deleteDatabase(context);
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        for(Product product : products) {
            DbCommands.insertProduct(db, product, true);
        }
        for(Recipe recipe : recipes) {
            DbCommands.insertRecipe(db, recipe, true);
        }
        for(Ingredient ingredient : ingredients) {
            DbCommands.insertIngredient(db, ingredient);
        }
        for(Step step : steps) {
            DbCommands.insertStep(db, step);
        }
        db.close();
    }
}
