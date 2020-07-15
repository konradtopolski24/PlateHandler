package com.fatiner.platehandler.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.RecipeAdapter;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.fragments.recipe.RecipeShowFragment;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.viewmodels.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends PrimaryFragment implements RecipeAdapter.RecipeListener {

    private HomeViewModel viewModel;
    private CompositeDisposable disposables;

    @BindView(R.id.cv_statistics) CardView cvStatistics;
    @BindView(R.id.cv_recent) CardView cvRecent;
    @BindView(R.id.iv_hd_statistics) ImageView ivHdStatistics;
    @BindView(R.id.iv_hd_recent) ImageView ivHdRecent;
    @BindView(R.id.tv_recipe) TextView tvRecipe;
    @BindView(R.id.tv_product) TextView tvProduct;
    @BindView(R.id.tv_empty) TextView tvEmpty;
    @BindView(R.id.rv_recent) RecyclerView rvRecent;

    @OnClick(R.id.cv_hd_statistics)
    void clickCvHdStatistics() {
        manageExpandCv(cvStatistics, ivHdStatistics);
    }

    @OnClick(R.id.cv_hd_recent)
    void clickCvHdRecent() {
        manageExpandCv(cvRecent, ivHdRecent);
    }

    @OnClick(R.id.iv_tt_statistics)
    void clickIvTtStatistics() {
        showDialog(R.string.hd_hm_statistics, R.string.tt_hm_statistics);
    }

    @OnClick(R.id.iv_tt_recent)
    void clickIvTtRecent() {
        showDialog(R.string.hd_hm_recent, R.string.tt_hm_recent);
    }

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOptions(R.id.it_home, R.string.tb_hm_welcome, false);
        initViewModelEssentials();
        initAction();
    }

    private void initViewModelEssentials() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        disposables = new CompositeDisposable();
    }

    private void initAction() {
        readAmount();
        setRecent();
    }

    private void setRecent() {
        if (SharedManager.isValueAvailable(getContext(), Shared.SR_HOME, Shared.KY_RECENT))
            observeGetRecipes();
    }

    private RecipeAdapter getRecipeAdapter(List<Recipe> recipes) {
        return new RecipeAdapter(getContext(), recipes, this);
    }

    private List<Integer> getRecentIds() {
        return TypeManager.jsonToRecent(SharedManager.getString(
                getContext(), Shared.SR_HOME, Shared.KY_RECENT));
    }

    private String getAmount(int amount) {
        return String.format(Locale.ENGLISH, Format.FM_AMOUNT, getString(R.string.ct_amount), amount);
    }

    private List<Recipe> getSortedRecent(List<Recipe> recipes) {
        List<Recipe> sortedRecipes = new ArrayList<>();
        for (Integer recent : getRecentIds()) {
            for (Recipe recipe : recipes) if (recent == recipe.getId()) sortedRecipes.add(recipe);
        }
        return sortedRecipes;
    }

    private void readAmount() {
        observeGetRowCount(tvRecipe, true);
        observeGetRowCount(tvProduct, false);
    }


    private void observeGetRecipes() {
        viewModel.getRecipes(getRecentIds())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRecentObserver());
    }

    private void observeGetRowCount(TextView tv, boolean isRecipe) {
        viewModel.getRowCount(isRecipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAmountObserver(tv));
    }

    private SingleObserver<List<Recipe>> getRecentObserver() {
        return new SingleObserver<List<Recipe>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(List<Recipe> recipes) {
                List<Recipe> sortRecipes = getSortedRecent(recipes);
                setRv(rvRecent, getManager(getColumnAmountChoose()), getRecipeAdapter(sortRecipes));
                changeRvSize(rvRecent);
                checkIfRvEmpty(rvRecent, tvEmpty);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    private SingleObserver<Integer> getAmountObserver(TextView tv) {
        return new SingleObserver<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(Integer amount) {
                setTv(tv, getAmount(amount));
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    @Override
    public void clickRecipe(int id) {
        resetRecipeDetails();
        setFragment(RecipeShowFragment.getInstance(id));
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }
}
