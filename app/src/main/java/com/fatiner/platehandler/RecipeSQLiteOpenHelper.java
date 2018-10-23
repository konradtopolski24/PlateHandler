package com.fatiner.platehandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fatiner.platehandler.globals.DatabaseGlobals;

public class RecipeSQLiteOpenHelper extends SQLiteOpenHelper {

    Context context;

    public RecipeSQLiteOpenHelper(Context context) {
        super(context, DatabaseGlobals.NAME_RECIPES_DB_MAIN, null, DatabaseGlobals.VER_CURRENT_DB_MAIN);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, DatabaseGlobals.VER_0_DB_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int version){
        if(version < DatabaseGlobals.VER_CURRENT_DB_MAIN){
            createRecipesTable(db);
            createIngredientsTable(db);
            createStepsTable(db);
            createProductsTable(db);
        }
    }

    private void createRecipesTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DatabaseGlobals.TAB_RECIPES_DB_MAIN + " (" +
                DatabaseGlobals.COL_ID_TAB_RECIPES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseGlobals.COL_NAME_TAB_RECIPES + " TEXT, " +
                DatabaseGlobals.COL_AUTHOR_TAB_RECIPES + " TEXT, " +
                DatabaseGlobals.COL_SERVING_TAB_RECIPES + " INTEGER, " +
                DatabaseGlobals.COL_TIME_TAB_RECIPES + " TEXT, " +
                DatabaseGlobals.COL_DIFFICULTY_TAB_RECIPES + " INTEGER, " +
                DatabaseGlobals.COL_SPICINESS_TAB_RECIPES + " INTEGER, " +
                DatabaseGlobals.COL_COUNTRY_TAB_RECIPES + " INTEGER, " +
                DatabaseGlobals.COL_TYPE_TAB_RECIPES + " INTEGER, " +
                DatabaseGlobals.COL_PREFERENCES_TAB_RECIPES + " INTEGER, " +
                DatabaseGlobals.COL_FAVORITE_TAB_RECIPES + " INTEGER);";
        db.execSQL(command);
    }

    private void createIngredientsTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DatabaseGlobals.TAB_INGREDIENTS_DB_MAIN + " (" +
                DatabaseGlobals.COL_ID_TAB_INGREDIENTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseGlobals.COL_IDREC_TAB_INGREDIENTS + " INTEGER, " +
                DatabaseGlobals.COL_IDPROD_TAB_INGREDIENTS + " INTEGER, " +
                DatabaseGlobals.COL_AMOUNT_TAB_INGREDIENTS + " REAL, " +
                DatabaseGlobals.COL_MEASURE_TAB_INGREDIENTS + " INTEGER, " +
                DatabaseGlobals.COL_CATEGORY_TAB_INGREDIENTS + " TEXT);";
        db.execSQL(command);
    }

    private void createStepsTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DatabaseGlobals.TAB_STEPS_DB_MAIN + " (" +
                DatabaseGlobals.COL_ID_TAB_STEPS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseGlobals.COL_IDREC_TAB_STEPS + " INTEGER, " +
                DatabaseGlobals.COL_INSTRUCTION_TAB_STEPS + " TEXT);";
        db.execSQL(command);
    }

    private void createProductsTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DatabaseGlobals.TAB_PRODUCTS_DB_MAIN + " (" +
                DatabaseGlobals.COL_ID_TAB_PRODUCTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseGlobals.COL_NAME_TAB_PRODUCTS + " TEXT, " +
                DatabaseGlobals.COL_TYPE_TAB_PRODUCTS + " INTEGER, " +
                DatabaseGlobals.COL_CARBOHYDRATES_TAB_PRODUCTS + " REAL, " +
                DatabaseGlobals.COL_PROTEIN_TAB_PRODUCTS + " REAL, " +
                DatabaseGlobals.COL_FAT_TAB_PRODUCTS + " REAL);";
        db.execSQL(command);
    }
}
