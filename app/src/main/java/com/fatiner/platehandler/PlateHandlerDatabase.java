package com.fatiner.platehandler;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fatiner.platehandler.interfaces.IngredientDAO;
import com.fatiner.platehandler.interfaces.StepDAO;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.interfaces.ProductDAO;
import com.fatiner.platehandler.interfaces.RecipeDAO;
import com.fatiner.platehandler.models.Step;

@Database(entities = {Product.class, Recipe.class, Step.class, Ingredient.class},
        version = Db.DB_CURRENT)
public abstract class PlateHandlerDatabase extends RoomDatabase {

    public abstract ProductDAO getProductDAO();
    public abstract RecipeDAO getRecipeDAO();
    public abstract IngredientDAO getIngredientDAO();
    public abstract StepDAO getStepDAO();
}
