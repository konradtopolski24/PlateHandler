package com.fatiner.platehandler.managers;

import android.content.Context;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;

import java.util.ArrayList;
import java.util.Locale;

public class QueryManager {

    private QueryManager() {
    }

    private static String getBool(Context context, String column, String name, String key) {
        if (SharedManager.isValueAvailable(context, name, key)) {
            boolean value = SharedManager.getBoolean(context, name, key);
            String text = String.valueOf(TypeManager.boolToInt(value));
            return String.format(Locale.ENGLISH, Format.FM_OTHER, column, text);
        }
        return Globals.SN_EMPTY;
    }

    private static String getString(Context context, String column, String name, String key) {
        if (SharedManager.isValueAvailable(context, name, key)) {
            String text = SharedManager.getString(context, name, key);
            return String.format(Locale.ENGLISH, Format.FM_STRING, column, text);
        }
        return Globals.SN_EMPTY;
    }

    private static String getInt(Context context, String column, String name, String key) {
        if (SharedManager.isValueAvailable(context, name, key)) {
            int value = SharedManager.getInt(context, name, key);
            String text = String.valueOf(value);
            return String.format(Locale.ENGLISH, Format.FM_OTHER, column, text);
        }
        return Globals.SN_EMPTY;
    }

    public static ArrayList<String> getRpConditions(Context context) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(getBool(context, Db.CL_RP_FAVORITE, Shared.SR_RECIPE, Shared.KY_FAVORITE));
        strings.add(getString(context, Db.CL_RP_AUTHOR, Shared.SR_RECIPE, Shared.KY_AUTHOR));
        strings.add(getInt(context, Db.CL_RP_DIFFICULTY, Shared.SR_RECIPE, Shared.KY_DIFFICULTY));
        strings.add(getInt(context, Db.CL_RP_SPICINESS, Shared.SR_RECIPE, Shared.KY_SPICINESS));
        strings.add(getInt(context, Db.CL_RP_COUNTRY, Shared.SR_RECIPE, Shared.KY_COUNTRY));
        strings.add(getInt(context, Db.CL_RP_TYPE, Shared.SR_RECIPE, Shared.KY_TYPE));
        strings.add(getBool(context, Db.CL_RP_PREFERENCE, Shared.SR_RECIPE, Shared.KY_PREFERENCE));
        return strings;
    }

    public static ArrayList<String> getPdConditions(Context context) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(getInt(context, Db.CL_RP_TYPE, Shared.SR_PRODUCT, Shared.KY_TYPE));
        return strings;
    }

    public static String getWhere(ArrayList<String> conditions) {
        StringBuilder builder = new StringBuilder();
        for (String condition : conditions) builder.append(condition);
        String where = builder.toString();
        if (where.endsWith(Format.FM_AND)) {
            where = where.substring(Globals.DF_ZERO, where.length() - Format.FM_AND.length());
            return String.format(Locale.ENGLISH, Format.FM_WHERE, where);
        } else return Globals.SN_EMPTY;
    }

    public static String getOrderBy(Context context, String name, String column) {
        if (SharedManager.getBoolean(context, name, Shared.KY_ALPHABETICAL))
            return String.format(Locale.ENGLISH, Format.FM_ORDER, column);
        return Globals.SN_EMPTY;
    }

    public static SimpleSQLiteQuery getRowsQuery(String tb, String where, String orderBy) {
        String query = String.format(Locale.ENGLISH, Format.FM_SELECT, tb, where, orderBy);
        return new SimpleSQLiteQuery(query);
    }
}
