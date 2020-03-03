package com.fatiner.platehandler.fragments.recipe.manage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.RecipePagerAdapter;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.Step;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class RecipeManagePagerFragment extends PrimaryFragment {

    @BindView(R.id.vp_recipe) ViewPager vpRecipe;
    @BindView(R.id.tl_recipe) TabLayout tlRecipe;

    @OnClick(R.id.fab_finished)
    void clickFabFinished() {
        hideKeyboard();
        chooseFinished();
    }

    public RecipeManagePagerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_recipe_manage_pager, container, false);
        init(this, view, R.id.it_recipe, getToolbar(), false);
        setViews();
        return view;
    }

    private Recipe getRecipe() {
        return RecipeDetails.getRecipe();
    }

    private int getToolbar() {
        if(isEditing()) return R.string.tb_rp_edit;
        else return R.string.tb_rp_add;
    }

    private boolean isEditing() {
        return isValueInBundle(Globals.BN_BOOL);
    }

    private void setViews() {
        setVp(vpRecipe, getRecipePagerAdapter());
        setTl(tlRecipe, vpRecipe);
    }

    private RecipePagerAdapter getRecipePagerAdapter() {
        return new RecipePagerAdapter(getChildFragmentManager(), getContext());
    }

    private void chooseDialog() {
        if(isEditing()) showDialog(R.string.dg_rp_edit, getDialogListener());
        else showDialog(R.string.dg_rp_add, getDialogListener());
    }

    private void chooseDbAction() {
        if(isEditing()) updateRecipe();
        else insertRecipe();
    }

    private void chooseFinished() {
        if(RecipeDetails.isRecipeCorrect()) chooseDialog();
        else showShortToast(R.string.ts_recipe);
    }

    private void setRecipeIdInIngredients(int id) {
        for(Ingredient ingredient : RecipeDetails.getRecipe().getIngredients())
            ingredient.setRecipeId(id);
    }

    private void setRecipeIdInSteps(int id) {
        for(Step step : RecipeDetails.getRecipe().getSteps())
            step.setRecipeId(id);
    }

    //Insert Recipe
    private void insertRecipe() {
        getInsertCompletable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAddRecipeObserver());
    }

    private Completable getInsertCompletable() {
        return Completable.fromAction(() -> {
            PlateHandlerDatabase db = getDb(getContext());
            long id = db.getRecipeDAO().addRecipe(RecipeDetails.getRecipe());
            setRecipeIdInIngredients(TypeManager.longToInt(id));
            setRecipeIdInSteps(TypeManager.longToInt(id));
            db.getIngredientDAO().addIngredients(RecipeDetails.getRecipe().getIngredients());
            db.getStepDAO().addSteps(RecipeDetails.getRecipe().getSteps());
        });
    }

    private DisposableCompletableObserver getAddRecipeObserver() {
        return new DisposableCompletableObserver() {

            @Override
            public void onComplete() {
                recipeSuccess(R.string.sb_rp_add);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    //Update Recipe
    private void updateRecipe() {
        getUpdateCompletable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUpdateRecipeObserver());
    }

    private Completable getUpdateCompletable() {
        return Completable.fromAction(() -> {
            PlateHandlerDatabase db = getDb(getContext());
            int id = getRecipe().getId();
            setRecipeIdInIngredients(id);
            setRecipeIdInSteps(id);
            db.getRecipeDAO().updateRecipe(getRecipe());
            db.getIngredientDAO().deleteIngredients(id);
            db.getStepDAO().deleteSteps(id);
            db.getIngredientDAO().addIngredients(getRecipe().getIngredients());
            db.getStepDAO().addSteps(getRecipe().getSteps());
        });
    }

    private DisposableCompletableObserver getUpdateRecipeObserver() {
        return new DisposableCompletableObserver() {

            @Override
            public void onComplete() {
                recipeSuccess(R.string.sb_rp_edit);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    private DialogInterface.OnClickListener getDialogListener() {
        return (dialog, which) -> {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    chooseDbAction();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
    }

    //Image
    /*private void manageImageRecipeSaving() {
        if(RecipeDetails.getRecipe().getEncodedImage() == null) {
            ImageManager.deleteImage(ImageManager.getImageRecipeName(RecipeDetails.getRecipe().getId()));
        } else {
            ImageManager.saveImage(
                    TypeManager.base64StringToBitmap(
                            RecipeDetails.getRecipe().getEncodedImage()),
                    ImageManager.getImageRecipeName(RecipeDetails.getRecipe().getId()));
        }
    }

    private void setImage(int[] idRecipe) {
        ImageManager.saveImage(
                TypeManager.base64StringToBitmap(RecipeDetails.getRecipe().getEncodedImage()),
                ImageManager.getImageRecipeName(idRecipe[Globals.DF_ZERO]));
    }*/
}