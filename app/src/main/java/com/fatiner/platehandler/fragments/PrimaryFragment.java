package com.fatiner.platehandler.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.shared.SharedRecipeManager;

import java.util.ArrayList;

public class PrimaryFragment extends Fragment {

    protected void setToolbarTitle(int id){
        ((MainActivity) getActivity()).setToolbarTitle(getString(id));
    }

    protected void setToolbarTitle(String title){
        ((MainActivity) getActivity()).setToolbarTitle(title);
    }

    protected void setMenuItem(int id){
        ((MainActivity) getActivity()).setMenuItem(id);
    }

    protected void setFragment(Fragment fragment){
        ((MainActivity) getActivity()).setFragment(fragment, true);
    }

    protected String [] getStringArray(int id){
        return getActivity().getResources().getStringArray(id);
    }

    protected int [] getIntegerArray(int id){
        return getActivity().getResources().getIntArray(id);
    }

    protected void resetRecipeDetails(){
        RecipeDetails.resetRecipeDetails();
    }

    protected void resetProductDetails(){
        ProductDetails.resetProductDetails();
    }

    protected void resetShoppingListDetails(){
        ShoppingListDetails.resetShoppingListDetails();
    }

    protected void popFragment(){
        getFragmentManager().popBackStackImmediate();
    }

    protected GridLayoutManager getGridLayoutManager(int spanCount){
        return new GridLayoutManager(getActivity(), spanCount);
    }

    protected LinearLayoutManager getLinearLayoutManager(int orientation){
        return new LinearLayoutManager(getContext(), orientation, false);
    }

    protected LinearLayoutManager getLayoutManagerNoScroll(int orientation){
        return new LinearLayoutManager(getContext(), orientation, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
    }

    protected void recipeSuccess(int id){
        showShortSnack(id);
        resetRecipeDetails();
        popFragment();
    }

    protected void productSuccess(int id){
        showShortSnack(id);
        resetProductDetails();
        popFragment();
    }

    protected Bundle getBundle(){
        return this.getArguments();
    }

    protected boolean isBundleNotEmpty(){
        Bundle bundle = getBundle();
        return bundle != null;
    }

    protected int getIntFromBundle(String id){
        return getBundle().getInt(id);
    }

    protected boolean getBoolFromBundle(String id){
        return getBundle().getBoolean(id);
    }

    protected void setBoolInBundle(Fragment fragment, boolean bool, String id){
        Bundle bundle = new Bundle();
        bundle.putBoolean(id, bool);
        fragment.setArguments(bundle);
    }

    protected void setIntInBundle(Fragment fragment, int integer, String id){
        Bundle bundle = new Bundle();
        bundle.putInt(id, integer);
        fragment.setArguments(bundle);
    }

    protected boolean isAuthorNotAvailable(ArrayList<String> authors){
        for(String author : authors){
            if(author.equals(SharedRecipeManager.getSharedAuthor(getContext()))){
                return false;
            }
        }
        return true;
    }

    protected void removeUnavailableAuthor(ArrayList<String> authors){
        if(isAuthorNotAvailable(authors)){
            SharedRecipeManager.removeSharedAuthor(getContext());
        }
    }

    protected String getStepHeader(int id){
        int actualId = id + MainGlobals.DF_INCREMENT;
        return getString(R.string.ct_step) + MainGlobals.SN_SPACE + actualId;
    }

    protected void selectPhoto(){
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, MainGlobals.PH_REQUEST);
    }

    protected String getEncodedImage(int resultCode, Intent data){
        if(resultCode == getActivity().RESULT_OK && data != null){
            Uri uri = data.getData();
            return TypeManager.uriImageToBase64String(getContext(), uri);
        }
        return null;
    }

    protected void showShortToast(int id){
        Toast.makeText(getContext(), getContext().getString(id), Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int id){
        Toast.makeText(getContext(), getContext().getString(id), Toast.LENGTH_LONG).show();
    }

    protected void showShortSnack(int id){
        Snackbar.make(getView(), getContext().getString(id), Snackbar.LENGTH_SHORT).show();
    }

    protected void showLongSnack(int id){
        Snackbar.make(getView(), getContext().getString(id), Snackbar.LENGTH_LONG).show();
    }

    protected void showAlertDialog(int id, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(id)
                .setPositiveButton(R.string.dg_ok, listener)
                .setNegativeButton(R.string.dg_cancel, listener)
                .show();
    }

    protected void setRecyclerView(RecyclerView recycView, RecyclerView.LayoutManager manager,
                                   RecyclerView.Adapter adapter){
        setRecycLayoutManager(recycView, manager);
        setRecycAdapter(recycView, adapter);
    }

    private void setRecycLayoutManager(RecyclerView recycView, RecyclerView.LayoutManager manager){
        recycView.setLayoutManager(manager);
    }

    private void setRecycAdapter(RecyclerView recycView, RecyclerView.Adapter adapter){
        recycView.setAdapter(adapter);
    }

    protected void manageSeekBar(SeekBar seekBar, int progress, int max,
                                 SeekBar.OnSeekBarChangeListener listener){
        setSeekProgress(seekBar, progress);
        setSeekMax(seekBar, max);
        setSeekListener(seekBar, listener);
    }

    private void setSeekProgress(SeekBar seekBar, int progress){
        seekBar.setProgress(progress);
    }

    private void setSeekMax(SeekBar seekBar, int max){
        seekBar.setMax(max);
    }

    private void setSeekListener(SeekBar seekBar, SeekBar.OnSeekBarChangeListener listener){
        seekBar.setOnSeekBarChangeListener(listener);
    }

    protected String getOrderByProducts(){
        return DbGlobals.CL_PD_NAME + " ASC";
    }

    protected void hideKeyboard(){
        View view = getActivity().getCurrentFocus();
        if(view == null) return;
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected float checkEditTextValue(String text){
        float value;
        if(text.isEmpty()){
            value =  MainGlobals.DF_ZERO;
        } else {
            value = Float.valueOf(String.valueOf(text));
        }
        return value;
    }

    protected void showView(View view) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(getAnimationDuration())
                .setListener(null);
    }

    protected void hideView(View view) {
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

    protected void rotateView(View view, boolean isOpening) {
        int rotate = getRotate(isOpening);
        Animation animation = AnimationUtils.loadAnimation(getContext(), rotate);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    private int getRotate(boolean isOpening) {
        if(isOpening) {
            return R.anim.rotate_open;
        } else {
            return R.anim.rotate_close;
        }
    }

    protected void manageExpandCardView(CardView cv, ImageView iv) {
        if(cv.getVisibility() == View.VISIBLE){
            hideView(cv);
            rotateView(iv, false);
        } else {
            showView(cv);
            rotateView(iv, true);
        }
    }

    protected void setTv(TextView tv, String text){
        tv.setText(text);
    }

    protected void setTv(TextView tv, int id){
        tv.setText(getString(id));
    }

    protected void setTv(TextView tv, float text, String unit) {
        String full = text + MainGlobals.SN_SPACE + unit;
        tv.setText(full);
    }

    protected void setTv(TextView tv, int id, int idArray){
        String[] array = getStringArray(idArray);
        tv.setText(array[id]);
    }

    protected void checkIfRvEmpty(RecyclerView rv, TextView tvEmpty) {
        if (rv.getAdapter() != null && rv.getAdapter().getItemCount() == 0) {
            showView(tvEmpty);
        }
    }
}
