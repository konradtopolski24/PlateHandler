package com.fatiner.platehandler.managers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.ImageView;

import com.fatiner.platehandler.globals.ImageGlobals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageManager {

    private ImageManager(){}

   public static void saveImage(Bitmap image, String imageName){
        if(image == null) return;
        File directory = new File(getDirectoryPath());
        directory.mkdirs();

        File file = new File(directory, imageName);

        OutputStream stream = null;
        try{
            stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, ImageGlobals.QUAL_PNG_COMP_BITMAP, stream);
            stream.flush();
            stream.close();
        } catch (IOException e){

        }
    }

    public static Bitmap getImageFromStorage(String imageName){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(getFullPath(imageName), options);
    }

    private static String getDirectoryPath(){
        File sdPath = Environment.getExternalStorageDirectory();
        return sdPath.getAbsolutePath() + ImageGlobals.DIR_PATH_SAVE_IMG;
    }

    public static String getImageProductName(int id){
        return ImageGlobals.NAME_PRODUCT_SAVE_IMG + id + ImageGlobals.FILE_PNG_SAVE_IMG;
    }

    public static String getImageRecipeName(int id){
        return ImageGlobals.NAME_RECIPE_SAVE_IMG + id + ImageGlobals.FILE_PNG_SAVE_IMG;
    }

    public static String getImageStepName(int idRecipe, int idStep){
        return ImageGlobals.NAME_STEP_SAVE_IMG + idRecipe
                + ImageGlobals.NAME_NO_SAVE_IMG + idStep + ImageGlobals.FILE_PNG_SAVE_IMG;
    }

    private static String getFullPath(String imageName){
        return getDirectoryPath() + imageName;
    }

    public static void deleteImage(String imageName){
        String path = getFullPath(imageName);
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }

    public static Bitmap getImageFromView(ImageView imageView){
        BitmapDrawable drawable = ((BitmapDrawable)imageView.getDrawable());
        if(drawable == null){
            return null;
        } else {
            return drawable.getBitmap();
        }
    }
}
