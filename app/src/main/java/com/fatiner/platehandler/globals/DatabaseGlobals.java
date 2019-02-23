package com.fatiner.platehandler.globals;

import java.util.ArrayList;

public class DatabaseGlobals {
    public static final String NAME_RECIPES_DB_MAIN = "Recipes DatabaseGlobals";
    public static final int VER_CURRENT_DB_MAIN = 1;
    public static final int VER_0_DB_MAIN = 0;

    //Table RECIPES
    public static final String TAB_RECIPES_DB_MAIN = "RECIPES";

    public static final String COL_ID_TAB_RECIPES = "ID";
    public static final String COL_NAME_TAB_RECIPES = "NAME";
    public static final String COL_AUTHOR_TAB_RECIPES = "AUTHOR";
    public static final String COL_SERVING_TAB_RECIPES = "SERVING";
    public static final String COL_TIME_TAB_RECIPES = "TIME";
    public static final String COL_DIFFICULTY_TAB_RECIPES = "DIFFICULTY";
    public static final String COL_SPICINESS_TAB_RECIPES = "SPICINESS";
    public static final String COL_COUNTRY_TAB_RECIPES = "COUNTRY";
    public static final String COL_TYPE_TAB_RECIPES = "TYPE";
    public static final String COL_PREFERENCES_TAB_RECIPES = "PREFERENCES";
    public static final String COL_FAVORITE_TAB_RECIPES = "FAVORITE";

    public static ArrayList<String> getRecipesColumns(){
        ArrayList<String> columns = new ArrayList<>();
        columns.add(COL_ID_TAB_RECIPES);
        columns.add(COL_NAME_TAB_RECIPES);
        columns.add(COL_AUTHOR_TAB_RECIPES);
        columns.add(COL_SERVING_TAB_RECIPES);
        columns.add(COL_TIME_TAB_RECIPES);
        columns.add(COL_DIFFICULTY_TAB_RECIPES);
        columns.add(COL_SPICINESS_TAB_RECIPES);
        columns.add(COL_COUNTRY_TAB_RECIPES);
        columns.add(COL_TYPE_TAB_RECIPES);
        columns.add(COL_PREFERENCES_TAB_RECIPES);
        columns.add(COL_FAVORITE_TAB_RECIPES);
        return columns;
    }

    //Table INGREDIENTS
    public static final String TAB_INGREDIENTS_DB_MAIN = "INGREDIENTS";

    public static final String COL_ID_TAB_INGREDIENTS = "ID";
    public static final String COL_IDREC_TAB_INGREDIENTS = "IDREC";
    public static final String COL_IDPROD_TAB_INGREDIENTS = "IDPROD";
    public static final String COL_AMOUNT_TAB_INGREDIENTS = "AMOUNT";
    public static final String COL_MEASURE_TAB_INGREDIENTS = "MEASURE";
    public static final String COL_CATEGORY_TAB_INGREDIENTS = "CATEGORY";

    //Table STEPS
    public static final String TAB_STEPS_DB_MAIN = "STEPS";

    public static final String COL_ID_TAB_STEPS = "ID";
    public static final String COL_IDREC_TAB_STEPS = "IDREC";
    public static final String COL_INSTRUCTION_TAB_STEPS = "INSTRUCTION";

    //Table PRODUCTS
    public static final String TAB_PRODUCTS_DB_MAIN = "PRODUCTS";

    public static final String COL_ID_TAB_PRODUCTS = "ID";
    public static final String COL_NAME_TAB_PRODUCTS = "NAME";
    public static final String COL_TYPE_TAB_PRODUCTS = "TYPE";
    public static final String COL_CARBOHYDRATES_TAB_PRODUCTS = "CARBOHYDRATES";
    public static final String COL_PROTEIN_TAB_PRODUCTS = "PROTEIN";
    public static final String COL_FAT_TAB_PRODUCTS = "FAT";
}
