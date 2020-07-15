package com.fatiner.platehandler.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.RecipeComplete;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface RecipeDAO {

    @Insert
    Single<Long> addRecipe(Recipe recipe);

    @Insert
    void addRecipes(List<Recipe> recipes);

    @Update
    void updateRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @RawQuery
    Single<List<Recipe>> getRecipes(SupportSQLiteQuery query);

    @Transaction
    @Query("SELECT * FROM " + Db.TB_RECIPE + " WHERE " + Db.CL_RP_ID + "==:id")
    Single<RecipeComplete> getCompleteRecipe(int id);

    @Query("SELECT * FROM " + Db.TB_RECIPE + " WHERE " + Db.CL_RP_ID + " IN (:ids)")
    Single<List<Recipe>> getRecipes(List<Integer> ids);

    @Query("SELECT DISTINCT " + Db.CL_RP_AUTHOR + " FROM " + Db.TB_RECIPE + " ORDER BY "
            + Db.CL_RP_AUTHOR)
    Single<List<String>> getAuthors();

    @Query("SELECT COUNT( " + Db.CL_RP_ID + ") FROM " + Db.TB_RECIPE)
    Single<Integer> getRowCount();

    @Query("UPDATE " + Db.TB_RECIPE + " SET " + Db.CL_RP_FAVORITE + " = :favorite WHERE "
            + Db.CL_RP_ID + "==:id")
    void updateFavorite(int id, boolean favorite);

    @Query("SELECT * FROM " + Db.TB_RECIPE)
    Single<List<Recipe>> getAllRecipes();

    @Query("DELETE FROM " + Db.TB_RECIPE)
    void deleteAllRecipes();
}
