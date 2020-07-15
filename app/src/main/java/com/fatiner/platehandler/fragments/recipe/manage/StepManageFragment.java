package com.fatiner.platehandler.fragments.recipe.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.models.Step;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class StepManageFragment extends PrimaryFragment {

    @BindView(R.id.til_content) TextInputLayout tilContent;
    @BindView(R.id.et_content) EditText etContent;

    @OnTextChanged(R.id.et_content)
    void changedEtContent() {
        setError(tilContent, R.string.er_st_content, isContentEmpty());
    }

    @OnClick(R.id.fab_finished)
    void clickFabFinished() {
        hideKeyboard();
        endingAction();
    }

    @OnClick(R.id.iv_tt_data)
    void clickIvTtData() {
        showDialog(R.string.hd_st_mg_data, R.string.tt_st_mg_data);
    }

    public static StepManageFragment getInstance(int position) {
        StepManageFragment fragment = new StepManageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Globals.BN_INT, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_step_manage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOptions(R.id.it_recipe, getToolbar(), false);
        initAction();
    }

    private List<Step> getSteps() {
        return RecipeDetails.getRecipe().getSteps();
    }

    private int getToolbar() {
        if (isPosition()) return R.string.tb_st_edit;
        else return R.string.tb_st_add;
    }

    private boolean isPosition() {
        int position = getBundle().getInt(Globals.BN_INT);
        return position != Globals.DF_DECREMENT;
    }

    private void initAction() {
        if (isPosition()) setViews();
        else setHint(tilContent, getStepHint(getSteps().size()));
        setError(tilContent, R.string.er_st_content, isContentEmpty());
    }

    private void setViews() {
        int id = getIntFromBundle();
        Step step = getSteps().get(id);
        setHint(tilContent, getStepHint(id));
        setEt(etContent, step.getContent());
    }

    private String getStepHint(int size) {
        int id = size + Globals.DF_INCREMENT;
        return String.format(Locale.ENGLISH, Format.FM_NUMBERED, getString(R.string.ct_step), id);
    }

    private void setStepInDetails(Step step) {
        if (isPosition()) {
            int id = getIntFromBundle();
            getSteps().remove(id);
            getSteps().add(id, step);
        } else getSteps().add(step);
    }

    private boolean isContentEmpty() {
        return etContent.getText().toString().isEmpty();
    }

    private void endingAction() {
        if (isContentEmpty()) showShortToast(R.string.ts_step);
        else correctAction();
    }

    private void correctAction() {
        Step step = new Step();
        step.setContent(etContent.getText().toString());
        setStepInDetails(step);
        showShortSnack(R.string.sb_st_add);
        popFragment();
    }
}
