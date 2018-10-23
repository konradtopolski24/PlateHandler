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

    @BindView(R.id.edit_name_frag_mnginfo)
    EditText editName;
    @BindView(R.id.edit_author_frag_mnginfo)
    EditText editAuthor;
    @BindView(R.id.text_serving_frag_mnginfo)
    TextView textServing;
    @BindView(R.id.text_time_frag_mnginfo)
    TextView textTime;
    @BindView(R.id.text_difficulty_frag_mnginfo)
    TextView textDifficulty;
    @BindView(R.id.seek_difficulty_frag_mnginfo)
    SeekBar seekDifficulty;
    @BindView(R.id.seek_spiciness_frag_mnginfo)
    SeekBar seekSpiciness;
    @BindViews({
            R.id.image_spiciness0_frag_mnginfo,
            R.id.image_spiciness1_frag_mnginfo,
            R.id.image_spiciness2_frag_mnginfo})
    List<ImageView> arrayImageSpiciness;
    @BindView(R.id.spin_country_frag_mnginfo)
    Spinner spinCountry;
    @BindView(R.id.spin_type_frag_mnginfo)
    Spinner spinType;
    @BindView(R.id.radio_meat_frag_mnginfo)
    RadioButton radioMeat;
    @BindView(R.id.radio_vegetarian_frag_mnginfo)
    RadioButton radioVegetarian;
    @BindView(R.id.image_photo_frag_mnginfo)
    ImageView imagePhoto;

    @OnTextChanged(R.id.edit_name_frag_mnginfo)
    public void onTextChangedEditName(CharSequence text){
        RecipeDetails.getRecipe().setName(String.valueOf(text));
    }

    @OnTextChanged(R.id.edit_author_frag_mnginfo)
    public void onTextChangedEditAuthor(CharSequence text){
        RecipeDetails.getRecipe().setAuthor(String.valueOf(text));
    }

    @OnTextChanged(R.id.text_serving_frag_mnginfo)
    public void onTextChangedTextServing(CharSequence text){
        RecipeDetails.getRecipe().setServing(Integer.valueOf(String.valueOf(text)));
    }

    @OnTextChanged(R.id.text_time_frag_mnginfo)
    public void onTextChangedTextTime(CharSequence text){
        RecipeDetails.getRecipe().setTime(String.valueOf(text));
    }

    @OnItemSelected(R.id.spin_country_frag_mnginfo)
    public void onItemSelectedSpinCountry(int id){
        RecipeDetails.getRecipe().setCountry(id);
    }

    @OnItemSelected(R.id.spin_type_frag_mnginfo)
    public void onItemSelectedSpinType(int id){
        RecipeDetails.getRecipe().setType(id);
    }

    @OnCheckedChanged(R.id.radio_meat_frag_mnginfo)
    public void onCheckedChangedRadioMeat(boolean checked){
        RecipeDetails.getRecipe().setPreference(checked);
    }

    @OnClick(R.id.imgbutt_plus_frag_mnginfo)
    public void onClickImgbuttPlus(){
        String text = String.valueOf(textServing.getText());
        int value = Integer.valueOf(text);
        if(isServingNotTooBig(value)){
            textServing.setText(String.valueOf(++value));
        }
    }

    @OnClick(R.id.imgbutt_minus_frag_mnginfo)
    public void onClickImgbuttMinus(){
        String text = String.valueOf(textServing.getText());
        int value = Integer.valueOf(text);
        if(isServingNotTooSmall(value)){
            textServing.setText(String.valueOf(--value));
        }
    }

    @OnClick(R.id.imgbutt_time_frag_mnginfo)
    public void onClickImgbuttTime(){
        int[] times = TypeManager.stringToTime(RecipeDetails.getRecipe().getTime());
        TimePickerDialog dialog = new TimePickerDialog(getContext(),
                getNewOnTimeSetListener(),
                times[MainGlobals.ID_HOURS_MANAG_TYPE],
                times[MainGlobals.ID_MINUTES_MANAG_TYPE],
                true);
        dialog.show();
    }

    @OnClick(R.id.imgbutt_photo_frag_mnginfo)
    public void onClickImgbuttPhoto(){
        selectPhoto();
    }

    @OnClick(R.id.imgbutt_remove_frag_mnginfo)
    public void onClickImgbuttRemove(){
        RecipeDetails.getRecipe().setEncodedImage(null);
        imagePhoto.setImageResource(android.R.color.transparent);
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
                seekDifficulty,
                MainGlobals.PROG_STARTING_SEEK_DIFFICULTY,
                MainGlobals.PROG_MAX_SEEK_DIFFICULTY,
                getSeekBarListener(true)
        );
        manageSeekBar(
                seekSpiciness,
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
        editName.setText(text);
    }

    private void setEditAuthor(String text){
        editAuthor.setText(text);
    }

    private void setEditServing(int serving) {
        textServing.setText(String.valueOf(serving));
    }

    private void setTextTime(String time){
        textTime.setText(time);
    }

    private void setTextDifficulty(int progress){
        String [] arrayDifficulty = getStringArray(R.array.array_difficulty_frag_recipe);
        textDifficulty.setText(arrayDifficulty[progress]);
    }

    private void setImageSpiciness(int progress){
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < progress; i++){
            arrayImageSpiciness.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void hideAllImageSpiciness(){
        for(ImageView image : arrayImageSpiciness){
            image.setVisibility(View.GONE);
        }
    }

    private void setSeekDifficulty(int difficulty){
        seekDifficulty.setProgress(difficulty);
    }

    private void setSeekSpiciness(int spiciness){
        seekSpiciness.setProgress(spiciness);
    }

    private void setSpinCountry(int country){
        spinCountry.setSelection(country);
    }

    private void setSpinType(int type){
        spinType.setSelection(type);
    }

    private void setRadioMeat(boolean isMeat){
        radioMeat.setChecked(isMeat);
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
        imagePhoto.setImageBitmap(TypeManager.base64StringToBitmap(encodedImage));
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
