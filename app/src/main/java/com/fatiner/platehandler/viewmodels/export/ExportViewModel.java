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

import io.reactivex.Single;

public class ExportViewModel extends AndroidViewModel {

    private RecipeRepository recipeRepository;
    private ProductRepository productRepository;
    private IngredientRepository ingredientRepository;
    private StepRepository stepRepository;

    public ExportViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        productRepository = new ProductRepository(application);
        ingredientRepository = new IngredientRepository(application);
        stepRepository = new StepRepository(application);
    }

    public Single<List<Recipe>> getAllRecipes() {
        return recipeRepository.getAllRecipes();
    }

    public Single<List<Product>> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Single<List<Ingredient>> getAllIngredients() {
        return ingredientRepository.getAllIngredients();
    }

    public Single<List<Step>> getAllSteps() {
        return stepRepository.getAllSteps();
    }
}
