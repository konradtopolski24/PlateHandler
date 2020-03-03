package com.fatiner.platehandler.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.RecipeAdapter;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.fragments.recipe.RecipeShowFragment;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Recipe;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends PrimaryFragment implements RecipeAdapter.RecipeListener {

    @BindView(R.id.rv_recent) RecyclerView rvRecent;
    @BindView(R.id.cv_summary) CardView cvSummary;
    @BindView(R.id.cv_recent) CardView cvRecent;
    @BindView(R.id.iv_summary) ImageView ivSummary;
    @BindView(R.id.iv_recent) ImageView ivRecent;
    @BindView(R.id.tv_recipe) TextView tvRecipe;
    @BindView(R.id.tv_product) TextView tvProduct;
    @BindView(R.id.tv_empty) TextView tvEmpty;

    @OnClick(R.id.cv_hd_summary)
    void clickCvHdSummary() {
        manageExpandCv(cvSummary, ivSummary);
    }

    @OnClick(R.id.cv_hd_recent)
    void clickCvHdRecent() {
        manageExpandCv(cvRecent, ivRecent);
    }

    @OnClick(R.id.iv_tt_summary)
    void clickIvTtSummary() {
        showDialog(R.string.hd_hm_summary, R.string.tt_hm_summary);
    }

    @OnClick(R.id.iv_tt_recent)
    void clickIvTtRecent() {
        showDialog(R.string.hd_hm_recent, R.string.tt_hm_recent);
    }

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(this, view, R.id.it_home, R.string.tb_hm_welcome, false);
        readRecipeAmount();
        readProductAmount();
        setRecent();
        return view;
    }

    private void setRecent() {
        if(SharedManager.isValueAvailable(getContext(), Shared.SR_HOME, Shared.KY_RECENT))
            readRecent();
    }

    private RecipeAdapter getRecipeAdapter(List<Recipe> recipes) {
        return new RecipeAdapter(getContext(), recipes, this);
    }

    private List<Integer> getRecent() {
        return TypeManager.jsonToRecent(SharedManager.getString(
                getContext(), Shared.SR_HOME, Shared.KY_RECENT));
    }

    private String getAmount(int amount, int id) {
        return String.format(Locale.ENGLISH, Format.FM_AMOUNT, getString(id), amount);
    }

    //Read Recent
    private void readRecent() {
        PlateHandlerDatabase db = getDb(getContext());
        Single<List<Recipe>> single = db.getRecipeDAO().getRecipes(getRecent());
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadRecentObserver());
    }

    private DisposableSingleObserver<List<Recipe>> getReadRecentObserver() {
        return new DisposableSingleObserver<List<Recipe>>() {

            @Override
            public void onSuccess(List<Recipe> recipes) {
                setRv(rvRecent, getManager(Globals.GL_TWO), getRecipeAdapter(recipes));
                changeRvSize(rvRecent);
                checkIfRvEmpty(rvRecent, tvEmpty);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    //Read Amount
    private void readRecipeAmount() {
        PlateHandlerDatabase db = getDb(getContext());
        Single<Integer> single = db.getRecipeDAO().getRowCount();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadAmountObserver(tvRecipe, R.string.nv_recipe));
    }

    private void readProductAmount() {
        PlateHandlerDatabase db = getDb(getContext());
        Single<Integer> single = db.getProductDAO().getRowCount();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadAmountObserver(tvProduct, R.string.nv_product));
    }

    private DisposableSingleObserver<Integer> getReadAmountObserver(TextView tv, int id) {
        return new DisposableSingleObserver<Integer>() {

            @Override
            public void onSuccess(Integer amount) {
                setTv(tv, getAmount(amount, id));
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
        setFragment(new RecipeShowFragment(), id, Globals.BN_INT);
    }
}
