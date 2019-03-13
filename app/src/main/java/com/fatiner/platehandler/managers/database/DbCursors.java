package com.fatiner.platehandler.managers.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.managers.TypeManager;

import java.util.ArrayList;

public class DbCursors {

    private DbCursors() {}

    //
    //getCursor
    //
    public static Cursor getIdCursor(SQLiteDatabase db) {
        return db.query(DbGlobals.TAB_RECIPES_DB_MAIN, new String[] {
                DbGlobals.COL_ID_TAB_RECIPES},
                null, null, null, null, null);
    }

    public static Cursor getRecipeCursor(SQLiteDatabase db, String selection, String orderBy) {
        return db.query(
                DbGlobals.TAB_RECIPES_DB_MAIN,
                TypeManager.arrayListToArray(DbGlobals.getRecipesColumns()),
                selection, null, null, null, orderBy);
    }

    public static Cursor getRecipeCursor(SQLiteDatabase db, int id){
        return db.query(
                DbGlobals.TAB_RECIPES_DB_MAIN,
                TypeManager.arrayListToArray(DbGlobals.getRecipesColumns()),
                DbGlobals.COL_ID_TAB_RECIPES + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    public static Cursor getIngredientCursor(SQLiteDatabase db, int id){
        return db.query(
                DbGlobals.TAB_INGREDIENTS_DB_MAIN,
                TypeManager.arrayListToArray(DbGlobals.getIngredientsColumns()),
                DbGlobals.COL_IDREC_TAB_INGREDIENTS + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    public static Cursor getIngredientCursor(SQLiteDatabase db, String selection, String orderBy){
        return db.query(
                DbGlobals.TAB_INGREDIENTS_DB_MAIN,
                TypeManager.arrayListToArray(DbGlobals.getIngredientsColumns()),
                selection, null, null, null, orderBy);
    }

    public static Cursor getStepCursor(SQLiteDatabase db, int id){
        return db.query(DbGlobals.TAB_STEPS_DB_MAIN,
                TypeManager.arrayListToArray(DbGlobals.getStepsColumns()),
                DbGlobals.COL_IDREC_TAB_STEPS + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    public static Cursor getStepCursor(SQLiteDatabase db, String selection, String orderBy){
        return db.query(DbGlobals.TAB_STEPS_DB_MAIN,
                TypeManager.arrayListToArray(DbGlobals.getStepsColumns()),
                selection, null, null, null, orderBy);
    }

    public static Cursor getAuthorsCursor(SQLiteDatabase db){
        return db.query(true, DbGlobals.TAB_RECIPES_DB_MAIN, new String[] {
                        DbGlobals.COL_AUTHOR_TAB_RECIPES
                },
                null, null, DbGlobals.COL_AUTHOR_TAB_RECIPES,
                null, null, null);
    }

    public static Cursor getProductCursor(SQLiteDatabase db, String selection, String orderBy){
        return db.query(DbGlobals.TAB_PRODUCTS_DB_MAIN,
                TypeManager.arrayListToArray(DbGlobals.getProductsColumns()),
                selection, null,null, null, orderBy);
    }

    public static Cursor getProductCursor(SQLiteDatabase db, int id){
        return db.query(DbGlobals.TAB_PRODUCTS_DB_MAIN,
                TypeManager.arrayListToArray(DbGlobals.getProductsColumns()),
                DbGlobals.COL_ID_TAB_PRODUCTS + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    //
    //set
    //
    public static void setRecipe(Cursor cursor, Recipe recipe){
        ArrayList<String> columns = DbGlobals.getRecipesColumns();
        if(cursor.moveToFirst()){
            do{
                recipe.setId(cursor.getInt(columns.indexOf(DbGlobals.COL_ID_TAB_RECIPES)));
                recipe.setName(cursor.getString(columns.indexOf(DbGlobals.COL_NAME_TAB_RECIPES)));
                recipe.setAuthor(cursor.getString(columns.indexOf(DbGlobals.COL_AUTHOR_TAB_RECIPES)));
                recipe.setServing(cursor.getInt(columns.indexOf(DbGlobals.COL_SERVING_TAB_RECIPES)));
                recipe.setTime(cursor.getString(columns.indexOf(DbGlobals.COL_TIME_TAB_RECIPES)));
                recipe.setDifficulty(cursor.getInt(columns.indexOf(DbGlobals.COL_DIFFICULTY_TAB_RECIPES)));
                recipe.setSpiciness(cursor.getInt(columns.indexOf(DbGlobals.COL_SPICINESS_TAB_RECIPES)));
                recipe.setCountry(cursor.getInt(columns.indexOf(DbGlobals.COL_COUNTRY_TAB_RECIPES)));
                recipe.setType(cursor.getInt(columns.indexOf(DbGlobals.COL_TYPE_TAB_RECIPES)));
                recipe.setPreference(TypeManager.integerToBoolean(
                        cursor.getInt(columns.indexOf(DbGlobals.COL_PREFERENCES_TAB_RECIPES))));
                recipe.setFavorite(TypeManager.integerToBoolean(
                        cursor.getInt(columns.indexOf(DbGlobals.COL_FAVORITE_TAB_RECIPES))));
            }while(cursor.moveToNext());
        }
    }

    public static void setRecipeInfo(Cursor cursor, ArrayList<Recipe> arrayRecipes){
        ArrayList<String> columns = DbGlobals.getRecipesColumns();
        if(cursor.moveToFirst()){
            do{
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(columns.indexOf(DbGlobals.COL_ID_TAB_RECIPES)));
                recipe.setName(cursor.getString(columns.indexOf(DbGlobals.COL_NAME_TAB_RECIPES)));
                recipe.setAuthor(cursor.getString(columns.indexOf(DbGlobals.COL_AUTHOR_TAB_RECIPES)));
                recipe.setServing(cursor.getInt(columns.indexOf(DbGlobals.COL_SERVING_TAB_RECIPES)));
                recipe.setTime(cursor.getString(columns.indexOf(DbGlobals.COL_TIME_TAB_RECIPES)));
                recipe.setDifficulty(cursor.getInt(columns.indexOf(DbGlobals.COL_DIFFICULTY_TAB_RECIPES)));
                recipe.setSpiciness(cursor.getInt(columns.indexOf(DbGlobals.COL_SPICINESS_TAB_RECIPES)));
                recipe.setCountry(cursor.getInt(columns.indexOf(DbGlobals.COL_COUNTRY_TAB_RECIPES)));
                recipe.setType(cursor.getInt(columns.indexOf(DbGlobals.COL_TYPE_TAB_RECIPES)));
                recipe.setPreference(TypeManager.integerToBoolean(
                        cursor.getInt(columns.indexOf(DbGlobals.COL_PREFERENCES_TAB_RECIPES))));
                recipe.setFavorite(TypeManager.integerToBoolean(
                        cursor.getInt(columns.indexOf(DbGlobals.COL_FAVORITE_TAB_RECIPES))));
                arrayRecipes.add(recipe);
            }while(cursor.moveToNext());
        }
    }

    public static void setCategories(Cursor cursor, ArrayList<Category> arrayCategories){
        ArrayList<String> columns = DbGlobals.getIngredientsColumns();
        Category category = new Category();
        category.setIngredients(new ArrayList<Ingredient>());
        category.setName(MainGlobals.STR_EMPTY_OBJ_INIT);
        if(cursor.moveToFirst()){
            do{
                Ingredient ingredient = new Ingredient();
                ingredient.setProduct(new Product());
                ingredient.getProduct().setId(cursor.getInt(columns.indexOf(DbGlobals.COL_IDPROD_TAB_INGREDIENTS)));
                ingredient.setAmount(cursor.getFloat(columns.indexOf(DbGlobals.COL_AMOUNT_TAB_INGREDIENTS)));
                ingredient.setMeasure(cursor.getInt(columns.indexOf(DbGlobals.COL_MEASURE_TAB_INGREDIENTS)));
                String currentName = cursor.getString(columns.indexOf(DbGlobals.COL_CATEGORY_TAB_INGREDIENTS));
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
        ArrayList<String> columns = DbGlobals.getStepsColumns();
        if(cursor.moveToFirst()){
            do{
                Step step = new Step();
                step.setId(cursor.getInt(columns.indexOf(DbGlobals.COL_ID_TAB_STEPS)));
                step.setIdRec(cursor.getInt(columns.indexOf(DbGlobals.COL_IDREC_TAB_STEPS)));
                step.setInstruction(cursor.getString(columns.indexOf(DbGlobals.COL_INSTRUCTION_TAB_STEPS)));
                arraySteps.add(step);
            }while(cursor.moveToNext());
        }
    }

    public static void setAuthors(Cursor cursor, ArrayList<String> arrayAuthors){
        if(cursor.moveToFirst()){
            do{
                String author = cursor.getString(0);
                arrayAuthors.add(author);
            }while(cursor.moveToNext());
        }
    }

    public static void setIds(Cursor cursor, ArrayList<Integer> arrayIds){
        if(cursor.moveToFirst()){
            do{
                Integer id = cursor.getInt(0);
                arrayIds.add(id);
            }while(cursor.moveToNext());
        }
    }

    public static void setProduct(Cursor cursor, Product product){
        ArrayList<String> columns = DbGlobals.getProductsColumns();
        if(cursor.moveToFirst()){
            do{
                product.setId(cursor.getInt(columns.indexOf(DbGlobals.COL_ID_TAB_PRODUCTS)));
                product.setName(cursor.getString(columns.indexOf(DbGlobals.COL_NAME_TAB_PRODUCTS)));
                product.setType(cursor.getInt(columns.indexOf(DbGlobals.COL_TYPE_TAB_PRODUCTS)));
                product.setCarbohydrates(cursor.getFloat(
                        columns.indexOf(DbGlobals.COL_CARBOHYDRATES_TAB_PRODUCTS)));
                product.setProtein(cursor.getFloat(columns.indexOf(DbGlobals.COL_PROTEIN_TAB_PRODUCTS)));
                product.setFat(cursor.getFloat(columns.indexOf(DbGlobals.COL_FAT_TAB_PRODUCTS)));
            }while(cursor.moveToNext());
        }
    }

    public static void setProducts(Cursor cursor, ArrayList<Product> arrayProducts){
        ArrayList<String> columns = DbGlobals.getProductsColumns();
        if(cursor.moveToFirst()){
            do{
                Product product = new Product();
                product.setId(cursor.getInt(columns.indexOf(DbGlobals.COL_ID_TAB_PRODUCTS)));
                product.setName(cursor.getString(columns.indexOf(DbGlobals.COL_NAME_TAB_PRODUCTS)));
                product.setType(cursor.getInt(columns.indexOf(DbGlobals.COL_TYPE_TAB_PRODUCTS)));
                product.setCarbohydrates(cursor.getFloat(
                        columns.indexOf(DbGlobals.COL_CARBOHYDRATES_TAB_PRODUCTS)));
                product.setProtein(cursor.getFloat(columns.indexOf(DbGlobals.COL_PROTEIN_TAB_PRODUCTS)));
                product.setFat(cursor.getFloat(columns.indexOf(DbGlobals.COL_FAT_TAB_PRODUCTS)));
                arrayProducts.add(product);
            }while(cursor.moveToNext());
        }
    }

    public static void setIngredients(Cursor cursor, ArrayList<Ingredient> arrayIngredients){
        ArrayList<String> columns = DbGlobals.getIngredientsColumns();
        if(cursor.moveToFirst()){
            do{
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getInt(columns.indexOf(DbGlobals.COL_ID_TAB_INGREDIENTS)));
                ingredient.setIdRec(cursor.getInt(columns.indexOf(DbGlobals.COL_IDREC_TAB_INGREDIENTS)));
                ingredient.setIdProd(cursor.getInt(columns.indexOf(DbGlobals.COL_IDPROD_TAB_INGREDIENTS)));
                ingredient.setAmount(cursor.getFloat(columns.indexOf(DbGlobals.COL_AMOUNT_TAB_INGREDIENTS)));
                ingredient.setMeasure(cursor.getInt(columns.indexOf(DbGlobals.COL_MEASURE_TAB_INGREDIENTS)));
                ingredient.setCategory(cursor.getString(columns.indexOf(DbGlobals.COL_CATEGORY_TAB_INGREDIENTS)));
                ingredient.setProduct(new Product());
                ingredient.getProduct().setId(cursor.getInt(columns.indexOf(DbGlobals.COL_ID_TAB_INGREDIENTS)));
                arrayIngredients.add(ingredient);
            }while(cursor.moveToNext());
        }
    }

    //
    //getId
    //
    public static int getRecipeId(SQLiteDatabase db){
        int id = MainGlobals.INT_STARTING_VAR_INIT;
        Cursor cursor = db.query(DbGlobals.TAB_RECIPES_DB_MAIN, new String[] {
                        DbGlobals.COL_ID_TAB_RECIPES },
                null, null,null, null, null);
        if(cursor.moveToLast()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public static int getProductId(SQLiteDatabase db){
        int id = MainGlobals.INT_STARTING_VAR_INIT;
        Cursor cursor = db.query(DbGlobals.TAB_PRODUCTS_DB_MAIN, new String[] {
                        DbGlobals.COL_ID_TAB_PRODUCTS },
                null, null,null, null, null);
        if(cursor.moveToLast()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public static boolean isProductUsable(SQLiteDatabase db, int id){
        Cursor cursor = db.query(DbGlobals.TAB_INGREDIENTS_DB_MAIN, new String[] {
                        DbGlobals.COL_ID_TAB_INGREDIENTS },
                DbGlobals.COL_IDPROD_TAB_INGREDIENTS + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        return cursorCount != MainGlobals.INT_STARTING_VAR_INIT;
    }

    public static String getProductName(SQLiteDatabase db, int id){
        String name = MainGlobals.STR_EMPTY_OBJ_INIT;
        Cursor cursor = db.query(DbGlobals.TAB_PRODUCTS_DB_MAIN, new String[] {
                        DbGlobals.COL_NAME_TAB_PRODUCTS },
                DbGlobals.COL_ID_TAB_PRODUCTS + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
        if(cursor.moveToLast()){
            name = cursor.getString(0);
        }
        cursor.close();
        return name;
    }
}
