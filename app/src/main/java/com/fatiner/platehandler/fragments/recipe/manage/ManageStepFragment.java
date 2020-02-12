package com.fatiner.platehandler.fragments.recipe.manage;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageStepFragment extends PrimaryFragment {

    @BindView(R.id.et_instruction)
    EditText etInstruction;
    @BindView(R.id.til_step)
    TextInputLayout tilStep;

    @OnClick(R.id.fab_finished)
    public void onClickFabDone(){
        hideKeyboard();
        if(isStepCorrect()){
            Step step = new Step();
            step.setInstruction(etInstruction.getText().toString());
            setStepsInRecipeDetails(step);
            showShortSnack(R.string.sb_st_add);
            popFragment();
        } else {
            showShortToast(R.string.ts_step);
        }
    }

    public ManageStepFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_step, container, false);
        ButterKnife.bind(this, view);
        chooseStartingAction();
        chooseToolbar();
        setMenuItem(MainGlobals.ID_RECIPE);
        return view;
    }

    private void chooseToolbar(){
        if(isBundleNotEmpty()){
            setToolbarTitle(R.string.tb_st_edit);
        } else {
            setToolbarTitle(R.string.tb_st_add);
        }
    }

    private void chooseStartingAction(){
        if(isBundleNotEmpty()){
            setStepInfo();
        } else {
            String stepNo = getStepHeader(RecipeDetails.getRecipe().getSteps().size());
            setHint(tilStep, stepNo);
        }
    }

    private void setStepInfo(){
        int id = getIntFromBundle(MainGlobals.BN_ID);
        Step step = RecipeDetails.getRecipe().getSteps().get(id);
        String stepNo = getStepHeader(id);
        setHint(tilStep, stepNo);
        setEditInstruction(step.getInstruction());
    }

    private void setStepsInRecipeDetails(Step step){
        if(isBundleNotEmpty()){
            int id = getIntFromBundle(MainGlobals.BN_ID);
            RecipeDetails.getRecipe().getSteps().remove(id);
            RecipeDetails.getRecipe().getSteps().add(id, step);
        } else {
            RecipeDetails.getRecipe().getSteps().add(step);
        }
    }

    private boolean isStepCorrect(){
        return isStepTextNotEmpty();
    }

    private boolean isStepTextNotEmpty(){
        return !etInstruction.getText().toString().isEmpty();
    }

    private void setHint(TextInputLayout til, String hint){
        til.setHint(hint);
    }

    private void setEditInstruction(String text){
        etInstruction.setText(text);
    }
}
