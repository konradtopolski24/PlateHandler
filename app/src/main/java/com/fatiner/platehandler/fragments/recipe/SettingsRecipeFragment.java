package com.fatiner.platehandler.fragments.recipe;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.SharedGlobals;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbOperations;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnItemSelected;

public class SettingsRecipeFragment extends PrimaryFragment {

    @BindView(R.id.sw_alphabetical)
    Switch swAlphabetical;
    @BindView(R.id.sw_favorite)
    Switch swFavorite;
    @BindView(R.id.sw_author)
    Switch swAuthor;
    @BindView(R.id.sw_difficulty)
    Switch swDifficulty;
    @BindView(R.id.sw_spiciness)
    Switch swSpiciness;
    @BindView(R.id.sw_country)
    Switch swCountry;
    @BindView(R.id.sw_type)
    Switch swType;
    @BindView(R.id.sw_preference)
    Switch swPreferences;
    @BindView(R.id.sp_author)
    Spinner spAuthor;
    @BindView(R.id.sp_difficulty)
    Spinner spDifficulty;
    @BindView(R.id.sp_spiciness)
    Spinner spSpiciness;
    @BindView(R.id.sp_country)
    Spinner spCountry;
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.sp_preference)
    Spinner spPreference;

    @OnCheckedChanged(R.id.sw_alphabetical)
    public void onCheckedChangedSwAlphabetical(boolean checked){
        SharedManager.setValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_ALPHABETICAL, checked);
    }

    @OnCheckedChanged(R.id.sw_favorite)
    public void onCheckedChangedSwFavorite(boolean checked){
        if(checked){
            SharedManager.setValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_FAVORITE, checked);
        } else {
            SharedManager.removeValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_FAVORITE);
        }
    }

    @OnCheckedChanged(R.id.sw_author)
    public void onCheckedChangedSwAuthor(boolean checked){
        if(checked){
            spAuthor.setVisibility(View.VISIBLE);
        } else {
            spAuthor.setVisibility(View.GONE);
            SharedManager.removeValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_AUTHOR);
        }
    }

    @OnCheckedChanged(R.id.sw_difficulty)
    public void onCheckedChangedSwDifficulty(boolean checked){
        if(checked){
            spDifficulty.setVisibility(View.VISIBLE);
        } else {
            spDifficulty.setVisibility(View.GONE);
            SharedManager.removeValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_DIFFICULTY);
        }
    }

    @OnCheckedChanged(R.id.sw_spiciness)
    public void onCheckedChangedSwSpiciness(boolean checked){
        if(checked){
            spSpiciness.setVisibility(View.VISIBLE);
        } else {
            spSpiciness.setVisibility(View.GONE);
            SharedManager.removeValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_SPICINESS);
        }
    }

    @OnCheckedChanged(R.id.sw_country)
    public void onCheckedChangedSwCountry(boolean checked){
        if(checked){
            spCountry.setVisibility(View.VISIBLE);
        } else {
            spCountry.setVisibility(View.GONE);
            SharedManager.removeValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_COUNTRY);
        }
    }

    @OnCheckedChanged(R.id.sw_type)
    public void onCheckedChangedSwType(boolean checked){
        if(checked){
            spType.setVisibility(View.VISIBLE);
        } else {
            spType.setVisibility(View.GONE);
            SharedManager.removeValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_TYPE);
        }
    }

    @OnCheckedChanged(R.id.sw_preference)
    public void onCheckedChangedSwPreference(boolean checked){
        if(checked){
            spPreference.setVisibility(View.VISIBLE);
        } else {
            spPreference.setVisibility(View.GONE);
            SharedManager.removeValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_PREFERENCE);
        }
    }

    @OnItemSelected(R.id.sp_author)
    public void onItemSelectedSpAuthor(int id){
        SharedManager.setValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_AUTHOR, spAuthor.getItemAtPosition(id).toString());
    }

    @OnItemSelected(R.id.sp_difficulty)
    public void onItemSelectedSpDifficulty(int id){
        SharedManager.setValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_DIFFICULTY, id);
    }

    @OnItemSelected(R.id.sp_spiciness)
    public void onItemSelectedSpSpiciness(int id){
        SharedManager.setValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_SPICINESS, id);
    }

    @OnItemSelected(R.id.sp_country)
    public void onItemSelectedSpCountry(int id){
        SharedManager.setValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_COUNTRY, id);
    }

    @OnItemSelected(R.id.sp_type)
    public void onItemSelectedSpType(int id){
        SharedManager.setValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_TYPE, id);
    }

    @OnItemSelected(R.id.sp_preference)
    public void onItemSelectedSpPreference(int id){
        SharedManager.setValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_PREFERENCE, TypeManager.integerToBoolean(id));
    }

    public SettingsRecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_recipe, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_rp_settings);
        new AsyncSettingRecipe().execute();
        return view;
    }

    private void setSpinAuthorAdapter(ArrayList<String> authors){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, authors);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spAuthor.setAdapter(adapter);
    }

    private SeekBar.OnSeekBarChangeListener getSeekBarListener(final boolean isDifficulty){
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                chooseAction(isDifficulty, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }

    private void chooseAction(boolean isDifficulty, int progress){
        if(isDifficulty){
            SharedManager.setValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_DIFFICULTY, progress);
        }
        else {
            SharedManager.setValue(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_SPICINESS, progress);
        }
    }

    private void setViewsWithSharedPreferences(ArrayList<String> authors){
        setSwitchAlphabetical();
        setSwitchFavorite();
        setSpinAuthor(authors);
        setSpinDifficulty();
        setSpinSpiciness();
        setSpinCountry();
        setSpinType();
        setSpinPreference();
    }

    private void setSwitchAlphabetical(){
        boolean isChecked = SharedManager.getBoolean(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_ALPHABETICAL);
        swAlphabetical.setChecked(isChecked);
    }

    private void setSwitchFavorite(){
        if(SharedManager.isValueAvailable(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_FAVORITE)){
            swFavorite.setChecked(true);
        }
    }

    private void setSpinAuthor(ArrayList<String> authors){
        if(SharedManager.isValueAvailable(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_AUTHOR)){
            String author = SharedManager.getString(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_AUTHOR);
            int id = authors.indexOf(author);
            spAuthor.setSelection(id);
            swAuthor.setChecked(true);
        }
    }

    private void setSpinDifficulty(){
        if(SharedManager.isValueAvailable(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_DIFFICULTY)){
            int difficulty = SharedManager.getInt(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_DIFFICULTY);
            spDifficulty.setSelection(difficulty);
            swDifficulty.setChecked(true);
        }
    }

    private void setSpinSpiciness(){
        if(SharedManager.isValueAvailable(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_SPICINESS)){
            int spiciness = SharedManager.getInt(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_SPICINESS);
            spSpiciness.setSelection(spiciness);
            swSpiciness.setChecked(true);
        }
    }

    private void setSpinCountry(){
        if(SharedManager.isValueAvailable(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_COUNTRY)){
            int country = SharedManager.getInt(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_COUNTRY);
            spCountry.setSelection(country);
            swCountry.setChecked(true);
        }
    }

    private void setSpinType(){
        if(SharedManager.isValueAvailable(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_TYPE)){
            int type = SharedManager.getInt(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_TYPE);
            spType.setSelection(type);
            swType.setChecked(true);
        }
    }

    private void setSpinPreference(){
        if(SharedManager.isValueAvailable(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_PREFERENCE)){
            boolean preference = SharedManager.getBoolean(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_PREFERENCE);
            spPreference.setSelection(TypeManager.booleanToInteger(preference));
            swPreferences.setChecked(true);
        }
    }

    private class AsyncSettingRecipe extends AsyncTask<Void, Void, Boolean> {

        private ArrayList<String> authors;

        protected void onPreExecute(){
            authors = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                DbOperations.readAuthors(getContext(), authors);
                return true;
            }catch (SQLiteException e){
                showShortToast(R.string.ts_database);
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(success){
                finishedReadAuthors(authors);
            }
        }
    }

    private void finishedReadAuthors(ArrayList<String> authors) {
        setSpinAuthorAdapter(authors);
        setViewsWithSharedPreferences(authors);
    }
}
