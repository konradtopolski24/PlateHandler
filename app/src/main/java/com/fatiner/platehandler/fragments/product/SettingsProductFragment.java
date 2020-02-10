package com.fatiner.platehandler.fragments.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.managers.shared.SharedProductManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnItemSelected;

public class SettingsProductFragment extends PrimaryFragment {

    @BindView(R.id.sw_alphabetical)
    Switch swAlphabetical;
    @BindView(R.id.sw_type)
    Switch swType;
    @BindView(R.id.sp_type)
    Spinner spType;

    @OnCheckedChanged(R.id.sw_alphabetical)
    public void onCheckedChangedSwAlphabetical(boolean checked){
        SharedProductManager.setSharedProductAlphabetical(getContext(), checked);
    }

    @OnCheckedChanged(R.id.sw_type)
    public void onCheckedChangedSwType(boolean checked){
        if(checked){
            spType.setVisibility(View.VISIBLE);
        } else {
            spType.setVisibility(View.GONE);
            SharedProductManager.removeSharedProductType(getContext());
        }
    }

    @OnItemSelected(R.id.sp_type)
    public void onItemSelectedSpType(int id){
        SharedProductManager.setSharedProductType(getContext(), id);
    }

    public SettingsProductFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_product, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_pd_settings);
        setViewsWithSharedPreferences();
        return view;
    }

    private void setViewsWithSharedPreferences(){
        setSwitchAlphabetical();
        setSpinType();
    }

    private void setSwitchAlphabetical(){
        boolean isChecked = SharedProductManager.getSharedProductAlphabetical(getContext());
        swAlphabetical.setChecked(isChecked);
    }

    private void setSpinType(){
        if(SharedProductManager.isSharedProductTypeAvailable(getContext())){
            int type = SharedProductManager.getSharedProductType(getContext());
            spType.setSelection(type);
            swType.setChecked(true);
        }
    }
}
