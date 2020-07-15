package com.fatiner.platehandler.viewmodels.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.repositories.ProductRepository;
import com.fatiner.platehandler.repositories.RecipeRepository;

import java.util.List;

import io.reactivex.Single;

public class HomeViewModel extends AndroidViewModel {

    private RecipeRepository recipeRepository;
    private ProductRepository productRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        productRepository = new ProductRepository(application);
    }

    public Single<List<Recipe>> getRecipes(List<Integer> recentIds) {
        return recipeRepository.getRecipes(recentIds);
    }

    public Single<Integer> getRowCount(boolean isRecipe) {
        if (isRecipe) return recipeRepository.getRowCount();
        else return productRepository.getRowCount();
    }
}
