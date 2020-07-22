package com.fatiner.platehandler.managers;

import android.content.Context;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.Globals;

public class CaloriesManager {

    private CaloriesManager() {}

    public static float getBurningValue(Context context, int id, float serving) {
        int[] factors = context.getResources().getIntArray(R.array.nb_bn_factor);
        return serving * Globals.TM_BURNING / factors[id];
    }
}
