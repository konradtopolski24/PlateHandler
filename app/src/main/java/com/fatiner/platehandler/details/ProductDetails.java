package com.fatiner.platehandler.details;

import com.fatiner.platehandler.models.Product;

public final class ProductDetails {

    private static Product product;

    private ProductDetails() {
        resetDetails();
    }

    public static Product getProduct() {
        return product;
    }
    
    public static void resetDetails() {
        product = new Product();
    }
}
