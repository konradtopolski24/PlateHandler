package com.fatiner.platehandler.managers.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fatiner.platehandler.globals.CursorGlobals;
import com.fatiner.platehandler.globals.DatabaseGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.managers.TypeManager;

import java.util.ArrayList;

public class DbCursorManager {

    private DbCursorManager(){}

    //
    //getCursor
    //
    public static Cursor getIdCursor(SQLiteDatabase db){
        return db.query(DatabaseGlobals.TAB_RECIPES_DB_MAIN, new String[] {
                DatabaseGlobals.COL_ID_TAB_RECIPES},
                null, null, null, null, null);
    }

    public static Cursor getRecipeCursor(SQLiteDatabase db, String selection, String orderBy){
        return db.query(DatabaseGlobals.TAB_RECIPES_DB_MAIN, new String[] {
                        DatabaseGlobals.COL_ID_TAB_RECIPES,
                        DatabaseGlobals.COL_NAME_TAB_RECIPES,
                        DatabaseGlobals.COL_AUTHOR_TAB_RECIPES,
                        DatabaseGlobals.COL_SERVING_TAB_RECIPES,
                        DatabaseGlobals.COL_TIME_TAB_RECIPES,
                        DatabaseGlobals.COL_DIFFICULTY_TAB_RECIPES,
                        DatabaseGlobals.COL_SPICINESS_TAB_RECIPES,
                        DatabaseGlobals.COL_COUNTRY_TAB_RECIPES,
                        DatabaseGlobals.COL_TYPE_TAB_RECIPES,
                        DatabaseGlobals.COL_PREFERENCES_TAB_RECIPES,
                        DatabaseGlobals.COL_FAVORITE_TAB_RECIPES
                },
                selection, null, null, null, orderBy);
    }

    public static Cursor getRecipeCursor(SQLiteDatabase db, int id){
        return db.query(DatabaseGlobals.TAB_RECIPES_DB_MAIN,
                new String[] {
                        DatabaseGlobals.COL_ID_TAB_RECIPES,
                        DatabaseGlobals.COL_NAME_TAB_RECIPES,
                        DatabaseGlobals.COL_AUTHOR_TAB_RECIPES,
                        DatabaseGlobals.COL_SERVING_TAB_RECIPES,
                        DatabaseGlobals.COL_TIME_TAB_RECIPES,
                        DatabaseGlobals.COL_DIFFICULTY_TAB_RECIPES,
                        DatabaseGlobals.COL_SPICINESS_TAB_RECIPES,
                        DatabaseGlobals.COL_COUNTRY_TAB_RECIPES,
                        DatabaseGlobals.COL_TYPE_TAB_RECIPES,
                        DatabaseGlobals.COL_PREFERENCES_TAB_RECIPES,
                        DatabaseGlobals.COL_FAVORITE_TAB_RECIPES
                },
                DatabaseGlobals.COL_ID_TAB_RECIPES + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    public static Cursor getIngredientCursor(SQLiteDatabase db, int id){
        return db.query(DatabaseGlobals.TAB_INGREDIENTS_DB_MAIN,
                new String[] {
                        DatabaseGlobals.COL_IDPROD_TAB_INGREDIENTS,
                        DatabaseGlobals.COL_AMOUNT_TAB_INGREDIENTS,
                        DatabaseGlobals.COL_MEASURE_TAB_INGREDIENTS,
                        DatabaseGlobals.COL_CATEGORY_TAB_INGREDIENTS
                },
                DatabaseGlobals.COL_IDREC_TAB_INGREDIENTS + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    public static Cursor getStepCursor(SQLiteDatabase db, int id){
        return db.query(DatabaseGlobals.TAB_STEPS_DB_MAIN,
                new String[] {
                        DatabaseGlobals.COL_INSTRUCTION_TAB_STEPS
                },
                DatabaseGlobals.COL_IDREC_TAB_STEPS + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    public static Cursor getAuthorsCursor(SQLiteDatabase db){
        return db.query(true, DatabaseGlobals.TAB_RECIPES_DB_MAIN, new String[] {
                        DatabaseGlobals.COL_AUTHOR_TAB_RECIPES
                },
                null, null, DatabaseGlobals.COL_AUTHOR_TAB_RECIPES,
                null, null, null);
    }

    public static Cursor getProductCursor(SQLiteDatabase db, String selection, String orderBy){
        return db.query(DatabaseGlobals.TAB_PRODUCTS_DB_MAIN,
                new String[] {
                        DatabaseGlobals.COL_ID_TAB_PRODUCTS,
                        DatabaseGlobals.COL_NAME_TAB_PRODUCTS,
                        DatabaseGlobals.COL_TYPE_TAB_PRODUCTS,
                        DatabaseGlobals.COL_CARBOHYDRATES_TAB_PRODUCTS,
                        DatabaseGlobals.COL_PROTEIN_TAB_PRODUCTS,
                        DatabaseGlobals.COL_FAT_TAB_PRODUCTS
                },
                selection, null,null, null, orderBy);
    }

    public static Cursor getProductCursor(SQLiteDatabase db, int id){
        return db.query(DatabaseGlobals.TAB_PRODUCTS_DB_MAIN,
                new String[] {
                        DatabaseGlobals.COL_ID_TAB_PRODUCTS,
                        DatabaseGlobals.COL_NAME_TAB_PRODUCTS,
                        DatabaseGlobals.COL_TYPE_TAB_PRODUCTS,
                        DatabaseGlobals.COL_CARBOHYDRATES_TAB_PRODUCTS,
                        DatabaseGlobals.COL_PROTEIN_TAB_PRODUCTS,
                        DatabaseGlobals.COL_FAT_TAB_PRODUCTS
                },
                DatabaseGlobals.COL_ID_TAB_PRODUCTS + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    //
    //set
    //
    public static void setRecipe(Cursor cursor, Recipe recipe){
        if(cursor.moveToFirst()){
            do{
                recipe.setId(cursor.getInt(CursorGlobals.ID_ID_CURSOR_RECIPES));
                recipe.setName(cursor.getString(CursorGlobals.ID_NAME_CURSOR_RECIPES));
                recipe.setAuthor(cursor.getString(CursorGlobals.ID_AUTHOR_CURSOR_RECIPES));
                recipe.setServing(cursor.getInt(CursorGlobals.ID_SERVING_CURSOR_RECIPES));
                recipe.setTime(cursor.getString(CursorGlobals.ID_TIME_CURSOR_RECIPES));
                recipe.setDifficulty(cursor.getInt(CursorGlobals.ID_DIFFICULTY_CURSOR_RECIPES));
                recipe.setSpiciness(cursor.getInt(CursorGlobals.ID_SPICINESS_CURSOR_RECIPES));
                recipe.setCountry(cursor.getInt(CursorGlobals.ID_COUNTRY_CURSOR_RECIPES));
                recipe.setType(cursor.getInt(CursorGlobals.ID_TYPE_CURSOR_RECIPES));
                recipe.setPreference(TypeManager.integerToBoolean(
                        cursor.getInt(CursorGlobals.ID_PREFERENCES_CURSOR_RECIPES)));
                recipe.setFavorite(TypeManager.integerToBoolean(
                        cursor.getInt(CursorGlobals.ID_FAVORITE_CURSOR_RECIPES)));
            }while(cursor.moveToNext());
        }
    }

    public static void setRecipeInfo(Cursor cursor, ArrayList<Recipe> arrayRecipes){
        if(cursor.moveToFirst()){
            do{
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(CursorGlobals.ID_ID_CURSOR_RECIPES));
                recipe.setName(cursor.getString(CursorGlobals.ID_NAME_CURSOR_RECIPES));
                recipe.setAuthor(cursor.getString(CursorGlobals.ID_AUTHOR_CURSOR_RECIPES));
                recipe.setServing(cursor.getInt(CursorGlobals.ID_SERVING_CURSOR_RECIPES));
                recipe.setTime(cursor.getString(CursorGlobals.ID_TIME_CURSOR_RECIPES));
                recipe.setDifficulty(cursor.getInt(CursorGlobals.ID_DIFFICULTY_CURSOR_RECIPES));
                recipe.setSpiciness(cursor.getInt(CursorGlobals.ID_SPICINESS_CURSOR_RECIPES));
                recipe.setCountry(cursor.getInt(CursorGlobals.ID_COUNTRY_CURSOR_RECIPES));
                recipe.setType(cursor.getInt(CursorGlobals.ID_TYPE_CURSOR_RECIPES));
                recipe.setPreference(TypeManager.integerToBoolean(
                        cursor.getInt(CursorGlobals.ID_PREFERENCES_CURSOR_RECIPES)));
                recipe.setFavorite(TypeManager.integerToBoolean(
                        cursor.getInt(CursorGlobals.ID_FAVORITE_CURSOR_RECIPES)));
                arrayRecipes.add(recipe);
            }while(cursor.moveToNext());
        }
    }

    public static void setCategories(Cursor cursor, ArrayList<Category> arrayCategories){
        Category category = new Category();
        category.setIngredients(new ArrayList<Ingredient>());
        category.setName(MainGlobals.STR_EMPTY_OBJ_INIT);
        if(cursor.moveToFirst()){
            do{
                Ingredient ingredient = new Ingredient();
                ingredient.setProduct(new Product());
                ingredient.getProduct().setId(cursor.getInt(CursorGlobals.ID_IDPROD_CURSOR_INGREDIENTS));
                ingredient.setAmount(cursor.getFloat(CursorGlobals.ID_AMOUNT_CURSOR_INGREDIENTS));
                ingredient.setMeasure(cursor.getInt(CursorGlobals.ID_MEASURE_CURSOR_INGREDIENTS));
                String currentName = cursor.getString(CursorGlobals.ID_CATEGORY_CURSOR_INGREDIENTS);
                String name = category.getName();
                if(name.equals(currentName)){
                    category.getIngredients().add(ingredient);
                } else {
                    category = new Category();
                    category.setIngredients(new ArrayList<Ingredient>());
                    category.setName(currentName);
                    arrayCategories.add(category);
                    category.getIngredients().add(ingredient);
                }
            }while(cursor.moveToNext());
        }
    }

    public static void setSteps(Cursor cursor, ArrayList<Step> arraySteps){
        if(cursor.moveToFirst()){
            do{
                Step step = new Step();
                step.setInstruction(cursor.getString(CursorGlobals.ID_INSTRUCTION_CURSOR_STEPS));
                arraySteps.add(step);
            }while(cursor.moveToNext());
        }
    }

    public static void setAuthors(Cursor cursor, ArrayList<String> arrayAuthors){
        if(cursor.moveToFirst()){
            do{
                String author = cursor.getString(CursorGlobals.ID_AUTHOR_CURSOR_AUTHORS);
                arrayAuthors.add(author);
            }while(cursor.moveToNext());
        }
    }

    public static void setIds(Cursor cursor, ArrayList<Integer> arrayIds){
        if(cursor.moveToFirst()){
            do{
                Integer id = cursor.getInt(CursorGlobals.ID_ID_CURSOR_RECIPES);
                arrayIds.add(id);
            }while(cursor.moveToNext());
        }
    }

    public static void setProduct(Cursor cursor, Product product){
        if(cursor.moveToFirst()){
            do{
                product.setId(cursor.getInt(CursorGlobals.ID_ID_CURSOR_PRODUCTS));
                product.setName(cursor.getString(CursorGlobals.ID_NAME_CURSOR_PRODUCTS));
                product.setType(cursor.getInt(CursorGlobals.ID_TYPE_CURSOR_PRODUCTS));
                product.setCarbohydrates(cursor.getFloat(
                        CursorGlobals.ID_CARBOHYDRATES_CURSOR_PRODUCTS));
                product.setProtein(cursor.getFloat(CursorGlobals.ID_PROTEIN_CURSOR_PRODUCTS));
                product.setFat(cursor.getFloat(CursorGlobals.ID_FAT_CURSOR_PRODUCTS));
            }while(cursor.moveToNext());
        }
    }

    public static void setProducts(Cursor cursor, ArrayList<Product> arrayProducts){
        if(cursor.moveToFirst()){
            do{
                Product product = new Product();
                product.setId(cursor.getInt(CursorGlobals.ID_ID_CURSOR_PRODUCTS));
                product.setName(cursor.getString(CursorGlobals.ID_NAME_CURSOR_PRODUCTS));
                product.setType(cursor.getInt(CursorGlobals.ID_TYPE_CURSOR_PRODUCTS));
                product.setCarbohydrates(cursor.getFloat(
                        CursorGlobals.ID_CARBOHYDRATES_CURSOR_PRODUCTS));
                product.setProtein(cursor.getFloat(CursorGlobals.ID_PROTEIN_CURSOR_PRODUCTS));
                product.setFat(cursor.getFloat(CursorGlobals.ID_FAT_CURSOR_PRODUCTS));
                arrayProducts.add(product);
            }while(cursor.moveToNext());
        }
    }

    public static void setIngredients(Cursor cursor, ArrayList<Ingredient> arrayIngredients){
        if(cursor.moveToFirst()){
            do{
                Ingredient ingredient = new Ingredient();
                ingredient.setProduct(new Product());
                ingredient.getProduct().setId(cursor.getInt(CursorGlobals.ID_IDPROD_CURSOR_INGREDIENTS));
                ingredient.setAmount(cursor.getFloat(CursorGlobals.ID_AMOUNT_CURSOR_INGREDIENTS));
                ingredient.setMeasure(cursor.getInt(CursorGlobals.ID_MEASURE_CURSOR_INGREDIENTS));
                arrayIngredients.add(ingredient);
            }while(cursor.moveToNext());
        }
    }

    //
    //getId
    //
    public static int getRecipeId(SQLiteDatabase db){
        int id = MainGlobals.INT_STARTING_VAR_INIT;
        Cursor cursor = db.query(DatabaseGlobals.TAB_RECIPES_DB_MAIN, new String[] {
                        DatabaseGlobals.COL_ID_TAB_RECIPES },
                null, null,null, null, null);
        if(cursor.moveToLast()){
            id = cursor.getInt(CursorGlobals.ID_ID_CURSOR_RECIPES);
        }
        cursor.close();
        return id;
    }

    public static int getProductId(SQLiteDatabase db){
        int id = MainGlobals.INT_STARTING_VAR_INIT;
        Cursor cursor = db.query(DatabaseGlobals.TAB_PRODUCTS_DB_MAIN, new String[] {
                        DatabaseGlobals.COL_ID_TAB_PRODUCTS },
                null, null,null, null, null);
        if(cursor.moveToLast()){
            id = cursor.getInt(CursorGlobals.ID_ID_CURSOR_PRODUCTS);
        }
        cursor.close();
        return id;
    }

    public static boolean isProductUsable(SQLiteDatabase db, int id){
        Cursor cursor = db.query(DatabaseGlobals.TAB_INGREDIENTS_DB_MAIN, new String[] {
                        DatabaseGlobals.COL_ID_TAB_INGREDIENTS },
                DatabaseGlobals.COL_IDPROD_TAB_INGREDIENTS + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        return cursorCount != MainGlobals.INT_STARTING_VAR_INIT;
    }

    public static String getProductName(SQLiteDatabase db, int id){
        String name = MainGlobals.STR_EMPTY_OBJ_INIT;
        Cursor cursor = db.query(DatabaseGlobals.TAB_PRODUCTS_DB_MAIN, new String[] {
                        DatabaseGlobals.COL_NAME_TAB_PRODUCTS },
                DatabaseGlobals.COL_ID_TAB_PRODUCTS + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
        if(cursor.moveToLast()){
            name = cursor.getString(CursorGlobals.ID_ID_CURSOR_PRODUCTS);
        }
        cursor.close();
        return name;
    }
}
