package com.fatiner.platehandler.managers;

import android.content.Context;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.Globals;

public class CalculateManager {

    private CalculateManager() {}

    public static float getCalorific(float weight, float energy) {
        return weight * energy / Globals.FC_GRAM;
    }

    public static float getKcal(float gram, int id){
        float factor = Globals.DF_ZERO;
        switch(id) {
            case Globals.NT_CARBOHYDRATES:
                return Globals.FC_CARBOHYDRATES * gram;
            case Globals.NT_PROTEINS:
                return Globals.FC_PROTEINS * gram;
            case Globals.NT_FATS:
                return Globals.FC_FATS * gram;
            default:
                return Globals.DF_ZERO;
        }
    }

    public static float getKj(float gram, int id){
        return getKcal(gram, id) * Globals.FC_KJ;
    }

    public static float getAmountWithMeasure(Context context, float amount, int measure) {
        int[] factors = context.getResources().getIntArray(R.array.tx_factor);
        return amount * factors[measure];
    }
}
