package com.fatiner.platehandler.fragments.recipe.manage;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.RecipesPagerAdapter;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.ImageManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbSuccessManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageRecipeFragment extends PrimaryFragment {

    @BindView(R.id.vp_recipe)
    ViewPager vpRecipe;
    @BindView(R.id.tl_recipe)
    TabLayout tlRecipe;

    @OnClick(R.id.fab_done)
    public void onClickFloatAdd(){
        hideKeyboard();
        if(RecipeDetails.isRecipeCorrect()){
            chooseDialog();
        } else {
            showShortToast(R.string.toast_incomplete_frag_recipe);
        }
    }

    private void chooseDatabaseAction(){
        if(isBundleNotEmpty()){
            new AsyncUpdateRecipe().execute();
        } else {
            new AsyncInsertRecipe().execute();
        }
    }

    public ManageRecipeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_recipe, container, false);
        ButterKnife.bind(this, view);
        chooseToolbar();
        setMenuItem(MainGlobals.ID_RECIPES_DRAW_MAIN);
        setPagerAdapter();
        setupTabLayoutWithViewPager();
        return view;
    }

    private void chooseToolbar(){
        if(isBundleNotEmpty()){
            setToolbarTitle(R.string.tool_edit_frag_mngrecp);
        } else {
            setToolbarTitle(R.string.tool_add_frag_mngrecp);
        }
    }

    private void chooseDialog(){
        if(isBundleNotEmpty()){
            showAlertDialog(R.string.dial_edit_frag_recipe, getDialogListener());
        } else {
            showAlertDialog(R.string.dial_add_frag_recipe, getDialogListener());
        }
    }

    private void setPagerAdapter(){
        vpRecipe.setAdapter(new RecipesPagerAdapter(getChildFragmentManager(), getContext()));
    }

    private void setupTabLayoutWithViewPager(){
        tlRecipe.setupWithViewPager(vpRecipe);
    }

    private DialogInterface.OnClickListener getDialogListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        chooseDatabaseAction();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }

    private void manageImageRecipeSaving(){
        if(RecipeDetails.getRecipe().getEncodedImage() == null){
            ImageManager.deleteImage(ImageManager.getImageRecipeName(RecipeDetails.getRecipe().getId()));
        } else {
            ImageManager.saveImage(
                    TypeManager.base64StringToBitmap(
                            RecipeDetails.getRecipe().getEncodedImage()),
                    ImageManager.getImageRecipeName(RecipeDetails.getRecipe().getId()));
        }
    }

    private class AsyncInsertRecipe extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.insertedRecipe(getContext());
        }

        protected void onPostExecute(Boolean success){
            if(success){
                new AsyncReadRecipeId().execute();
            } else {
                showShortToast(R.string.toast_fail_async_insrec);
            }
        }
    }

    private class AsyncReadRecipeId extends AsyncTask<Void, Void, Boolean> {

        private int[] idRecipe;

        protected void onPreExecute(){
            idRecipe = new int[MainGlobals.INT_INCREMENT_VAR_INIT];
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.readRecipeId(getContext(), idRecipe);
        }

        protected void onPostExecute(Boolean success){
            if(success){
                ImageManager.saveImage(
                        TypeManager.base64StringToBitmap(RecipeDetails.getRecipe().getEncodedImage()),
                        ImageManager.getImageRecipeName(idRecipe[MainGlobals.INT_STARTING_VAR_INIT]));
                recipeSuccess(R.string.snack_added_frag_recipe);
            } else {
                showShortToast(R.string.toast_fail_async_readprodid);
            }
        }
    }

    private class AsyncUpdateRecipe extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.updatedRecipe(getContext());
        }

        protected void onPostExecute(Boolean success){
            if(success){
                new AsyncReadAuthors().execute();
            } else {
                showShortToast(R.string.toast_fail_async_updrec);
            }
        }
    }

    private class AsyncReadAuthors extends AsyncTask<Void, Void, Boolean>{

        ArrayList<String> authors;

        protected void onPreExecute(){
            authors = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.readAuthors(getContext(), authors);
        }

        protected void onPostExecute(Boolean success){
            if(success){
                removeUnavailableAuthor(authors);
                manageImageRecipeSaving();
                productSuccess(R.string.snack_updated_frag_recipe);
            } else {
                showShortToast(R.string.toast_fail_async_insrec);
            }
        }
    }
}
