package com.fatiner.platehandler.fragments.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.SharedGlobals;
import com.fatiner.platehandler.managers.SharedManager;

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
        SharedManager.setValue(getContext(), SharedGlobals.SR_PRODUCT, SharedGlobals.KY_ALPHABETICAL, checked);
    }

    @OnCheckedChanged(R.id.sw_type)
    public void onCheckedChangedSwType(boolean checked){
        if(checked){
            spType.setVisibility(View.VISIBLE);
        } else {
            spType.setVisibility(View.GONE);
            SharedManager.removeValue(getContext(), SharedGlobals.SR_PRODUCT, SharedGlobals.KY_TYPE);
        }
    }

    @OnItemSelected(R.id.sp_type)
    public void onItemSelectedSpType(int id){
        SharedManager.setValue(getContext(), SharedGlobals.SR_PRODUCT, SharedGlobals.KY_TYPE, id);
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
        boolean isChecked = SharedManager.getBoolean(getContext(), SharedGlobals.SR_PRODUCT, SharedGlobals.KY_ALPHABETICAL);
        swAlphabetical.setChecked(isChecked);
    }

    private void setSpinType(){
        if(SharedManager.isValueAvailable(getContext(), SharedGlobals.SR_PRODUCT, SharedGlobals.KY_TYPE)){
            int type = SharedManager.getInt(getContext(), SharedGlobals.SR_PRODUCT, SharedGlobals.KY_TYPE);
            spType.setSelection(type);
            swType.setChecked(true);
        }
    }
}
