package com.fatiner.platehandler.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.fatiner.platehandler.globals.Db;

import java.util.List;

public class RecipeComplete {

    @Embedded
    public Recipe recipe;
    @Relation(parentColumn = Db.CL_RP_ID, entityColumn = Db.CL_IG_RECIPE_ID)
    public List<Ingredient> ingredients;
    @Relation(parentColumn = Db.CL_RP_ID, entityColumn = Db.CL_ST_RECIPE_ID)
    public List<Step> steps;
}
