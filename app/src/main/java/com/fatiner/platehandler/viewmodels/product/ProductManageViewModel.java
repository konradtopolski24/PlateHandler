package com.fatiner.platehandler.viewmodels.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.repositories.ProductRepository;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ProductManageViewModel extends AndroidViewModel {

    private ProductRepository productRepository;

    public ProductManageViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
    }

    public Single<Long> addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    public Completable updateProduct(Product product) {
        return Completable.fromAction(() -> productRepository.updateProduct(product));
    }
}
