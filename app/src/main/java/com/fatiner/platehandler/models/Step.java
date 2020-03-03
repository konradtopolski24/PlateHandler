package com.fatiner.platehandler.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fatiner.platehandler.globals.Db;

@Entity(tableName = Db.TB_STEP)
public class Step {

    @ColumnInfo(name = Db.CL_ST_ID)
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = Db.CL_ST_RECIPE_ID)
    private int recipeId;
    @ColumnInfo(name = Db.CL_ST_CONTENT)
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
