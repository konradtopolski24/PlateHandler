package com.fatiner.platehandler.fragments.recipe.manage;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.StepAdapter;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageInstructionsFragment extends PrimaryFragment {

    @BindView(R.id.rv_steps)
    RecyclerView recycSteps;

    @OnClick(R.id.bt_add)
    public void onClickButtAdd(){
        setFragment(new ManageStepFragment());
    }

    public ManageInstructionsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_instructions, container, false);
        ButterKnife.bind(this, view);
        hideKeyboard();
        setMenuItem(MainGlobals.ID_RECIPES_DRAW_MAIN);
        setRecyclerView(
                recycSteps,
                getGridLayoutManager(MainGlobals.RECYC_SPAN_FRAG_ADDSTEP),
                new StepAdapter(getContext(),
                        RecipeDetails.getRecipe().getSteps(), false));
        return view;
    }
}
