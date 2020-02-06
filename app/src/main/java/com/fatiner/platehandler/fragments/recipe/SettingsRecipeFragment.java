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
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbOperations;
import com.fatiner.platehandler.managers.shared.SharedRecipeManager;

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
        SharedRecipeManager.setSharedAlphabetical(getContext(), checked);
    }

    @OnCheckedChanged(R.id.sw_favorite)
    public void onCheckedChangedSwFavorite(boolean checked){
        if(checked){
            SharedRecipeManager.setSharedFavorite(getContext(), checked);
        } else {
            SharedRecipeManager.removeSharedFavorite(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_author)
    public void onCheckedChangedSwAuthor(boolean checked){
        if(checked){
            spAuthor.setVisibility(View.VISIBLE);
        } else {
            spAuthor.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedAuthor(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_difficulty)
    public void onCheckedChangedSwDifficulty(boolean checked){
        if(checked){
            spDifficulty.setVisibility(View.VISIBLE);
        } else {
            spDifficulty.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedDifficulty(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_spiciness)
    public void onCheckedChangedSwSpiciness(boolean checked){
        if(checked){
            spSpiciness.setVisibility(View.VISIBLE);
        } else {
            spSpiciness.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedSpiciness(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_country)
    public void onCheckedChangedSwCountry(boolean checked){
        if(checked){
            spCountry.setVisibility(View.VISIBLE);
        } else {
            spCountry.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedCountry(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_type)
    public void onCheckedChangedSwType(boolean checked){
        if(checked){
            spType.setVisibility(View.VISIBLE);
        } else {
            spType.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedType(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_preference)
    public void onCheckedChangedSwPreference(boolean checked){
        if(checked){
            spPreference.setVisibility(View.VISIBLE);
        } else {
            spPreference.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedPreference(getContext());
        }
    }

    @OnItemSelected(R.id.sp_author)
    public void onItemSelectedSpAuthor(int id){
        SharedRecipeManager.setSharedAuthor(getContext(), spAuthor.getItemAtPosition(id).toString());
    }

    @OnItemSelected(R.id.sp_difficulty)
    public void onItemSelectedSpDifficulty(int id){
        SharedRecipeManager.setSharedDifficulty(getContext(), id);
    }

    @OnItemSelected(R.id.sp_spiciness)
    public void onItemSelectedSpSpiciness(int id){
        SharedRecipeManager.setSharedSpiciness(getContext(), id);
    }

    @OnItemSelected(R.id.sp_country)
    public void onItemSelectedSpCountry(int id){
        SharedRecipeManager.setSharedCountry(getContext(), id);
    }

    @OnItemSelected(R.id.sp_type)
    public void onItemSelectedSpType(int id){
        SharedRecipeManager.setSharedType(getContext(), id);
    }

    @OnItemSelected(R.id.sp_preference)
    public void onItemSelectedSpPreference(int id){
        SharedRecipeManager.setSharedPreference(getContext(), TypeManager.integerToBoolean(id));
    }

    public SettingsRecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_recipe, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_recipe_settings);
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
            SharedRecipeManager.setSharedDifficulty(getContext(), progress);
        }
        else {
            SharedRecipeManager.setSharedSpiciness(getContext(), progress);
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
        boolean isChecked = SharedRecipeManager.getSharedAlphabetical(getContext());
        swAlphabetical.setChecked(isChecked);
    }

    private void setSwitchFavorite(){
        if(SharedRecipeManager.isSharedFavoriteAvailable(getContext())){
            swFavorite.setChecked(true);
        }
    }

    private void setSpinAuthor(ArrayList<String> authors){
        if(SharedRecipeManager.isSharedAuthorAvailable(getContext())){
            String author = SharedRecipeManager.getSharedAuthor(getContext());
            int id = authors.indexOf(author);
            spAuthor.setSelection(id);
            swAuthor.setChecked(true);
        }
    }

    private void setSpinDifficulty(){
        if(SharedRecipeManager.isSharedDifficultyAvailable(getContext())){
            int difficulty = SharedRecipeManager.getSharedDifficulty(getContext());
            spDifficulty.setSelection(difficulty);
            swDifficulty.setChecked(true);
        }
    }

    private void setSpinSpiciness(){
        if(SharedRecipeManager.isSharedSpicinessAvailable(getContext())){
            int spiciness = SharedRecipeManager.getSharedSpiciness(getContext());
            spSpiciness.setSelection(spiciness);
            swSpiciness.setChecked(true);
        }
    }

    private void setSpinCountry(){
        if(SharedRecipeManager.isSharedCountryAvailable(getContext())){
            int country = SharedRecipeManager.getSharedCountry(getContext());
            spCountry.setSelection(country);
            swCountry.setChecked(true);
        }
    }

    private void setSpinType(){
        if(SharedRecipeManager.isSharedTypeAvailable(getContext())){
            int type = SharedRecipeManager.getSharedType(getContext());
            spType.setSelection(type);
            swType.setChecked(true);
        }
    }

    private void setSpinPreference(){
        if(SharedRecipeManager.isSharedPreferenceAvailable(getContext())){
            boolean preference = SharedRecipeManager.getSharedPreference(getContext());
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
                showShortToast(R.string.ts_db_error);
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
