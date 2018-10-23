package com.fatiner.platehandler.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.fatiner.platehandler.classes.ShoppingList;
import com.fatiner.platehandler.globals.ImageGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TypeManager {

    private TypeManager(){}

    //
    //Boolean <--> Integer
    //
    public static int booleanToInteger(boolean bool){
        return bool ? MainGlobals.INT_INCREMENT_VAR_INIT : MainGlobals.INT_STARTING_VAR_INIT;
    }

    public static boolean integerToBoolean(int integer){
        return integer == MainGlobals.INT_INCREMENT_VAR_INIT;
    }

    //
    //Time <--> String
    //
    public static String timeToString(int hours, int minutes){
        return String.format(MainGlobals.FORM_HOUR_OBJ_INIT, hours, minutes);
    }

    public static int[] stringToTime(String time){
        int hours = Integer.valueOf(time.substring(MainGlobals.BEGIN_HOURS_MANAG_TYPE,
                MainGlobals.END_HOURS_MANAG_TYPE));
        int minutes = Integer.valueOf(time.substring(MainGlobals.BEGIN_MINUTES_MANAG_TYPE,
                MainGlobals.END_MINUTES_MANAG_TYPE));
        int[] times = new int[MainGlobals.SIZE_HOURS_MANAG_TYPE];
        times[MainGlobals.ID_HOURS_MANAG_TYPE] = hours;
        times[MainGlobals.ID_MINUTES_MANAG_TYPE] = minutes;
        return times;
    }

    //
    //Date <--> String
    //
    public static String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat(MainGlobals.FORM_DATE_OBJ_INIT);
        return format.format(date);
    }



    //
    //ShoppingList <--> Json
    //
    public static String shoppingListToJson(ShoppingList shoppingList){
        Gson gson = new Gson();
        return gson.toJson(shoppingList);
    }

    public static ShoppingList jsonToShoppingList(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, ShoppingList.class);
    }

    //
    //Recent <--> Json
    //
    public static String recentToJson(ArrayList<Integer> recent){
        JSONArray jsonArray = new JSONArray(recent);
        return jsonArray.toString();
    }

    public static ArrayList<Integer> jsonToRecent(String json){
        ArrayList<Integer> recent = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < jsonArray.length(); i++){
                recent.add(jsonArray.getInt(i));
            }
        } catch (Exception e){
            //
        }
        return recent;
    }

    //
    //Image
    //
    public static String bitmapToBase64String(Bitmap bitmap){
        return Base64.encodeToString(bitmapToByteArray(bitmap), Base64.DEFAULT);
    }

    public static Bitmap base64StringToBitmap(String base64String){
        if(base64String == null) return null;
        byte[] byteArray = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(byteArray,
                ImageGlobals.OFF_DECODE_COMP_BITMAP, byteArray.length);
    }

    public static String uriImageToBase64String(Context context, Uri uri){
        try{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            return Base64.encodeToString(bitmapToByteArray(bitmap), Base64.DEFAULT);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, ImageGlobals.FORM_JPEG_COMP_BITMAP, stream);
        return stream.toByteArray();
    }
}
