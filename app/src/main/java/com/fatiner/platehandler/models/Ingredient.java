package com.fatiner.platehandler.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.globals.Globals;

@Entity(tableName = Db.TB_INGREDIENT)
public class Ingredient {

    @ColumnInfo(name = Db.CL_IG_ID)
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = Db.CL_IG_RECIPE_ID)
    private int recipeId;
    @ColumnInfo(name = Db.CL_IG_PRODUCT_ID)
    private int productId;
    @ColumnInfo(name = Db.CL_IG_AMOUNT)
    private float amount;
    @ColumnInfo(name = Db.CL_IG_MEASURE)
    private int measure;
    @ColumnInfo(name = Db.CL_IG_USED)
    private boolean isUsed;
    @Ignore
    private Product product;

    public Ingredient() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getMeasure() {
        return measure;
    }

    public void setMeasure(int measure) {
        this.measure = measure;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public float getTotalKcal(int[] factors) {
        return (getSize(factors) * product.getTotalKcal()) / product.getSize();
    }

    public float getTotalKj(int[] factors) {
        return getTotalKcal(factors) * Globals.FC_KJ;
    }

    private float getFactor(int[] factors) {
        if (measure == Globals.DF_ZERO) return product.getSize();
        return factors[measure];
    }

    private float getSize(int[] factors) {
        return amount * getFactor(factors);
    }
}
