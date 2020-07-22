package com.fatiner.platehandler.items;

public class ShoppingItem {

    private float amount;
    private int measure;
    private String name;
    private boolean isCrossedOut;

    public ShoppingItem() {
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getMeasure() {
        return measure;
    }

    public void setMeasure(int measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCrossedOut() {
        return isCrossedOut;
    }

    public void setCrossedOut(boolean crossedOut) {
        isCrossedOut = crossedOut;
    }
}
