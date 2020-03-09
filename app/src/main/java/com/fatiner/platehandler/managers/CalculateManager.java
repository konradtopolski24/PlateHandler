package com.fatiner.platehandler.managers;

import android.content.Context;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.Globals;

public class CalculateManager {

    private CalculateManager() {}

    public static float getKcal(float gram, int id) {
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

    public static float getKj(float gram, int id) {
        return getKcal(gram, id) * Globals.FC_KJ;
    }
}
