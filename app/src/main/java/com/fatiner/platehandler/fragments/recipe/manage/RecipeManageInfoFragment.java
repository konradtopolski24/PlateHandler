package com.fatiner.platehandler.fragments.recipe.manage;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class RecipeManageInfoFragment extends PrimaryFragment {

    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_author) EditText etAuthor;
    @BindView(R.id.tv_serving) TextView tvServing;
    @BindView(R.id.tv_time) TextView tvTime;
    @BindView(R.id.tv_difficulty) TextView tvDifficulty;
    @BindView(R.id.iv_photo) ImageView ivPhoto;
    @BindViews({
            R.id.iv_spiciness0,
            R.id.iv_spiciness1,
            R.id.iv_spiciness2,
            R.id.iv_spiciness3}) List<ImageView> ivSpicinessList;
    @BindView(R.id.iv_type) ImageView ivType;
    @BindView(R.id.iv_country) ImageView ivCountry;
    @BindView(R.id.sb_difficulty) SeekBar sbDifficulty;
    @BindView(R.id.sb_spiciness) SeekBar sbSpiciness;
    @BindView(R.id.sp_country) Spinner spCountry;
    @BindView(R.id.sp_type) Spinner spType;
    @BindView(R.id.rb_meat) RadioButton rbMeat;
    @BindView(R.id.rb_vegetarian) RadioButton rbVegetarian;

    @OnTextChanged(R.id.et_name)
    void changedEtName(CharSequence text) {
        getRecipe().setName(String.valueOf(text));
        setError(etName, R.string.er_rp_name, isNameEmpty());
    }

    @OnTextChanged(R.id.et_author)
    void changedEtAuthor(CharSequence text) {
        getRecipe().setAuthor(String.valueOf(text));
        setError(etAuthor, R.string.er_rp_author, isAuthorEmpty());
    }

    @OnTextChanged(R.id.tv_serving)
    void changedTvServing(CharSequence text) {
        getRecipe().setServing(Integer.parseInt(String.valueOf(text)));
    }

    @OnTextChanged(R.id.tv_time)
    void changedTvTime(CharSequence text) {
        getRecipe().setTime(String.valueOf(text));
        setError(tvTime, isTimeZero());
    }

    @OnItemSelected(R.id.sp_country)
    void selectedSpCountry(int id) {
        getRecipe().setCountry(id);
        setIv(ivCountry, id, R.array.dw_country);
    }

    @OnItemSelected(R.id.sp_type)
    void selectedSpType(int id) {
        getRecipe().setType(id);
        setIv(ivType, id, R.array.dw_recipe);
    }

    @OnCheckedChanged(R.id.rb_meat)
    void checkedRbMeat(boolean checked) {
        getRecipe().setPreference(checked);
    }

    @OnClick(R.id.ib_plus)
    void clickIbPlus() {
        incrementServing();
    }

    @OnClick(R.id.ib_minus)
    void clickIbMinus() {
        decrementServing();
    }

    @OnClick(R.id.ib_time)
    void clickIbTime() {
        showTimeDialog();
    }

    @OnClick(R.id.ib_add) 
    void clickIbAdd() {
        selectImage();
    }

    @OnClick(R.id.ib_remove)
    void clickIbRemove() {
        getRecipe().setPhoto(null);
        setIv(ivPhoto, getRecipe().getPhoto());
    }

    @OnClick(R.id.iv_tt_photo)
    void clickIvTtPhoto() {
        showDialog(R.string.hd_rp_photo, R.string.tt_rp_photo);
    }

    @OnClick(R.id.iv_tt_name)
    void clickIvTtName() {
        showDialog(R.string.hd_rp_name, R.string.tt_rp_name);
    }

    @OnClick(R.id.iv_tt_info)
    void clickIvTtInfo() {
        showDialog(R.string.hd_rp_info, R.string.tt_rp_info);
    }

    public RecipeManageInfoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_recipe_manage_info, container, false);
        ButterKnife.bind(this, view);
        initAction();
        return view;
    }

    private Recipe getRecipe() {
        return RecipeDetails.getRecipe();
    }

    private void initAction() {
        hideKeyboard();
        initSb();
        setRecipeInfo();
    }

    private void initSb() {
        setSb(sbDifficulty, Globals.SB_DIFFICULTY, getSbListener(true));
        setSb(sbSpiciness, Globals.SB_SPICINESS, getSbListener(false));
    }

    private void setRecipeInfo() {
        Recipe recipe = getRecipe();
        setEt(etName, recipe.getName());
        setEt(etAuthor, recipe.getAuthor());
        setTv(tvServing, String.valueOf(recipe.getServing()));
        setTv(tvTime, recipe.getTime());
        setTv(tvDifficulty, recipe.getDifficulty(), R.array.tx_difficulty);
        setIvList(ivSpicinessList, recipe.getSpiciness());
        setSb(sbDifficulty, recipe.getDifficulty());
        setSb(sbSpiciness, recipe.getSpiciness());
        setSp(spCountry, recipe.getCountry());
        setSp(spType, recipe.getType());
        setRb(rbMeat, recipe.getPreference());
        setIv(ivPhoto, recipe.getPhoto());
    }

    private SeekBar.OnSeekBarChangeListener getSbListener(final boolean isDifficulty) {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                chooseSbAction(isDifficulty, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }

    private void chooseSbAction(boolean isDifficulty, int progress) {
        if(isDifficulty) {
            getRecipe().setDifficulty(progress);
            setTv(tvDifficulty, progress, R.array.tx_difficulty);
        }
        else {
            getRecipe().setSpiciness(progress);
            setIvList(ivSpicinessList, progress);
        }
    }

    private TimePickerDialog.OnTimeSetListener getTimeListener() {
        return (view, hourOfDay, minute) ->
                setTv(tvTime, TypeManager.timeToString(hourOfDay, minute));
    }

    private boolean isValueMax(int value) {
        return value == Globals.SV_MAX;
    }

    private boolean isValueMin(int value) {
        return value == Globals.SV_MIN;
    }

    private void incrementServing() {
        int value = Integer.parseInt(String.valueOf(tvServing.getText()));
        if(isValueMax(value)) return;
        tvServing.setText(String.valueOf(value + Globals.DF_INCREMENT));
    }

    private void decrementServing() {
        int value = Integer.parseInt(String.valueOf(tvServing.getText()));
        if(isValueMin(value)) return;
        tvServing.setText(String.valueOf(value + Globals.DF_DECREMENT));
    }

    private void showTimeDialog() {
        ArrayList<Integer> times = TypeManager.stringToTime(getRecipe().getTime());
        new TimePickerDialog(getContext(), getTimeListener(), times.get(Globals.TM_HOURS),
                times.get(Globals.TM_MINUTES), true).show();
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

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Bitmap photo = getImageFromGallery(resultCode, data);
        getRecipe().setPhoto(photo);
        setIv(ivPhoto, getRecipe().getPhoto());
    }
}