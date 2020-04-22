package com.fatiner.platehandler.managers;

import com.fatiner.platehandler.items.ShoppingList;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TypeManager {

    private TypeManager() {}

    public static int boolToInt(boolean bool) {
        return bool ? Globals.DF_INCREMENT : Globals.DF_ZERO;
    }

    public static boolean intToBool(int integer) {
        return integer == Globals.DF_INCREMENT;
    }

    public static String timeToString(int hours, int minutes) {
        return String.format(Locale.ENGLISH, Format.FM_HOUR, hours, minutes);
    }

    public static ArrayList<Integer> stringToTime(String time) {
        String[] split = time.split(Globals.SN_COLON);
        ArrayList<Integer> times = new ArrayList<>();
        times.add(Integer.parseInt(split[Globals.TM_HOURS]));
        times.add(Integer.parseInt(split[Globals.TM_MINUTES]));
        return times;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(Format.FM_DATE);
        return format.format(date);
    }

    public static String shoppingListToJson(ShoppingList shoppingList) {
        Gson gson = new Gson();
        return gson.toJson(shoppingList);
    }

    public static ShoppingList jsonToShoppingList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ShoppingList.class);
    }

    public static String recentToJson(List<Integer> recent) {
        JSONArray jsonArray = new JSONArray(recent);
        return jsonArray.toString();
    }

    public static List<Integer> jsonToRecent(String json) {
        List<Integer> recent = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = Globals.DF_ZERO; i < jsonArray.length(); i++)
                recent.add(jsonArray.getInt(i));
            return recent;
        } catch (Exception e) {
            return recent;
        }
    }

    public static int longToInt(Long value) {
        return value.intValue();
    }
}
