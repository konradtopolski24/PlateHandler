package com.fatiner.platehandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fatiner.platehandler.globals.DbGlobals;

public class RecipeSQLiteOpenHelper extends SQLiteOpenHelper {

    private Context context;

    public RecipeSQLiteOpenHelper(Context context) {
        super(context, DbGlobals.NAME_RECIPES_DB_MAIN, null, DbGlobals.VER_CURRENT_DB_MAIN);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, DbGlobals.VER_0_DB_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int version){
        if(version < DbGlobals.VER_CURRENT_DB_MAIN){
            createRecipesTable(db);
            createIngredientsTable(db);
            createStepsTable(db);
            createProductsTable(db);
        }
    }

    private void createRecipesTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DbGlobals.TAB_RECIPES_DB_MAIN + " (" +
                DbGlobals.COL_ID_TAB_RECIPES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbGlobals.COL_NAME_TAB_RECIPES + " TEXT, " +
                DbGlobals.COL_AUTHOR_TAB_RECIPES + " TEXT, " +
                DbGlobals.COL_SERVING_TAB_RECIPES + " INTEGER, " +
                DbGlobals.COL_TIME_TAB_RECIPES + " TEXT, " +
                DbGlobals.COL_DIFFICULTY_TAB_RECIPES + " INTEGER, " +
                DbGlobals.COL_SPICINESS_TAB_RECIPES + " INTEGER, " +
                DbGlobals.COL_COUNTRY_TAB_RECIPES + " INTEGER, " +
                DbGlobals.COL_TYPE_TAB_RECIPES + " INTEGER, " +
                DbGlobals.COL_PREFERENCES_TAB_RECIPES + " INTEGER, " +
                DbGlobals.COL_FAVORITE_TAB_RECIPES + " INTEGER);";
        db.execSQL(command);
    }

    private void createIngredientsTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DbGlobals.TAB_INGREDIENTS_DB_MAIN + " (" +
                DbGlobals.COL_ID_TAB_INGREDIENTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbGlobals.COL_IDREC_TAB_INGREDIENTS + " INTEGER, " +
                DbGlobals.COL_IDPROD_TAB_INGREDIENTS + " INTEGER, " +
                DbGlobals.COL_AMOUNT_TAB_INGREDIENTS + " REAL, " +
                DbGlobals.COL_MEASURE_TAB_INGREDIENTS + " INTEGER, " +
                DbGlobals.COL_CATEGORY_TAB_INGREDIENTS + " TEXT);";
        db.execSQL(command);
    }

    private void createStepsTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DbGlobals.TAB_STEPS_DB_MAIN + " (" +
                DbGlobals.COL_ID_TAB_STEPS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbGlobals.COL_IDREC_TAB_STEPS + " INTEGER, " +
                DbGlobals.COL_INSTRUCTION_TAB_STEPS + " TEXT);";
        db.execSQL(command);
    }

    private void createProductsTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DbGlobals.TAB_PRODUCTS_DB_MAIN + " (" +
                DbGlobals.COL_ID_TAB_PRODUCTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbGlobals.COL_NAME_TAB_PRODUCTS + " TEXT, " +
                DbGlobals.COL_TYPE_TAB_PRODUCTS + " INTEGER, " +
                DbGlobals.COL_CARBOHYDRATES_TAB_PRODUCTS + " REAL, " +
                DbGlobals.COL_PROTEIN_TAB_PRODUCTS + " REAL, " +
                DbGlobals.COL_FAT_TAB_PRODUCTS + " REAL);";
        db.execSQL(command);
    }
}
