package com.fatiner.platehandler.repositories;

import android.app.Application;

import androidx.sqlite.db.SupportSQLiteQuery;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.interfaces.RecipeDAO;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.RecipeComplete;

import java.util.List;

import io.reactivex.Single;

public class RecipeRepository {

    private RecipeDAO recipeDAO;

    public RecipeRepository(Application application) {
        PlateHandlerDatabase db = PlateHandlerDatabase.getInstance(application);
        recipeDAO = db.getRecipeDAO();
    }

    public Single<Long> addRecipe(Recipe recipe) {
        return recipeDAO.addRecipe(recipe);
    }

    public void addRecipes(List<Recipe> recipes) {
        recipeDAO.addRecipes(recipes);
    }

    public void updateRecipe(Recipe recipe) {
        recipeDAO.updateRecipe(recipe);
    }

    public void deleteRecipe(Recipe recipe) {
        recipeDAO.deleteRecipe(recipe);
    }

    public Single<List<Recipe>> getRecipes(SupportSQLiteQuery query) {
        return recipeDAO.getRecipes(query);
    }

    public Single<RecipeComplete> getCompleteRecipe(int id) {
        return recipeDAO.getCompleteRecipe(id);
    }

    public Single<List<Recipe>> getRecipes(List<Integer> ids) {
        return recipeDAO.getRecipes(ids);
    }

    public Single<List<String>> getAuthors() {
        return recipeDAO.getAuthors();
    }

    public Single<Integer> getRowCount() {
        return recipeDAO.getRowCount();
    }

    public void updateFavorite(int id, boolean favorite) {
        recipeDAO.updateFavorite(id, favorite);
    }

    public Single<List<Recipe>> getAllRecipes() {
        return recipeDAO.getAllRecipes();
    }

    public void deleteAllRecipes() {
        recipeDAO.deleteAllRecipes();
    }
}
