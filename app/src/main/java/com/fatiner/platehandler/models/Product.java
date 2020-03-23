package com.fatiner.platehandler.models;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.globals.Globals;

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
    @Ignore
    private Bitmap photo;

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

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    public float getKcal(int id) {
        switch (id) {
            case Globals.NT_CARBOHYDRATES:
                return Globals.FC_CARBOHYDRATES * carbohydrates;
            case Globals.NT_PROTEINS:
                return Globals.FC_PROTEINS * proteins;
            case Globals.NT_FATS:
                return Globals.FC_FATS * fats;
            default:
                return Globals.DF_ZERO;
        }
    }

    public float getKj(int id) {
        return getKcal(id) * Globals.FC_KJ;
    }

    public float getTotalKcal() {
        return getKcal(Globals.NT_CARBOHYDRATES)
                + getKcal(Globals.NT_PROTEINS)
                + getKcal(Globals.NT_FATS);
    }

    public float getTotalKj() {
        return getTotalKcal() * Globals.FC_KJ;
    }

    public float getOther() {
        return  size - getTotalNutrients();
    }

    public float getTotalNutrients() {
        return carbohydrates + proteins + fats;
    }

    public float getPercentage(int id) {
        float value = getNutrients(id);
        return value/size * Globals.FC_PERCENTAGE;
    }

    private float getNutrients(int id) {
        switch (id) {
            case Globals.NT_CARBOHYDRATES:
                return carbohydrates;
            case Globals.NT_PROTEINS:
                return proteins;
            case Globals.NT_FATS:
                return fats;
            default:
                return Globals.DF_ZERO;
        }
    }
}