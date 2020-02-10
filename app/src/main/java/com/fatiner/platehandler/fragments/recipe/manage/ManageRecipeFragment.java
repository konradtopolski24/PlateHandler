package com.fatiner.platehandler.fragments.recipe.manage;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
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
import com.fatiner.platehandler.managers.database.DbOperations;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageRecipeFragment extends PrimaryFragment {

    @BindView(R.id.vp_recipe)
    ViewPager vpRecipe;
    @BindView(R.id.tl_recipe)
    TabLayout tlRecipe;

    @OnClick(R.id.fab_finished)
    public void onClickFabDone(){
        hideKeyboard();
        if(RecipeDetails.isRecipeCorrect()){
            chooseDialog();
        } else {
            showShortToast(R.string.ts_recipe);
        }
    }

    private void chooseDatabaseAction(){
        if(isBundleNotEmpty()){
            new AsyncManageRecipe().execute(Type.UPDATE);
        } else {
            new AsyncManageRecipe().execute(Type.INSERT);
        }
    }

    public ManageRecipeFragment() {}

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
            setToolbarTitle(R.string.tb_rp_edit);
        } else {
            setToolbarTitle(R.string.tb_rp_add);
        }
    }

    private void chooseDialog(){
        if(isBundleNotEmpty()){
            showAlertDialog(R.string.dg_rp_edit, getDialogListener());
        } else {
            showAlertDialog(R.string.dg_rp_add, getDialogListener());
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

    private class AsyncManageRecipe extends AsyncTask<Type, Void, Boolean>{

        private Type type;
        private ArrayList<String> authors;
        private int[] idRecipe;

        protected void onPreExecute(){
            idRecipe = new int[MainGlobals.INT_INCREMENT_VAR_INIT];
            authors = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Type... types) {
            type = types[MainGlobals.INT_STARTING_VAR_INIT];

            try{
                switch(type) {
                    case INSERT:
                        DbOperations.insertedRecipe(getContext(), RecipeDetails.getRecipe(), false);
                        DbOperations.readRecipeId(getContext(), idRecipe);
                        break;
                    case UPDATE:
                        DbOperations.updatedRecipe(getContext(), RecipeDetails.getRecipe());
                        DbOperations.readAuthors(getContext(), authors);
                        break;
                }
                return true;
            }catch (SQLiteException e){
                showShortToast(R.string.ts_database);
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(success){
                switch(type) {
                    case INSERT:
                        insertRecipeFinished(idRecipe);
                        break;
                    case UPDATE:
                        updateRecipeFinished(authors);
                        break;
                }
            }
        }
    }

    private void insertRecipeFinished(int[] idRecipe) {
        ImageManager.saveImage(
                TypeManager.base64StringToBitmap(RecipeDetails.getRecipe().getEncodedImage()),
                ImageManager.getImageRecipeName(idRecipe[MainGlobals.INT_STARTING_VAR_INIT]));
        recipeSuccess(R.string.sb_rp_add);
    }

    private void updateRecipeFinished(ArrayList<String> authors) {
        removeUnavailableAuthor(authors);
        manageImageRecipeSaving();
        productSuccess(R.string.sb_rp_edit);
    }

    private enum Type {
        INSERT, UPDATE
    }
}