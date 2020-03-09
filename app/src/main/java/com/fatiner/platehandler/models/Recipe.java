package com.fatiner.platehandler.models;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fatiner.platehandler.globals.Db;

import java.util.List;

@Entity(tableName = Db.TB_RECIPE)
public class Recipe {

    @ColumnInfo(name = Db.CL_RP_ID)
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = Db.CL_RP_NAME)
    private String name;
    @ColumnInfo(name = Db.CL_RP_AUTHOR)
    private String author;
    @ColumnInfo(name = Db.CL_RP_SERVING)
    private int serving;
    @ColumnInfo(name = Db.CL_RP_TIME)
    private String time;
    @ColumnInfo(name = Db.CL_RP_DIFFICULTY)
    private int difficulty;
    @ColumnInfo(name = Db.CL_RP_SPICINESS)
    private int spiciness;
    @ColumnInfo(name = Db.CL_RP_COUNTRY)
    private int country;
    @ColumnInfo(name = Db.CL_RP_TYPE)
    private int type;
    @ColumnInfo(name = Db.CL_RP_PREFERENCE)
    private boolean preference;
    @ColumnInfo(name = Db.CL_RP_FAVORITE)
    private boolean favorite;
    @Ignore
    private Bitmap photo;
    @Ignore
    private List<Ingredient> ingredients;
    @Ignore
    private List<Step> steps;

    public Recipe() {}

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getSpiciness() {
        return spiciness;
    }

    public void setSpiciness(int spiciness) {
        this.spiciness = spiciness;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getPreference() {
        return preference;
    }

    public void setPreference(boolean preference) {
        this.preference = preference;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
