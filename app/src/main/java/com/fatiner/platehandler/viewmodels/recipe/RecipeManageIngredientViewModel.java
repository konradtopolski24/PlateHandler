package com.fatiner.platehandler.viewmodels.recipe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.repositories.ProductRepository;

import java.util.List;

import io.reactivex.Single;

public class RecipeManageIngredientViewModel extends AndroidViewModel {

    private ProductRepository productRepository;

    public RecipeManageIngredientViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
    }

    public Single<List<Product>> getProducts(SimpleSQLiteQuery query) {
        return productRepository.getProducts(query);
    }
}
