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
import com.fatiner.platehandler.managers.shared.SharedRecipeManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnItemSelected;

public class SettingsProductFragment extends PrimaryFragment {

    @BindView(R.id.switch_alphabetical_frag_sttprod)
    Switch switchAlphabetical;
    @BindView(R.id.switch_type_frag_sttprod)
    Switch switchType;
    @BindView(R.id.spin_type_frag_sttprod)
    Spinner spinType;

    @OnCheckedChanged(R.id.switch_alphabetical_frag_sttprod)
    public void onCheckedChangedSwitchAlphabetical(boolean checked){
        SharedProductManager.setSharedProductAlphabetical(getContext(), checked);
    }

    @OnCheckedChanged(R.id.switch_type_frag_sttprod)
    public void onCheckedChangedSwitchType(boolean checked){
        if(checked){
            spinType.setVisibility(View.VISIBLE);
        } else {
            spinType.setVisibility(View.GONE);
            SharedProductManager.removeSharedProductType(getContext());
        }
    }

    @OnItemSelected(R.id.spin_type_frag_sttprod)
    public void onItemSelectedSpinType(int id){
        SharedProductManager.setSharedProductType(getContext(), id);
    }

    public SettingsProductFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_product, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tool_settings_frag_sttprod);
        setViewsWithSharedPreferences();
        return view;
    }

    private void setViewsWithSharedPreferences(){
        setSwitchAlphabetical();
        setSpinType();
    }

    private void setSwitchAlphabetical(){
        boolean isChecked = SharedProductManager.getSharedProductAlphabetical(getContext());
        switchAlphabetical.setChecked(isChecked);
    }

    private void setSpinType(){
        if(SharedProductManager.isSharedProductTypeAvailable(getContext())){
            int type = SharedProductManager.getSharedProductType(getContext());
            spinType.setSelection(type);
            switchType.setChecked(true);
        }
    }
}
