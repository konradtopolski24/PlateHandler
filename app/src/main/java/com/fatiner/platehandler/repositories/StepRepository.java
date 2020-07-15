package com.fatiner.platehandler.repositories;

import android.app.Application;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.interfaces.StepDAO;
import com.fatiner.platehandler.models.Step;

import java.util.List;

import io.reactivex.Single;

public class StepRepository {

    private StepDAO stepDAO;

    public StepRepository(Application application) {
        PlateHandlerDatabase db = PlateHandlerDatabase.getInstance(application);
        stepDAO = db.getStepDAO();
    }

    public void addSteps(List<Step> steps) {
        stepDAO.addSteps(steps);
    }

    public void deleteSteps(List<Step> steps) {
        stepDAO.deleteSteps(steps);
    }

    public void deleteSteps(int recipeId) {
        stepDAO.deleteSteps(recipeId);
    }

    public Single<List<Step>> getAllSteps() {
        return stepDAO.getAllSteps();
    }

    public void updateIsDone(int id, boolean isDone) {
        stepDAO.updateIsDone(id, isDone);
    }

    public void deleteAllSteps() {
        stepDAO.deleteAllSteps();
    }
}
