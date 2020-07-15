package com.fatiner.platehandler.fragments.primary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.adapters.spinner.SpinnerAdapter;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.dialogs.TutorialDialog;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class PrimaryFragment extends Fragment {

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    protected void initOptions(int menuId, int toolbarId, boolean hasMenu) {
        setMenuItem(menuId);
        setTb(toolbarId);
        setHasOptionsMenu(hasMenu);
        hideKeyboard();
    }

    private void setMenuItem(int id) {
        getMainActivity().setMenuItem(id);
    }

    private void setTb(int id) {
        getMainActivity().setTbTitle(getString(id));
    }

    protected void setIv(ImageView iv, int value, int arrayId) {
        TypedArray array = getTypedArray(arrayId);
        int resource = array.getResourceId(value, Globals.DF_DECREMENT);
        iv.setImageResource(resource);
        array.recycle();
    }

    protected void setIvList(List<ImageView> array, int amount) {
        hideIvList(array);
        for (int i = Globals.DF_ZERO; i < amount + Globals.DF_INCREMENT; i++) {
            array.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void hideIvList(List<ImageView> array) {
        for (ImageView iv : array) {
            removeView(iv);
        }
    }

    protected void setTv(TextView tv, String text) {
        tv.setText(text);
        tv.setSelected(true);
    }

    protected void setTv(TextView tv, int id) {
        String text = getString(id);
        tv.setText(text);
        tv.setSelected(true);
    }

    protected void setTv(TextView tv, float value, String unit) {
        String text = String.format(Locale.ENGLISH, Format.FM_UNIT, value, unit);
        tv.setText(text);
        tv.setSelected(true);
    }

    protected void setTv(TextView tv, int id, int idArray) {
        String[] array = getStringArray(idArray);
        tv.setText(array[id]);
        tv.setSelected(true);
    }

    protected void setCb(CheckBox cb, boolean isChecked) {
        cb.setChecked(isChecked);
    }

    protected void setHint(TextInputLayout til, String hint) {
        til.setHint(hint);
    }

    protected int getInt(int id) {
        return getResources().getInteger(id);
    }

    protected String [] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    protected int [] getIntArray(int id) {
        return getResources().getIntArray(id);
    }

    private TypedArray getTypedArray(int id) {
        return getResources().obtainTypedArray(id);
    }

    protected void setFragment(Fragment fragment) {
        getMainActivity().setFragment(fragment, true);
    }

    protected void setEt(EditText et, String text) {
        et.setText(text);
    }

    protected void setEt(EditText et, float value) {
        et.setText(String.valueOf(value));
    }

    protected void setSp(Spinner sp, int position, List<?> entries, Context context) {
        sp.setAdapter(new SpinnerAdapter(context, entries));
        sp.setSelection(position);
    }

    protected void setSp(Spinner sp, List<?> entries, Context context) {
        sp.setAdapter(new SpinnerAdapter(context, entries));
    }

    protected void setVp(ViewPager vp, FragmentPagerAdapter adapter) {
        vp.setAdapter(adapter);
    }

    protected void setTl(TabLayout tl, ViewPager vp) {
        tl.setupWithViewPager(vp);
    }

    protected void setRb(RadioButton rb, boolean checked) {
        rb.setChecked(checked);
    }

    protected List<String> getEntries(int id) {
        String[] types = getStringArray(id);
        return Arrays.asList(types);
    }

    protected float getCorrectEtValue(CharSequence text) {
        try {
            return Float.parseFloat(String.valueOf(text));
        } catch (Exception e) {
            return Globals.DF_ZERO;
        }
    }

    protected void setCorrectInput(EditText et) {
        et.setKeyListener(DigitsKeyListener.getInstance(false, true));
    }

    protected void showDialog(int id, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(id)
                .setPositiveButton(R.string.dg_ok, listener)
                .setNegativeButton(R.string.dg_cancel, listener)
                .show();
    }

    protected void checkSwState(boolean checked, String name, String key) {
        if (checked)
            SharedManager.setValue(getContext(), name, key, checked);
        else
            SharedManager.removeValue(getContext(), name, key);
    }

    protected void checkSwState(boolean checked, Spinner sp, String name, String key) {
        if (checked) {
            sp.setVisibility(View.VISIBLE);
        } else {
            sp.setVisibility(View.GONE);
            SharedManager.removeValue(getContext(), name, key);
        }
    }

    protected void setSettingsInt(Switch sw, Spinner sp, String name, String key) {
        if (SharedManager.isValueAvailable(getContext(), name, key)) {
            int value = SharedManager.getInt(getContext(), name, key);
            sp.setSelection(value);
            sw.setChecked(true);
        }
    }

    protected void setSettingsBool(Switch sw, String name, String key) {
        if (SharedManager.isValueAvailable(getContext(), name, key)) {
            boolean value = SharedManager.getBoolean(getContext(), name, key);
            sw.setChecked(value);
        }
    }

    protected void setSettingsBool(Switch sw, Spinner sp, String name, String key) {
        if (SharedManager.isValueAvailable(getContext(), name, key)) {
            boolean value = SharedManager.getBoolean(getContext(), name, key);
            sp.setSelection(TypeManager.boolToInt(value));
            sw.setChecked(true);
        }
    }

    protected void setSettingsString(Switch sw, Spinner sp,
                                     String name, String key, List<String> entries) {
        if (SharedManager.isValueAvailable(getContext(), name, key)) {
            String value = SharedManager.getString(getContext(), name, key);
            int id = entries.indexOf(value);
            sp.setSelection(id);
            sw.setChecked(true);
        }
    }

    private float getDimen(int id) {
        return getResources().getDimension(id);
    }

    protected void changeRvSize(RecyclerView rv) {
        int height = (int) getDimen(R.dimen.ht_rv);
        rv.post(() -> {
            if (rv.computeVerticalScrollRange() > height)
                rv.setLayoutParams(getRvParamsBig(height));
            else rv.setLayoutParams(getRvParamsSmall());
        });
    }

    private ConstraintLayout.LayoutParams getRvParamsBig(int height) {
        return new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, height);
    }

    private ConstraintLayout.LayoutParams getRvParamsSmall() {
        return new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    protected void resetRecipeDetails() {
        RecipeDetails.resetDetails();
    }

    protected void resetProductDetails() {
        ProductDetails.resetDetails();
    }

    protected void resetShoppingListDetails() {
        ShoppingListDetails.resetDetails();
    }

    protected void popFragment() {
        getMainActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    protected GridLayoutManager getManager(int amount) {
        return new GridLayoutManager(getContext(), amount);
    }

    protected int getColumnAmountList() {
        if (getOrientation() == Configuration.ORIENTATION_PORTRAIT) return Globals.GL_ONE;
        else return Globals.GL_TWO;
    }

    protected int getColumnAmountChoose() {
        if (getOrientation() == Configuration.ORIENTATION_PORTRAIT) return Globals.GL_TWO;
        else return Globals.GL_THREE;
    }

    private int getOrientation() {
        return getResources().getConfiguration().orientation;
    }

    protected void recipeSuccess(int id) {
        showShortSnack(id);
        resetRecipeDetails();
        popFragment();
    }

    protected void productSuccess(int id) {
        showShortSnack(id);
        resetProductDetails();
        popFragment();
    }

    protected Bundle getBundle() {
        return this.getArguments();
    }

    private boolean isBundle() {
        return getBundle() != null;
    }

    protected boolean isValueInBundle(String key) {
        return isBundle() && getBundle().containsKey(key);
    }

    protected int getIntFromBundle() {
        return getBundle().getInt(Globals.BN_INT);
    }

    protected void showShortToast(int id) {
        Toast.makeText(getContext(), getString(id), Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int id) {
        Toast.makeText(getContext(), getString(id), Toast.LENGTH_LONG).show();
    }

    protected void showShortSnack(int id) {
        Snackbar.make(getView(), getString(id), Snackbar.LENGTH_SHORT).show();
    }

    protected void setRv(RecyclerView rv,
                         RecyclerView.LayoutManager manager, RecyclerView.Adapter adapter) {
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
    }

    protected void setSb(SeekBar sb, int max, SeekBar.OnSeekBarChangeListener listener) {
        sb.setProgress(Globals.SB_START);
        sb.setMax(max);
        sb.setOnSeekBarChangeListener(listener);
    }

    protected void setSb(SeekBar sb, int progress) {
        sb.setProgress(progress);
    }

    protected void hideKeyboard() {
        View view = getMainActivity().getCurrentFocus();
        if (view == null) return;
        InputMethodManager manager = getInputMethodManager();
        manager.hideSoftInputFromWindow(view.getWindowToken(), Globals.DF_ZERO);
    }

    private InputMethodManager getInputMethodManager() {
        return (InputMethodManager) getMainActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
    }

    private void showView(View view) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(getAnimationDuration())
                .setListener(null);
    }

    private void hideView(View view) {
        view.animate()
                .alpha(0f)
                .setDuration(getAnimationDuration())
                .setListener(getAnimatorAdapter(view));
    }

    private int getAnimationDuration() {
        return getResources().getInteger(android.R.integer.config_mediumAnimTime);
    }

    private AnimatorListenerAdapter getAnimatorAdapter(final View view) {
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setAlpha(1f);
                removeView(view);
            }
        };
    }

    protected void removeView(View view) {
        view.setVisibility(View.GONE);
    }

    private void rotateView(View view, boolean isOpening) {
        int rotate = getRotate(isOpening);
        Animation animation = AnimationUtils.loadAnimation(getContext(), rotate);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    private int getRotate(boolean isOpening) {
        if (isOpening) {
            return R.anim.rotate_open;
        } else {
            return R.anim.rotate_close;
        }
    }

    protected void manageExpandCv(CardView cv, ImageView iv) {
        if (cv.getVisibility() == View.VISIBLE) {
            hideView(cv);
            rotateView(iv, false);
        } else {
            showView(cv);
            rotateView(iv, true);
        }
    }

    protected void checkIfRvEmpty(RecyclerView rv, TextView tvEmpty) {
        if (rv.getAdapter() == null || rv.getAdapter().getItemCount() == Globals.DF_ZERO)
            showView(tvEmpty);
        else removeView(tvEmpty);
    }

    protected void showDialog(int header, int content) {
        TutorialDialog dialog = getTutorialDialog(header, content);
        dialog.show(getChildFragmentManager(), null);
    }

    private TutorialDialog getTutorialDialog(int header, int content) {
        TutorialDialog dialog = new TutorialDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(Globals.BN_HEADER, header);
        bundle.putInt(Globals.BN_CONTENT, content);
        dialog.setArguments(bundle);
        return dialog;
    }

    protected int getColor(int id) {
        return getResources().getColor(id);
    }

    protected void setError(TextInputLayout til, int id, boolean isError) {
        if (isError) {
            til.setErrorEnabled(true);
            til.setError(getString(id));
        } else {
            til.setError(null);
            til.setErrorEnabled(false);
        }
    }

    protected void setError(TextView tv, boolean isError) {
        if (isError) tv.setError(Globals.SN_EMPTY);
        else tv.setError(null);
    }

    protected File getExternalDir() {
        return getContext().getExternalFilesDir(null);
    }

    protected void createDirectory(String name) {
        File directory = new File(getExternalDir(), name);
        if (directory.exists()) return;
        directory.mkdirs();
    }

    protected File getFile(String name, String directory) {
        return new File(String.format(Locale.ENGLISH,
                Format.FM_DIRECTORY, getExternalDir(), directory), name);
    }

    private File getImageFile(String name, int id) {
        String fullName = String.format(Locale.ENGLISH, Format.FM_IMAGE, name, id, Globals.FL_JPG);
        return getFile(fullName, Globals.DR_IMAGES);
    }

    private void saveImage(Bitmap image, String name, int id) {
        try {
            File file = getImageFile(name, id);
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, Globals.PH_QUALITY, stream);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void removeImage(String name, int id) {
        File file = getImageFile(name, id);
        removeFile(file);
    }

    protected void removeFile(File file) {
        file.delete();
    }

    protected String getXlsName(String name) {
        return String.format(Locale.ENGLISH, Format.FM_FILE, name, Globals.FL_XLS);
    }

    protected void setIv(ImageView iv, Bitmap bitmap) {
        iv.setImageBitmap(bitmap);
        if (bitmap == null) iv.setVisibility(View.GONE);
        else iv.setVisibility(View.VISIBLE);
    }

    protected Bitmap getImage(String name, int id) {
        try {
            File file = getImageFile(name, id);
            return BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    protected void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(Globals.TP_IMAGES);
        startActivityForResult(intent, Globals.PH_REQUEST);
    }

    protected Bitmap getImageFromGallery(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                Uri uri = data.getData();
                InputStream stream = getContext().getContentResolver().openInputStream(uri);
                Bitmap image = BitmapFactory.decodeStream(stream);
                return getOptimalImage(image);
            } catch (FileNotFoundException e) {
                return null;
            }
        } else return null;
    }

    protected void manageImageSaving(Bitmap image, String name, int id) {
        createDirectory(Globals.DR_IMAGES);
        if (image == null) removeImage(name, id);
        else saveImage(image, name, id);
    }

    private Bitmap getOptimalImage(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();
        float ratio = (float) width / (float) height;
        if (ratio > Globals.DF_INCREMENT) {
            width = Globals.IM_SIZE;
            height = (int) (width / ratio);
        } else {
            width = (int) (height * ratio);
            height = Globals.IM_SIZE;
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
