package com.fatiner.platehandler.repositories;

import android.app.Application;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.interfaces.IngredientDAO;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.IngredientComplete;

import java.util.List;

import io.reactivex.Single;

public class IngredientRepository {

    private IngredientDAO ingredientDAO;

    public IngredientRepository(Application application) {
        PlateHandlerDatabase db = PlateHandlerDatabase.getInstance(application);
        ingredientDAO = db.getIngredientDAO();
    }

    public void addIngredients(List<Ingredient> ingredients) {
        ingredientDAO.addIngredients(ingredients);
    }

    public void deleteIngredients(List<Ingredient> ingredients) {
        ingredientDAO.deleteIngredients(ingredients);
    }

    public void deleteIngredients(int recipeId) {
        ingredientDAO.deleteIngredients(recipeId);
    }

    public Single<List<IngredientComplete>> getCompleteIngredients(int id) {
        return ingredientDAO.getCompleteIngredients(id);
    }

    public Single<List<Ingredient>> getAllIngredients() {
        return ingredientDAO.getAllIngredients();
    }

    public Single<Integer> getRowCount(int id) {
        return ingredientDAO.getRowCount(id);
    }

    public void updateIsUsed(int id, boolean isUsed) {
        ingredientDAO.updateIsUsed(id, isUsed);
    }

    public void deleteAllIngredients() {
        ingredientDAO.deleteAllIngredients();
    }
}
