package com.fatiner.platehandler.fragments.recipe.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.globals.MainGlobals;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageStepFragment extends PrimaryFragment {

    @BindView(R.id.et_instruction)
    EditText etInstruction;
    @BindView(R.id.tv_step)
    TextView tvStep;

    @OnClick(R.id.fab_done)
    public void onClickFloatAdd(){
        hideKeyboard();
        if(isStepCorrect()){
            Step step = new Step();
            step.setInstruction(etInstruction.getText().toString());
            setStepsInRecipeDetails(step);
            showShortSnack(R.string.snack_step_frag_recipe);
            popFragment();
        } else {
            showShortToast(R.string.toast_step_frag_recipe);
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
        setMenuItem(MainGlobals.ID_RECIPES_DRAW_MAIN);
        return view;
    }

    private void chooseToolbar(){
        if(isBundleNotEmpty()){
            setToolbarTitle(R.string.tool_edit_frag_mngstep);
        } else {
            setToolbarTitle(R.string.tool_add_frag_mngstep);
        }
    }

    private void chooseStartingAction(){
        if(isBundleNotEmpty()){
            setStepInfo();
        } else {
            String stepNo = getStepHeader(RecipeDetails.getRecipe().getSteps().size());
            setTextStep(stepNo);
        }
    }

    private void setStepInfo(){
        int id = getIntFromBundle(BundleGlobals.BUND_ID_FRAG_ADDSTEP);
        Step step = RecipeDetails.getRecipe().getSteps().get(id);
        String stepNo = getStepHeader(id);
        setTextStep(stepNo);
        setEditInstruction(step.getInstruction());
    }

    private void setStepsInRecipeDetails(Step step){
        if(isBundleNotEmpty()){
            int id = getIntFromBundle(BundleGlobals.BUND_ID_FRAG_ADDSTEP);
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

    private void setTextStep(String text){
        tvStep.setText(text);
    }

    private void setEditInstruction(String text){
        etInstruction.setText(text);
    }
}
