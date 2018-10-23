package com.fatiner.platehandler.managers.database;

import android.content.ContentValues;

import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.globals.DatabaseGlobals;
import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.managers.TypeManager;

public class DbContentManager {

    private DbContentManager(){}

    public static ContentValues getRecipeValues(){
        ContentValues contentValues = new ContentValues();
        Recipe recipe = RecipeDetails.getRecipe();
        contentValues.put(DatabaseGlobals.COL_NAME_TAB_RECIPES, recipe.getName());
        contentValues.put(DatabaseGlobals.COL_AUTHOR_TAB_RECIPES, recipe.getAuthor());
        contentValues.put(DatabaseGlobals.COL_SERVING_TAB_RECIPES, recipe.getServing());
        contentValues.put(DatabaseGlobals.COL_TIME_TAB_RECIPES, recipe.getTime());
        contentValues.put(DatabaseGlobals.COL_DIFFICULTY_TAB_RECIPES, recipe.getDifficulty());
        contentValues.put(DatabaseGlobals.COL_SPICINESS_TAB_RECIPES, recipe.getSpiciness());
        contentValues.put(DatabaseGlobals.COL_COUNTRY_TAB_RECIPES, recipe.getCountry());
        contentValues.put(DatabaseGlobals.COL_TYPE_TAB_RECIPES, recipe.getType());
        contentValues.put(DatabaseGlobals.COL_PREFERENCES_TAB_RECIPES,
                TypeManager.booleanToInteger(recipe.getPreference()));
        contentValues.put(DatabaseGlobals.COL_FAVORITE_TAB_RECIPES,
                TypeManager.booleanToInteger(recipe.getFavorite()));
        return contentValues;
    }

    public static ContentValues getIngredientValues(int id, int idCateg, int idIngred){
        ContentValues contentValues = new ContentValues();
        Category category = RecipeDetails.getRecipe().getCategories().get(idCateg);
        Ingredient ingredient = category.getIngredients().get(idIngred);
        contentValues.put(DatabaseGlobals.COL_IDREC_TAB_INGREDIENTS, id);
        contentValues.put(DatabaseGlobals.COL_IDPROD_TAB_INGREDIENTS, ingredient.getProduct().getId());
        contentValues.put(DatabaseGlobals.COL_AMOUNT_TAB_INGREDIENTS, ingredient.getAmount());
        contentValues.put(DatabaseGlobals.COL_MEASURE_TAB_INGREDIENTS, ingredient.getMeasure());
        contentValues.put(DatabaseGlobals.COL_CATEGORY_TAB_INGREDIENTS, category.getName());
        return contentValues;
    }

    public static ContentValues getStepValues(int id, int idStep){
        ContentValues contentValues = new ContentValues();
        Step step = RecipeDetails.getRecipe().getSteps().get(idStep);
        contentValues.put(DatabaseGlobals.COL_IDREC_TAB_STEPS, id);
        contentValues.put(DatabaseGlobals.COL_INSTRUCTION_TAB_STEPS, step.getInstruction());
        return contentValues;
    }

    public static ContentValues getFavoriteValues(){
        ContentValues contentValues = new ContentValues();
        Recipe recipe = RecipeDetails.getRecipe();
        contentValues.put(DatabaseGlobals.COL_FAVORITE_TAB_RECIPES,
                TypeManager.booleanToInteger(recipe.getFavorite()));
        return contentValues;
    }

    public static ContentValues getProductValues(){
        ContentValues contentValues = new ContentValues();
        Product product = ProductDetails.getProduct();
        contentValues.put(DatabaseGlobals.COL_NAME_TAB_PRODUCTS, product.getName());
        contentValues.put(DatabaseGlobals.COL_TYPE_TAB_PRODUCTS, product.getType());
        contentValues.put(DatabaseGlobals.COL_CARBOHYDRATES_TAB_PRODUCTS,
                product.getCarbohydrates());
        contentValues.put(DatabaseGlobals.COL_PROTEIN_TAB_PRODUCTS, product.getProtein());
        contentValues.put(DatabaseGlobals.COL_FAT_TAB_PRODUCTS, product.getFat());
        return contentValues;
    }
}
