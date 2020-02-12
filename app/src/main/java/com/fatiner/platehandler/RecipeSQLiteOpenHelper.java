package com.fatiner.platehandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fatiner.platehandler.globals.DbGlobals;

public class RecipeSQLiteOpenHelper extends SQLiteOpenHelper {

    private Context context;

    public RecipeSQLiteOpenHelper(Context context) {
        super(context, DbGlobals.DB_NAME, null, DbGlobals.DB_CURRENT);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, DbGlobals.DB_LOWEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int version){
        if(version < DbGlobals.DB_CURRENT){
            createRecipesTable(db);
            createIngredientsTable(db);
            createStepsTable(db);
            createProductsTable(db);
        }
    }

    private void createRecipesTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DbGlobals.TB_RECIPE + " (" +
                DbGlobals.CL_RP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbGlobals.CL_RP_NAME + " TEXT, " +
                DbGlobals.CL_RP_AUTHOR + " TEXT, " +
                DbGlobals.CL_RP_SERVING + " INTEGER, " +
                DbGlobals.CL_RP_TIME + " TEXT, " +
                DbGlobals.CL_RP_DIFFICULTY + " INTEGER, " +
                DbGlobals.CL_RP_SPICINESS + " INTEGER, " +
                DbGlobals.CL_RP_COUNTRY + " INTEGER, " +
                DbGlobals.CL_RP_TYPE + " INTEGER, " +
                DbGlobals.CL_RP_PREFERENCE + " INTEGER, " +
                DbGlobals.CL_RP_FAVORITE + " INTEGER);";
        db.execSQL(command);
    }

    private void createIngredientsTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DbGlobals.TB_INGREDIENT + " (" +
                DbGlobals.CL_IG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbGlobals.CL_IG_RECIPE + " INTEGER, " +
                DbGlobals.CL_IG_PRODUCT + " INTEGER, " +
                DbGlobals.CL_IG_AMOUNT + " REAL, " +
                DbGlobals.CL_IG_MEASURE + " INTEGER, " +
                DbGlobals.CL_IG_CATEGORY + " TEXT);";
        db.execSQL(command);
    }

    private void createStepsTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DbGlobals.TB_STEP + " (" +
                DbGlobals.CL_ST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbGlobals.CL_ST_RECIPE + " INTEGER, " +
                DbGlobals.CL_ST_INSTRUCTION + " TEXT);";
        db.execSQL(command);
    }

    private void createProductsTable(SQLiteDatabase db){
        String command = "CREATE TABLE " + DbGlobals.TB_PRODUCT + " (" +
                DbGlobals.CL_PD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbGlobals.CL_PD_NAME + " TEXT, " +
                DbGlobals.CL_PD_TYPE + " INTEGER, " +
                DbGlobals.CL_PD_CARBOHYDRATES + " REAL, " +
                DbGlobals.CL_PD_PROTEIN + " REAL, " +
                DbGlobals.CL_PD_FAT + " REAL);";
        db.execSQL(command);
    }
}
