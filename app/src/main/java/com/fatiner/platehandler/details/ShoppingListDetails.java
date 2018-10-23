package com.fatiner.platehandler.details;

import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.ShoppingItem;
import com.fatiner.platehandler.classes.ShoppingList;

import java.util.ArrayList;

public final class ShoppingListDetails {

    private static ShoppingList shoppingList;

    private ShoppingListDetails(){
        resetShoppingListDetails();
    }

    public static void resetShoppingListDetails(){
        shoppingList = new ShoppingList();
        resetShoppingItems();
    }

    private static void resetShoppingItems(){
        ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();
        shoppingList.setShoppingItems(shoppingItems);
    }

    public static ShoppingList getShoppingList(){
        return shoppingList;
    }

    public static boolean isShoppingListCorrect(){
        return areShoppingItemsCorrect();
    }

    private static boolean areShoppingItemsCorrect(){
        return areShoppingItemsNotEmpty() && areShoppingItemsNamesNotEmpty();
    }

    private static boolean areShoppingItemsNotEmpty(){
        ArrayList<ShoppingItem> shoppingItems = shoppingList.getShoppingItems();
        return !shoppingItems.isEmpty();
    }

    private static boolean areShoppingItemsNamesNotEmpty(){
        boolean areNotEmpty = true;
        ArrayList<ShoppingItem> shoppingItems = shoppingList.getShoppingItems();
        for(ShoppingItem shoppingItem : shoppingItems){
            if(shoppingItem.getName().equals(MainGlobals.STR_EMPTY_OBJ_INIT)){
                areNotEmpty = false;
                break;
            }
        }
        return areNotEmpty;
    }
}
