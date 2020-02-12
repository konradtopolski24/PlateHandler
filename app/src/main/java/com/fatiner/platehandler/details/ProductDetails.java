package com.fatiner.platehandler.details;

import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.Product;

public final class ProductDetails {

    private static Product product;

    private ProductDetails(){
        resetProductDetails();
    }

    public static void resetProductDetails(){
        product = new Product();
    }

    public static Product getProduct(){
        return product;
    }

    public static boolean isProductCorrect(){
        return isProductNameCorrect();
    }

    private static boolean isProductNameCorrect(){
        String name = product.getName();
        return !name.equals(MainGlobals.SN_EMPTY);
    }
}
