package com.fatiner.platehandler.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.fatiner.platehandler.globals.Db;

public class IngredientComplete {

    @Embedded
    public Ingredient ingredient;
    @Relation(parentColumn = Db.CL_IG_PRODUCT_ID, entityColumn = Db.CL_PD_ID)
    public Product product;
}
