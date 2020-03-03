package com.fatiner.platehandler.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.fatiner.platehandler.classes.ShoppingList;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TypeManager {

    private TypeManager() {}

    //Boolean <--> Integer
    public static int boolToInt(boolean bool) {
        return bool ? Globals.DF_INCREMENT : Globals.DF_ZERO;
    }

    public static boolean intToBool(int integer) {
        return integer == Globals.DF_INCREMENT;
    }


    //Time <--> String
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

    //Date <--> String
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(Format.FM_DATE);
        return format.format(date);
    }

    //ShoppingList <--> Json
    public static String shoppingListToJson(ShoppingList shoppingList) {
        Gson gson = new Gson();
        return gson.toJson(shoppingList);
    }

    public static ShoppingList jsonToShoppingList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ShoppingList.class);
    }


    //Recent <--> Json
    public static String recentToJson(List<Integer> recent) {
        JSONArray jsonArray = new JSONArray(recent);
        return jsonArray.toString();
    }

    public static List<Integer> jsonToRecent(String json) {
        List<Integer> recent = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = Globals.DF_ZERO; i < jsonArray.length(); i++) {
                recent.add(jsonArray.getInt(i));
            }
        } catch (Exception e) {
            //
        }
        return recent;
    }

    //Int <--> Long
    public static int longToInt(Long value) {
        return value.intValue();
    }

    //Image
    public static String bitmapToBase64String(Bitmap bitmap) {
        return Base64.encodeToString(bitmapToByteArray(bitmap), Base64.DEFAULT);
    }

    public static Bitmap base64StringToBitmap(String base64String) {
        if(base64String == null) return null;
        byte[] byteArray = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(byteArray,
                Globals.PH_DECODE, byteArray.length);
    }

    public static String uriImageToBase64String(Context context, Uri uri) {
        try{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            return Base64.encodeToString(bitmapToByteArray(bitmap), Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, Globals.PH_COMPRESSION, stream);
        return stream.toByteArray();
    }

    public static String[] arrayListToArray(ArrayList<String> arrayList) {
        String[] array = new String[arrayList.size()];
        return arrayList.toArray(array);
    }
}
