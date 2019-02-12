package com.fatiner.platehandler.fragments.recipe.manage;

import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.TimePicker;

import com.fatiner.platehandler.globals.ImageGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.managers.TypeManager;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class ManageInfoFragment extends PrimaryFragment {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_author)
    EditText etAuthor;
    @BindView(R.id.tv_serving)
    TextView tvServing;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_difficulty)
    TextView tvDifficulty;
    @BindView(R.id.sb_difficulty)
    SeekBar sbDifficulty;
    @BindView(R.id.sb_spiciness)
    SeekBar sbSpiciness;
    @BindViews({
            R.id.iv_spiciness0,
            R.id.iv_spiciness1,
            R.id.iv_spiciness2})
    List<ImageView> arIvSpiciness;
    @BindView(R.id.sp_country)
    Spinner spCountry;
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.rb_meat)
    RadioButton rbMeat;
    @BindView(R.id.rb_vegetarian)
    RadioButton rbVegetarian;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    @OnTextChanged(R.id.et_name)
    public void onTextChangedEtName(CharSequence text){
        RecipeDetails.getRecipe().setName(String.valueOf(text));
    }

    @OnTextChanged(R.id.et_author)
    public void onTextChangedEtAuthor(CharSequence text){
        RecipeDetails.getRecipe().setAuthor(String.valueOf(text));
    }

    @OnTextChanged(R.id.tv_serving)
    public void onTextChangedTvServing(CharSequence text){
        RecipeDetails.getRecipe().setServing(Integer.valueOf(String.valueOf(text)));
    }

    @OnTextChanged(R.id.tv_time)
    public void onTextChangedTvTime(CharSequence text){
        RecipeDetails.getRecipe().setTime(String.valueOf(text));
    }

    @OnItemSelected(R.id.sp_country)
    public void onItemSelectedSpCountry(int id){
        RecipeDetails.getRecipe().setCountry(id);
    }

    @OnItemSelected(R.id.sp_type)
    public void onItemSelectedSpType(int id){
        RecipeDetails.getRecipe().setType(id);
    }

    @OnCheckedChanged(R.id.rb_meat)
    public void onCheckedChangedRbMeat(boolean checked){
        RecipeDetails.getRecipe().setPreference(checked);
    }

    @OnClick(R.id.ib_plus)
    public void onClickIbPlus(){
        String text = String.valueOf(tvServing.getText());
        int value = Integer.valueOf(text);
        if(isServingNotTooBig(value)){
            tvServing.setText(String.valueOf(++value));
        }
    }

    @OnClick(R.id.ib_minus)
    public void onClickIbMinus(){
        String text = String.valueOf(tvServing.getText());
        int value = Integer.valueOf(text);
        if(isServingNotTooSmall(value)){
            tvServing.setText(String.valueOf(--value));
        }
    }

    @OnClick(R.id.ib_time)
    public void onClickIbTime(){
        int[] times = TypeManager.stringToTime(RecipeDetails.getRecipe().getTime());
        TimePickerDialog dialog = new TimePickerDialog(getContext(),
                getNewOnTimeSetListener(),
                times[MainGlobals.ID_HOURS_MANAG_TYPE],
                times[MainGlobals.ID_MINUTES_MANAG_TYPE],
                true);
        dialog.show();
    }

    @OnClick(R.id.ib_add)
    public void onClickIbAdd(){
        selectPhoto();
    }

    @OnClick(R.id.ib_remove)
    public void onClickIbRemove(){
        RecipeDetails.getRecipe().setEncodedImage(null);
        ivPhoto.setImageResource(android.R.color.transparent);
    }

    public ManageInfoFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_info, container, false);
        ButterKnife.bind(this, view);
        hideKeyboard();
        setMenuItem(MainGlobals.ID_RECIPES_DRAW_MAIN);
        manageSeekBars();
        setEditServing(RecipeDetails.getRecipe().getServing());
        setTextDifficulty(MainGlobals.PROG_STARTING_SEEK_DIFFICULTY);
        hideAllImageSpiciness();
        setRecipeInfo();
        return view;
    }

    private void manageSeekBars(){
        manageSeekBar(
                sbDifficulty,
                MainGlobals.PROG_STARTING_SEEK_DIFFICULTY,
                MainGlobals.PROG_MAX_SEEK_DIFFICULTY,
                getSeekBarListener(true)
        );
        manageSeekBar(
                sbSpiciness,
                MainGlobals.PROG_STARTING_SEEK_SPICINESS,
                MainGlobals.PROG_MAX_SEEK_SPICINESS,
                getSeekBarListener(false)
        );
    }

    private SeekBar.OnSeekBarChangeListener getSeekBarListener(final boolean isDifficulty){
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                chooseAction(isDifficulty, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    private void chooseAction(boolean isDifficulty, int progress){
        if(isDifficulty){
            setTextDifficulty(progress);
            RecipeDetails.getRecipe().setDifficulty(progress);
        }
        else {
            hideAllImageSpiciness();
            setImageSpiciness(progress);
            RecipeDetails.getRecipe().setSpiciness(progress);
        }
    }

    private void setEditName(String text){
        etName.setText(text);
    }

    private void setEditAuthor(String text){
        etAuthor.setText(text);
    }

    private void setEditServing(int serving) {
        tvServing.setText(String.valueOf(serving));
    }

    private void setTextTime(String time){
        tvTime.setText(time);
    }

    private void setTextDifficulty(int progress){
        String [] arrayDifficulty = getStringArray(R.array.ar_difficulty);
        tvDifficulty.setText(arrayDifficulty[progress]);
    }

    private void setImageSpiciness(int progress){
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < progress; i++){
            arIvSpiciness.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void hideAllImageSpiciness(){
        for(ImageView image : arIvSpiciness){
            image.setVisibility(View.GONE);
        }
    }

    private void setSeekDifficulty(int difficulty){
        sbDifficulty.setProgress(difficulty);
    }

    private void setSeekSpiciness(int spiciness){
        sbSpiciness.setProgress(spiciness);
    }

    private void setSpinCountry(int country){
        spCountry.setSelection(country);
    }

    private void setSpinType(int type){
        spType.setSelection(type);
    }

    private void setRadioMeat(boolean isMeat){
        rbMeat.setChecked(isMeat);
    }

    private void setRecipeInfo(){
        Recipe recipe = RecipeDetails.getRecipe();
        setEditName(recipe.getName());
        setEditAuthor(recipe.getAuthor());
        setEditServing(recipe.getServing());
        setTextTime(recipe.getTime());
        setSeekDifficulty(recipe.getDifficulty());
        setSeekSpiciness(recipe.getSpiciness());
        setSpinCountry(recipe.getCountry());
        setSpinType(recipe.getType());
        setRadioMeat(recipe.getPreference());
        setImagePhoto(recipe.getEncodedImage());
    }

    private TimePickerDialog.OnTimeSetListener getNewOnTimeSetListener(){
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = TypeManager.timeToString(hourOfDay, minute);
                setTextTime(time);
            }
        };
    }

    private boolean isServingNotTooBig(int value){
        return value < MainGlobals.MAX_SERVING_TEXT_SERVING;
    }

    private boolean isServingNotTooSmall(int value){
        return value > MainGlobals.MIN_SERVING_TEXT_SERVING;
    }

    private void setImagePhoto(String encodedImage){
        if(encodedImage == null) return;
        ivPhoto.setImageBitmap(TypeManager.base64StringToBitmap(encodedImage));
    }

    private void setEncodedImageInfo(int resultCode, Intent data){
        String encodedImage = getEncodedImage(resultCode, data);
        RecipeDetails.getRecipe().setEncodedImage(encodedImage);
        setImagePhoto(encodedImage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case ImageGlobals.REQ_PHOTO_FRAG_PRIMARY:
                setEncodedImageInfo(resultCode, data);
                break;
        }
    }
}
