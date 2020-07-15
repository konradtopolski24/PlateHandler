package com.fatiner.platehandler.fragments.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.viewmodels.recipe.RecipeSettingsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeSettingsFragment extends PrimaryFragment {

    private RecipeSettingsViewModel viewModel;
    private CompositeDisposable disposables;

    @BindView(R.id.sw_alphabetical) Switch swAlphabetical;
    @BindView(R.id.sw_favorite) Switch swFavorite;
    @BindView(R.id.sw_author) Switch swAuthor;
    @BindView(R.id.sw_difficulty) Switch swDifficulty;
    @BindView(R.id.sw_spiciness) Switch swSpiciness;
    @BindView(R.id.sw_country) Switch swCountry;
    @BindView(R.id.sw_type) Switch swType;
    @BindView(R.id.sw_preference) Switch swPreference;
    @BindView(R.id.sp_author) Spinner spAuthor;
    @BindView(R.id.sp_difficulty) Spinner spDifficulty;
    @BindView(R.id.sp_spiciness) Spinner spSpiciness;
    @BindView(R.id.sp_country) Spinner spCountry;
    @BindView(R.id.sp_type) Spinner spType;
    @BindView(R.id.sp_preference) Spinner spPreference;

    @OnCheckedChanged(R.id.sw_alphabetical)
    void checkedSwAlphabetical(boolean checked) {
        checkSwState(checked, Shared.SR_RECIPE, Shared.KY_ALPHABETICAL);
    }

    @OnCheckedChanged(R.id.sw_favorite)
    void checkedSwFavorite(boolean checked) {
        checkSwState(checked, Shared.SR_RECIPE, Shared.KY_FAVORITE);
    }

    @OnCheckedChanged(R.id.sw_author)
    void checkedSwAuthor(boolean checked) {
        checkSwState(checked, spAuthor, Shared.SR_RECIPE, Shared.KY_AUTHOR);
    }

    @OnCheckedChanged(R.id.sw_difficulty)
    void checkedSwDifficulty(boolean checked) {
        checkSwState(checked, spDifficulty, Shared.SR_RECIPE, Shared.KY_DIFFICULTY);
    }

    @OnCheckedChanged(R.id.sw_spiciness)
    void checkedSwSpiciness(boolean checked) {
        checkSwState(checked, spSpiciness, Shared.SR_RECIPE, Shared.KY_SPICINESS);
    }

    @OnCheckedChanged(R.id.sw_country)
    void checkedSwCountry(boolean checked) {
        checkSwState(checked, spCountry, Shared.SR_RECIPE, Shared.KY_COUNTRY);
    }

    @OnCheckedChanged(R.id.sw_type)
    void checkedSwType(boolean checked) {
        checkSwState(checked, spType, Shared.SR_RECIPE, Shared.KY_TYPE);
    }

    @OnCheckedChanged(R.id.sw_preference)
    void checkedSwPreference(boolean checked) {
        checkSwState(checked, spPreference, Shared.SR_RECIPE, Shared.KY_PREFERENCE);
    }

    @OnItemSelected(R.id.sp_author)
    void selectedSpAuthor(int id) {
        SharedManager.setValue(getContext(), Shared.SR_RECIPE, Shared.KY_AUTHOR,
                spAuthor.getItemAtPosition(id).toString());
    }

    @OnItemSelected(R.id.sp_difficulty)
    void selectedSpDifficulty(int id) {
        SharedManager.setValue(getContext(), Shared.SR_RECIPE, Shared.KY_DIFFICULTY, id);
    }

    @OnItemSelected(R.id.sp_spiciness)
    void selectedSpSpiciness(int id) {
        SharedManager.setValue(getContext(), Shared.SR_RECIPE, Shared.KY_SPICINESS, id);
    }

    @OnItemSelected(R.id.sp_country)
    void selectedSpCountry(int id) {
        SharedManager.setValue(getContext(), Shared.SR_RECIPE, Shared.KY_COUNTRY, id);
    }

    @OnItemSelected(R.id.sp_type)
    void selectedSpType(int id) {
        SharedManager.setValue(getContext(), Shared.SR_RECIPE, Shared.KY_TYPE, id);
    }

    @OnItemSelected(R.id.sp_preference)
    void selectedSpPreference(int id) {
        SharedManager.setValue(getContext(), Shared.SR_RECIPE, Shared.KY_PREFERENCE,
                TypeManager.intToBool(id));
    }

    @OnClick(R.id.iv_tt_settings)
    void clickIvTtSettings() {
        showDialog(R.string.hd_rp_settings, R.string.tt_rp_settings);
    }

    public static RecipeSettingsFragment getInstance(boolean isShopping) {
        RecipeSettingsFragment fragment = new RecipeSettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Globals.BN_BOOL, isShopping);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_recipe_settings, container,
                false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOptions(getMenuId(), R.string.tb_rp_settings, false);
        initViewModelEssentials();
        observeGetAuthors();
    }

    private void initViewModelEssentials() {
        viewModel = new ViewModelProvider(this).get(RecipeSettingsViewModel.class);
        disposables = new CompositeDisposable();
    }

    private int getMenuId() {
        if (isShopping()) return R.id.it_shopping;
        else return R.id.it_recipe;
    }

    private boolean isShopping() {
        return getBundle().getBoolean(Globals.BN_BOOL);
    }

    private void setViews(List<String> authors) {
        setSp(spAuthor, authors, getContext());
        setSp(spDifficulty, getEntries(R.array.tx_difficulty), getContext());
        setSp(spSpiciness, getEntries(R.array.tx_spiciness), getContext());
        setSp(spCountry, getEntries(R.array.tx_country), getContext());
        setSp(spType, getEntries(R.array.tx_recipe), getContext());
        setSp(spPreference, getEntries(R.array.tx_preference), getContext());
    }

    private void setSettings(List<String> authors) {
        setSettingsBool(swAlphabetical, Shared.SR_RECIPE, Shared.KY_ALPHABETICAL);
        setSettingsBool(swFavorite, Shared.SR_RECIPE, Shared.KY_FAVORITE);
        setSettingsString(swAuthor, spAuthor, Shared.SR_RECIPE, Shared.KY_AUTHOR, authors);
        setSettingsInt(swDifficulty, spDifficulty, Shared.SR_RECIPE, Shared.KY_DIFFICULTY);
        setSettingsInt(swSpiciness, spSpiciness, Shared.SR_RECIPE, Shared.KY_SPICINESS);
        setSettingsInt(swCountry, spCountry, Shared.SR_RECIPE, Shared.KY_COUNTRY);
        setSettingsInt(swType, spType, Shared.SR_RECIPE, Shared.KY_TYPE);
        setSettingsBool(swPreference, spPreference, Shared.SR_RECIPE, Shared.KY_PREFERENCE);
    }

    private void observeGetAuthors() {
        viewModel.getAuthors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAuthorObserver());
    }

    private SingleObserver<List<String>> getAuthorObserver() {
        return new SingleObserver<List<String>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(List<String> authors) {
                setViews(authors);
                setSettings(authors);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }
}
