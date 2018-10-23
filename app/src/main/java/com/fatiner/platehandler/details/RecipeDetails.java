package com.fatiner.platehandler.details;

import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.classes.Step;

import java.util.ArrayList;

public final class RecipeDetails {

    private static Recipe recipe;
    private static ArrayList<Ingredient> tempIngredients;

    private RecipeDetails(){
        resetRecipeDetails();
    }

    public static void resetRecipeDetails(){
        recipe = new Recipe();
        resetName();
        resetAuthor();
        resetServing();
        resetTime();
        resetFavorite();
        resetCategories();
        resetSteps();
    }

    private static void resetName(){
        recipe.setName(MainGlobals.STR_EMPTY_OBJ_INIT);
    }

    private static void resetAuthor(){
        recipe.setAuthor(MainGlobals.STR_EMPTY_OBJ_INIT);
    }

    private static void resetServing(){
        recipe.setServing(MainGlobals.DEF_SERVING_DET_RECIPE);
    }

    private static void resetTime(){
        recipe.setTime(MainGlobals.DEF_TIME_DET_RECIPE);
    }

    private static void resetFavorite(){
        recipe.setFavorite(false);
    }

    private static void resetCategories(){
        ArrayList<Category> categories = new ArrayList<>();
        recipe.setCategories(categories);
    }

    private static void resetSteps(){
        ArrayList<Step> steps = new ArrayList<>();
        recipe.setSteps(steps);
    }

    public static Recipe getRecipe() {
        return recipe;
    }

    public static boolean isRecipeCorrect(){
        return isRecipeNameCorrect() && isRecipeAuthorCorrect() && isRecipeTimeCorrect()
                && areRecipeCategoriesCorrect() && areRecipeStepsCorrect();
    }

    private static boolean isRecipeNameCorrect(){
        String name = recipe.getName();
        return !name.equals(MainGlobals.STR_EMPTY_OBJ_INIT);
    }

    private static boolean isRecipeAuthorCorrect(){
        String author = recipe.getAuthor();
        return !author.equals(MainGlobals.STR_EMPTY_OBJ_INIT);
    }

    private static boolean isRecipeTimeCorrect(){
        String time = recipe.getTime();
        return !time.equals(MainGlobals.DEF_TIME_DET_RECIPE);
    }

    private static boolean areRecipeCategoriesCorrect(){
        ArrayList<Category> categories = recipe.getCategories();
        return !categories.isEmpty();
    }

    private static boolean areRecipeStepsCorrect() {
        ArrayList<Step> steps = recipe.getSteps();
        return !steps.isEmpty();
    }

    public static void resetTempIngredients(){
        tempIngredients = new ArrayList<>();
    }

    public static ArrayList<Ingredient> getTempIngredients() {
        return tempIngredients;
    }
}
