package com.fatiner.platehandler.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.models.Product;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ProductDAO {

    @Insert
    Single<Long> addProduct(Product product);

    @Insert
    void addProducts(List<Product> products);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @RawQuery
    Single<List<Product>> getProducts(SupportSQLiteQuery query);

    @Query("SELECT * FROM " + Db.TB_PRODUCT + " WHERE " + Db.CL_PD_ID + "==:id")
    Single<Product> getProduct(int id);

    @Query("SELECT * FROM " + Db.TB_PRODUCT + " WHERE " + Db.CL_PD_ID + " IN (:ids)")
    Single<List<Product>> getProducts(List<Integer> ids);

    @Query("SELECT COUNT( " + Db.CL_PD_ID + ") FROM " + Db.TB_PRODUCT)
    Single<Integer> getRowCount();

    @Query("SELECT * FROM " + Db.TB_PRODUCT)
    Single<List<Product>> getAllProducts();

    @Query("DELETE FROM " + Db.TB_PRODUCT)
    void deleteAllProducts();
}
