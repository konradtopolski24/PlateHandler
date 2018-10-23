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

    @BindView(R.id.switch_alphabetical_frag_sttrecp)
    Switch switchAlphabetical;
    @BindView(R.id.switch_favorite_frag_sttrecp)
    Switch switchFavorite;
    @BindView(R.id.switch_author_frag_sttrecp)
    Switch switchAuthor;
    @BindView(R.id.switch_difficulty_frag_sttrecp)
    Switch switchDifficulty;
    @BindView(R.id.switch_spiciness_frag_sttrecp)
    Switch switchSpiciness;
    @BindView(R.id.switch_country_frag_sttrecp)
    Switch switchCountry;
    @BindView(R.id.switch_type_frag_sttrecp)
    Switch switchType;
    @BindView(R.id.switch_preference_frag_sttrecp)
    Switch switchPreferences;
    @BindView(R.id.spin_author_frag_sttrecp)
    Spinner spinAuthor;
    @BindView(R.id.spin_country_frag_sttrecp)
    Spinner spinCountry;
    @BindView(R.id.spin_type_frag_sttrecp)
    Spinner spinType;
    @BindView(R.id.seek_difficulty_frag_sttrecp)
    SeekBar seekDifficulty;
    @BindView(R.id.seek_spiciness_frag_sttrecp)
    SeekBar seekSpiciness;
    @BindView(R.id.radio_preference_frag_sttrecp)
    RadioGroup radioPreference;
    @BindView(R.id.radio_meat_frag_sttrecp)
    RadioButton radioMeat;

    @OnCheckedChanged(R.id.switch_alphabetical_frag_sttrecp)
    public void onCheckedChangedSwitchAlphabetical(boolean checked){
        SharedRecipeManager.setSharedAlphabetical(getContext(), checked);
    }

    @OnCheckedChanged(R.id.switch_favorite_frag_sttrecp)
    public void onCheckedChangedSwitchFavorite(boolean checked){
        if(checked){
            SharedRecipeManager.setSharedFavorite(getContext(), checked);
        } else {
            SharedRecipeManager.removeSharedFavorite(getContext());
        }
    }

    @OnCheckedChanged(R.id.switch_author_frag_sttrecp)
    public void onCheckedChangedSwitchAuthor(boolean checked){
        if(checked){
            spinAuthor.setVisibility(View.VISIBLE);
        } else {
            spinAuthor.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedAuthor(getContext());
        }
    }

    @OnCheckedChanged(R.id.switch_difficulty_frag_sttrecp)
    public void onCheckedChangedSwitchDifficulty(boolean checked){
        if(checked){
            seekDifficulty.setVisibility(View.VISIBLE);
            SharedRecipeManager.setSharedDifficulty(getContext(), seekDifficulty.getProgress());
        } else {
            seekDifficulty.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedDifficulty(getContext());
        }
    }

    @OnCheckedChanged(R.id.switch_spiciness_frag_sttrecp)
    public void onCheckedChangedSwitchSpiciness(boolean checked){
        if(checked){
            seekSpiciness.setVisibility(View.VISIBLE);
            SharedRecipeManager.setSharedSpiciness(getContext(), seekSpiciness.getProgress());
        } else {
            seekSpiciness.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedSpiciness(getContext());
        }
    }

    @OnCheckedChanged(R.id.switch_country_frag_sttrecp)
    public void onCheckedChangedSwitchCountry(boolean checked){
        if(checked){
            spinCountry.setVisibility(View.VISIBLE);
        } else {
            spinCountry.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedCountry(getContext());
        }
    }

    @OnCheckedChanged(R.id.switch_type_frag_sttrecp)
    public void onCheckedChangedSwitchType(boolean checked){
        if(checked){
            spinType.setVisibility(View.VISIBLE);
        } else {
            spinType.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedType(getContext());
        }
    }

    @OnCheckedChanged(R.id.switch_preference_frag_sttrecp)
    public void onCheckedChangedSwitchPreference(boolean checked){
        if(checked){
            radioPreference.setVisibility(View.VISIBLE);
            SharedRecipeManager.setSharedPreference(getContext(), radioMeat.isChecked());
        } else {
            radioPreference.setVisibility(View.GONE);
            SharedRecipeManager.removeSharedPreference(getContext());
        }
    }

    @OnItemSelected(R.id.spin_author_frag_sttrecp)
    public void onItemSelectedSpinAuthor(int id){
        SharedRecipeManager.setSharedAuthor(getContext(), spinAuthor.getItemAtPosition(id).toString());
    }

    @OnItemSelected(R.id.spin_country_frag_sttrecp)
    public void onItemSelectedSpinCountry(int id){
        SharedRecipeManager.setSharedCountry(getContext(), id);
    }

    @OnItemSelected(R.id.spin_type_frag_sttrecp)
    public void onItemSelectedSpinType(int id){
        SharedRecipeManager.setSharedType(getContext(), id);
    }

    @OnCheckedChanged(R.id.radio_meat_frag_sttrecp)
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

    private void setSpinAuthorAdapter(ArrayList<String> authors){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, authors);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinAuthor.setAdapter(adapter);
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
        switchAlphabetical.setChecked(isChecked);
    }

    private void setSwitchFavorite(){
        if(SharedRecipeManager.isSharedFavoriteAvailable(getContext())){
            switchFavorite.setChecked(true);
        }
    }

    private void setSpinAuthor(ArrayList<String> authors){
        if(SharedRecipeManager.isSharedAuthorAvailable(getContext())){
            String author = SharedRecipeManager.getSharedAuthor(getContext());
            int id = authors.indexOf(author);
            spinAuthor.setSelection(id);
            switchAuthor.setChecked(true);
        }
    }

    private void setSeekDifficulty(){
        if(SharedRecipeManager.isSharedDifficultyAvailable(getContext())){
            int difficulty = SharedRecipeManager.getSharedDifficulty(getContext());
            seekDifficulty.setProgress(difficulty);
            switchDifficulty.setChecked(true);
        }
    }

    private void setSeekSpiciness(){
        if(SharedRecipeManager.isSharedSpicinessAvailable(getContext())){
            int spiciness = SharedRecipeManager.getSharedSpiciness(getContext());
            seekSpiciness.setProgress(spiciness);
            switchSpiciness.setChecked(true);
        }
    }

    private void setSpinCountry(){
        if(SharedRecipeManager.isSharedCountryAvailable(getContext())){
            int country = SharedRecipeManager.getSharedCountry(getContext());
            spinCountry.setSelection(country);
            switchCountry.setChecked(true);
        }
    }

    private void setSpinType(){
        if(SharedRecipeManager.isSharedTypeAvailable(getContext())){
            int type = SharedRecipeManager.getSharedType(getContext());
            spinType.setSelection(type);
            switchType.setChecked(true);
        }
    }

    private void setRadioMeat(){
        if(SharedRecipeManager.isSharedPreferenceAvailable(getContext())){
            boolean preference = SharedRecipeManager.getSharedPreference(getContext());
            radioMeat.setChecked(preference);
            switchPreferences.setChecked(true);
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
