package com.fatiner.platehandler.fragments.export;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ExportFragment extends PrimaryFragment {

    @BindView(R.id.et_export)
    EditText etExport;

    @OnTextChanged(R.id.et_export)
    public void onTextChangedEtExport(CharSequence text){}

    @OnClick(R.id.bt_export)
    public void onClickBtExport(){}

    public ExportFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_export);
        setMenuItem(MainGlobals.ID_EXPORT_DRAW_MAIN);
        return view;
    }
}
