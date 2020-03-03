package com.fatiner.platehandler.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fatiner.platehandler.globals.Db;

@Entity(tableName = Db.TB_PRODUCT)
public class Product {

    @ColumnInfo(name = Db.CL_PD_ID)
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = Db.CL_PD_NAME)
    private String name;
    @ColumnInfo(name = Db.CL_PD_TYPE)
    private int type;
    @ColumnInfo(name = Db.CL_PD_SIZE)
    private float size;
    @ColumnInfo(name = Db.CL_PD_CARBOHYDRATES)
    private float carbohydrates;
    @ColumnInfo(name = Db.CL_PD_PROTEINS)
    private float proteins;
    @ColumnInfo(name = Db.CL_PD_FATS)
    private float fats;

    public Product() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
