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
        resetDetails();
    }

    public static Recipe getRecipe() {
        return recipe;
    }

    public static void resetDetails() {
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
}
