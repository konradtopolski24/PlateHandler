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
        return db.query(DbGlobals.TB_RECIPE, new String[] {
                DbGlobals.CL_RP_ID},
                null, null, null, null, null);
    }

    public static Cursor getRecipeCursor(SQLiteDatabase db, String selection, String orderBy) {
        return db.query(
                DbGlobals.TB_RECIPE,
                TypeManager.arrayListToArray(DbGlobals.getRecipesColumns()),
                selection, null, null, null, orderBy);
    }

    public static Cursor getRecipeCursor(SQLiteDatabase db, int id){
        return db.query(
                DbGlobals.TB_RECIPE,
                TypeManager.arrayListToArray(DbGlobals.getRecipesColumns()),
                DbGlobals.CL_RP_ID + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    public static Cursor getIngredientCursor(SQLiteDatabase db, int id){
        return db.query(
                DbGlobals.TB_INGREDIENT,
                TypeManager.arrayListToArray(DbGlobals.getIngredientsColumns()),
                DbGlobals.CL_IG_RECIPE + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    public static Cursor getIngredientCursor(SQLiteDatabase db, String selection, String orderBy){
        return db.query(
                DbGlobals.TB_INGREDIENT,
                TypeManager.arrayListToArray(DbGlobals.getIngredientsColumns()),
                selection, null, null, null, orderBy);
    }

    public static Cursor getStepCursor(SQLiteDatabase db, int id){
        return db.query(DbGlobals.TB_STEP,
                TypeManager.arrayListToArray(DbGlobals.getStepsColumns()),
                DbGlobals.CL_ST_RECIPE + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    public static Cursor getStepCursor(SQLiteDatabase db, String selection, String orderBy){
        return db.query(DbGlobals.TB_STEP,
                TypeManager.arrayListToArray(DbGlobals.getStepsColumns()),
                selection, null, null, null, orderBy);
    }

    public static Cursor getAuthorsCursor(SQLiteDatabase db){
        return db.query(true, DbGlobals.TB_RECIPE, new String[] {
                        DbGlobals.CL_RP_AUTHOR
                },
                null, null, DbGlobals.CL_RP_AUTHOR,
                null, null, null);
    }

    public static Cursor getProductCursor(SQLiteDatabase db, String selection, String orderBy){
        return db.query(DbGlobals.TB_PRODUCT,
                TypeManager.arrayListToArray(DbGlobals.getProductsColumns()),
                selection, null,null, null, orderBy);
    }

    public static Cursor getProductCursor(SQLiteDatabase db, int id){
        return db.query(DbGlobals.TB_PRODUCT,
                TypeManager.arrayListToArray(DbGlobals.getProductsColumns()),
                DbGlobals.CL_PD_ID + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
    }

    //
    //set
    //
    public static void setRecipe(Cursor cursor, Recipe recipe){
        ArrayList<String> columns = DbGlobals.getRecipesColumns();
        if(cursor.moveToFirst()){
            do{
                recipe.setId(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_ID)));
                recipe.setName(cursor.getString(columns.indexOf(DbGlobals.CL_RP_NAME)));
                recipe.setAuthor(cursor.getString(columns.indexOf(DbGlobals.CL_RP_AUTHOR)));
                recipe.setServing(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_SERVING)));
                recipe.setTime(cursor.getString(columns.indexOf(DbGlobals.CL_RP_TIME)));
                recipe.setDifficulty(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_DIFFICULTY)));
                recipe.setSpiciness(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_SPICINESS)));
                recipe.setCountry(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_COUNTRY)));
                recipe.setType(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_TYPE)));
                recipe.setPreference(TypeManager.integerToBoolean(
                        cursor.getInt(columns.indexOf(DbGlobals.CL_RP_PREFERENCE))));
                recipe.setFavorite(TypeManager.integerToBoolean(
                        cursor.getInt(columns.indexOf(DbGlobals.CL_RP_FAVORITE))));
            }while(cursor.moveToNext());
        }
    }

    public static void setRecipeInfo(Cursor cursor, ArrayList<Recipe> arrayRecipes){
        ArrayList<String> columns = DbGlobals.getRecipesColumns();
        if(cursor.moveToFirst()){
            do{
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_ID)));
                recipe.setName(cursor.getString(columns.indexOf(DbGlobals.CL_RP_NAME)));
                recipe.setAuthor(cursor.getString(columns.indexOf(DbGlobals.CL_RP_AUTHOR)));
                recipe.setServing(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_SERVING)));
                recipe.setTime(cursor.getString(columns.indexOf(DbGlobals.CL_RP_TIME)));
                recipe.setDifficulty(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_DIFFICULTY)));
                recipe.setSpiciness(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_SPICINESS)));
                recipe.setCountry(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_COUNTRY)));
                recipe.setType(cursor.getInt(columns.indexOf(DbGlobals.CL_RP_TYPE)));
                recipe.setPreference(TypeManager.integerToBoolean(
                        cursor.getInt(columns.indexOf(DbGlobals.CL_RP_PREFERENCE))));
                recipe.setFavorite(TypeManager.integerToBoolean(
                        cursor.getInt(columns.indexOf(DbGlobals.CL_RP_FAVORITE))));
                arrayRecipes.add(recipe);
            }while(cursor.moveToNext());
        }
    }

    public static void setCategories(Cursor cursor, ArrayList<Category> arrayCategories){
        ArrayList<String> columns = DbGlobals.getIngredientsColumns();
        Category category = new Category();
        category.setIngredients(new ArrayList<Ingredient>());
        category.setName(MainGlobals.SN_EMPTY);
        if(cursor.moveToFirst()){
            do{
                Ingredient ingredient = new Ingredient();
                ingredient.setProduct(new Product());
                ingredient.getProduct().setId(cursor.getInt(columns.indexOf(DbGlobals.CL_IG_PRODUCT)));
                ingredient.setAmount(cursor.getFloat(columns.indexOf(DbGlobals.CL_IG_AMOUNT)));
                ingredient.setMeasure(cursor.getInt(columns.indexOf(DbGlobals.CL_IG_MEASURE)));
                String currentName = cursor.getString(columns.indexOf(DbGlobals.CL_IG_CATEGORY));
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
                step.setId(cursor.getInt(columns.indexOf(DbGlobals.CL_ST_ID)));
                step.setIdRec(cursor.getInt(columns.indexOf(DbGlobals.CL_ST_RECIPE)));
                step.setInstruction(cursor.getString(columns.indexOf(DbGlobals.CL_ST_INSTRUCTION)));
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
                product.setId(cursor.getInt(columns.indexOf(DbGlobals.CL_PD_ID)));
                product.setName(cursor.getString(columns.indexOf(DbGlobals.CL_PD_NAME)));
                product.setType(cursor.getInt(columns.indexOf(DbGlobals.CL_PD_TYPE)));
                product.setCarbohydrates(cursor.getFloat(
                        columns.indexOf(DbGlobals.CL_PD_CARBOHYDRATES)));
                product.setProtein(cursor.getFloat(columns.indexOf(DbGlobals.CL_PD_PROTEIN)));
                product.setFat(cursor.getFloat(columns.indexOf(DbGlobals.CL_PD_FAT)));
            }while(cursor.moveToNext());
        }
    }

    public static void setProducts(Cursor cursor, ArrayList<Product> arrayProducts){
        ArrayList<String> columns = DbGlobals.getProductsColumns();
        if(cursor.moveToFirst()){
            do{
                Product product = new Product();
                product.setId(cursor.getInt(columns.indexOf(DbGlobals.CL_PD_ID)));
                product.setName(cursor.getString(columns.indexOf(DbGlobals.CL_PD_NAME)));
                product.setType(cursor.getInt(columns.indexOf(DbGlobals.CL_PD_TYPE)));
                product.setCarbohydrates(cursor.getFloat(
                        columns.indexOf(DbGlobals.CL_PD_CARBOHYDRATES)));
                product.setProtein(cursor.getFloat(columns.indexOf(DbGlobals.CL_PD_PROTEIN)));
                product.setFat(cursor.getFloat(columns.indexOf(DbGlobals.CL_PD_FAT)));
                arrayProducts.add(product);
            }while(cursor.moveToNext());
        }
    }

    public static void setIngredients(Cursor cursor, ArrayList<Ingredient> arrayIngredients){
        ArrayList<String> columns = DbGlobals.getIngredientsColumns();
        if(cursor.moveToFirst()){
            do{
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getInt(columns.indexOf(DbGlobals.CL_IG_ID)));
                ingredient.setIdRec(cursor.getInt(columns.indexOf(DbGlobals.CL_IG_RECIPE)));
                ingredient.setIdProd(cursor.getInt(columns.indexOf(DbGlobals.CL_IG_PRODUCT)));
                ingredient.setAmount(cursor.getFloat(columns.indexOf(DbGlobals.CL_IG_AMOUNT)));
                ingredient.setMeasure(cursor.getInt(columns.indexOf(DbGlobals.CL_IG_MEASURE)));
                ingredient.setCategory(cursor.getString(columns.indexOf(DbGlobals.CL_IG_CATEGORY)));
                ingredient.setProduct(new Product());
                ingredient.getProduct().setId(cursor.getInt(columns.indexOf(DbGlobals.CL_IG_ID)));
                arrayIngredients.add(ingredient);
            }while(cursor.moveToNext());
        }
    }

    //
    //getId
    //
    public static int getRecipeId(SQLiteDatabase db){
        int id = MainGlobals.DF_ZERO;
        Cursor cursor = db.query(DbGlobals.TB_RECIPE, new String[] {
                        DbGlobals.CL_RP_ID},
                null, null,null, null, null);
        if(cursor.moveToLast()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public static int getProductId(SQLiteDatabase db){
        int id = MainGlobals.DF_ZERO;
        Cursor cursor = db.query(DbGlobals.TB_PRODUCT, new String[] {
                        DbGlobals.CL_PD_ID},
                null, null,null, null, null);
        if(cursor.moveToLast()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public static boolean isProductUsable(SQLiteDatabase db, int id){
        Cursor cursor = db.query(DbGlobals.TB_INGREDIENT, new String[] {
                        DbGlobals.CL_IG_ID},
                DbGlobals.CL_IG_PRODUCT + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        return cursorCount != MainGlobals.DF_ZERO;
    }

    public static String getProductName(SQLiteDatabase db, int id){
        String name = MainGlobals.SN_EMPTY;
        Cursor cursor = db.query(DbGlobals.TB_PRODUCT, new String[] {
                        DbGlobals.CL_PD_NAME},
                DbGlobals.CL_PD_ID + " = ?",
                new String[] {Integer.toString(id)}, null, null, null);
        if(cursor.moveToLast()){
            name = cursor.getString(0);
        }
        cursor.close();
        return name;
    }
}
