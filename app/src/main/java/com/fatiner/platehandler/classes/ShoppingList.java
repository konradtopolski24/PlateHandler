package com.fatiner.platehandler.classes;

import java.util.ArrayList;

public class ShoppingList {

    private String date;
    private ArrayList<ShoppingItem> shoppingItems;

    public ShoppingList() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(ArrayList<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }
}
