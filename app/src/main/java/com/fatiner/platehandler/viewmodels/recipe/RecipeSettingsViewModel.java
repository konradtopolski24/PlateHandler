package com.fatiner.platehandler.viewmodels.recipe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fatiner.platehandler.repositories.RecipeRepository;

import java.util.List;

import io.reactivex.Single;

public class RecipeSettingsViewModel extends AndroidViewModel {

    private RecipeRepository recipeRepository;

    public RecipeSettingsViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
    }

    public Single<List<String>> getAuthors() {
        return recipeRepository.getAuthors();
    }
}
