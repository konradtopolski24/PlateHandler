package com.fatiner.platehandler.fragments.recipe.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.StepAdapter;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeManageStepFragment extends PrimaryFragment implements StepAdapter.StepListener {

    @BindView(R.id.rv_step) RecyclerView rvStep;
    @BindView(R.id.tv_empty) TextView tvEmpty;

    @OnClick(R.id.bt_add)
    void clickBtAdd() {
        setFragment(new StepManageFragment());
    }

    @OnClick(R.id.iv_tt_add)
    void clickIvTtAdd() {
        showDialog(R.string.hd_st_mg_add, R.string.tt_st_mg_add);
    }

    @OnClick(R.id.iv_tt_current)
    void clickIvTtCurrent() {
        showDialog(R.string.hd_st_mg_current, R.string.tt_st_mg_current);
    }

    public RecipeManageStepFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_recipe_manage_step, container, false);
        ButterKnife.bind(this, view);
        initAction();
        return view;
    }

    private List<Step> getSteps() {
        return RecipeDetails.getRecipe().getSteps();
    }

    private void initAction() {
        hideKeyboard();
        setViews();
    }

    private void setViews() {
        setRv(rvStep, getManager(getColumnAmountList()), getStepAdapter());
        checkIfRvEmpty(rvStep, tvEmpty);
    }

    private StepAdapter getStepAdapter() {
        return new StepAdapter(getContext(), getSteps(),this, false);
    }

    private RecyclerView.Adapter getAdapter() {
        return rvStep.getAdapter();
    }

    @Override
    public void setDone(int id, boolean checked) {}

    @Override
    public void editStep(int position) {
        setFragment(new StepManageFragment(), position, Globals.BN_INT);
    }

    @Override
    public void removeStep(int position) {
        getSteps().remove(position);
        getAdapter().notifyItemRemoved(position);
        checkIfRvEmpty(rvStep, tvEmpty);
    }
}
