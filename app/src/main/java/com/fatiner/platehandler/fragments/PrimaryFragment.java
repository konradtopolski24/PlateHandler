package com.fatiner.platehandler.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import android.widget.Toast;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.globals.ImageGlobals;
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
        int actualId = id + MainGlobals.INT_INCREMENT_VAR_INIT;
        return getString(R.string.tv_step) + MainGlobals.STR_SPACE_OBJ_INIT + actualId + MainGlobals.STR_COLON_OBJ_INIT;
    }

    protected void selectPhoto(){
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, ImageGlobals.REQ_PHOTO_FRAG_PRIMARY);
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

    protected float getKcal(float value, int factor){
        return value * factor;
    }

    protected float getKj(float value, int factor){
        return value * factor * MainGlobals.KJ_FACTOR_OBJ_PROD;
    }

    protected String getOrderByProducts(){
        return DbGlobals.COL_NAME_TAB_PRODUCTS + " ASC";
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
            value =  MainGlobals.INT_STARTING_VAR_INIT;
        } else {
            value = Float.valueOf(String.valueOf(text));
        }
        return value;
    }
}
