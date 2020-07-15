package com.fatiner.platehandler.repositories;

import android.app.Application;

import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.interfaces.ProductDAO;
import com.fatiner.platehandler.models.Product;

import java.util.List;

import io.reactivex.Single;

public class ProductRepository {

    private ProductDAO productDAO;

    public ProductRepository(Application application) {
        PlateHandlerDatabase db = PlateHandlerDatabase.getInstance(application);
        productDAO = db.getProductDAO();
    }

    public Single<Long> addProduct(Product product) {
        return productDAO.addProduct(product);
    }

    public Single<List<Product>> getProducts(SimpleSQLiteQuery query) {
        return productDAO.getProducts(query);
    }

    public void addProducts(List<Product> products) {
        productDAO.addProducts(products);
    }

    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }

    public void deleteProduct(Product product) {
        productDAO.deleteProduct(product);
    }

    public Single<List<Product>> getProducts(SupportSQLiteQuery query) {
        return productDAO.getProducts(query);
    }

    public Single<Product> getProduct(int id) {
        return productDAO.getProduct(id);
    }

    public Single<List<Product>> getProducts(List<Integer> ids) {
        return productDAO.getProducts(ids);
    }

    public Single<Integer> getRowCount() {
        return productDAO.getRowCount();
    }

    public Single<List<Product>> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public void deleteAllProducts() {
        productDAO.deleteAllProducts();
    }
}
