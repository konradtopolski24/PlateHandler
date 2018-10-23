package com.fatiner.platehandler.managers.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.fatiner.platehandler.RecipeSQLiteOpenHelper;
import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.classes.ShoppingItem;
import com.fatiner.platehandler.managers.shared.SharedRecipeManager;

import java.util.ArrayList;

public class DbSuccessManager {

    private DbSuccessManager(){}

    public static boolean readProducts(Context context, ArrayList<Product> products,
                                                   String selection, String orderBy){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = DbCursorManager.getProductCursor(db, selection, orderBy);
            DbCursorManager.setProducts(cursor, products);
            cursor.close();
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public static boolean readProduct(Context context, Product product, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = DbCursorManager.getProductCursor(db, id);
            DbCursorManager.setProduct(cursor, product);
            cursor.close();
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public static boolean readShoppingItems(Context context, ArrayList<ShoppingItem> shoppingItems, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            readIngredientsPart(db, ingredients, id);
            readShoppingItemsPart(db, shoppingItems, ingredients);
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    private static void readIngredientsPart(SQLiteDatabase db, ArrayList<Ingredient> ingredients, int id){
        Cursor cursor = DbCursorManager.getIngredientCursor(db, id);
        DbCursorManager.setIngredients(cursor, ingredients);
        cursor.close();
    }

    private static void readShoppingItemsPart(SQLiteDatabase db, ArrayList<ShoppingItem> shoppingItems, ArrayList<Ingredient> ingredients){
        for(Ingredient ingredient : ingredients){
            ShoppingItem shoppingItem = new ShoppingItem();
            String name = DbCursorManager.getProductName(db, ingredient.getProduct().getId());
            shoppingItem.setAmount(ingredient.getAmount());
            shoppingItem.setMeasure(ingredient.getMeasure());
            shoppingItem.setName(name);
            shoppingItems.add(shoppingItem);
        }
    }

    public static boolean deletedProduct(Context context, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getWritableDatabase();
            if(DbCursorManager.isProductUsable(db, id)) return false;
            DbCommandManager.deleteProductValues(db, id);
            db.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public static boolean deletedRecipe(Context context){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getWritableDatabase();
            DbCommandManager.deleteRecipeValues(db, RecipeDetails.getRecipe().getId());
            DbCommandManager.deleteIngredientValues(db, RecipeDetails.getRecipe().getId());
            DbCommandManager.deleteStepValues(db, RecipeDetails.getRecipe().getId());
            db.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public static boolean insertedProduct(Context context){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getWritableDatabase();
            DbCommandManager.insertProductValues(db);
            db.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public static boolean insertedRecipe(Context context){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getWritableDatabase();
            DbCommandManager.insertRecipeValues(db);
            int id = DbCursorManager.getRecipeId(db);
            DbCommandManager.insertIngredientValues(db, id);
            DbCommandManager.insertStepValues(db, id);
            db.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public static boolean updatedRecipe(Context context){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getWritableDatabase();
            DbCommandManager.updateRecipeValues(db, RecipeDetails.getRecipe().getId());
            DbCommandManager.deleteIngredientValues(db, RecipeDetails.getRecipe().getId());
            DbCommandManager.deleteStepValues(db, RecipeDetails.getRecipe().getId());
            DbCommandManager.insertIngredientValues(db, RecipeDetails.getRecipe().getId());
            DbCommandManager.insertStepValues(db, RecipeDetails.getRecipe().getId());
            db.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public static boolean updatedProduct(Context context){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getWritableDatabase();
            DbCommandManager.updateProductValues(db, ProductDetails.getProduct().getId());
            db.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public static boolean updatedRecipeFavorite(Context context){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getWritableDatabase();
            DbCommandManager.updateFavoriteValues(db, RecipeDetails.getRecipe().getId());
            db.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public static boolean readRecipeId(Context context, int[] id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            id[MainGlobals.INT_STARTING_VAR_INIT] = DbCursorManager.getRecipeId(db);
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public static boolean readProductId(Context context, int[] id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            id[MainGlobals.INT_STARTING_VAR_INIT] = DbCursorManager.getProductId(db);
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public static boolean readAuthors(Context context, ArrayList<String> authors){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = DbCursorManager.getAuthorsCursor(db);
            DbCursorManager.setAuthors(cursor, authors);
            cursor.close();
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public static boolean readRecipes(Context context, ArrayList<Recipe> recipes, String selection, String orderBy){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = DbCursorManager.getRecipeCursor(db, selection, orderBy);
            DbCursorManager.setRecipeInfo(cursor, recipes);
            cursor.close();
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public static boolean readRecipe(Context context, Recipe recipe, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            readRecipeInfoPart(db, recipe, id);
            readRecipeCategoriesPart(db, recipe, id);
            readRecipeProductsPart(db, recipe);
            readRecipeStepsPart(db, recipe, id);
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    private static void readRecipeInfoPart(SQLiteDatabase db, Recipe recipe, int id){
        Cursor cursor = DbCursorManager.getRecipeCursor(db, id);
        DbCursorManager.setRecipe(cursor, recipe);
        cursor.close();
    }

    private static void readRecipeCategoriesPart(SQLiteDatabase db, Recipe recipe, int id){
        Cursor cursor = DbCursorManager.getIngredientCursor(db, id);
        DbCursorManager.setCategories(cursor, recipe.getCategories());
        cursor.close();
    }

    private static void readRecipeProductsPart(SQLiteDatabase db, Recipe recipe){
        for(Category category : recipe.getCategories()){
            for(Ingredient ingredient : category.getIngredients()){
                Cursor cursor = DbCursorManager.getProductCursor(db, ingredient.getProduct().getId());
                Product product = new Product();
                DbCursorManager.setProduct(cursor, product);
                ingredient.setProduct(product);
                cursor.close();
            }
        }
    }

    private static void readRecipeStepsPart(SQLiteDatabase db, Recipe recipe, int id){
        Cursor cursor = DbCursorManager.getStepCursor(db, id);
        DbCursorManager.setSteps(cursor, recipe.getSteps());
        cursor.close();
    }

    public static boolean readRecent(Context context, ArrayList<Recipe> recentRecipes, ArrayList<Integer> recentIds){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            for(int id : recentIds){
                readRecentPart(db, recentRecipes, id);
            }
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    private static void readRecentPart(SQLiteDatabase db,  ArrayList<Recipe> recipes, int id){
        Cursor cursor = DbCursorManager.getRecipeCursor(db, id);
        Recipe recipe = new Recipe();
        DbCursorManager.setRecipe(cursor, recipe);
        recipes.add(recipe);
        cursor.close();
    }

    public static boolean readDish(Context context, ArrayList<Recipe> dish, int id){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            Recipe recipe = new Recipe();
            readRecipeInfoPart(db, recipe, id);
            dish.add(recipe);
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public static boolean readIds(Context context, ArrayList<Integer> ids){
        SQLiteOpenHelper helper = new RecipeSQLiteOpenHelper(context);
        try{
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = DbCursorManager.getIdCursor(db);
            DbCursorManager.setIds(cursor, ids);
            cursor.close();
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }
}
