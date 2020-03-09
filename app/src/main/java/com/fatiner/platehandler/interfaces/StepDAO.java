package com.fatiner.platehandler.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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

    @Query("UPDATE " + Db.TB_STEP + " SET " + Db.CL_ST_DONE + " = :isDone WHERE " + Db.CL_ST_ID + "==:id")
    void updateIsDone(int id, boolean isDone);
}
