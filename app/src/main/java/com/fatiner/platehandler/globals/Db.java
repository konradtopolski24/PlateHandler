package com.fatiner.platehandler.globals;

import java.util.ArrayList;
import java.util.List;

public class Db {
    public static final String DB_NAME = "PLATE HANDLER";
    public static final int DB_CURRENT = 1;

    public static final String TB_RECIPE = "RECIPES";

    public static final String CL_RP_ID = "ID";
    public static final String CL_RP_NAME = "NAME";
    public static final String CL_RP_AUTHOR = "AUTHOR";
    public static final String CL_RP_SERVING = "SERVING";
    public static final String CL_RP_TIME = "TIME";
    public static final String CL_RP_DIFFICULTY = "DIFFICULTY";
    public static final String CL_RP_SPICINESS = "SPICINESS";
    public static final String CL_RP_COUNTRY = "COUNTRY";
    public static final String CL_RP_TYPE = "TYPE";
    public static final String CL_RP_PREFERENCE = "PREFERENCE";
    public static final String CL_RP_FAVORITE = "FAVORITE";

    public static List<String> getRecipeColumns() {
        List<String> columns = new ArrayList<>();
        columns.add(CL_RP_ID);
        columns.add(CL_RP_NAME);
        columns.add(CL_RP_AUTHOR);
        columns.add(CL_RP_SERVING);
        columns.add(CL_RP_TIME);
        columns.add(CL_RP_DIFFICULTY);
        columns.add(CL_RP_SPICINESS);
        columns.add(CL_RP_COUNTRY);
        columns.add(CL_RP_TYPE);
        columns.add(CL_RP_PREFERENCE);
        columns.add(CL_RP_FAVORITE);
        return columns;
    }

    public static final String TB_INGREDIENT = "INGREDIENTS";

    public static final String CL_IG_ID = "ID";
    public static final String CL_IG_RECIPE_ID = "RECIPE_ID";
    public static final String CL_IG_PRODUCT_ID = "PRODUCT_ID";
    public static final String CL_IG_AMOUNT = "AMOUNT";
    public static final String CL_IG_MEASURE = "MEASURE";
    public static final String CL_IG_USED = "USED";

    public static List<String> getIngredientColumns() {
        List<String> columns = new ArrayList<>();
        columns.add(CL_IG_ID);
        columns.add(CL_IG_RECIPE_ID);
        columns.add(CL_IG_PRODUCT_ID);
        columns.add(CL_IG_AMOUNT);
        columns.add(CL_IG_MEASURE);
        columns.add(CL_IG_USED);
        return columns;
    }

    public static final String TB_STEP = "STEPS";

    public static final String CL_ST_ID = "ID";
    public static final String CL_ST_RECIPE_ID = "RECIPE_ID";
    public static final String CL_ST_CONTENT = "CONTENT";
    public static final String CL_ST_DONE = "DONE";

    public static List<String> getStepColumns() {
        List<String> columns = new ArrayList<>();
        columns.add(CL_ST_ID);
        columns.add(CL_ST_RECIPE_ID);
        columns.add(CL_ST_CONTENT);
        columns.add(CL_ST_DONE);
        return columns;
    }

    public static final String TB_PRODUCT = "PRODUCTS";

    public static final String CL_PD_ID = "ID";
    public static final String CL_PD_NAME = "NAME";
    public static final String CL_PD_TYPE = "TYPE";
    public static final String CL_PD_SIZE = "SIZE";
    public static final String CL_PD_CARBOHYDRATES = "CARBOHYDRATES";
    public static final String CL_PD_PROTEINS = "PROTEINS";
    public static final String CL_PD_FATS = "FATS";

    public static List<String> getProductColumns() {
        List<String> columns = new ArrayList<>();
        columns.add(CL_PD_ID);
        columns.add(CL_PD_NAME);
        columns.add(CL_PD_TYPE);
        columns.add(CL_PD_SIZE);
        columns.add(CL_PD_CARBOHYDRATES);
        columns.add(CL_PD_PROTEINS);
        columns.add(CL_PD_FATS);
        return columns;
    }
}