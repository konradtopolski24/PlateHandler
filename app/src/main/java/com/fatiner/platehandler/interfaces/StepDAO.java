package com.fatiner.platehandler.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.models.Step;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface StepDAO {

    @Insert
    void addSteps(List<Step> steps);

    @Delete
    void deleteSteps(List<Step> steps);

    @Query("DELETE FROM " + Db.TB_STEP + " WHERE " + Db.CL_ST_RECIPE_ID + "==:recipeId")
    void deleteSteps(int recipeId);

    @Query("SELECT * FROM " + Db.TB_STEP)
    Single<List<Step>> getAllSteps();
}
