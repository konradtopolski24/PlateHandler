package com.fatiner.platehandler.fragments.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.IngredientAdapter;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.home.HomeFragment;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeCalculateFragment extends PrimaryFragment {

    @BindView(R.id.cv_kcal) CardView cvKcal;
    @BindView(R.id.cv_kj) CardView cvKj;
    @BindView(R.id.cv_ingredient) CardView cvIngredient;
    @BindView(R.id.iv_hd_kcal) ImageView ivHdKcal;
    @BindView(R.id.iv_hd_kj) ImageView ivHdKj;
    @BindView(R.id.iv_hd_ingredient) ImageView ivHdIngredient;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_total1) TextView tvTotal1;
    @BindView(R.id.tv_total2) TextView tvTotal2;
    @BindView(R.id.tv_serving1) TextView tvServing1;
    @BindView(R.id.tv_serving2) TextView tvServing2;
    @BindView(R.id.rv_ingredient) RecyclerView rvIngredient;

    @OnClick(R.id.cv_hd_kcal)
    void clickCvHdKcal() {
        manageExpandCv(cvKcal, ivHdKcal);
    }

    @OnClick(R.id.cv_hd_kj)
    void clickCvHdKj() {
        manageExpandCv(cvKj, ivHdKj);
    }

    @OnClick(R.id.cv_hd_ingredient)
    void clickCvHdIngredient() {
        manageExpandCv(cvIngredient, ivHdIngredient);
    }

    @OnClick(R.id.iv_tt_kcal)
    void clickIvTtKcal() {
        showDialog(R.string.hd_rp_cl_kcal, R.string.tt_rp_cl_kcal);
    }

    @OnClick(R.id.iv_tt_kj)
    void clickIvTtKj() {
        showDialog(R.string.hd_rp_cl_kj, R.string.tt_rp_cl_kj);
    }

    @OnClick(R.id.iv_tt_ingredient)
    void clickIvTtIngredient() {
        showDialog(R.string.hd_rp_cl_ingredient, R.string.tt_rp_cl_ingredient);
    }

    public static RecipeCalculateFragment getInstance() {
        return new RecipeCalculateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_recipe_calculate, container,
                false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(R.id.it_recipe, R.string.tb_rp_calculate, true);
        setViews();
    }

    private Recipe getRecipe() {
        return RecipeDetails.getRecipe();
    }

    private void setViews() {
        Recipe recipe = getRecipe();
        setTv(tvName, recipe.getName());
        if (isSizeZero()) zeroAction();
        else setCaloriesInfo();
        manageRv();
    }

    private void setCaloriesInfo() {
        Recipe recipe = getRecipe();
        int[] factorArray = getIntArray(R.array.tx_factor);
        setTv(tvTotal1, recipe.getTotalKcal(factorArray), Globals.UT_KCAL);
        setTv(tvServing1, recipe.getServingKcal(factorArray), Globals.UT_KCAL);
        setTv(tvTotal2, recipe.getTotalKj(factorArray), Globals.UT_KJ);
        setTv(tvServing2, recipe.getServingKj(factorArray), Globals.UT_KJ);
    }

    private void setEmptyInfo() {
        setTv(tvTotal1, Globals.SN_DASH);
        setTv(tvServing1, Globals.SN_DASH);
        setTv(tvTotal2, Globals.SN_DASH);
        setTv(tvServing2, Globals.SN_DASH);
    }

    private void zeroAction() {
        showShortToast(R.string.ts_empty);
        setEmptyInfo();
    }

    private void manageRv() {
        setRv(rvIngredient, getManager(getColumnAmountList()), getIngredientAdapter());
        changeRvSize(rvIngredient);
    }

    private IngredientAdapter getIngredientAdapter() {
        return new IngredientAdapter(getContext(), getRecipe().getIngredients(), null);
    }

    private boolean isSizeZero() {
        for (Ingredient ingredient : getRecipe().getIngredients()) {
            if (ingredient.getProduct().getSize() == Globals.DF_ZERO) return true;
        }
        return false;
    }
}
