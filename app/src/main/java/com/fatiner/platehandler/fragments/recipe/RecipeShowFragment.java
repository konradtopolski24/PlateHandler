package com.fatiner.platehandler.fragments.recipe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.IngredientAdapter;
import com.fatiner.platehandler.adapters.recyclerview.StepAdapter;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.fragments.recipe.manage.RecipeManagePagerFragment;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.RecipeComplete;
import com.fatiner.platehandler.models.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RecipeShowFragment extends PrimaryFragment implements
        IngredientAdapter.IngredientListener, StepAdapter.StepListener {

    @BindView(R.id.cv_data) CardView cvData;
    @BindView(R.id.cv_ingredient) CardView cvIngredient;
    @BindView(R.id.cv_step) CardView cvStep;
    @BindView(R.id.iv_hd_data) ImageView ivHdData;
    @BindView(R.id.iv_hd_ingredient) ImageView ivHdIngredient;
    @BindView(R.id.iv_hd_step) ImageView ivHdStep;
    @BindView(R.id.iv_photo) ImageView ivPhoto;
    @BindView(R.id.iv_country) ImageView ivCountry;
    @BindView(R.id.iv_type) ImageView ivType;
    @BindView(R.id.iv_preference) ImageView ivPreference;
    @BindViews({
            R.id.iv_spiciness0,
            R.id.iv_spiciness1,
            R.id.iv_spiciness2,
            R.id.iv_spiciness3}) List<ImageView> ivSpicinessList;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_author) TextView tvAuthor;
    @BindView(R.id.tv_serving) TextView tvServing;
    @BindView(R.id.tv_time) TextView tvTime;
    @BindView(R.id.tv_difficulty) TextView tvDifficulty;
    @BindView(R.id.tv_country) TextView tvCountry;
    @BindView(R.id.tv_type) TextView tvType;
    @BindView(R.id.tv_preference) TextView tvPreference;
    @BindView(R.id.rv_ingredient) RecyclerView rvIngredients;
    @BindView(R.id.rv_step) RecyclerView rvStep;
    @BindView(R.id.cb_favorite) CheckBox cbFavorite;

    @OnCheckedChanged(R.id.cb_favorite)
    void checkedCbFavorite(boolean checked) {
        getRecipe().setFavorite(checked);
        updateFavorite(checked);
    }

    @OnClick(R.id.cv_hd_data)
    void clickCvHdData() {
        manageExpandCv(cvData, ivHdData);
    }

    @OnClick(R.id.cv_hd_ingredient)
    void clickCvHdIngredient() {
        manageExpandCv(cvIngredient, ivHdIngredient);
    }

    @OnClick(R.id.cv_hd_step)
    void clickCvHdStep() {
        manageExpandCv(cvStep, ivHdStep);
    }

    @OnClick(R.id.iv_tt_data)
    void clickIvTtData() {
        showDialog(R.string.hd_rp_sh_data, R.string.tt_rp_sh_data);
    }

    @OnClick(R.id.iv_tt_ingredient)
    void clickIvTtIngredient() {
        showDialog(R.string.hd_rp_sh_ingredient, R.string.tt_rp_sh_ingredient);
    }

    @OnClick(R.id.iv_tt_step)
    void clickIvTtStep() {
        showDialog(R.string.hd_rp_sh_step, R.string.tt_rp_sh_step);
    }

    public RecipeShowFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_recipe_show, container, false);
        init(this, view, R.id.it_recipe, R.string.tb_rp_show, true);
        initAction();
        return view;
    }

    private Recipe getRecipe() {
        return RecipeDetails.getRecipe();
    }

    private void initAction() {
        resetRecipeDetails();
        checkId();
    }

    private void checkId() {
        if (isId()) readRecipe();
    }

    private boolean isId() {
        return isValueInBundle(Globals.BN_INT);
    }

    private void setProductsInIngredients(List<Product> products) {
        for (Ingredient ingredient : getRecipe().getIngredients()) {
            for (Product product : products) {
                if (ingredient.getProductId() == product.getId()) {
                    ingredient.setProduct(product);
                    break;
                }
            }
        }
    }

    private List<Integer> getProductIds() {
        List<Integer> ids = new ArrayList<>();
        for (Ingredient ingredient : getRecipe().getIngredients())
            ids.add(ingredient.getProductId());
        return ids;
    }

    private IngredientAdapter getIngredientAdapter() {
        return new IngredientAdapter(getContext(), getRecipe().getIngredients(), this);
    }

    private StepAdapter getStepAdapter() {
        return new StepAdapter(getContext(), getRecipe().getSteps(), this, true);
    }

    private int getRecipeId() {
        return getIntFromBundle();
    }

    private void setRecipeDetails(Recipe recipe, List<Ingredient> ingredients, List<Step> steps) {
        getRecipe().setId(recipe.getId());
        getRecipe().setName(recipe.getName());
        getRecipe().setAuthor(recipe.getAuthor());
        getRecipe().setServing(recipe.getServing());
        getRecipe().setTime(recipe.getTime());
        getRecipe().setDifficulty(recipe.getDifficulty());
        getRecipe().setSpiciness(recipe.getSpiciness());
        getRecipe().setCountry(recipe.getCountry());
        getRecipe().setType(recipe.getType());
        getRecipe().setPreference(recipe.getPreference());
        getRecipe().setFavorite(recipe.getFavorite());
        getRecipe().setIngredients(ingredients);
        getRecipe().setSteps(steps);
        getRecipe().setPhoto(getImage(Globals.NM_RECIPE, recipe.getId()));
    }

    private void setViews() {
        setRecipeInfo();
        manageRv();
    }

    private void setRecipeInfo() {
        Recipe recipe = getRecipe();
        setTv(tvName, recipe.getName());
        setTv(tvAuthor, recipe.getAuthor());
        setTv(tvServing, String.valueOf(recipe.getServing()));
        setTv(tvTime, recipe.getTime());
        setTv(tvDifficulty, recipe.getDifficulty(), R.array.tx_difficulty);
        setTv(tvCountry, recipe.getCountry(), R.array.tx_country);
        setTv(tvType, recipe.getType(), R.array.tx_recipe);
        setTv(tvPreference, TypeManager.boolToInt(recipe.getPreference()), R.array.tx_preference);
        setIvList(ivSpicinessList, recipe.getSpiciness());
        setIv(ivCountry, recipe.getCountry(), R.array.dw_country);
        setIv(ivType, recipe.getType(), R.array.dw_recipe);
        setIv(ivPreference, TypeManager.boolToInt(recipe.getPreference()), R.array.dw_preference);
        setCb(cbFavorite, recipe.getFavorite());
        setIv(ivPhoto, recipe.getPhoto());
    }

    private void manageRv() {
        setRv(rvIngredients, getManager(getColumnAmountList()), getIngredientAdapter());
        setRv(rvStep, getManager(getColumnAmountList()), getStepAdapter());
        changeRvSize(rvIngredients);
        changeRvSize(rvStep);
    }

    private void setRecentRecipe() {
        if (SharedManager.isValueAvailable(getContext(), Shared.SR_HOME, Shared.KY_RECENT))
            recentExistsAction();
        else recentUnavailableAction();
    }

    private void recentExistsAction() {
        List<Integer> recent = getRecent();
        checkIfContains(recent);
        checkIfFull(recent);
        addNewRecent(recent);
        setRecentInShared(recent);
    }

    private void recentUnavailableAction() {
        List<Integer> recent = new ArrayList<>();
        addNewRecent(recent);
        setRecentInShared(recent);
    }

    private List<Integer> getRecent() {
        return TypeManager.jsonToRecent(
                SharedManager.getString(getContext(), Shared.SR_HOME, Shared.KY_RECENT));
    }

    private void checkIfContains(List<Integer> recent) {
        if (recent.contains(getRecipeId())) recent.remove(getPosition(recent));
    }

    private int getPosition(List<Integer> recent) {
        return recent.indexOf(getRecipeId());
    }

    private void checkIfFull(List<Integer> recent) {
        if (recent.size() == Globals.RC_MAX)
            recent.remove(recent.size() + Globals.DF_DECREMENT);
    }

    private void addNewRecent(List<Integer> recent) {
        recent.add(Globals.DF_ZERO, getRecipeId());
    }

    private void setRecentInShared(List<Integer> recent) {
        SharedManager.setValue(getContext(), Shared.SR_HOME, Shared.KY_RECENT,
                TypeManager.recentToJson(recent));
    }

    private DialogInterface.OnClickListener getDialogListener() {
        return (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    deleteRecipe();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
    }

    private void deleteRecentId() {
        if (SharedManager.isValueAvailable(getContext(), Shared.SR_HOME, Shared.KY_RECENT)) {
            List<Integer> recent = getRecent();
            Integer id = getRecipe().getId();
            recent.remove(id);
            SharedManager.setValue(getContext(),
                    Shared.SR_HOME, Shared.KY_RECENT, TypeManager.recentToJson(recent));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rp_show, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.it_calculate:
                setFragment(new RecipeCalculateFragment());
                return true;
            case R.id.it_edit:
                setFragment(new RecipeManagePagerFragment(), true, Globals.BN_BOOL);
                return true;
            case R.id.it_remove:
                showDialog(R.string.dg_rp_remove, getDialogListener());
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    //Read Recipe
    private void readRecipe() {
        PlateHandlerDatabase db = getDb(getContext());
        int id = getIntFromBundle();
        Single<RecipeComplete> single = db.getRecipeDAO().getCompleteRecipe(id);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadRecipeObserver());
    }

    private DisposableSingleObserver<RecipeComplete> getReadRecipeObserver() {
        return new DisposableSingleObserver<RecipeComplete>() {

            @Override
            public void onSuccess(RecipeComplete complete) {
                setRecipeDetails(complete.recipe, complete.ingredients, complete.steps);
                readProducts();
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    //Read Products
    private void readProducts() {
        PlateHandlerDatabase db = getDb(getContext());
        Single<List<Product>> single = db.getProductDAO().getProducts(getProductIds());
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadProductsObserver());
    }

    private DisposableSingleObserver<List<Product>> getReadProductsObserver() {
        return new DisposableSingleObserver<List<Product>>() {

            @Override
            public void onSuccess(List<Product> products) {
                setProductsInIngredients(products);
                setViews();
                setRecentRecipe();
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    //Update Favorite
    private void updateFavorite(boolean checked) {
        getCompletable(checked).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUpdateObserver());
    }

    private Completable getCompletable(boolean checked) {
        return Completable.fromAction(() -> {
            PlateHandlerDatabase db = getDb(getContext());
            db.getRecipeDAO().updateFavorite(getRecipe().getId(), checked);
        });
    }

    private DisposableCompletableObserver getUpdateObserver() {
        return new DisposableCompletableObserver() {

            @Override
            public void onComplete() {}

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    //Update Ingredient Used
    private void updateIngredientIsUsed(int id, boolean checked) {
        getIngredientCompletable(id, checked).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUpdateObserver());
    }

    private Completable getIngredientCompletable(int id, boolean checked) {
        return Completable.fromAction(() -> {
            PlateHandlerDatabase db = getDb(getContext());
            db.getIngredientDAO().updateIsUsed(id, checked);
        });
    }

    //Update Step Done
    private void updateStepIsDone(int id, boolean checked) {
        getStepCompletable(id, checked).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUpdateObserver());
    }

    private Completable getStepCompletable(int id, boolean checked) {
        return Completable.fromAction(() -> {
            PlateHandlerDatabase db = getDb(getContext());
            db.getStepDAO().updateIsDone(id, checked);
        });
    }

    //Delete Recipe
    private void deleteRecipe() {
        getCompletable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getDeleteRecipeObserver());
    }

    private Completable getCompletable() {
        return Completable.fromAction(() -> {
            PlateHandlerDatabase db = getDb(getContext());
            db.getRecipeDAO().deleteRecipe(getRecipe());
            db.getIngredientDAO().deleteIngredients(getRecipe().getIngredients());
            db.getStepDAO().deleteSteps(getRecipe().getSteps());
        });
    }

    private DisposableCompletableObserver getDeleteRecipeObserver() {
        return new DisposableCompletableObserver() {

            @Override
            public void onComplete() {
                deleteRecentId();
                removeImage(Globals.NM_RECIPE, getRecipe().getId());
                recipeSuccess(R.string.sb_rp_remove);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    @Override
    public void setUsed(int id, boolean isDone) {
        updateIngredientIsUsed(id, isDone);
    }

    @Override
    public void setDone(int id, boolean isDone) {
        updateStepIsDone(id, isDone);
    }

    @Override
    public void editStep(int position) {}

    @Override
    public void removeStep(int position) {}
}
