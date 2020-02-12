package com.fatiner.platehandler.managers;

import android.content.Context;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.MainGlobals;

public class CalculateManager {

    private CalculateManager() {}

    public static float getCalorific(float weight, float energy) {
        return weight * energy / MainGlobals.FC_GRAM;
    }

    public static float getKcal(float gram, Organic organic){
        float factor = MainGlobals.DF_ZERO;
        switch(organic) {
            case CARBOHYDRATES:
                factor = MainGlobals.FC_CARBOHYDRATES;
                break;
            case PROTEIN:
                factor = MainGlobals.FC_PROTEIN;
                break;
            case FAT:
                factor = MainGlobals.FC_FAT;
                break;
        }
        return gram * factor;
    }

    public static float getKj(float gram, Organic organic){
        return getKcal(gram, organic) * MainGlobals.FC_KJ;
    }

    public static float getAmountWithMeasure(Context context, float amount, int measure) {
        int[] factors = context.getResources().getIntArray(R.array.tx_factor);
        return amount * factors[measure];
    }

    public enum Organic {
        CARBOHYDRATES, PROTEIN, FAT
    }
}
