package com.fatiner.platehandler.viewmodels.recipe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.RecipeComplete;
import com.fatiner.platehandler.repositories.IngredientRepository;
import com.fatiner.platehandler.repositories.ProductRepository;
import com.fatiner.platehandler.repositories.RecipeRepository;
import com.fatiner.platehandler.repositories.StepRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class RecipeShowViewModel extends AndroidViewModel {

    private RecipeRepository recipeRepository;
    private ProductRepository productRepository;
    private IngredientRepository ingredientRepository;
    private StepRepository stepRepository;

    public RecipeShowViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        productRepository = new ProductRepository(application);
        ingredientRepository = new IngredientRepository(application);
        stepRepository = new StepRepository(application);
    }

    public Single<RecipeComplete> getCompleteRecipe(int id) {
        return recipeRepository.getCompleteRecipe(id);
    }

    public Single<List<Product>> getProducts(List<Integer> productIds) {
        return productRepository.getProducts(productIds);
    }

    public Completable updateFavorite(int id, boolean checked) {
        return Completable.fromAction(() -> recipeRepository.updateFavorite(id, checked));
    }

    public Completable updateIsUsed(int id, boolean checked) {
        return Completable.fromAction(() -> ingredientRepository.updateIsUsed(id, checked));
    }

    public Completable updateIsDone(int id, boolean checked) {
        return Completable.fromAction(() -> stepRepository.updateIsDone(id, checked));
    }

    public Completable deleteRecipe(Recipe recipe) {
        return Completable.fromAction(() -> {
            recipeRepository.deleteRecipe(recipe);
            ingredientRepository.deleteIngredients(recipe.getIngredients());
            stepRepository.deleteSteps(recipe.getSteps());
        });
    }
}
