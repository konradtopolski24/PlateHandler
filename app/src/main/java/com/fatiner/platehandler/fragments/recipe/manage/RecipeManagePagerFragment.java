package com.fatiner.platehandler.fragments.recipe.manage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.pager.RecipePagerAdapter;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.Step;
import com.fatiner.platehandler.viewmodels.recipe.RecipeManagePagerViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeManagePagerFragment extends PrimaryFragment {

    private RecipeManagePagerViewModel viewModel;
    private CompositeDisposable disposables;

    @BindView(R.id.vp_recipe)
    ViewPager vpRecipe;
    @BindView(R.id.tl_recipe)
    TabLayout tlRecipe;

    @OnClick(R.id.fab_finished)
    void clickFabFinished() {
        hideKeyboard();
        chooseFinished();
    }

    public static RecipeManagePagerFragment getInstance(boolean isEditing) {
        RecipeManagePagerFragment fragment = new RecipeManagePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Globals.BN_BOOL, isEditing);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_recipe_manage_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOptions(R.id.it_recipe, getToolbar(), false);
        initViewModelEssentials();
        setViews();
    }

    private void initViewModelEssentials() {
        viewModel = new ViewModelProvider(this).get(RecipeManagePagerViewModel.class);
        disposables = new CompositeDisposable();
    }

    private Recipe getRecipe() {
        return RecipeDetails.getRecipe();
    }

    private int getToolbar() {
        if (isEditing()) return R.string.tb_rp_edit;
        else return R.string.tb_rp_add;
    }

    private boolean isEditing() {
        return getBundle().getBoolean(Globals.BN_BOOL);
    }

    private void setViews() {
        setVp(vpRecipe, getRecipePagerAdapter());
        setTl(tlRecipe, vpRecipe);
    }

    private RecipePagerAdapter getRecipePagerAdapter() {
        return new RecipePagerAdapter(getChildFragmentManager(), getContext());
    }

    private void chooseDialog() {
        if (isEditing()) showDialog(R.string.dg_rp_edit, getDialogListener());
        else showDialog(R.string.dg_rp_add, getDialogListener());
    }

    private void chooseDbAction() {
        if (isEditing()) {
            int id = getRecipe().getId();
            manageIngredientsDb(id);
            manageStepsDb(id);
            observeUpdateRecipe();
        } else observeAddRecipe();
    }

    private void chooseFinished() {
        if (isIncomplete()) showShortToast(R.string.ts_recipe);
        else chooseDialog();
    }

    private boolean isIncomplete() {
        return isNameEmpty() || isAuthorEmpty() || isTimeZero()
                || isAmountZero() || isProductIdZero() || isStepListEmpty();
    }

    private boolean isNameEmpty() {
        return getRecipe().getName().equals(Globals.SN_EMPTY);
    }

    private boolean isAuthorEmpty() {
        return getRecipe().getAuthor().equals(Globals.SN_EMPTY);
    }

    private boolean isTimeZero() {
        return getRecipe().getTime().equals(Globals.TM_DEFAULT);
    }

    private boolean isAmountZero() {
        List<Ingredient> ingredients = getRecipe().getIngredients();
        for (Ingredient ingredient : ingredients)
            if (ingredient.getAmount() == Globals.DF_ZERO) return true;
        return false;
    }

    private boolean isProductIdZero() {
        List<Ingredient> ingredients = getRecipe().getIngredients();
        for (Ingredient ingredient : ingredients)
            if (ingredient.getProductId() == Globals.DF_ZERO) return true;
        return false;
    }

    private boolean isStepListEmpty() {
        return getRecipe().getSteps().isEmpty();
    }

    private void manageIngredientsDb(int id) {
        for (Ingredient ingredient : getRecipe().getIngredients()) {
            ingredient.setRecipeId(id);
            ingredient.setUsed(false);
        }
    }

    private void manageStepsDb(int id) {
        for (Step step : getRecipe().getSteps()) {
            step.setRecipeId(id);
            step.setDone(false);
        }
    }

    private DialogInterface.OnClickListener getDialogListener() {
        return (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    chooseDbAction();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
    }

    private void observeAddRecipe() {
        viewModel.addRecipe(getRecipe())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getInsertObserver());
    }

    private void observeAddRest() {
        viewModel.addRest(getRecipe())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRestObserver());
    }

    private void observeUpdateRecipe() {
        viewModel.updateRecipe(getRecipe())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUpdateObserver());
    }

    private SingleObserver<Long> getInsertObserver() {
        return new SingleObserver<Long>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(Long id) {
                getRecipe().setId(TypeManager.longToInt(id));
                manageIngredientsDb(TypeManager.longToInt(id));
                manageStepsDb(TypeManager.longToInt(id));
                observeAddRest();
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    private CompletableObserver getRestObserver() {
        return new CompletableObserver() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onComplete() {
                if (getRecipe().isPhotoChanged()) manageImageSaving(getRecipe().getPhoto(),
                        Globals.NM_RECIPE, getRecipe().getId());
                recipeSuccess(R.string.sb_rp_add);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    private CompletableObserver getUpdateObserver() {
        return new CompletableObserver() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onComplete() {
                if (getRecipe().isPhotoChanged()) manageImageSaving(getRecipe().getPhoto(),
                        Globals.NM_RECIPE, getRecipe().getId());
                recipeSuccess(R.string.sb_rp_edit);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }
}