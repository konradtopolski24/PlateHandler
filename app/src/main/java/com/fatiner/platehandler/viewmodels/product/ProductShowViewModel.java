package com.fatiner.platehandler.viewmodels.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.repositories.IngredientRepository;
import com.fatiner.platehandler.repositories.ProductRepository;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ProductShowViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private IngredientRepository ingredientRepository;

    public ProductShowViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        ingredientRepository = new IngredientRepository(application);
    }

    public Single<Product> getProduct(int id) {
        return productRepository.getProduct(id);
    }

    public Completable deleteProduct(Product product) {
        return Completable.fromAction(() -> productRepository.deleteProduct(product));
    }

    public Single<Integer> getRowCount(int id) {
        return ingredientRepository.getRowCount(id);
    }
}
