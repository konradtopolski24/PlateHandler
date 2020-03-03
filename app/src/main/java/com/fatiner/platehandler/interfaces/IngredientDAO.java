package com.fatiner.platehandler.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.IngredientComplete;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface IngredientDAO {

    @Insert
    void addIngredients(List<Ingredient> ingredients);

    @Delete
    void deleteIngredients(List<Ingredient> ingredients);

    @Query("DELETE FROM " + Db.TB_INGREDIENT + " WHERE " + Db.CL_IG_RECIPE_ID + "==:recipeId")
    void deleteIngredients(int recipeId);

    @Transaction
    @Query("SELECT * FROM " + Db.TB_INGREDIENT + " WHERE " + Db.CL_IG_RECIPE_ID + "==:id")
    Single<List<IngredientComplete>> getCompleteIngredients(int id);

    @Query("SELECT * FROM " + Db.TB_INGREDIENT + " WHERE " + Db.CL_IG_RECIPE_ID + "==:id")
    Single<List<Ingredient>> getIngredients(int id);

    @Query("SELECT * FROM " + Db.TB_INGREDIENT)
    Single<List<Ingredient>> getAllIngredients();
}
