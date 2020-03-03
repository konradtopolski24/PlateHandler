package com.fatiner.platehandler.details;

import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.Step;

import java.util.ArrayList;
import java.util.List;

public final class RecipeDetails {

    private static Recipe recipe;

    private RecipeDetails() {
        resetRecipeDetails();
    }

    public static void resetRecipeDetails() {
        recipe = new Recipe();
        resetName();
        resetAuthor();
        resetServing();
        resetTime();
        resetFavorite();
        resetIngredients();
        resetSteps();
    }

    private static void resetName() {
        recipe.setName(Globals.SN_EMPTY);
    }

    private static void resetAuthor() {
        recipe.setAuthor(Globals.SN_EMPTY);
    }

    private static void resetServing() {
        recipe.setServing(Globals.SV_MIN);
    }

    private static void resetTime() {
        recipe.setTime(Globals.TM_DEFAULT);
    }

    private static void resetFavorite() {
        recipe.setFavorite(false);
    }

    private static void resetIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        recipe.setIngredients(ingredients);
    }

    private static void resetSteps() {
        List<Step> steps = new ArrayList<>();
        recipe.setSteps(steps);
    }

    public static Recipe getRecipe() {
        return recipe;
    }

    public static boolean isRecipeCorrect() {
        return isRecipeNameCorrect() && isRecipeAuthorCorrect() && isRecipeTimeCorrect()
                && areRecipeIngredientsCorrect() && areRecipeStepsCorrect();
    }

    private static boolean isRecipeNameCorrect() {
        String name = recipe.getName();
        return !name.equals(Globals.SN_EMPTY);
    }

    private static boolean isRecipeAuthorCorrect() {
        String author = recipe.getAuthor();
        return !author.equals(Globals.SN_EMPTY);
    }

    private static boolean isRecipeTimeCorrect() {
        String time = recipe.getTime();
        return !time.equals(Globals.TM_DEFAULT);
    }

    private static boolean areRecipeIngredientsCorrect() {
        List<Ingredient> ingredients = recipe.getIngredients();
        return !ingredients.isEmpty();
    }
    
    private static boolean areRecipeStepsCorrect() {
        List<Step> steps = recipe.getSteps();
        return !steps.isEmpty();
    }
}
