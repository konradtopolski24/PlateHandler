package com.fatiner.platehandler.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.fatiner.platehandler.classes.ShoppingList;
import com.fatiner.platehandler.globals.MainGlobals;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TypeManager {

    private TypeManager(){}

    //
    //Boolean <--> Integer
    //
    public static int booleanToInteger(boolean bool){
        return bool ? MainGlobals.DF_INCREMENT : MainGlobals.DF_ZERO;
    }

    public static boolean integerToBoolean(int integer){
        return integer == MainGlobals.DF_INCREMENT;
    }

    //
    //Time <--> String
    //
    public static String timeToString(int hours, int minutes){
        return String.format(MainGlobals.FM_HOUR, hours, minutes);
    }

    public static int[] stringToTime(String time){
        int hours = Integer.valueOf(time.substring(MainGlobals.HR_BEGIN,
                MainGlobals.HR_END));
        int minutes = Integer.valueOf(time.substring(MainGlobals.MN_BEGIN,
                MainGlobals.MN_END));
        int[] times = new int[MainGlobals.TM_SIZE];
        times[MainGlobals.TM_HOURS] = hours;
        times[MainGlobals.TM_MINUTES] = minutes;
        return times;
    }

    //
    //Date <--> String
    //
    public static String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat(MainGlobals.FM_DATE);
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
            for(int i = MainGlobals.DF_ZERO; i < jsonArray.length(); i++){
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
                MainGlobals.PH_DECODE, byteArray.length);
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, MainGlobals.PH_COMPRESSION, stream);
        return stream.toByteArray();
    }

    public static String[] arrayListToArray(ArrayList<String> arrayList) {
        String[] array = new String[arrayList.size()];
        return arrayList.toArray(array);
    }
}
