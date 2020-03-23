package com.fatiner.platehandler.details;

import com.fatiner.platehandler.classes.ShoppingItem;
import com.fatiner.platehandler.classes.ShoppingList;

import java.util.ArrayList;

public final class ShoppingListDetails {

    private static ShoppingList shoppingList;

    private ShoppingListDetails() {
        resetDetails();
    }

    public static ShoppingList getShoppingList() {
        return shoppingList;
    }

    public static void resetDetails() {
        shoppingList = new ShoppingList();
        resetShoppingItems();
    }

    private static void resetShoppingItems() {
        ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();
        shoppingList.setShoppingItems(shoppingItems);
    }
}
