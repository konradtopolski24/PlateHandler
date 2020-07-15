package com.fatiner.platehandler.viewmodels.recipe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.fatiner.platehandler.models.IngredientComplete;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.repositories.IngredientRepository;
import com.fatiner.platehandler.repositories.RecipeRepository;

import java.util.List;

import io.reactivex.Single;

public class RecipeChooseViewModel extends AndroidViewModel {

    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;

    public RecipeChooseViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        ingredientRepository = new IngredientRepository(application);
    }

    public Single<List<Recipe>> getRecipes(SimpleSQLiteQuery query) {
        return recipeRepository.getRecipes(query);
    }

    public Single<List<IngredientComplete>> getCompleteIngredients(int id) {
        return ingredientRepository.getCompleteIngredients(id);
    }
}
