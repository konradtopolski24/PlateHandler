package com.fatiner.platehandler.fragments.recipe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.database.DbSuccessManager;
import com.fatiner.platehandler.managers.shared.SharedRecipeManager;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.PrimaryFragment;

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
    @BindView(R.id.sp_country)
    Spinner spCountry;
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.sb_difficulty)
    SeekBar sbDifficulty;
    @BindView(R.id.sb_spiciness)
    SeekBar sbSpiciness;
    @BindView(R.id.rg_preference)
    RadioGroup rgPreference;
    @BindView(R.id.rb_meat)
    RadioButton rbMeat;

    @OnCheckedChanged(R.id.sw_alphabetical)
    public void onCheckedChangedSwitchAlphabetical(boolean checked){
        SharedRecipeManager.setSharedAlphabetical(getContext(), checked);
    }

    @OnCheckedChanged(R.id.sw_favorite)
    public void onCheckedChangedSwitchFavorite(boolean checked){
        if(checked){
            SharedRecipeManager.setSharedFavorite(getContext(), checked);
        } else {
            SharedRecipeManager.removeSharedFavorite(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_author)
    public void onCheckedChangedSwitchAuthor(boolean checked){
        if(checked){
            spAuthor.setVisibility(View.VISIBLE);
        } else {
            spAuthor.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedAuthor(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_difficulty)
    public void onCheckedChangedSwitchDifficulty(boolean checked){
        if(checked){
            sbDifficulty.setVisibility(View.VISIBLE);
            SharedRecipeManager.setSharedDifficulty(getContext(), sbDifficulty.getProgress());
        } else {
            sbDifficulty.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedDifficulty(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_spiciness)
    public void onCheckedChangedSwitchSpiciness(boolean checked){
        if(checked){
            sbSpiciness.setVisibility(View.VISIBLE);
            SharedRecipeManager.setSharedSpiciness(getContext(), sbSpiciness.getProgress());
        } else {
            sbSpiciness.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedSpiciness(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_country)
    public void onCheckedChangedSwitchCountry(boolean checked){
        if(checked){
            spCountry.setVisibility(View.VISIBLE);
        } else {
            spCountry.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedCountry(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_type)
    public void onCheckedChangedSwitchType(boolean checked){
        if(checked){
            spType.setVisibility(View.VISIBLE);
        } else {
            spType.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedType(getContext());
        }
    }

    @OnCheckedChanged(R.id.sw_preference)
    public void onCheckedChangedSwitchPreference(boolean checked){
        if(checked){
            rgPreference.setVisibility(View.VISIBLE);
            SharedRecipeManager.setSharedPreference(getContext(), rbMeat.isChecked());
        } else {
            rgPreference.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedPreference(getContext());
        }
    }

    @OnItemSelected(R.id.sp_author)
    public void onItemSelectedSpinAuthor(int id){
        SharedRecipeManager.setSharedAuthor(getContext(), spAuthor.getItemAtPosition(id).toString());
    }

    @OnItemSelected(R.id.sp_country)
    public void onItemSelectedSpinCountry(int id){
        SharedRecipeManager.setSharedCountry(getContext(), id);
    }

    @OnItemSelected(R.id.sp_type)
    public void onItemSelectedSpinType(int id){
        SharedRecipeManager.setSharedType(getContext(), id);
    }

    @OnCheckedChanged(R.id.rb_meat)
    public void onCheckedChangedRadioMeat(boolean checked){
        SharedRecipeManager.setSharedPreference(getContext(), checked);
    }

    public SettingsRecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_recipe, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tool_settings_frag_sttrecp);
        new AsyncReadAuthors().execute();
        manageSeekBars();
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

    private void setSpinAuthorAdapter(ArrayList<String> authors){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, authors);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spAuthor.setAdapter(adapter);
    }

    private SeekBar.OnSeekBarChangeListener getSeekBarListener(final boolean isDifficulty){
        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
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
        return listener;
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
        setSeekDifficulty();
        setSeekSpiciness();
        setSpinCountry();
        setSpinType();
        setRadioMeat();
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

    private void setSeekDifficulty(){
        if(SharedRecipeManager.isSharedDifficultyAvailable(getContext())){
            int difficulty = SharedRecipeManager.getSharedDifficulty(getContext());
            sbDifficulty.setProgress(difficulty);
            swDifficulty.setChecked(true);
        }
    }

    private void setSeekSpiciness(){
        if(SharedRecipeManager.isSharedSpicinessAvailable(getContext())){
            int spiciness = SharedRecipeManager.getSharedSpiciness(getContext());
            sbSpiciness.setProgress(spiciness);
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

    private void setRadioMeat(){
        if(SharedRecipeManager.isSharedPreferenceAvailable(getContext())){
            boolean preference = SharedRecipeManager.getSharedPreference(getContext());
            rbMeat.setChecked(preference);
            swPreferences.setChecked(true);
        }
    }

    private class AsyncReadAuthors extends AsyncTask<Void, Void, Boolean> {

        ArrayList<String> authors;

        protected void onPreExecute(){
            authors = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.readAuthors(getContext(), authors);
        }

        protected void onPostExecute(Boolean success){
            if(success){
                setSpinAuthorAdapter(authors);
                setViewsWithSharedPreferences(authors);
            } else {
                showShortToast(R.string.toast_fail_async_readauth);
            }
        }
    }
}
