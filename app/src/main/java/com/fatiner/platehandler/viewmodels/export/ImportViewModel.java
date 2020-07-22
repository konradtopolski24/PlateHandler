package com.fatiner.platehandler.viewmodels.export;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.Step;
import com.fatiner.platehandler.repositories.IngredientRepository;
import com.fatiner.platehandler.repositories.ProductRepository;
import com.fatiner.platehandler.repositories.RecipeRepository;
import com.fatiner.platehandler.repositories.StepRepository;

import java.util.List;

import io.reactivex.Completable;

public class ImportViewModel extends AndroidViewModel {

    private RecipeRepository recipeRepository;
    private ProductRepository productRepository;
    private IngredientRepository ingredientRepository;
    private StepRepository stepRepository;

    public ImportViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        productRepository = new ProductRepository(application);
        ingredientRepository = new IngredientRepository(application);
        stepRepository = new StepRepository(application);
    }

    public Completable importDatabase(List<Recipe> recipes, List<Product> products,
                                      List<Ingredient> ingredients, List<Step> steps) {
        return Completable.fromAction(() -> {
            recipeRepository.deleteAllRecipes();
            productRepository.deleteAllProducts();
            ingredientRepository.deleteAllIngredients();
            stepRepository.deleteAllSteps();
            recipeRepository.addRecipes(recipes);
            productRepository.addProducts(products);
            ingredientRepository.addIngredients(ingredients);
            stepRepository.addSteps(steps);
        });
    }
}
