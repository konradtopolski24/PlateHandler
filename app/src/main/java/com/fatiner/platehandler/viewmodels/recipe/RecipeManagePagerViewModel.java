package com.fatiner.platehandler.viewmodels.recipe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.repositories.IngredientRepository;
import com.fatiner.platehandler.repositories.RecipeRepository;
import com.fatiner.platehandler.repositories.StepRepository;

import io.reactivex.Completable;
import io.reactivex.Single;

public class RecipeManagePagerViewModel extends AndroidViewModel {

    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;
    private StepRepository stepRepository;

    public RecipeManagePagerViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        ingredientRepository = new IngredientRepository(application);
        stepRepository = new StepRepository(application);
    }

    public Single<Long> addRecipe(Recipe recipe) {
        return recipeRepository.addRecipe(recipe);
    }

    public Completable addRest(Recipe recipe) {
        return Completable.fromAction(() -> {
            ingredientRepository.addIngredients(recipe.getIngredients());
            stepRepository.addSteps(recipe.getSteps());
        });
    }

    public Completable updateRecipe(Recipe recipe) {
        return Completable.fromAction(() -> {
            recipeRepository.updateRecipe(recipe);
            ingredientRepository.deleteIngredients(recipe.getId());
            stepRepository.deleteSteps(recipe.getId());
            ingredientRepository.addIngredients(recipe.getIngredients());
            stepRepository.addSteps(recipe.getSteps());
        });
    }
}
