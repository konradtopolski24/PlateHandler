package com.fatiner.platehandler;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fatiner.platehandler.interfaces.IngredientDAO;
import com.fatiner.platehandler.interfaces.StepDAO;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.interfaces.ProductDAO;
import com.fatiner.platehandler.interfaces.RecipeDAO;
import com.fatiner.platehandler.models.Step;

@Database(entities = {Product.class, Recipe.class, Step.class, Ingredient.class}, version = Db.DB_CURRENT)
public abstract class PlateHandlerDatabase extends RoomDatabase {

    private static PlateHandlerDatabase instance;

    public abstract ProductDAO getProductDAO();
    public abstract RecipeDAO getRecipeDAO();
    public abstract IngredientDAO getIngredientDAO();
    public abstract StepDAO getStepDAO();

    public static synchronized PlateHandlerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PlateHandlerDatabase.class, Db.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
