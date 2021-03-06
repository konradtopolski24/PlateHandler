package com.fatiner.platehandler.fragments.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.SharedManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class ProductSettingsFragment extends PrimaryFragment {

    @BindView(R.id.sw_alphabetical)
    Switch swAlphabetical;
    @BindView(R.id.sw_type)
    Switch swType;
    @BindView(R.id.sp_type)
    Spinner spType;

    @OnCheckedChanged(R.id.sw_alphabetical)
    void checkedSwAlphabetical(boolean checked) {
        checkSwState(checked, Shared.SR_PRODUCT, Shared.KY_ALPHABETICAL);
    }

    @OnCheckedChanged(R.id.sw_type)
    void checkedSwType(boolean checked) {
        checkSwState(checked, spType, Shared.SR_PRODUCT, Shared.KY_TYPE);
    }

    @OnItemSelected(R.id.sp_type)
    void selectedSpType(int id) {
        SharedManager.setValue(getContext(), Shared.SR_PRODUCT, Shared.KY_TYPE, id);
    }

    @OnClick(R.id.iv_tt_settings)
    void clickIvTtSettings() {
        showDialog(R.string.hd_pd_settings, R.string.tt_pd_settings);
    }

    public static ProductSettingsFragment getInstance() {
        return new ProductSettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_product_settings, container,
                false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOptions(R.id.it_product, R.string.tb_pd_settings, false);
        initAction();
    }

    private void initAction() {
        setViews();
        setSettings();
    }

    private void setViews() {
        setSp(spType, getEntries(R.array.tx_pd_type), getContext());
    }

    private void setSettings() {
        setSettingsBool(swAlphabetical, Shared.SR_PRODUCT, Shared.KY_ALPHABETICAL);
        setSettingsInt(swType, spType, Shared.SR_PRODUCT, Shared.KY_TYPE);
    }
}
