package com.fatiner.platehandler.managers;

import android.content.Context;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.MainGlobals;

public class CalculateManager {

    private CalculateManager() {}

    public static float getCalorific(float weight, float energy) {
        return weight * energy / MainGlobals.GRAM_PRIMARY_OBJ_PROD;
    }

    public static float getKcal(float gram, Organic organic){
        float factor = MainGlobals.INT_STARTING_VAR_INIT;
        switch(organic) {
            case CARBOHYDRATES:
                factor = MainGlobals.KCAL_CARBOHYDRATES_OBJ_PROD;
                break;
            case PROTEIN:
                factor = MainGlobals.KCAL_PROTEIN_OBJ_PROD;
                break;
            case FAT:
                factor = MainGlobals.KCAL_FAT_OBJ_PROD;
                break;
        }
        return gram * factor;
    }

    public static float getKj(float gram, Organic organic){
        return getKcal(gram, organic) * MainGlobals.KJ_FACTOR_OBJ_PROD;
    }

    public static float getAmountWithMeasure(Context context, float amount, int measure) {
        int[] factors = context.getResources().getIntArray(R.array.tx_factor);
        return amount * factors[measure];
    }

    public enum Organic {
        CARBOHYDRATES, PROTEIN, FAT
    }
}
